/**
 * @author zhangrp
 * Created on 2016/1/12 10:36
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

/**
 * 【示例】
 *  <input class="aiui-datebox" id="startDate" type="date或者datetime" data-options="format:'yyyy-MM-dd HH:mm:ss'" />
 *
 *
 * 【属性】
 *  type: date、datetime
 *  format：日期格式化，yyyy-MM-dd HH:mm:ss
 *         yyyy：年
 *         MM：月
 *         dd：日
 *         HH：小时
 *         mm：分
 *         ss：秒
 *         default；yyyy-MM-dd or yyyy-MM-dd HH:mm:ss
 *
 *
 * 【方法】
 *  max: set/get max value
 *  min: set/get min value
 *  value: set/get Value
 *
 *
 * 【事件】
 *  change: 值改变事件，$("#id").datebox().bind('change', functione(){todo;});
 *
 */

$.fn.datebox = function () {
    var that = this;
    var target = that[0];
    if ($(target).is("input") == false) {
        return;
    }
    if ($.data(target, 'datebox')) {
        var kendoType = $.data(target, 'datebox');
        return $(target).data(kendoType);
    }
    return $.fn.datebox.create(target);
};

$.fn.datebox.create = function (target) {
    var options = $.fn.datebox.parseOptions(target, {
        type: 'date'
    }, {
        format: "string",
        type: "string"
    });

    var kendoType = "";
    if (options.type == "date") {
        $(target).kendoDatePicker({
            animation: false,
            format: options.format ? options.format : "yyyy-MM-dd"
        });
        kendoType = "kendoDatePicker";
    } else if (options.type == "datetime") {
        $(target).kendoDateTimePicker({
            format: options.format ? options.format : "yyyy-MM-dd HH:mm:ss"
        });
        kendoType = "kendoDateTimePicker";
    }
    $.data(target, 'datebox', kendoType);
    return $(target).data(kendoType);
};

$.fn.datebox.parseOptions = function (target, options, properties) {
    options = options || {};
    properties = properties || {};
    return $.extend(options, $.parser.parseOptions(target, [properties]));
};