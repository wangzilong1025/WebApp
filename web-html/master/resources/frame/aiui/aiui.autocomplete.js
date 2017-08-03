/**
 * @author zhangrp
 * Created on 2015/9/18 10:14
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */

/**
 * 扩展组件 AIAutoComplete
 * 支持动态从后台拉去数据
 */
(function ($) {
    var AIAutoComplete = kendo.ui.AutoComplete.extend({
        init: function (element, options) {
            var that = this;
            kendo.ui.AutoComplete.fn.init.call(that, element, options);
            if (options.realTime) {
                that._realTimeInit(element, options);
            }
        },
        options: {
            name: "AIAutoComplete",
            realTime: false
        },
        _realTimeInit: function () {
            var that = this;
            if (that.dataSource) {
                $(that.element).keydown(function (e) {
                    if (e.keyCode != kendo.keys.DOWN && e.keyCode != kendo.keys.UP && e.keyCode != kendo.keys.ENTER && e.keyCode != kendo.keys.TAB
                        && e.keyCode != kendo.keys.ESC) {
                        var key = $(that.element).val();
                        if (key != "" && key.length >= that.options.minLength) {
                            //var data = that.dataSource.transport.options.read.data || {};
                            //data.key = key;
                            //that.dataSource.transport.options.read.data = data;
                            that.dataSource._data = [];
                            that.dataSource._destroyed = [];
                        }
                    }
                });
            }
        }
    });
    kendo.ui.plugin(AIAutoComplete);
})(jQuery);
