(function(){

    var process = $.process;

    function initUI(){
        var request = comm.tools.getRequest();
        var isEdit = true;
        var jsonData = {};
        if(request){
            var operType = request.operType;
            var processId = request.processId;
            if("new"==operType){
                isEdit = true;
            }else if("update"==operType){
                isEdit = true;
                var json = comm.storage.SessionStorage.get("PROCESS_INFO_JSON_UPDATE");
                if(!comm.lang.isEmpty(json)){
                    jsonData = JSON.parse(json);
                }
            }else if("view"==operType){
                var json = comm.storage.SessionStorage.get("PROCESS_INFO_JSON_VIEW");
                if(!comm.lang.isEmpty(json)){
                    jsonData = JSON.parse(json);
                }
                isEdit = false;
            }
        }

        $.extend(true,process.config.props.props,{
            processName : {name:'processName', label:'流程名称 *', value:'', editor:function(){return new process.editors.inputEditor({placeholder:"请输入流程名称",isEdit:isEdit});}},
            processType: {name:'processType', label:'流程类型 *', value:'1', editor:function(){return new process.editors.selectEditor({isEdit:isEdit,data:[{text:'日常工作流程',value:'1'},{text:'独立流程',value:2}]});}},
            validDate : {name:'validDate', label:'生效时间', value:'', editor:function(){return new process.editors.dateEditor({isEdit:isEdit});}},
            expireDate : {name:'expireDate', label:'失效时间', value:'', editor:function(){return new process.editors.dateEditor({isEdit:isEdit});}},
            processDesc : {name:'processDesc', label:'流程描述', value:'', editor:function(){return new process.editors.inputEditor({isEdit:isEdit});}},
            processParam : {name:'processParam',label:"流程参数",value:'',editor: function(){return new process.editors.windowEditor({isEdit:isEdit,title:"流程参数",url:PROJECT_URL+"/view/general/common/process/processParam.html",showText:"text",key:"paramCode"});}}

        });

        $.extend(true,process.config.tools.states,{
            start : {
                type : 'start',
                name : {text:'开始节点'},
                text : {text:'开始'},
                img : {src : '/resources/img/process_start.png',width : 13, height:13},
                props : {
                    text: {name:'text',label: '开始节点', value:'开始', editor: function(){return new process.editors.textEditor();}}
                }},
            end : {type : 'end',
                name : {text:'结束节点'},
                text : {text:'结束'},
                img : {src : '/resources/img/process_end.png',width : 13, height:13},
                props : {
                    text: {name:'text',label: '结束节点', value:'结束', editor: function(){return new process.editors.textEditor();}}
                }},
            task : {type : 'task',
                name : {text:'任务节点'},
                text : {text:'任务节点'},
                img : {src : '/resources/img/process_commtask.png',width :13, height:13},
                props : {
                    text: {name:'text', label: '任务名称 *', value:'任务节点', editor: function(){return new process.editors.textEditor();}},
                    taskDesc: {name:'taskDesc', label: '任务描述', value:'', editor: function(){return new process.editors.inputEditor({isEdit:isEdit});}},
                    planTime: {name:'planTime', label: '处理时长', value:'', editor: function(){return new process.editors.inputEditor({isEdit:isEdit,placeholder:"与quartz表达式(* * * * * ?)一致"});}},
                    autoDealFlag: {name:'autoDealFlag', label: '超时自动处理', value:'0', editor: function(){return new process.editors.selectEditor({isEdit:isEdit,data:[{text:"否",value:0},{text:"是",value:1}]});}},
                    taskDealClass:{name:'taskDealClass', label: '任务处理类', value:'', editor: function(){return new process.editors.inputEditor({isEdit:isEdit});}},
                    taskDealParam:{name:'taskDealParam', label: '任务处理参数', value:'', editor: function(){return new process.editors.inputEditor({isEdit:isEdit});}},
                    taskParam: {name:'taskParam', label: '任务参数', value:'', editor: function(){return new process.editors.windowEditor({isEdit:isEdit,title:"任务参数",url:PROJECT_URL+"/view/general/common/process/processParam.html",showText:"text",key:"paramCode"});}},
                    taskDealPerson: {name:'taskDealPerson', label: '任务处理人 *', value:'', editor: function(){return new process.editors.windowEditor({isEdit:isEdit,title:"任务处理人",url:PROJECT_URL+"/view/general/common/process/taskHandler.html",showText:"text",key:"text"});}}
                }},
            childProcess : {type : 'childProcess',
                name : {text:'子流程'},
                text : {text:'子流程'},
                img : {src : '/resources/img/process_child_process.png',width :13, height:13},
                props : {
                    text: {name:'text', label: '子流程名称', value:'', editor: function(){return new process.editors.textEditor();}, value:'任务'},
                    childProcess: {name:'childProcess', label: '子流程 *', value:'', editor: function(){return new process.editors.selectEditor({isEdit:isEdit,srvId:"ICFGPROCESSFSV_QUERYVALIDPROCESSINFO",params:{},schemaData:"data.list",dataTextField:"processName",dataValueField:"processDefineId",placeholder:"请选择子流程"});}},
                }}
        });

        $(function() {
            $('#process').process({
                basePath : "",
                restore : jsonData,
                tools : {
                    save : {
                        onclick : function(data) {
                            //校验数据提交内容
                            if(validSubmitData(data)){
                                //调用后台进行变更操作
                                comm.ajax.ajaxEntity({
                                    busiCode:busiCode.common.ICFGPROCESSFSV_EDITPROCESS,
                                    param:{
                                        operType: request.operType,
                                        processId:request.processId,
                                        data : data
                                    },
                                    callback:function(data,isSucc,msg){
                                        if(isSucc){//接口调用成功
                                            comm.dialog.notification({
                                                type:comm.dialog.type.success,
                                                content:"数据处理成功"
                                            });
                                        }else{//接口调用失败
                                            comm.dialog.notification({
                                               type:comm.dialog.type.error,
                                               content:msg,
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });
            if(isEdit==false){
                $("#process_tools").hide();//隐藏操作类
            }
        });

        //校验提交数据
        function validSubmitData(data){
            var jsonData = JSON.parse(data);
            //流程名称是否为空
            if(comm.lang.isEmpty(jsonData.props.props.processName.value)){
                comm.dialog.notification({
                    content:"流程名称不能为空",
                    type:comm.dialog.type.error
                });
                return false;
            }
            //看是否有多个开始节点
            var startCount = 0;
            var endCount = 0;
            //看是否有多个结束节点
            for(var c in jsonData.states){
                var rect = jsonData.states[c];
                if(rect.type=="start"){
                    startCount++;
                }else if(rect.type=="end"){
                    endCount++;
                }else{
                    if(rect.type=="task"){
                        //任务名称是否为空
                        if(comm.lang.isEmpty(rect.props.text.value)){
                            comm.dialog.notification({
                                content:"人工节点【"+c+"】的任务名称不能为空",
                                type:comm.dialog.type.error
                            });
                            return false;
                        }
                    }else if(rect.type=="childProcess"){
                        //子流程是否为空
                        if(comm.lang.isEmpty(rect.props.text.value)){
                            comm.dialog.notification({
                                content:"子流程【"+c+"】子流程名称不能为空",
                                type:comm.dialog.type.error
                            });
                            return false;
                        }
                        //子流程是否为空
                        if(comm.lang.isEmpty(rect.props.childProcess.value)){
                            comm.dialog.notification({
                                content:"子流程【"+rect.props.text.value+"】子流程编号不能为空",
                                type:comm.dialog.type.error
                            });
                            return false;
                        }
                    }
                }
                var hasFrom = false;
                var hasTo = false;
                for(var d in jsonData.paths){
                    var from = jsonData.paths[d].from;
                    var to = jsonData.paths[d].to;
                    if(from==c){
                        hasFrom = true;
                    }
                    if(to==c){
                        hasTo = true;
                    }
                }
                if(!hasFrom){
                    if(rect.type!="end"){
                        comm.dialog.notification({
                            content:"【"+rect.props.text.value+"】节点，缺少连接线",
                            type:comm.dialog.type.error
                        });
                        return false;
                    }
                }
                if(!hasTo){
                    if(rect.type!="start"){
                        comm.dialog.notification({
                            content:"【"+rect.props.text.value+"】节点，缺少连接线",
                            type:comm.dialog.type.error
                        });
                        return false;
                    }
                }
            }
            if(startCount==0){
                comm.dialog.notification({
                    content:"不存在【开始节点】",
                    type:comm.dialog.type.error
                });
                return false;
            }else if(startCount>1){
                comm.dialog.notification({
                    content:"存在多个【开始节点】",
                    type:comm.dialog.type.error
                });
                return false;
            }
            if(endCount==0){
                comm.dialog.notification({
                    content:"不存在【结束节点】",
                    type:comm.dialog.type.error
                });
                return false;
            }else if(endCount>1){
                comm.dialog.notification({
                    content:"存在多个【结束节点】",
                    type:comm.dialog.type.error
                });
                return false;
            }
            //校验是否有多条相同的连接线
            if(jsonData.paths!=null){
                var fromtoArray = [];
                for(var d in jsonData.paths){
                    var from = jsonData.paths[d].from;
                    var to = jsonData.paths[d].to;
                    var key = from+"_"+to;
                    for(var k=0;k<fromtoArray.length;k++){
                        if(fromtoArray[k]==key){
                            var fromText = "";
                            var toText = "";
                            for(var c in jsonData.states){
                                if(c==from){
                                    fromText = jsonData.states[c].props.text.value;
                                }
                                if(c==to){
                                    toText = jsonData.states[c].props.text.value;
                                }
                            }
                            comm.dialog.notification({
                                content:"从【"+fromText+"】到【"+toText+"】存在多条连线",
                                type:comm.dialog.type.error
                            });
                            return false;
                        }
                    }
                    fromtoArray.push(key);
                }
            }
            return true;
        }
    }

    function initEvent(){

    }

    function init(){
        initUI();
        initEvent();
    }

    init();
})();