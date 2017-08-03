/**
 * @author zhangruiping
 * Created on 17/3/3 下午1:53
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
var dyncBusiManageModule = {};
(function ($) {

    var getParams = function (data) {
        var remark = $("#remark").val();
        var state = $("#state").combobox().value();
        data = data || {};
        data.remark = remark;
        data.state = state;
        return data;
    };


    var initUI = function () {
        $("#toolbar").kendoToolBar({
            items: [
                {type: "button", text: "启用", click: startBusi},
                {type: "button", text: "停用", click: lockBusi},
                {type: "button", text: "修改", click: editBusi},
                {type: "button", text: "新增", click: addBusi}
            ]
        });
    };

    var initEnv = function () {
        $("#queryButton").click(function () {
            $("#dyncBuisGird").datagrid().load();
        });
    };

    var startBusi = function () {
        var select = $("#dyncBuisGird").datagrid().getSelect();

        if (select.BUSI_FRAME_ID) {
            if (select.STATE != 2) {
                comm.dialog.notification({
                    title: "警告",
                    type: comm.dialog.type.warn,
                    content: "当前业务已处于启用状态！"
                });
                return;
            }
            comm.ajax.ajaxEntity({
                busiCode: "ICFGDYNCCOMMONFSV_STARTBUSI",
                param: {
                    busiFrameId: select.BUSI_FRAME_ID
                },
                callback: function (data, isSucc, msg) {
                    if (isSucc) {
                        comm.dialog.notification({
                            title: "成功",
                            type: comm.dialog.type.success,
                            content: "启用业务成功!"
                        });
                        $("#dyncBuisGird").datagrid().load();
                    } else {
                        comm.dialog.notification({
                            title: "错误",
                            type: comm.dialog.type.error,
                            content: "启用业务失败!" + msg
                        });
                    }
                }
            });
        } else {
            comm.dialog.notification({
                title: "警告",
                type: comm.dialog.type.warn,
                content: "请选择具体的业务！"
            });
        }

    };
    var lockBusi = function () {
        var select = $("#dyncBuisGird").datagrid().getSelect();

        if (select.BUSI_FRAME_ID) {
            if (select.STATE != 1) {
                comm.dialog.notification({
                    title: "警告",
                    type: comm.dialog.type.warn,
                    content: "当前业务已处于停用状态！"
                });
                return;
            }
            comm.ajax.ajaxEntity({
                busiCode: "ICFGDYNCCOMMONFSV_LOCKBUSI",
                param: {
                    busiFrameId: select.BUSI_FRAME_ID
                },
                callback: function (data, isSucc, msg) {
                    if (isSucc) {
                        comm.dialog.notification({
                            title: "成功",
                            type: comm.dialog.type.success,
                            content: "停用业务成功!"
                        });
                        $("#dyncBuisGird").datagrid().load();
                    } else {
                        comm.dialog.notification({
                            title: "错误",
                            type: comm.dialog.type.error,
                            content: "停用业务失败!" + msg
                        });
                    }
                }
            });
        } else {
            comm.dialog.notification({
                title: "警告",
                type: comm.dialog.type.warn,
                content: "请选择具体的业务！"
            });
        }
    };
    var editBusi = function () {

        var select = $("#dyncBuisGird").datagrid().getSelect();

        if (select.BUSI_FRAME_ID) {
            if (select.STATE != 2) {
                comm.dialog.notification({
                    title: "警告",
                    type: comm.dialog.type.warn,
                    content: "当前业务处于启用状态！不能编辑！"
                });
                return;
            }
            var url = "./dyncConfigure.html?busiFrameId=" + select.BUSI_FRAME_ID;
            window.location.href = url;
        } else {
            comm.dialog.notification({
                title: "警告",
                type: comm.dialog.type.warn,
                content: "请选择具体的业务！"
            });
        }
    };

    var addBusi = function () {
        window.location.href = "./dyncConfigure.html";
    };

    var init = function () {
        initUI();
        initEnv();
    };

    init();

    $.extend(dyncBusiManageModule, {
        getParams: getParams
    });
})(jQuery);