/**
 * @author zhangruiping
 * Created on 2017/4/18 上午9:22
 * Copyright 2017 Asiainfo Technologies(China),Inc. All rights reserved.
 */
(function ($) {
    var kendo = window.kendo,
        ui = kendo.ui,
        Widget = ui.Widget;

    var AIFileView = Widget.extend({
        init: function (element, options) {
            var that = this;
            Widget.fn.init.call(that, element, options);

            element = that.element;
            options = that.options;
            var fileInputId = element.attr("fileInputId");
            if (fileInputId) {
                options.fileInputId = fileInputId;
            }
            if (options.fileInputId) {
                comm.ajax.ajaxEntity({
                    busiCode: busiCode.common.IELECFSV_QUERYELECINS,
                    param:{
                        fileInputId: options.fileInputId
                    },
                    callback: function (data, isSucc, msg) {
                        if (isSucc && data) {
                            for (var i = 0; i < data.length; i++) {
                                var html = "<p><a style='text-decoration: underline'>" + data[i].fileName + "</a></p>";
                                $(html).appendTo(element).click(function () {
                                    comm.browser.reload(PROJECT_URL + "/elec/download?fileTypeId=" + this.fileTypeId + "&fileSaveName=" + this.fileSaveName + "&fileName=" + encodeURI(encodeURI(this.fileName)));
                                }.bind(data[i]))
                            }
                            if (data.length == 0) {
                                element.html("无文件");
                            }
                        } else {
                            comm.dialog.notification({
                                title: "错误",
                                type: comm.dialog.type.error,
                                content: "获取数据异常"
                            });
                        }
                    }
                });
            }
        },
        options: {
            name: "AIFileView"
        },
        value: function (value) {
            var that = this;
            if (value === undefined) {
                return that.element.val();
            }
            that.element.val(value);
        }
    });
    ui.plugin(AIFileView);


})(jQuery);