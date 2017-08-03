/**
 * @author zhangruiping
 * Created on 17/3/3 下午1:53
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
var dyncRulesetManageModule = {};
(function ($) {

    var getParams = function (data) {
        var rulesetName = $("#rulesetName").val();
        data = data || {};
        data.rulesetName = rulesetName;
        return data;
    };


    var rulesetUpdateWindowValidator = $("#rulesetUpdateWindow").kendoValidator({
        rules: {}
    }).data("kendoValidator");

    var initUI = function () {
        $("#toolbar").kendoToolBar({
            items: [
                {type: "button", text: "修改", click: editRuleset},
                {type: "button", text: "新增", click: addRuleset},
                {type: "button", text: "删除", click: delRuleset}
            ]
        });

        $("#rulesetUpdateWindow").kendoWindow({
            actions: ["Close"],
            animation: false,
            modal: true,
            pinned: true,
            iframe: false,
            visible: false,
            title: "规则集",
            position: {
                left: ($(window).width() - 600) / 2 + $(document).scrollLeft(),
                top: ($(window).height() - 400) / 2 + $(document).scrollTop()
            },
            width: 600,
            height: 400,
            close: function () {

            },
            open: function () {
                beforeRulesetUpdateWindowOpen();
            }
        });

        comm.ajax.ajaxEntity({
            busiCode: "ICFGDYNCCOMMONFSV_GETCFGDYNCRULE",
            param: {},
            callback: function (data) {
                data = data || [];
                $("#rulesetUpdateWindow").find("input[name=rule]").kendoDropDownList({
                    dataTextField: "ruleName",
                    dataValueField: "ruleId",
                    dataSource: data
                });
            }
        });

        //规则集类型
        $("#rulesetUpdateWindow").find("input[name=rulesetType]").kendoDropDownList({
            dataTextField: "name",
            dataValueField: "value",
            dataSource: [
                {name: "frame规则", value: "1"},
                {name: "page规则", value: "2"},
                {name: "area规则", value: "3"},
                {name: "attr规则", value: "4"},
                {name: "button规则", value: "5"}
            ]
        });

        $("#rulesetUpdateWindow").find("input[name=rullTriggerType]").kendoDropDownList({
            dataTextField: "name",
            dataValueField: "value",
            dataSource: [
                {name: "onLoad", value: "onLoad"},
                {name: "onChange", value: "onChange"},
                {name: "submit", value: "submit"}
            ]
        });

    };

    var initEnv = function () {
        $("#queryButton").click(function () {
            $("#dyncRulesetGird").datagrid().load();
        });

        $("#rulesetUpdateWindow").find("button[name=addNewRule]").click(function (){

            var rullTriggerType = $("#rulesetUpdateWindow").find("input[name=rullTriggerType]").data("kendoDropDownList").value();
            var rule = $("#rulesetUpdateWindow").find("input[name=rule]").data("kendoDropDownList").value();
            var ruleText = $("#rulesetUpdateWindow").find("input[name=rule]").data("kendoDropDownList").text();
            var html = "<p>"+rullTriggerType+"："+ruleText+"<input type='hidden' name='ruleValue' value='"+rullTriggerType+";"+rule+"'>&nbsp;<button class='k-icon k-i-cancel'></button></p>";
            $(html).appendTo($("#rulesetUpdateWindow").find("div[name=rules]")).find("button").click(function (t) {
                $(this).parent().remove();
            })

        });



        $("#rulesetUpdateWindow").find("[type=button]").click(function () {
            var title = $(this).html();
            if (title == "确定") {
                if (rulesetUpdateWindowValidator.validate()) {
                    var rules = [];
                    // $("#rulesetUpdateWindow").find("input[name=ruleValue]").each(function(e){
                    //     rules.push($(this).val());
                    // });
                    var elements = document.getElementsByName("ruleValue");
                    for(var i=0;i<elements.length;i++){
                        rules.push(elements[i].value);
                    }
                    if(rules.length ==0 ){
                        comm.dialog.notification({
                            title: "警告",
                            type: comm.dialog.type.warn,
                            content: "请选择规则！"
                        });
                        return ;
                    }
                    var param = {
                        rulesetId: $("#rulesetUpdateWindow").find("input[name=rulesetId]").val(),
                        rulesetName: $("#rulesetUpdateWindow").find("input[name=rulesetName]").val(),
                        remark: $("#rulesetUpdateWindow").find("textarea[name=remark]").val(),
                        rulesetType: $("#rulesetUpdateWindow").find("input[name=rulesetType]").data("kendoDropDownList").value(),
                        rules: rules.join(",")
                    };

                    comm.ajax.ajaxEntity({
                        busiCode: "ICFGDYNCCOMMONFSV_SAVERULESET",
                        param: param,
                        callback: function (data, isSucc, msg) {
                            if (isSucc) {
                                comm.dialog.notification({
                                    title: "提示",
                                    type: comm.dialog.type.success,
                                    content: "保存成功！"
                                });
                                $("#dyncRulesetGird").datagrid().load();
                                $("#rulesetUpdateWindow").data("kendoWindow").close();
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
                $("#rulesetUpdateWindow").data("kendoWindow").close();
            }
        });
    };


    var windowType = "";
    var beforeRulesetUpdateWindowOpen = function () {
        var rulesetUpdateWindow = $("#rulesetUpdateWindow").data("kendoWindow");
        var title = "";
        if (windowType == "edit") {
            title = "规则集修改";

            var select = $("#dyncRulesetGird").datagrid().getSelect();
            $("#rulesetUpdateWindow").find("input[name=rulesetId]").val(select.RULESET_ID);
            $("#rulesetUpdateWindow").find("input[name=rulesetName]").val(select.RULESET_NAME);
            $("#rulesetUpdateWindow").find("textarea[name=remark]").val(select.REMARK);
            $("#rulesetUpdateWindow").find("input[name=rulesetType]").data("kendoDropDownList").value(select.RULESET_TYPE || 1);

            $("#rulesetUpdateWindow").find("div[name=rules]").html("");
            var rules = select.RULES;
            if(rules){
                rules = rules.split(",");
                for(var i=0;i<rules.length;i++){
                    var rule__ = rules[i].split(";");
                    var rullTriggerType = rule__[0];
                    var ruleText = rule__[2];
                    var rule = rule__[1];
                    var html = "<p>"+rullTriggerType+"："+ruleText+"<input type='hidden' name='ruleValue' value='"+rullTriggerType+";"+rule+"'>&nbsp;<button class='k-icon k-i-cancel'></button></p>";
                    $(html).appendTo($("#rulesetUpdateWindow").find("div[name=rules]")).find("button").click(function (t) {
                        $(this).parent().remove();
                    })
                }
            }



        } else if (windowType == "add") {
            title = "规则集添加";

            $("#rulesetUpdateWindow").find("input[name=rulesetId]").val("");
            $("#rulesetUpdateWindow").find("input[name=rulesetName]").val("");
            $("#rulesetUpdateWindow").find("textarea[name=remark]").val("");
            $("#rulesetUpdateWindow").find("input[name=rulesetType]").data("kendoDropDownList").value("");
            $("#rulesetUpdateWindow").find("div[name=rules]").html("");
        }

        rulesetUpdateWindow.title(title);
    };

    var editRuleset = function () {
        windowType = "edit";
        var select = $("#dyncRulesetGird").datagrid().getSelect();
        if (select.RULESET_ID) {
            $("#rulesetUpdateWindow").data("kendoWindow").open();
        } else {
            comm.dialog.notification({
                title: "警告",
                type: comm.dialog.type.warn,
                content: "请选择一具体的规则集！"
            });
        }
    };
    var addRuleset = function () {
        windowType = "add";
        $("#rulesetUpdateWindow").data("kendoWindow").open();
    };

    var delRuleset = function () {
        windowType = "edit";
        var select = $("#dyncRulesetGird").datagrid().getSelect();
        if (select.RULESET_ID) {
            comm.dialog.confirm({
                title: "提示",
                type: comm.dialog.type.question,
                content: "您确定要删除规则集【" + select.RULESET_NAME + "】吗？",
                func: function (f) {
                    if (f) {
                        comm.ajax.ajaxEntity({
                            busiCode: "ICFGDYNCCOMMONFSV_DELRULESET",
                            param: {
                                rulesetId: select.RULESET_ID
                            },
                            callback: function (data, isSucc, mssg) {
                                if (isSucc) {
                                    $("#dyncRulesetGird").datagrid().load();
                                } else {
                                    comm.dialog.notification({
                                        title: "错误",
                                        type: comm.dialog.type.error,
                                        content: "删除规则集失败！" + mssg
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
                content: "请选择您要删除的规则集！"
            });
        }
    };


    var init = function () {
        initUI();
        initEnv();
    };

    init();

    $.extend(dyncRulesetManageModule, {
        getParams: getParams
    });
})(jQuery);