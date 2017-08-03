var tabPageModel = tabPageModel || {};
(function ($) {
    var getBusiFrameData = function (callBack) {//获取动态模板数据
        var busiFrameId = comm.tools.getRequest().busiFrameId;//"10002";//
        if (busiFrameId && busiFrameId != "") {
            comm.ajax.ajaxEntity({
                sync: false,
                param: {
                    busiFrameId: busiFrameId
                },
                busiCode: "ICFGDYNCCOMMONFSV_GETBUSIFRAMEENTITY",
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
        var template = kendo.template($("#tabPageTemplate").html());
        var tabPage = $(template(data));
        tabPage.appendTo($("#tabPage"));
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
            //tabStrip.tabGroup.hide();
            $(".flow > .flow-step").click(onFlowClick);
        };

        var init = function () {
            // 调用java规则
            initRule();
            initUI();
            if (busiFrameModel.invokeJavaRule(javaRuleList)) {

            } else {
                //关闭当前的tab页
            }
            $.publish(DYNC.TOPIC_PAGE_ONLOAD, {});
            // 调用js初始化规则
            for (var i = 0; i < jsInitRule.length; i++) {
                eval(jsInitRule[i].funcName + "()");
            }
            //初始化内容
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
            var result = busiFrameModel.getNodeInfos(pageIds, function (pageId) {
                var tab = $("#tabstrip_" + busiFrame.busiFrameId + " li[data-uid=" + pageId + "]")[0];
                tabStrip.select(tab);
            });
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
            busiFrameModel.validateData = validateData;
            init();
        });
    };
})(jQuery);
