/**
 * @author hyf
 * Created on 2017/3/8 15:23
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

var warnColumnModule = {};

(function ($) {


    var warnTypeId = comm.browser.getParameter("warnTypeId","");
    $("#warnTypeId").val(warnTypeId);


    var getParams = function (data) {
        data = data || {};
        var warnTypeId = $("#warnTypeId").val();
        // var email = $("#email").val();
        // data.name = name;
        // data.email= email;
        data.warnTypeId = warnTypeId;
        return data;
    };


    $("#delButton").click(function(){
        var data = $("#resultGrid").datagrid().getSelects();
        if(data == '' ){
            comm.dialog.notice({
                type:comm.dialog.type.warn,
                position:"center",
                content:"没有选择要删除的字段！"
            });

        }else{
            comm.dialog.confirm({
                title: "删除确认",
                content: "确认删除所选记录吗？",
                okBtnText:"删除",
                cancelBtnText:"取消",
                func:function (isdel) {
                    if(isdel){
                        var options = {
                            "param": {"warnColumnId": data[0].warnColumnId},
                            "busiCode": busiCode.routine.IWARNCOLUMNDEFINEFSV_DELWARNCOLUMN,
                            "moduleName": "common",
                            "sync": false,
                            "callback": function (data, isSucc, msg) {
                                if (isSucc) {

                                    comm.dialog.notice({
                                        type:comm.dialog.type.success,
                                        position:"center",
                                        content:"删除成功！"
                                    });

                                    $("#resultGrid").datagrid().load();
                                } else {
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
            title:"添加告警字段",
            url:"/view/general/routine/warn/addWarnColumn.html?warnTypeId="+$("#warnTypeId").val(),
            width:400,
            height:300,
            isSec:true,
            callback:function () {
                $("#resultGrid").datagrid().load();
                comm.dialog.notice({
                    type:comm.dialog.type.success,
                    position:"center",
                    content:"增加成功！"
                });
            }
        });
    });


    $("#updateButton").click(function() {
        var data = $("#resultGrid").datagrid().getSelects();
        if (data == '' || data.warnColumnId == '') {
            comm.dialog.notice({
                type:comm.dialog.type.warn,
                position:"center",
                content:"请选择要修改的数据！"
            });
        } else {
            comm.dialog.window({
                title: "更新告警字段信息",
                url: "/view/general/routine/warn/updateWarnColumn.html?warnColumnId=" + data[0].warnColumnId,
                width: 400,
                height: 300,
                isSec: true,
                callback: function (e) {
                    $("#resultGrid").datagrid().load();
                    comm.dialog.notice({
                        type:comm.dialog.type.success,
                        position:"center",
                        content:"更新成功！"
                    });
                }
            });
        }
    });


    $.extend(warnColumnModule, {
        getParams: getParams
    });

})(jQuery);