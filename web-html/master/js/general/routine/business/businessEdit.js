/**
 * Created by dizl on 2017/4/5.
 */

(function(){
    function initUI(){
        var request = comm.tools.getRequest();
        if(request.busiOpportInfoId!=null && request.busiOpportInfoId>0){
            //加载商机信息
            comm.ajax.ajaxEntity({
                busiCode:busiCode.routine.IBUSIOPPORTINFOFSV_QUERYOPPORTINFO,
                param:{
                    busiOpportInfoId:request.busiOpportInfoId
                },
                callback:function(data,isSucc,msg){
                    if(isSucc){
                        $("#businessForm").form().setData(data.list[0]);
                    }
                }
            });
        }
        $("#businessForm").form().blurValidate();
    }
    function initEvent(){
        //保存数据
        $("#saveBtn").bind("click",function(){
            var data = $("#businessForm").form().getData();
            data["operateType"] = "save";
            comm.ajax.ajaxEntity({
                busiCode:busiCode.routine.IBUSIOPPORTINFOFSV_EDITOPPORTINFO,
                param:data,
                callback:function(data,isSucc,msg){
                    if(isSucc){
                        comm.dialog.notification({
                            type:comm.dialog.type.success,
                            content:"商机保存成功"
                        });
                    }else{
                        comm.dialog.notification({
                            type:comm.dialog.type.error,
                            content:"商机保存失败"+msg
                        });
                    }
                }
            });
        });

        $("#submitBtn").bind("click",function(){
            //校验填写是否正确
            if($("#businessForm").form().validate()){
                var data = $("#businessForm").form().getData();
                data["operateType"] = "submit";
                comm.ajax.ajaxEntity({
                    busiCode:busiCode.routine.IBUSIOPPORTINFOFSV_EDITOPPORTINFO,
                    param:data,
                    callback:function(data,isSucc,msg){
                        if(isSucc){
                            comm.dialog.notification({
                                type:comm.dialog.type.success,
                                content:"新建商机成功",
                                func:function(){
                                    parent.indexModel.closeTab();
                                }
                            });
                        }else{
                            comm.dialog.notification({
                                type:comm.dialog.type.error,
                                content:"新建商机失败"+msg
                            });
                        }
                    }
                });
            }
        });
    }
    function init(){
        initUI();
        initEvent();
    }
    init();
})();