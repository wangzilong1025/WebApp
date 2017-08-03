(function($){
	var process = $.process;

	$.extend(true, process.editors, {
		inputEditor : function(options){
			var _props,_k,_div,_src,_r;
			this.init = function(props, k, div, src, r){
				_props=props; _k=k; _div=div; _src=src; _r=r;
				var placeholder = "";
				var isEdit = true;
				if(options){
					if(options.isEdit!=null){
						isEdit = options.isEdit;
					}
					if(options.placeholder!=null && isEdit){
						placeholder = options.placeholder;
					}
				}

				var obj = $('<input type="text" class="process_input k-textbox" placeholder="'+placeholder+'"/>').val(props[_k].value).change(function(){
					props[_k].value = $(this).val();
				});
				if(!isEdit){
					obj.attr("readonly",true);
				}
				obj.appendTo('#'+_div);

				$('#'+_div).data('editor', this);
			}
			this.destroy = function(){
				$('#'+_div+' input').each(function(){
					_props[_k].value = $(this).val();
				});
			}
		},
		dateEditor:function(options){
			var _props,_k,_div,_src,_r;
			this.init = function(props, k, div, src, r){
				_props=props; _k=k; _div=div; _src=src; _r=r;
				var placeholder = "";
				var isEdit = true;
				if(options){
					if(options.placeholder!=null){
						placeholder = options.placeholder;
					}
					if(options.isEdit!=null){
						isEdit = options.isEdit;
					}
				}
				var obj = $('<input class="process_input" placeholder="'+placeholder+'"/>').change(function () {
					props[_k].value = $(this).data("kendoDatePicker").value().format("yyyy-MM-dd");
				});
				obj.appendTo('#' + _div);
				obj.kendoDatePicker({
					format: "yyyy-MM-dd",
				});
				if(!isEdit){
					obj.data("kendoDatePicker").readonly();
				}

				if(!comm.lang.isEmpty(props[_k].value)){
					obj.data("kendoDatePicker").value(props[_k].value);
				}
				$('#'+_div).data('editor', this);
			}
			this.destroy = function(){
			}
		},
		selectEditor : function(options){
			var _props,_k,_div,_src,_r,obj;
			this.init = function(props, k, div, src, r){
				_props=props; _k=k; _div=div; _src=src; _r=r;
				var placeholder = "";
				var isEdit = true;//是否可编辑
				var data = null;
				var srvId = "";
				var params = "";
				var dataTextField = "text";
				var dataValueField = "value";
				if(options!=null){
					if(!comm.lang.isEmpty(options.isEdit)){
						isEdit = options.isEdit;
					}
					if(!comm.lang.isEmpty(options.data)){
						data = options.data;
					}
					if(!comm.lang.isEmpty(options.srvId)){
						srvId = options.srvId;
					}
					if(!comm.lang.isEmpty(options.params)){
						params = options.params;
					}
					if(!comm.lang.isEmpty(options.dataTextField)){
						dataTextField = options.dataTextField;
					}
					if(!comm.lang.isEmpty(options.dataValueField)){
						dataValueField = options.dataValueField;
					}
					if(!comm.lang.isEmpty(options.placeholder)){
						placeholder = options.placeholder;
					}
				}
				//TODO 需要增加级联操作，待将来优化
				var obj = $('<input class="process_input"/>').change(function () {
					props[_k].value = $(obj).data("kendoComboBox").value();
				});
				obj.appendTo('#' + _div);


				var dataSource = [];
				if(!comm.lang.isEmpty(data)){
					dataSource = data;
				}else if("static"==srvId){
					dataSource = comm.tools.getStaticData(params);
				}else{
					dataSource = {
						transport: {
							read: {
								url: SERVER_URL,
								type: "POST"
							},
							parameterMap: function (params, operation) {
								var jsonRequstParam = {
									"param": params,
									"busiCode": options.srvId
								};
								return comm.ajax.paramWrap(jsonRequstParam);
							}
						},
						batch: true,
						schema: {
							data: options.schemaData
						}
					}
				}
				obj.kendoComboBox({
					placeholder:placeholder,
					dataTextField: dataTextField,
					dataValueField: dataValueField,
					dataSource: dataSource,
					value:props[_k].value
				});

				if(!isEdit){
					obj.data("kendoComboBox").readonly();
				}
				$('#'+_div).data('editor', this);
			};
			this.destroy = function(){
			};
		},
		windowEditor:function(options){
			var _props,_k,_div,_src,_r;
			this.init = function(props, k, div, src, r){
				_props=props; _k=k; _div=div; _src=src; _r=r;
				var placeholder = "";
				var isEdit = true;
				var url = "";//必填参数
				var title="标题";//
				var showText = "text";//必填参数
				var key = 'id';//必填参数
				var contents = [];//用于维护当前内容
				if(options){
					if(options.placeholder!=null){
						placeholder = options.placeholder;
					}
					if(options.isEdit!=null){
						isEdit = options.isEdit;
					}
					if(options.showText!=null){
						showText = options.showText;
					}
					if(options.key!=null){
						key = options.key;
					}
					if(options.title!=null){
						title = options.title;
					}
					if(options.url!=null){
						url = options.url;
					}
				}
				var removeFromArray = function(array, index) {
					if(index != -1){
						array.splice(index, 1);
					}
				};

				var findInArray = function(array, findId) {
					if (array == null || array == undefined) {
						return -1;
					}
					for (var i = 0; i < array.length; i++) {
						if (findId == array[i][key]) {
							return i;
						}
					}
					return -1;
				};

				var container = $("<div data-id='container' class='col-md-12' style='padding:5px 0px;'></div>");
				var operationContainer = $("<div style='text-align: center;' class='col-md-2 padding-top-15' data-id='operation'></div>");
				var contentDivContainer = $("<div style='text-align: center;height: 80px;overflow-y: auto;' class='col-md-10' data-id='operation'></div>");;
				var contentContainer = $("<ul data-id='content' class='pui-list pui-list-view pui-list-view-radius pui-list-view-small' style='min-height:24px;'></ul>");
				var operationAdd = $('<i style="font-size: 20px;cursor: pointer;" class="fa fa-plus-circle pui-text-green-300"></i><br/>');
				var operationMinus = $('<i style="font-size: 20px;cursor: pointer;" class="fa fa-minus-circle pui-text-green-300 padding-top-5"></i>');
				if(props[_k].value != null && props[_k].value!= ""){
					contents = props[_k].value;
					for(var j = 0; j < contents.length; j++){
						var temp = contents[j];
						var item = $("<li id='con_"+temp[key]+"' style='line-height:12px;'><a href='javascript:;'><label class='padding-left-5'>" + temp[showText]+ "</label></a></li>");
						item.bind('click', function (ev) {
							$(this).siblings().removeClass('selected');
							$(this).addClass("selected");
						});
						contentContainer.append(item);
					}
				}
				if(isEdit){
					var thisComponent = this;
					operationAdd.bind('click', function () {
						comm.dialog.window({
							title:title,
							url:url,
							width:450,
							height:300,
							isSec:true,
							callback:function(retObj){
								if(!comm.lang.isEmpty(retObj)) {
									var addItem = $("<li id='con_" + retObj[key] + "' style='line-height:12px;' class='selected'><a href='javascript:;'><label class='padding-left-5 font-blue'>" + retObj[showText] + "</label></a></li>");
									contents.push(retObj);
									addItem.bind('click', function (ev) {
										$(this).siblings().removeClass('selected');
										$(this).addClass("selected");
									});
									contentContainer.append(addItem);
									props[_k].value = contents;
								}
							},
							toolbar:{
								close:false
							}
						});
					});
					operationMinus.bind('click', function () {
						if(contentContainer.find(".selected").length==0){
							return;
						}
						var removeId = contentContainer.find(".selected").attr("id").split("_")[1];
						removeFromArray(contents,findInArray(contents,removeId));
						contentContainer.find(".selected").remove();
						props[_k].value=contents;
					});
					container.append(contentDivContainer).append(operationContainer);
					operationContainer.append(operationAdd).append(operationMinus);
					contentDivContainer.append(contentContainer);
					container.appendTo('#'+_div);
				}else{
					container.append(contentContainer);
					container.appendTo('#'+_div);
				}

				$('#'+_div).data('editor', this);
			}
			this.destroy = function(){
			}
		}
	});
})(jQuery);