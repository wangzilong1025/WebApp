/**
 * Created by dizl on 2017/2/16.
 * 平铺的下拉框
 */
$.fn.tilecombobox = function () {
    var that = this;
    var target = that[0];
    if ($.data(target, 'tilecombobox')) {
        return $(target).data("tilecombobox");
    }
    return $.fn.tilecombobox.create(target);
};

$.fn.tilecombobox.create = function(target){
    var options = $.fn.form.parseOptions(target, {
        schemaData: 'data',
        hasAll: false,
        multFlag:false,
        hasMore:true,
        dataTextField:"text",
        dataValueField:"value"
    }, {
        srvId: "string",
        param: 'string',
        dataTextField: "string",
        dataValueField: "string",
        defaultValue: "string",
        schemaData: 'string',
        type: 'string',
        codeType:'string',
        codeValue:'string',
        parentCode:'string',
        parentCodeField:'string',
        hasAll: 'boolean',
        hasMore:'boolean',
        moduleName:'string',
        multFlag:'boolean',
        id:'string'
    });
    var filedData = [];
    var defaultValues = [];
    if(!comm.lang.isEmpty(options.defaultValue)){
        defaultValues = options.defaultValue.split(",")
    }
    if (options.srvId) {
        var param;
        if (options.beforeLoad) {
            param = options.beforeLoad({});
        }else if(!comm.lang.isEmpty(options.param)){
            param = JSON.parse(options.param);
        }
        comm.ajax.ajaxEntity({
            busiCode:options.srvId,
            moduleName:moduleName,
            param:param,
            callback:function(data){
                var data = data[options.schemaData];
                if(data!=null && data.length>0){
                    for(var i=0;i<data.length;i++){
                        var selected = false;
                        var value = data[i][options.dataValueField];
                        var text = data[i][options.dataTextField];
                        if(defaultValues!=null && defaultValues.length>0){
                            for(var j=0;j<defaultValues.length;j++){
                                if(defaultValues[j]==value){
                                    selected = true;
                                    break;
                                }
                            }
                        }
                        filedData.push({
                            text:text,
                            value:value,
                            selected:selected
                        })
                    }
                }
                var obj = $.fn.tilecombobox.initUI(filedData,options,target);
                $.fn.tilecombobox.initEvent(options);
                return obj;
            }
        });
    } else if (options.type == 'static') {
        var codeType = options.codeType;
        var codeValue = options.codeValue;
        var parentCode = options.parentCode;
        var param;
        if (options.beforeLoad) {
            param = options.beforeLoad({});
        }else if(!comm.lang.isEmpty(options.param)){
            param = JSON.parse(options.param);
        }
        if(param && !comm.lang.isEmpty(param.codeType)){
            codeType = param.codeType;
            codeValue = param.codeValue;
            parentCode = param.parentCode;
        }
        if(!comm.lang.isEmpty(options.parentCodeField)){
            var parentValue = $("#"+options.parentCodeField).tilecombobox().value();
            if(parentValue!=null){
                parentCode = parentValue;
            }
        }
        options.codeType= codeType;
        comm.tools.getStaticData({
            codeType:codeType,
            codeValue:codeValue,
            parentCode:parentCode,
            callback:function(data){
                if(data!=null && data.length>0){
                    for(var i=0;i<data.length;i++){
                        var selected = false;
                        var codeValue = data[i]["codeValue"];
                        var codeName = data[i]["codeName"];
                        var codeType = data[i]["codeType"];
                        var parentCode = data[i]["parentCode"];
                        var extendCode = data[i]["extendCode"];
                        var extendCode2 = data[i]["extendCode2"];
                        if(defaultValues!=null && defaultValues.length>0){
                            for(var j=0;j<defaultValues.length;j++){
                                if(defaultValues[j]==codeValue){
                                    selected = true;
                                    break;
                                }
                            }
                        }
                        filedData.push({
                            text:codeName,
                            value:codeValue,
                            codeType:codeType,
                            parentCode:parentCode,
                            extendCode:extendCode,
                            extendCode2:extendCode2,
                            selected:selected
                        })
                    }
                }
                var obj = $.fn.tilecombobox.initUI(filedData,options,target);
                $.fn.tilecombobox.initEvent(options);
                return obj;
            }
        });
    }else{
        var childOptions = $(target).children("option");
        if(childOptions!=null && childOptions.length>0){
            for(var i=0;i<childOptions.length;i++){
                var selected = false;
                var text = $(childOptions[i]).text();
                var value = $(childOptions[i]).attr("value");
                if(defaultValues!=null && defaultValues.length>0){
                    for(var j=0;j<defaultValues.length;j++){
                        if(defaultValues[j]==value){
                            selected = true;
                            break;
                        }
                    }
                }
                filedData.push({
                    text:text,
                    value:value,
                    selected:selected
                });
            }
        }
        var obj = $.fn.tilecombobox.initUI(filedData,options,target);
        $.fn.tilecombobox.initEvent(options);
        return obj;
    }

    var o = {};
    o.value = function(){
        return defaultValues;
    };
    return o;
};

$.fn.tilecombobox.initUI = function(data,options,target){
    var innerHtml = "";
    if (data != null && data.length > 0) {
        var moreHtml = "";
        var textCount = 0;
        var targetWidth = $(target.parentElement).width();
        var hasSelected = false;
        if (options.hasAll) {
            moreHtml = "<a id='aiui-all" + options.id + "' value='' text='全部'";
            textCount = 2 * 12 + 25;
        }
        var isFirstSpan = true;
        for (var i = 0; i < data.length; i++) {
            innerHtml += "<a value='" + data[i].value + "' text='" + data[i].text + "' isSelected='" + data[i].selected + "'"
            if (options.type == "static") {
                innerHtml += " codeType='" + data[i].codeType + "' parentCode='" + data[i].parentCode + "' extendCode='" + data[i].extendCode + "' extendCode2='" + data[i].extendCode2 + "'"
            }
            if (data[i].selected) {
                hasSelected = true;
                innerHtml += " class='font-blue font-bold'"
            }
            innerHtml += ">" + data[i].text + "</a>";
            textCount += data[i].text.length * 12 + 25;
            if (options.hasMore == true || options.hasMore == "true") {
                if (textCount > targetWidth - 180) {
                    if (isFirstSpan) {
                        innerHtml += "<a class='float-right padding-right-20' id='aiui-more" + options.id + "' isSelected='false'>更多<i class='fa fa-chevron-down font-greye3 font10'></i></a>"
                    }
                    innerHtml += "</span><span style='display:none' aiuiSpanFlag='true'><br/>"
                    textCount = 0;
                    isFirstSpan = false;
                }
            } else {
                if (textCount > targetWidth - 30) {
                    innerHtml += "</span><span><br/>";
                    textCount = 0;
                }
            }
        }
        if (hasSelected == false) {
            moreHtml += " class='font-blue font-bold'"
        }
        moreHtml += ">全部</a>";
        innerHtml = "<span>" + moreHtml + innerHtml + "</span>";
    } else {
        if (options.hasAll) {
            innerHtml = "<span><a>全部</a></span>";
        } else {
            innerHtml = "<span><a></a></span>"
        }
    }
    var childElement = document.createElement("span");
    childElement.id=options.id;
    childElement.innerHTML = innerHtml;
    var parentElement = target.parentElement;
    parentElement.removeChild(target);
    parentElement.appendChild(childElement);

    $("#" + options.id + " >span > a").bind("click", function () {
        var id = $(this).attr("id");
        var value = $(this).attr("value");
        var isSelected = $(this).attr("isSelected");
        if (id == "aiui-more" + options.id) {//显示影藏所有项
            var spans = $("#" + options.id + " > span[aiuiSpanFlag='true']");
            if (spans != null && spans.length > 0) {
                for (var i = 0; i < spans.length; i++) {
                    $(spans[i]).toggle();
                }
            }
            if (isSelected == false || isSelected == "false") {
                $(this).attr("isSelected", "true");
                $(this).html("收起<i class='fa fa-chevron-up font-greye3 font10'></i>");
            } else {
                $(this).attr("isSelected", "false");
                $(this).html("更多<i class='fa fa-chevron-down font-greye3 font10'></i>");
            }
        } else if (id == "aiui-all" + options.id) {//选择全部
            var spans = $("#" + options.id + " > span > a[isSelected='true'][id!='aiui-more-" + options.id + "']");//找到所有已经选择的项
            if (spans != null && spans.length > 0) {
                for (var i = 0; i < spans.length; i++) {
                    $(spans[i]).removeClass("font-blue").removeClass("font-bold").attr("isSelected", "false");
                }
            }
            $(this).addClass("font-blue").addClass("font-bold").attr("isSelected", "true");
        } else {
            var isSelected = $(this).attr("isSelected");
            if (isSelected == "true") {//如果已选择，则取消选择
                $(this).removeClass("font-blue").removeClass("font-bold").attr("isSelected", "false");
                if (options.hasAll) {
                    //判断当前是否所有都取消
                    var spans = $("#" + options.id + " > span > a[isSelected='true'][id!='aiui-more" + options.id + "']");//找到所有已经选择的项
                    var hasSelected = false;
                    if (spans == null || spans.length <= 0) {
                        $("#aiui-all" + options.id).addClass("font-blue").addClass("font-bold").attr("isSelected", "true");//设置不全选
                    }
                }
            } else {
                //判断是否多选
                if (options.multFlag == false || options.multFlag == "false") {
                    var spans = $("#" + options.id + " > span > a[isSelected='true'][id!='aiui-more" + options.id + "']");//找到所有已经选择的项
                    if (spans != null && spans.length > 0) {
                        for (var i = 0; i < spans.length; i++) {
                            $(spans[i]).removeClass("font-blue").removeClass("font-bold").attr("isSelected", "false");
                        }
                    }
                }

                $(this).addClass("font-blue").addClass("font-bold").attr("isSelected", "true");
                if (options.hasAll) {
                    $("#aiui-all" + options.id).removeClass("font-blue").removeClass("font-bold").attr("isSelected", "false");//设置不全选
                }
            }
            if (options.afterclick)
                options.afterclick(value);
        }
    });
    var retObj = $("#" + options.id);
    retObj.value = function () {
        var retVal = [];
        var spans = $("#" + this.attr("id") + " > span > a[isSelected='true'][id!='aiui-more" + this.attr("id") + "'][id!='aiui-all"+ this.attr("id") + "']");//找到所有已经选择的项
        if (spans != null && spans.length > 0) {
            for (var i = 0; i < spans.length; i++) {
                retVal.push($(spans[i]).attr("value"));
            }
        }
        return retVal;
    };
    $.data($("#" + options.id)[0], 'tilecombobox', retObj);
    return retObj;;
};

$.fn.tilecombobox.initEvent=function(options){
    if(!comm.lang.isEmpty(options.parentCodeField)){//如果有父元素
        $("#" + options.parentCodeField + " > span > a[id!='aiui-more" + options.parentCodeField + "']").bind('click', function (e) {
            var parentCode = $("#"+options.parentCodeField).tilecombobox().value();
            comm.tools.getStaticData({
                codeType: options.codeType,
                parentCode: parentCode,
                callback: function (data) {
                    var filedData = [];
                    if (data != null && data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            var selected = false;
                            var codeValue = data[i]["codeValue"];
                            var codeName = data[i]["codeName"];
                            var codeType = data[i]["codeType"];
                            var parentCode = data[i]["parentCode"];
                            var extendCode = data[i]["extendCode"];
                            var extendCode2 = data[i]["extendCode2"];
                            filedData.push({
                                text: codeName,
                                value: codeValue,
                                codeType: codeType,
                                parentCode: parentCode,
                                extendCode: extendCode,
                                extendCode2: extendCode2,
                                selected: selected
                            })
                        }
                    }
                    $.fn.tilecombobox.initUI(filedData, options, $("#"+options.id)[0]);
                }
            });
        });
    }
};
