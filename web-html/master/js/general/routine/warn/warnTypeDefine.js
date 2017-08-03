/**
 * @author houlf3
 * Created on 2017/3/7 14:51
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
var warnTypeModule = {};
function initNeedDeal (dataItem){
    if(dataItem.needDeal == 0){
        return '需要处理';
    }else if (dataItem.needDeal == 1){
        return '自动处理';
    }
}
function initwarnClass (dataItem){
    if(dataItem.warnClass == 0){
        return '集团告警';
    }else if (dataItem.warnClass == 1){
        return '个人告警';
    }
}


(function ($) {


    var getParams = function (data) {
        data = data || {};
        return data;
    };


    $("#resultGrid").datagrid().dbclick(function () {
        var data = $("#resultGrid").datagrid().getSelect();
        comm.dialog.window({
            title:data.warnTypeName+" 字段信息",
            url:"/view/general/routine/warn/warnColumnDefine.html?warnTypeId="+data.warnTypeId,
            width:700,
            height:400,
            isSec:true
        });
    });

    $("#export").click(function(){
        $("#resultGrid").datagrid().exportExcel("告警类型配置导出文件",{"warnClass":"WARN_TYPE_STATE"});
    });


    $("#delButton").click(function(){
        var data = $("#resultGrid").datagrid().getSelects();
        if(data == '' ){
            // comm.dialog.notification({
            //
            //     content: "没有选择要删除的告警类型！"
            // });
            comm.dialog.notice({
                type:comm.dialog.type.warn,
                position:"center",
                content:"没有选择要删除的告警类型！"
            });
        }else{
            comm.dialog.confirm({
                title: "删除确认",
                content: "确认删除所选记录吗？同时将删除告警字段、告警反馈信息！",
                okBtnText:"删除",
                cancelBtnText:"取消",
                func:function (isdel) {
                    if(isdel){
                        var options = {
                            "param": {"warnTypeId": data[0].warnTypeId},
                            "busiCode": busiCode.routine.IWARNTYPEDEFINEFSV_DELWARNTYPE,
                            "moduleName": "common",
                            "sync": false,
                            "callback": function (data, isSucc, msg) {
                                if (isSucc) {
                                    // comm.dialog.notification({
                                    //     content: "删除成功！"
                                    // });
                                    comm.dialog.notice({
                                        type:comm.dialog.type.success,
                                        position:"center",
                                        content:"删除成功！"
                                    });

                                    $("#resultGrid").datagrid().load();
                                } else {
                                    // comm.dialog.notification({
                                    //     content: "删除失败！"+msg
                                    // });
                                    comm.dialog.notice({
                                        type:comm.dialog.type.error,
                                        position:"center",
                                        content:"删除失败！"
                                    });

                                }
                            }
                        }
                        comm.ajax.ajaxEntity(options);
                    }
                }
            });

        }
    });

    $("#addButton").click(function(){
        comm.dialog.window({
            title:"添加新告警类型",
            url:"/view/general/routine/warn/addWarnType.html",
            width:300,
            height:460,
            isSec:true,
            callback:function () {
                $("#resultGrid").datagrid().load();
                comm.dialog.notice({
                    type:comm.dialog.type.success,
                    position:"center",
                    content:"增加成功！"
                });

                // comm.dialog.notification({
                //     content: "增加成功！"
                // });
            }
        });
    });


    $("#updateButton").click(function(){
        var data = $("#resultGrid").datagrid().getSelects();
        if(data == '' || data.warnTypeId == '' ){
            comm.dialog.notice({
                type:comm.dialog.type.warn,
                position:"center",
                content:"请选择要修改的数据！"
            });

            // comm.dialog.notification({
            //     content: "请选择要修改的数据！"
            // });
        }else{
            comm.dialog.window({
                title:"更新告警类型信息",
                url:"/view/general/routine/warn/updateWarnType.html?warnTypeId="+data[0].warnTypeId,
                width:300,
                height:460,
                isSec:true,
                callback:function (e) {
                    $("#resultGrid").datagrid().load();
                    // comm.dialog.notification({
                    //     content: "更新成功！"
                    // });
                    comm.dialog.notice({
                        type:comm.dialog.type.success,
                        position:"center",
                        content:"更新成功！"
                    });

                }
            });
        }
    });

    $("#warnButton").click(function(){
        var data = $("#resultGrid").datagrid().getSelects();
        if(data == '' || data.warnTypeId == '' ){
            // comm.dialog.notification({
            //     content: "请选择一行数据！"
            // });
            comm.dialog.notice({
                type:comm.dialog.type.warn,
                position:"center",
                content:"请选择一行数据！"
            });

        }else{
            comm.dialog.window({
                title:data[0].warnTypeName+" 字段信息",
                url:"/view/general/routine/warn/warnColumnDefine.html?warnTypeId="+data[0].warnTypeId,
                width:700,
                height:400,
                isSec:true
            });
        }
    });

    $("#dealRule").click(function(){
        var data = $("#resultGrid").datagrid().getSelects();
        if(data == '' || data.warnTypeId == '' ){
            // comm.dialog.notification({
            //     content: "请选择一行数据！"
            // });
            comm.dialog.notice({
                type:comm.dialog.type.warn,
                position:"center",
                content:"请选择一行数据！"
            });
        }else{
            comm.dialog.window({
                title:data[0].warnTypeName+" 反馈信息",
                url:"/view/general/routine/warn/warnDealRule.html?warnTypeId="+data[0].warnTypeId,
                width:800,
                height:550,
                isSec:true
            });
        }
    });



    $.extend(warnTypeModule, {
        getParams: getParams
    });


})(jQuery);