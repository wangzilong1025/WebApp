(function ($) {
    var getBusiFrameData = function (callBack) {//获取动态模板数据
        var busiFrameId = comm.tools.getRequest().busiFrameId;
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

    var dealBusiFrameData = function (data) {
        document.title = data.dyncData[0].remark;
        generateHtml(data);
        for (var i = 0; i < data.dyncData[0].cfgDyncFramePageEntities.length; i++) {
            busiFrameModel.createPageModel(data.dyncData[0].cfgDyncFramePageEntities[i].cfgDyncPageEntity, data.dyncData[0].cfgDyncFramePageEntities[i].cfgDyncRuleEntities || []);
        }
        initBusiFrameModel(data.dyncData[0]);
    };

    var generateHtml = function (data) {
        document.title = data.dyncData[0].remark;
        var template = kendo.template($("#singlePageTemplate").html());
        var singlePage = $(template(data));
        singlePage.appendTo($("#singlePage"));
    };

    $(function () {
        getBusiFrameData(dealBusiFrameData);
    });


    var initBusiFrameModel = function (busiFrame) {
        var javaRuleList = [];
        var jsInitRule = [];
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

        var initEvent = function () {
            // commonModel.autoHeight();
        };

        var init = function () {
            //调用java规则
            initEvent();
            initRule();
            if (busiFrameModel.invokeJavaRule(javaRuleList)) {

            } else {
                //关闭当前的tab页
            }
            $.publish(DYNC.TOPIC_PAGE_ONLOAD, {});
            //commonModel.autoHeight();

            //调用js初始化规则
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
            var result = busiFrameModel.getNodeInfos(pageIds);
            if (result) {
                submitObject.submitData[0].nodeinfo = result;
                return submitObject;
            }
        };

        var validateData = function (pageIds) {
            return busiFrameModel.validatePage(pageIds);
        };

        $(function () {
            busiFrameModel.getSubmitData = getSubmitData;
            busiFrameModel.validateData = validateData;
            init();
        });
    }
})(jQuery);
