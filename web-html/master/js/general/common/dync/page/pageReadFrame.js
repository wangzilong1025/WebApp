var pageReadModel = pageReadModel || {};
(function ($) {
    var viewModel = kendo.observable({
        dropdownData: {},
        visibleData: {},
        enabledData: {},
        valueData: {}
    });
    var validator = $("#pageRead").kendoValidator().data("kendoValidator");

    var getBusiFrameData = function (callBack) {//获取动态模板数据
        var busiFrameId = comm.tools.getRequest().busiFrameId;//"10003";//
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
        //处理规则数据
        var SinglePgeClass = DyncPageClass.extend({generateHtml: generateHtml});
        var pageObject = new SinglePgeClass("pageReadModel", data.dyncData, viewModel, validator);
        pageObject.initialize(data);
        //viewModule中把数据放进去，主要是初始化一些下拉框的值
        pageObject.dealViewModel(data);
        //通过模板生成html代码
        pageObject.generateHtml(data);
        //处理native页面
        pageObject.dealPages(data);
    };

    var generateHtml = function (data) {
        var template = kendo.template($("#pageReadTemplate").html());
        var pageRead = $(template(data));
        pageRead.appendTo($("#pageRead"));
        kendo.bind($("#pageRead"), viewModel);
        pageReadModel.viewModel = viewModel;
    };

    var init = function () {
        initData();
    };

    var initData = function () {
        getBusiFrameData(dealBusiFrameData);
    };

    $(function () {
        init();
    })
})(jQuery);