/**
 * Created by dizl on 2017/2/15.
 */

function getParams(data){
    data = data || {};
    var value = $("#queryFiled").val();
    var processName = "";
    var processDefineId = "";
    if(!comm.lang.isEmpty(value)){
        if(comm.validate.checkReg(comm.validate.regexp.num,value)){
            processDefineId = value;
        }else{
            processName = value;
        }
    }

    data.processName = processName;
    data.processDefineId = processDefineId;
    try{
        data.processState = $("#processState").tilecombobox().value();
        var startDate = $("#startDate").datebox().value();
        if(startDate!=null){
            data.startDate = startDate.format("yyyy-MM-dd");
        }
        var endDate = $("#endDate").datebox().value();
        if(endDate!=null){
            data.endDate = endDate.format("yyyy-MM-dd");
        }
    }catch(e){

    }

    return data;
}

/**
 * 流程启用
 * */
function useProcess(data){
    comm.dialog.confirm({
        content:"确定要启用该流程？",
        func:function(flag){
            if(flag){
                //调用后台接口，将流程设置为已启用
                comm.ajax.ajaxEntity({
                    busiCode:busiCode.common.ICFGPROCESSFSV_EDITPROCESS,
                    param:{
                        operType:"use",
                        processId:data.processId
                    },
                    callback:function(data,isSucc,msg){
                        if(isSucc){
                            comm.dialog.notification({
                                type:comm.dialog.type.success,
                                content:"数据处理成功",
                                func:function(){
                                    //重新加载页面
                                    $("#queryFiled").val(data.processDefineId);
                                    $("#processGrid").datagrid().load();
                                }
                            })
                        }else{
                            comm.dialog.notification({
                                type:comm.dialog.type.error,
                                content:msg
                            });
                        }
                    }
                })
            }
        }
    });
}

/**
 * 流程修改
 * */
function updProcess(data){
    //打开流程详细页面
    var json = "";
    if(data.processJson1!=null){
        json = json + data.processJson1;
    }
    if(data.processJson2!=null){
        json = json + data.processJson2;
    }
    if(data.processJson3!=null){
        json = json + data.processJson3;
    }
    if(data.processJson4!=null){
        json = json + data.processJson4;
    }
    if(data.processJson5!=null){
        json = json + data.processJson5;
    }
    comm.storage.SessionStorage.set("PROCESS_INFO_JSON_UPDATE",json);
    parent.indexModel.addTab("修改流程信息","/view/general/common/process/processView.html?operType=update&processId="+data.processId);
}

/**
 * 流程删除
 * */
function delProcess(data){
    //删除流程
    comm.dialog.confirm({
        content:"确定要删除该流程？",
        func:function(flag){
            if(flag){
                //调用后台接口，将流程设置为已启用
                comm.ajax.ajaxEntity({
                    busiCode:busiCode.common.ICFGPROCESSFSV_EDITPROCESS,
                    param:{
                        operType:"delete",
                        processId:data.processId
                    },
                    callback:function(data,isSucc,msg){
                        if(isSucc){
                            comm.dialog.notification({
                                type:comm.dialog.type.success,
                                content:"数据处理成功",
                                func:function(){
                                    //重新加载页面
                                    $("#queryFiled").val(data.processDefineId);
                                    $("#processGrid").datagrid().load();
                                }
                            })
                        }else{
                            comm.dialog.notification({
                                type:comm.dialog.type.error,
                                content:msg
                            });
                        }
                    }
                })
            }
        }
    });
}

(function(){
    function init(){
        initUI();
        initEvent();
    }
    function initUI(){

    }

    function initEvent(){
        $("#queryProcess").bind("click",function(){//查询按钮添加事件
            $("#processGrid").datagrid().load();
        });

        $("#queryFiled").bind("keyup",function(event){
            if(event.keyCode==13){//点击回车键的时候，重新加载
                $("#processGrid").datagrid().load();
            }
        });

        $("#newProcess").bind("click",function(){//新增按钮添加事件
            parent.indexModel.addTab("新建流程","/view/general/common/process/processView.html?operType=new");
        });

        $("#processGrid").datagrid().dbclick(function(data,element){
            //打开流程详细页面
            var json = "";
            if(data.processJson1!=null){
                json = json + data.processJson1;
            }
            if(data.processJson2!=null){
                json = json + data.processJson2;
            }
            if(data.processJson3!=null){
                json = json + data.processJson3;
            }
            if(data.processJson4!=null){
                json = json + data.processJson4;
            }
            if(data.processJson5!=null){
                json = json + data.processJson5;
            }
            comm.storage.SessionStorage.set("PROCESS_INFO_JSON_VIEW",json);
            parent.indexModel.addTab("查看流程信息","/view/general/common/process/processView.html?operType=view&processId="+data.processId);
        });
    }
    init();
})();