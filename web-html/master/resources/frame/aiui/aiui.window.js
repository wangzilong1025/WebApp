/**
 * @author zhangrp
 * Created on 2016/3/27 15:07
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

var aiwindow;
(function ($) {
    $.extend(
        kendo.ui, {
            window: function (options) {
                var current = window;
                var container = $(current.document.body);

                var _id = new Date().getTime();
                var aiwin_id = "aiwin_" + _id;
                var aiwin_iframe_id = "aiwin_iframe_" + _id;
                var allAction = ["Minimize", "Maximize", "Close"];
                //mod by zhangch6
                if(options.isHasClose==false){
                    allAction.pop();
                }
                //mod by zhangch6
                $('<div id="' + aiwin_id + '"></div>')
                    .insertAfter(container).kendoWindow({
                        actions: allAction,
                        animation: false,
                        modal: true,
                        pinned: true,
                        content: options.url,
                        iframe: true,
                        visible: false,
                        position: {
                            left: ($(window).width() - options.width) / 2 + $(document).scrollLeft(),
                            top: ($(window).height() - options.height) / 2 + $(document).scrollTop()
                        },
                        width: options.width,
                        height: options.height,
                        close: function () {
                            if (options.dataReturn &&
                                options.dataReturn != "" &&
                                options.dataReturn != null &&
                                options.dataReturn != "null" &&
                                options.close &&
                                typeof options.close === "function") {

                                var returnValue = $("#" + aiwin_iframe_id).contents().find("#" + options.dataReturn).val();
                                $("div[id=" + aiwin_id + "]").parent("div").remove();
                                options.close(returnValue);
                            } else {
                                $("div[id=" + aiwin_id + "]").parent("div").remove();
                            }
                        }
                    });
                $("#" + aiwin_id).data("kendoWindow").open();
                $(document.body).find(".k-overlay").css({"background-color": "#FFFFFF", "opacity": "0.1"});
                $($("#" + aiwin_id).find("iframe[class=k-content-frame]")[0]).attr("id", aiwin_iframe_id);

                if (options.close &&
                    typeof options.close === "function") {
                    $("div[id=" + aiwin_id + "]").data("kendoWindow").closeFuction = options.close;
                }
                return $("div[id=" + aiwin_id + "]").data("kendoWindow");
            }
        });
    aiwindow = {
        /**
         * 关闭窗口
         * @param that 窗口
         * @param value 返回值
         */
        close: function (that, value) {
            var AIWIN_IFRAME ="aiwin_iframe_";
            var frameId = that.frameElement.id;
            if (frameId.indexOf(AIWIN_IFRAME) == 0) {
                frameId = frameId.substr(AIWIN_IFRAME.length);
            }
            var winid = "aiwin_" + frameId;

            var window = $("#" + winid).data("kendoWindow");
            var closeFuction = window.closeFuction;
            window.close();
            if(value && closeFuction && typeof closeFuction === "function"){
                closeFuction(value);
            }

        }
    };
})(jQuery);