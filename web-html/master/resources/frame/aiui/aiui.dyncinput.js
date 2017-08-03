/**
 * @author zhangrp
 * Created on 2015/10/26 14:20
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */

(function ($) {
    var kendo = window.kendo,
        ui = kendo.ui,
        Widget = ui.Widget;

    var AIDYNCInput = Widget.extend({
        init: function (element, options) {
            var that = this;
            Widget.fn.init.call(that, element, options);

            element = that.element;
            options = that.options;
            var busicode = element.attr("data-busicode");
            if (busicode && dataRet != "" && dataRet != "null") {
                options.busicode = busicode;
            }
            var dataRet = element.attr("data-return");
            if (dataRet && dataRet != "" && dataRet != "null") {
                options.dataRet = dataRet;
            } else if (options.dataRet == undefined) {
                options.dataRet = "returnValue";
            }

            if (options.busicode) {
                comm.ajax.ajaxEntity({
                    "param": {
                        "PRODUCT_ID": parent.busiFrameModel.protocalData.retInfo.submitData[0].paramMap.specId+"",
                        "CUSTOMER_ID": parent.busiFrameModel.groupInfo.partyRoleId,
                        "OFFER_ID":parent.busiFrameModel.param.offerId+""
                    },
                    "busiCode": options.busicode
                }, function (data,isSucc,msg) {
                    if (data != null) {
                        that.element.val(data[options.dataRet]);
                    }
                });
            }
            try {
                element[0].setAttribute("type", "text");
            } catch (e) {
                element[0].type = "text";
            }
            element.addClass("k-textbox");
            element.attr("readonly", "readonly");
        },
        options: {
            name: "AIDYNCInput"
        },
        value: function (value) {
            var that = this;
            if (value === undefined) {
                return that.element.val();
            }
            that.element.val(value);
        }
    });
    ui.plugin(AIDYNCInput);


})(jQuery);