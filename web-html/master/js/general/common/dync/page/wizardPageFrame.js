(function ($) {
    var getBusiFrameData = function (callBack) {//获取动态模板数据
        var busiFrameId = comm.tools.getRequest().busiFrameId;//"10001";//
        if (busiFrameId && busiFrameId != "") {
            comm.ajax.ajaxEntity({
                busiCode: "ICFGDYNCCOMMONFSV_GETBUSIFRAMEENTITY",
                param: {
                    busiFrameId: busiFrameId
                },
                callback: function (data, isSucc, msg) {
                    if (isSucc) {
                        callBack(data);
                    } else {
                        comm.dialog.notification({
                            title: "错误",
                            type: comm.dialog.type.error,
                            content: "加载数据失败！" + msg
                        });
                    }
                }
            });
        } else {
            comm.dialog.notification({
                title: "错误",
                type: comm.dialog.type.error,
                content: "未找到有效的参数【busiFrameId】！"
            });
        }
    };

    var dealBusiFrameData = function (data) {//处理动态模板数据
        document.title = data.dyncData[0].remark;
        generateHtml(data);
        for (var i = 0; i < data.dyncData[0].cfgDyncFramePageEntities.length; i++) {
            busiFrameModel.createPageModel(data.dyncData[0].cfgDyncFramePageEntities[i].cfgDyncPageEntity, data.dyncData[0].cfgDyncFramePageEntities[i].cfgDyncRuleEntities || []);
        }
        initBusiFrameModel(data.dyncData[0]);
    };

    var generateHtml = function (data) {
        var template = kendo.template($("#wizardPageTemplate").html());
        var wizardPage = $(template(data));
        wizardPage.appendTo($("#wizardPage"));
    };

    $(function () {
        getBusiFrameData(dealBusiFrameData);
    });


    var initBusiFrameModel = function (busiFrame) {
        var javaRuleList = [];
        var jsInitRule = [];
        var tabStrip;
        var initRule = function () {
            if (busiFrame.cfgDyncRuleEntityList) {
                for (var x in busiFrame.cfgDyncRuleEntityList) {
                    if (x.ruleType == 2) {
                        javaRuleList.push({"fileName": x.fileName});
                    } else if (x.ruleType == 1 && x.ruleTriggerType == "onLoad") {
                        jsInitRule.push({"funcName": x.funcName});
                    }
                }
            }
        };

        var onActive = function (event) {
            var $item = $(event.item);
            var curPageId = $item.data("uid");
            var $hr = $(".flow > .flow-hr[data-uid=" + curPageId + "]");
            var $step = $(".flow > .flow-step[data-uid=" + curPageId + "]");
            if ($hr && $hr.hasClass("flow-hr-gray")) {
                $hr.removeClass("flow-hr-gray");
            }
            if ($step && $step.hasClass("flow-step-gray")) {
                $step.removeClass("flow-step-gray");
            }
            $step.addClass("flow-step-white");
            var $other = $(".flow > .flow-step[data-uid!=" + curPageId + "]");

            if ($other && $other.hasClass("flow-step-white")) {
                $other.removeClass("flow-step-white");
            }
            controlBtn();
        };

        // 控制按钮的显示-->
        var controlBtn = function () {
            var tabStrip = $("#tabstrip_" + busiFrame.busiFrameId).data("kendoTabStrip");
            var tab = tabStrip.select();
            var curPageId = tab.data("uid");
            var pageList = [];
            for (var x in busiFrame.cfgDyncFramePageEntityList) {
                pageList.push(x.pageId);
            }
            if (pageList.length == 1) {
                $(".preBtn").hide();
                $(".nextBtn").hide();
                $(".submit").show();
            } else {
                for (var i = 0; i < pageList.length; i++) {
                    if (pageList[i] == curPageId) {
                        if (i == 0) {
                            $(".preBtn").hide();
                            $(".nextBtn").show();
                            $(".submit").hide();
                        } else if (i == pageList.length - 1) {
                            $(".preBtn").show();
                            $(".nextBtn").hide();
                            $(".submit").show();
                        } else {
                            $(".preBtn").show();
                            $(".nextBtn").show();
                            $(".submit").hide();
                        }
                    }
                }
            }
        };

        var onFlowClick = function (e) {
            if ($(this).hasClass("flow-step-gray")) {
                return;
            }
            var tabStrip = $("#tabstrip_" + busiFrame.busiFrameId).data("kendoTabStrip");
            var tab = $("#tabstrip_" + busiFrame.busiFrameId + " li[data-uid=" + $(this).data("uid") + "]")[0];
            tabStrip.select(tab);
        };

        var initUI = function () {
            tabStrip = $("#tabstrip_" + busiFrame.busiFrameId).kendoTabStrip({
                animation: false,
                activate: onActive
            }).data('kendoTabStrip');

            tabStrip.tabGroup.hide();
            $(".flow > .flow-step").click(onFlowClick);

            $(".preBtn").click(function () {
                var tab = tabStrip.select();
                tabStrip.select(tab.prev());
            });


            $(".nextBtn").click(function (flag) {
                if (flag == "check") {
                    var tab = tabStrip.select();
                    // //清空错误信息-->
                    var param = {currentPageId: tab.data("uid"), errorMsg: [], warningMsg: [], returnValue: false};
                    try {
                        $.publish(DYNC.TOPIC_SO_NEXTPAGE, param);
                    } catch (e) {
                        comm.dialog.notification({
                            title: "提示",
                            type: comm.dialog.type.error,
                            content: JSON.stringify(e)
                        });
                        return;
                    }

                    if (param.errorMsg.length > 0) {
                        comm.dialog.notification({
                            title: "提示",
                            type: comm.dialog.type.error,
                            content: param.errorMsg[0]
                        });
                        return;
                    }
                    if (param.warningMsg.length > 0) {
                        for (var i = 0; i < param.warningMsg.length; i++) {
                            comm.dialog.notification({
                                title: "提示",
                                type: comm.dialog.type.warn,
                                content: param.warningMsg[i]
                            });
                        }
                    }
                    if (param.returnValue) {
                        var nextTab = tab.next();
                        tabStrip.select(nextTab);
                    }
                } else {
                    var tab = tabStrip.select();
                    var nextTab = tab.next();
                    tabStrip.select(nextTab);
                }
            });
            controlBtn();
        };

        var init = function () {
            // 调用java规则
            initRule();
            initUI();
            if (busiFrameModel.invokeJavaRule(javaRuleList)) {

            } else {
                // 关闭当前的tab页
            }
            $.publish(DYNC.TOPIC_PAGE_ONLOAD, {});
            // commonModel.autoHeight();
            // 调用js初始化规则
            for (var i = 0; i < jsInitRule.length; i++) {
                eval(jsInitRule[i].funcName + "()");
            }
        };

        var getSubmitData = function (pageIds) {
            var submitObject = {
                "submitData": [{
                    "name": busiFrame.remark,
                    "id": busiFrame.busiFrameId,
                    "paramMap": comm.tools.getRequest(),
                    "nodeinfo": []
                }]
            };

            var tabStrip = $("#tabstrip_" + busiFrame.busiFrameId).data("kendoTabStrip");
            var tab = tabStrip.select();//当前选中的tab
            var tabArrays = $("#tabstrip_" + busiFrame.busiFrameId + " li[data-uid]");
            var operate = "tab";
            if (tabArrays) {
                var tabCount = tabArrays.length - 1;
                for (var i = 0; i < tabCount; i++) {
                    operate += ".prev()";
                }
            }
            tabStrip.select(eval(operate));//选中第一个tab

            //校验每一个tab中的page数据必填项是否都已经填写OK
            var tab;
            var param;
            var n = busiFrame.cfgDyncFramePageEntities.size - 1;
            for (var t = 1; t < n; t++) {
                tab = tabStrip.select();
                param = {currentPageId: tab.data("uid"), errorMsg: [], warningMsg: [], returnValue: false};
                try {
                    $.publish(DYNC.TOPIC_SO_NEXTPAGE, param);
                } catch (e) {
                    comm.dialog.notification({
                        title: "提示",
                        type: comm.dialog.type.error,
                        content: JSON.stringify(e)
                    });
                    return;
                }
                if (param.errorMsg.length > 0) {
                    comm.dialog.notification({
                        title: "提示",
                        type: comm.dialog.type.error,
                        content: param.errorMsg[0]
                    });
                    return;
                }
                if (param.warningMsg.length > 0) {
                    for (var i = 0; i < param.warningMsg.length; i++) {
                        comm.dialog.notification({
                            title: "提示",
                            type: comm.dialog.type.warn,
                            content: param.warningMsg[i]
                        });
                    }
                }
                if (param.returnValue) {
                    var nextTab = tab.next();
                    tabStrip.select(nextTab);
                }
            }
            var result = busiFrameModel.getNodeInfos(pageIds, function (pageId) {
                var tab = $("#tabstrip_" + busiFrame.busiFrameId + " li[data-uid=" + pageId + "]")[0];
                tabStrip.select(tab);
            });
            if (result) {
                submitObject.submitData[0].nodeinfo = result;
                return submitObject;
            }
        };

        var getSubmitDataNoCheck = function (pageIds) {
            var submitObject = {
                "submitData": [{
                    "name": busiFrame.busiName,
                    "id": busiFrame.busiFrameId,
                    "paramMap": comm.tools.getRequest(),
                    "nodeinfo": []
                }]
            };
            var result = busiFrameModel.getNodeInfosNoCheck(pageIds);
            if (result) {
                submitObject.submitData[0].nodeinfo = result;
                return submitObject;
            }
        };

        var validateData = function (pageIds) {
            var result = busiFrameModel.validatePage(pageIds, function (pageId) {
                var tab = $("#tabstrip_" + busiFrame.busiFrameId + " li[data-uid=" + pageId + "]")[0];
                tabStrip.select(tab);
            });
            return result;

        };
        $(function () {
            busiFrameModel.getSubmitData = getSubmitData;
            busiFrameModel.getSubmitDataNoCheck = getSubmitDataNoCheck;
            busiFrameModel.validateData = validateData;
            init();
        });
    }
})(jQuery);