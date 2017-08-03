/**
 * Created by dizl on 2017/3/13.
 */
var flowInstanceModel = {};
(function(){

    function initUI(){

    }

    function initEvent(){
        //查询流程信息
        $("#queryProcess").bind("click",function(){
            $("#processGrid").datagrid().load();
        });

        //启动新流程
        $("#newProcess").bind("click",function(){
            //打开流程选择页面
            comm.dialog.window({
                url:"/view/general/routine/flow/esFlowList.html",
                width:500,
                height:450,
                callback:function(data){
                    //调用动态表单打开流程页面
                    var url = comm.tools.getDyncHtmlUrl(data.processDefineId,"CREATE");
                    url = comm.tools.appendRequetParam(url,{
                        processId:data.processId,
                        processDefineId:data.processDefineId
                    });
                    comm.dialog.window({
                        url: url,
                        title:"新建流程"+data.processName,
                        width: 800,
                        height: 450,
                        toolbar:{
                            max:true,
                            min:true,
                        },
                        callback: function (data) {

                        }
                    });
                }
            });
        });
    }

    /**
     * 新建流程
     * */
    function submitNewFlow(){
        //提交流程数据
        debugger;
        var submitData = busiFrameModel.getSubmitData();
        comm.ajax.ajaxEntity({
           busiCode:busiCode.routine.IFLOWINSTANCE_STARTFLOW,
            param:submitData,
            callback:function(data,isSucc,msg){
                if(isSucc){
                    comm.dialog.notification({
                        content:"数据提交成功",
                        type:comm.dialog.type.success
                    });
                }else{

                }
            }
        });
    }

    /**
     * 页面关闭
     * */
    function cancel(){
        comm.dialog.unWindow({
            confirm:false
        });
    }

    function init(){
        initUI();
        initEvent();

        flowInstanceModel = {
            submitNewFlow:submitNewFlow,
            cancel:cancel
        }
    }
    init();
})();