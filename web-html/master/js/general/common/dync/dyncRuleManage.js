/**
 * @author zhangruiping
 * Created on 17/4/11 下午2:09
 * Copyright 2017 Asiainfo Technologies(China),Inc. All rights reserved.
 */

(function ($) {

    var editor;

    var getSelectedRange = function () {
        return {from: editor.getCursor(true).line, to: editor.getCursor(false).line};
    };

    var fileTypeChange = function () {
        var fileType = $("#fileType").data("kendoDropDownList").value();
        $("#file_type_1_div").hide();
        $("#file_type_2_div").hide();

        if (fileType == 1) {
            $("#file_type_1_div").show();
            $("#file_type_2_div").hide();
        } else if (fileType == 2) {
            $("#file_type_2_div").show();
            $("#file_type_1_div").hide();
            editor.refresh()
        }

    };


    var editViewValidator = $("#editView").kendoValidator({
        rules: {}
    }).data("kendoValidator");


    var initUI = function () {
        $("#splitter").height($(window).height());
        $("#splitter").kendoSplitter({
            orientation: "horizontal",
            panes: [
                {collapsible: false, size: "25%"},
                {collapsible: false}
            ]
        });

        $("#ruleListView").kendoListView({
            dataSource: {
                transport: {
                    read: {
                        url: SERVER_URL,
                        type: "POST"
                    },
                    parameterMap: function (data, operation) {
                        if (operation == "read") {
                            return comm.ajax.paramWrap({
                                "param": data,
                                "busiCode": "ICFGDYNCCOMMONFSV_GETCFGDYNCRULE"
                            });
                        } else if (data.models) {
                            return JSON.stringify(data.models);
                        }
                    }
                },
                batch: true,
                schema: {
                    data: "data"
                }
            },
            template: "<div class='row span-lineHeight30' style='padding-left: 10px;'>#: ruleName #</div>",
            navigatable: true,
            selectable: "single",
            change: function () {
                showRule();
            }
        });

        $("#ruleListToolbar").kendoToolBar({
            items: [
                {type: "button", text: "新增", click: addRule},
                {type: "button", text: "删除", click: delRule}
            ]
        });


        $("#undoButton").kendoButton({
            imageUrl: "/resources/img/arrow_undo.png",
            click: function (e) {
                editor.undo();
            }
        });
        $("#redoButton").kendoButton({
            imageUrl: "/resources/img/arrow_redo.png",
            click: function (e) {
                editor.redo();
            }
        });
        $("#indentButton").kendoButton({
            imageUrl: "/resources/img/text_indent.png",
            click: function (e) {
                var range = getSelectedRange();
                var formLine = range.from.line;
                var toLine = range.to.line;
                if (range.from.line == range.to.line && range.from.ch == range.to.ch) {
                    formLine = 0;
                    toLine = editor.lineCount()
                }
                for (var i = formLine; i < toLine + 1; i++) {
                    editor.indentLine(i, "smart");
                }
            }
        });

        editor = CodeMirror.fromTextArea(document.getElementById("code"), {
            lineNumbers: true,
            styleActiveLine: true,
            matchBrackets: true,
            theme: "eclipse",
            indentUnit: 4
        });


        $("#fileType").kendoDropDownList({
            dataTextField: "name",
            dataValueField: "value",
            index: 2,
            dataSource: [
                {name: "文件路径", value: 1},
                {name: "文件内容", value: 2}
            ],
            change: function () {
                fileTypeChange();
            }
        });

        $("#ruleType").kendoDropDownList({
            dataTextField: "name",
            dataValueField: "value",
            dataSource: [
                {name: "JS规则", value: 1},
                {name: "JAVA规则", value: 2}
            ]
        });

        fileTypeChange();
    };


    var showRule = function () {
        $("#editViewHeader").text("编辑规则");
        var data = $("#ruleListView").data("kendoListView").dataItem($("#ruleListView").data("kendoListView").select());
        $("#editView").show();
        $("#ruleId").val(data.ruleId);
        $("#ruleName").val(data.ruleName);
        $("#ruleType").data("kendoDropDownList").value(data.ruleType);
        $("#remark").val(data.remark);
        $("#fileType").data("kendoDropDownList").value(data.fileType);
        $("#funcName").val(data.funcName);
        $("#fileName").val(data.fileName);
        editor.setValue(data.fileContent || "");
        fileTypeChange();

    };

    var addRule = function () {

        comm.ajax.ajaxEntity({
            busiCode: "ICFGDYNCCOMMONFSV_GETNEWRULEID",
            param: {},
            callback: function (data, isSucc, mssg) {
                if (isSucc) {
                    $("#editViewHeader").text("新增规则");
                    $("#editView").show();
                    $("#ruleId").val(data);
                    $("#ruleName").val("");
                    $("#remark").val("");
                    $("#fileType").data("kendoDropDownList").value(1);
                    $("#funcName").val("");
                    $("#fileName").val("");
                    editor.setValue("/**\n * \n */\n\nvar rule_" + data + "_module = {};\n(function ($) {\n\n    //todo\n\n    $.extend(rule_" + data + "_module, {\n        //todo\n    });\n})(jQuery);");
                    editor.refresh();
                    fileTypeChange();
                } else {
                    comm.dialog.notification({
                        title: "错误",
                        type: comm.dialog.type.error,
                        content: "获取数据失败！" + mssg
                    });
                }
            }
        });

    };

    var delRule = function () {
        var data = $("#ruleListView").data("kendoListView").dataItem($("#ruleListView").data("kendoListView").select());
        if (data && data.ruleId) {
            comm.dialog.confirm({
                title: "提示",
                type: comm.dialog.type.question,
                content: "您确定要删除【" + data.ruleName + "】吗？",
                func: function (f) {
                    if (f) {
                        comm.ajax.ajaxEntity({
                            busiCode: "ICFGDYNCCOMMONFSV_DELCFGDYNCRULE",
                            param: {
                                ruleId: data.ruleId
                            },
                            callback: function (data, isSucc, mssg) {
                                if (isSucc) {
                                    $("#ruleListView").data("kendoListView").dataSource.read();
                                    $("#editView").hide();
                                } else {
                                    comm.dialog.notification({
                                        title: "错误",
                                        type: comm.dialog.type.error,
                                        content: "删除规则失败！" + mssg
                                    });
                                }
                            }
                        });
                    }
                }
            });
        } else {
            comm.dialog.notification({
                title: "提示",
                type: comm.dialog.type.warn,
                content: "请选择需要删除的规则！"
            });
        }


    };

    var initEnv = function () {
        $("#commitButton").click(function () {

            if (editViewValidator.validate()) {
                var ruleId = $("#ruleId").val();
                var ruleName = $("#ruleName").val();
                var remark = $("#remark").val();
                var ruleType = $("#ruleType").data("kendoDropDownList").value();
                var fileType = $("#fileType").data("kendoDropDownList").value();
                var funcName = $("#funcName").val();
                var fileName = $("#fileName").val();
                var fileContent = editor.getValue();

                if (fileType == 1 && fileName == "") {
                    comm.dialog.notification({
                        title: "警告",
                        type: comm.dialog.type.warn,
                        content: "文件路径不能为空！"
                    });
                    return;
                } else if (fileType == 2 && fileContent == "") {
                    comm.dialog.notification({
                        title: "警告",
                        type: comm.dialog.type.warn,
                        content: "文件内容不能为空！"
                    });
                    return;
                }

                if(fileType == 1){
                    fileContent = "";
                }else if(fileType == 2){
                    fileName = "";
                }

                comm.ajax.ajaxEntity({
                    busiCode: "ICFGDYNCCOMMONFSV_SAVECFGDYNCRULE",
                    param: {
                        ruleId: ruleId,
                        ruleName: ruleName,
                        remark: remark,
                        ruleType: ruleType,
                        fileType: fileType,
                        funcName: funcName,
                        fileName: fileName,
                        fileContent: fileContent
                    },
                    callback: function (data, isSucc, mssg) {
                        if (isSucc) {
                            comm.dialog.notification({
                                title: "提示",
                                type: comm.dialog.type.success,
                                content: "保存成功！"
                            });
                            $("#ruleListView").data("kendoListView").dataSource.read();
                            $("#editView").hide();
                        } else {
                            comm.dialog.notification({
                                title: "错误",
                                type: comm.dialog.type.error,
                                content: "保存失败！" + mssg
                            });
                        }

                    }
                });
            } else {
                comm.dialog.notification({
                    title: "警告",
                    type: comm.dialog.type.warn,
                    content: "请输入各项数据！"
                });
            }

        });


        $("#cancelButton").click(function () {
            var data = $("#ruleListView").data("kendoListView").dataItem($("#ruleListView").data("kendoListView").select());
            if (data && data.ruleId) {
                showRule();
            } else {
                $("#editView").hide();
            }

        });
    };

    var init = function () {
        initUI();
        initEnv();
    };

    init();
})(jQuery);