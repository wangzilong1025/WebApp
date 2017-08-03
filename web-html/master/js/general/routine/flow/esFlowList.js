/**
 * Created by dizl on 2017/3/14.
 */
(function(){

    function initUI(){

    }

    function initEvent(){
        $("#okBtn").bind("click",function(){
            var data = $("#processGrid").datagrid().getSelect();
            if(data && data.processDefineId!=null && data.processDefineId!=undefined){
                comm.dialog.unWindow({
                    confirm:true,
                    retVal:{
                        processDefineId:data.processDefineId,
                        processId:data.processId,
                        processName:data.processName
                    }
                });
            }else{
                comm.dialog.notice({
                    position:"center",
                    type:comm.dialog.type.warn,
                    content:"请选择要启动的流程"
                });
            }
        });

        $("#cancelBtn").bind("click",function(){
            comm.dialog.unWindow({
                confirm:false
            });
        });

        $("#queryFiled").bind("keyup",function(){
            if(event.keyCode==13){
                $("#processGrid").datagrid().load();
            }
        });
    }
    function init(){
        initUI();
        initEvent();
    }
    init();
})();

function getParams(){
    var textValue = $("#queryFiled").val();
    var processName;
    var processDefineId;
    if(comm.validate.checkReg(comm.validate.regexp.num,textValue)){
        processDefineId = textValue;
    }else{
        processName = textValue;
    }
    return {
        processType:"1",
        processName:processName,
        processDefineId:processDefineId
    };
}