/**
 * @author zhangruiping
 * Created on 17/3/3 下午1:53
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
var dyncButtonsetManageModule = {};
(function ($) {

    var getParams = function (data) {
        var buttonsetName = $("#buttonsetName").val();
        data = data || {};
        data.buttonsetName = buttonsetName;
        return data;
    };


    var buttonsetUpdateWindowValidator = $("#buttonsetUpdateWindow").kendoValidator({
        rules: {}
    }).data("kendoValidator");

    var initUI = function () {
        $("#toolbar").kendoToolBar({
            items: [
                {type: "button", text: "修改", click: editButtonset},
                {type: "button", text: "新增", click: addButtonset},
                {type: "button", text: "删除", click: delButtonset}
            ]
        });

        $("#buttonsetUpdateWindow").kendoWindow({
            actions: ["Close"],
            animation: false,
            modal: true,
            pinned: true,
            iframe: false,
            visible: false,
            title: "按钮集",
            position: {
                left: ($(window).width() - 500) / 2 + $(document).scrollLeft(),
                top: ($(window).height() - 300) / 2 + $(document).scrollTop()
            },
            width: 500,
            height: 300,
            close: function () {

            },
            open: function () {
                beforeButtonsetUpdateWindowOpen();
            }
        });

        comm.ajax.ajaxEntity({
            busiCode: "ICFGDYNCCOMMONFSV_GETCFGDYNCBUTTON",
            param: {},
            callback: function (data) {
                data = data || [];
                $("#buttonsetUpdateWindow").find("select[name=buttons]").kendoMultiSelect({
                    dataTextField: "buttonText",
                    dataValueField: "buttonId",
                    template: kendo.template("#: remark #  [#: buttonText #]"),
                    dataSource: data
                });
            }
        });

    };

    var initEnv = function () {
        $("#queryButton").click(function () {
            $("#dyncButtonsetGird").datagrid().load();
        });


        $("#buttonsetUpdateWindow").find("[type=button]").click(function () {
            var title = $(this).html();
            if (title == "确定") {
                if (buttonsetUpdateWindowValidator.validate()) {
                    var param = {
                        buttonsetId: $("#buttonsetUpdateWindow").find("input[name=buttonsetId]").val(),
                        buttonsetName: $("#buttonsetUpdateWindow").find("input[name=buttonsetName]").val(),
                        remark: $("#buttonsetUpdateWindow").find("textarea[name=remark]").val(),
                        buttons: $("#buttonsetUpdateWindow").find("select[name=buttons]").data("kendoMultiSelect").value().join(",")
                    };

                    comm.ajax.ajaxEntity({
                        busiCode: "ICFGDYNCCOMMONFSV_SAVEBUTTONSET",
                        param: param,
                        callback: function (data, isSucc, msg) {
                            if (isSucc) {
                                comm.dialog.notification({
                                    title: "提示",
                                    type: comm.dialog.type.success,
                                    content: "保存成功！"
                                });
                                $("#dyncButtonsetGird").datagrid().load();
                                $("#buttonsetUpdateWindow").data("kendoWindow").close();
                            } else {
                                comm.dialog.notification({
                                    title: "提示",
                                    type: comm.dialog.type.error,
                                    content: "保存失败！" + msg
                                });
                            }
                        }
                    });
                }
            } else if (title == "取消") {
                $("#buttonsetUpdateWindow").data("kendoWindow").close();
            }
        });
    };


    var windowType = "";
    var beforeButtonsetUpdateWindowOpen = function () {
        var buttonsetUpdateWindow = $("#buttonsetUpdateWindow").data("kendoWindow");
        var title = "";
        if (windowType == "edit") {
            title = "按钮集修改";

            var select = $("#dyncButtonsetGird").datagrid().getSelect();
            $("#buttonsetUpdateWindow").find("input[name=buttonsetId]").val(select.BUTTONSET_ID);
            $("#buttonsetUpdateWindow").find("input[name=buttonsetName]").val(select.BUTTONSET_NAME);
            $("#buttonsetUpdateWindow").find("textarea[name=remark]").val(select.REMARK);
            $("#buttonsetUpdateWindow").find("select[name=buttons]").data("kendoMultiSelect").value(select.BUTTONSID.split(","));

        } else if (windowType == "add") {
            title = "按钮集添加";

            $("#buttonsetUpdateWindow").find("input[name=buttonsetId]").val("");
            $("#buttonsetUpdateWindow").find("input[name=buttonsetName]").val("");
            $("#buttonsetUpdateWindow").find("textarea[name=remark]").val("");
            $("#buttonsetUpdateWindow").find("select[name=buttons]").data("kendoMultiSelect").value("");
        }

        buttonsetUpdateWindow.title(title);
    };

    var editButtonset = function () {
        windowType = "edit";
        var select = $("#dyncButtonsetGird").datagrid().getSelect();
        if (select.BUTTONSET_ID) {
            $("#buttonsetUpdateWindow").data("kendoWindow").open();
        } else {
            comm.dialog.notification({
                title: "警告",
                type: comm.dialog.type.warn,
                content: "请选择一具体的按钮集！"
            });
        }
    };
    var addButtonset = function () {
        windowType = "add";
        $("#buttonsetUpdateWindow").data("kendoWindow").open();
    };

    var delButtonset = function () {
        windowType = "edit";
        var select = $("#dyncButtonsetGird").datagrid().getSelect();
        if (select.BUTTONSET_ID) {
            comm.dialog.confirm({
                title: "提示",
                type: comm.dialog.type.question,
                content: "您确定要删除按钮集【" + select.BUTTONSET_NAME + "】吗？",
                func: function (f) {
                    if (f) {
                        comm.ajax.ajaxEntity({
                            busiCode: "ICFGDYNCCOMMONFSV_DELBUTTONSET",
                            param: {
                                buttonsetId: select.BUTTONSET_ID
                            },
                            callback: function (data, isSucc, mssg) {
                                if (isSucc) {
                                    $("#dyncButtonsetGird").datagrid().load();
                                } else {
                                    comm.dialog.notification({
                                        title: "错误",
                                        type: comm.dialog.type.error,
                                        content: "删除按钮集失败！" + mssg
                                    });
                                }
                            }
                        });
                    }
                }
            });
        } else {
            comm.dialog.notification({
                title: "警告",
                type: comm.dialog.type.warn,
                content: "请选择您要删除的按钮集！"
            });
        }
    };


    var init = function () {
        initUI();
        initEnv();
    };

    init();

    $.extend(dyncButtonsetManageModule, {
        getParams: getParams
    });
})(jQuery);