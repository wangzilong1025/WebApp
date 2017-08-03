/**
 * @author zhangrp
 * Created on 2015/9/17 10:01
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */


/**
 * ui 基础控件
 *
 * aiui.progress
 *     进度条
 *
 * aiui.notification
 *     提示框
 *
 * aiui.prompt
 *     确认框
 *
 * aiui.window
 *     弹出指定url的页面
 *
 */

$.extend(
    kendo.ui, {
        progress: function (container, toggle, title) {
            if (!title) {
                title = "数据加载中..."
            }
            var mask = container.find(".k-loading-mask"),
                support = kendo.support,
                browser = support.browser,
                isRtl, leftRight, webkitCorrection, containerScrollLeft;
            if (toggle) {
                if (!mask.length) {
                    isRtl = support.isRtl(container);
                    leftRight = isRtl ? "right" : "left";
                    containerScrollLeft = container.scrollLeft();
                    webkitCorrection = browser.webkit ? (!isRtl ? 0 : container[0].scrollWidth - container.width() - 2 * containerScrollLeft) : 0;
                    container.css("position", "relative");
                    mask = $("<div class='k-loading-mask'><div class='k-loading-image'><div class='k-loading-text'>" + title + "</div></div><div class='k-loading-color'/></div>")
                        .width("100%").height("100%")
                        .css("top", container.scrollTop())
                        .css(leftRight, Math.abs(containerScrollLeft) + webkitCorrection)
                        .prependTo(container);
                }
            } else if (mask) {
                mask.remove();
            }
        },
        notification: function (showType, title, message, type, callback, options) {
            if (showType != "0" && showType != 0) {
                showType = "alert";
            } else {
                showType = "notice"
            }

            if (type) {
                if (type != "info" && type != "error" && type != "success" && type != "warning" && type != "confirm") {
                    type = "info";
                }
            } else {
                type = "info";
            }
            if (type == "warning") {
                type = "warn";
            }
            if (type == "confirm") {
                options = options || {cancel: true};
            } else {
                options = options || {cancel: false};
            }
            if (showType === "alert") {
                var container = $(window.document.body);
                var mask = container.find(".k-notification-mask");
                if (mask) {
                    mask.remove();
                }
                var box = "<div class='box " + type + "'>" +
                    "    <div class='box-title'>" + title + "</div>" +
                    "    <p>" + message + "</p>" +
                    "    <input type='button' value='确定' name='confirm' >" +
                    (options.cancel == true ? " <input type='button' value='取消' name='cancel'>" : "") +
                    "</div>";
                mask = $('<div class="k-notification-mask"><div class="mask" style="height: ' + $(window.document).height() + 'px"></div>' + box + '</div>')
                    .width("99%")
                    .height($(window.document).height())
                    .prependTo(container);
                container.find(".k-notification-mask input[type=button]").click(function () {
                    container.find(".k-notification-mask").remove();
                    if (callback) {
                        if ($(this).val() == "确定") {
                            callback(true);
                        } else {
                            callback(false);
                        }
                    }
                });
            } else if (showType === "notice") {
                if (type == "warn") {
                    type = "warning";
                }
                var container = $(window.document.body);
                var mask = container.find(".k-notification-no-mask-span");
                if (mask && mask.length > 0) {
                    var notification = $(mask[0]).data("notification");
                    notification.show({
                        title: title,
                        message: message,
                        options: options
                    }, type);
                } else {
                    mask = $('<span class="k-notification-no-mask-span"></span>').prependTo(container);
                    var box = "<div class='box-title'>#=title#</div>" +
                        "<p>#=message#</p>";
                    /*"<div style='text-align: center;'>#if(options.confirm == true){ # <input type='button' value='确定' name='confirm' >" +" #}#" +
                     "#if(options.cancel == true){  # <input type='button' value='取消' name='cancel' >" +" #}#</div>" ;*/

                    var notification = $(mask).kendoNotification({
                        position: {
                            pinned: true,
                            top: 155,
                            right: 30
                        },
                        autoHideAfter: 5000,
                        stacking: "down",
                        button: true,
                        hideOnClick: true,
                        templates: [{
                            type: "info",
                            template: "<div class='k-notification-no-mask' style='height: 107px'><div class='box info'>" + box + "</div></div>"
                            //template: "#var notificationHeight = 107 + (options.confirm || options.cancel ? 42 : 0);#<div class='k-notification-no-mask' style='height: #=notificationHeight#px'><div class='box info'>" + box + "</div></div>"
                        }, {
                            type: "error",
                            template: "<div class='k-notification-no-mask' style='height: 107px'><div class='box error'>" + box + "</div></div>"
                            //template: "#var notificationHeight = 107 + (options.confirm || options.cancel ? 42 : 0);#<div class='k-notification-no-mask' style='height: #=notificationHeight#px'><div class='box error'>" + box + "</div></div>"
                        }, {
                            type: "success",
                            template: "<div class='k-notification-no-mask' style='height: 107px'><div class='box success'>" + box + "</div></div>"
                            //template: "#var notificationHeight = 107 + (options.confirm || options.cancel ? 42 : 0);#<div class='k-notification-no-mask' style='height: #=notificationHeight#px'><div class='box success'>" + box + "</div></div>"
                        }, {
                            type: "warning",
                            template: "<div class='k-notification-no-mask' style='height: 107px'><div class='box warn'>" + box + "</div></div>"
                            //template: "#var notificationHeight = 107 + (options.confirm || options.cancel ? 42 : 0);#<div class='k-notification-no-mask' style='height: #=notificationHeight#px'><div class='box warning'>" + box + "</div></div>"
                        }]
                    }).data("kendoNotification");
                    $(mask).data("notification", notification);
                    notification.show({
                        title: title,
                        message: message,
                        options: options
                    }, type);
                }
            }

        },
        prompt: function (title, message, callback, defaultText) {
            var container = $(window.document.body);
            var mask = container.find(".k-notification-mask");
            if (mask) {
                mask.remove();
            }
            if (!defaultText) {
                defaultText = "";
            }
            var box = "<div class='box confirm'>" +
                "    <div class='box-title'>" + title + "</div>" +
                "    <p>" + message + "<input class='k-textbox' type='text' value='" + defaultText + "'></p>" +
                "    <input type='button' value='确定' name='confirm'>" +
                "    <input type='button' value='取消' name='cancel'>" +
                "</div>";
            mask = $('<div class="k-notification-mask">' + '<div class="mask" style="height: ' + $(window.document).height() + 'px"></div>' + box + '</div>')
                .height($(window.document).height())
                .width("99%")
                .prependTo(container);
            container.find(".k-notification-mask input[type=button]").click(function () {
                var input = container.find(".k-notification-mask input[type=text]").val();
                container.find(".k-notification-mask").remove();
                if (callback) {
                    if ($(this).val() == "确定" && input) {
                        callback(input);
                    } else {

                    }
                }
            });
        }
    });
var aiui = kendo.ui;