/**
 * @author zhangrp
 * Created on 2016/1/25 14:00
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

/**
 * textbox
 * 属性
 *  editable 是否可编辑
 *  multiline 是否多行，默认false
 *
 *  type：string,number,boolean,email
 *  required
 *  min
 *  max
 *  pattern
 *
 * 方法
 *
 *
 *
 * 事件
 *
 *
 *
 */

$.fn.textbox = function () {
    var that = this;
    var target = that[0];
    if ($(target).is("input") == false) {
        return;
    }
    if ($.data(target, 'textbox')) {
        var kendoType = $.data(target, 'textbox');
        return $(target).data(kendoType);
    }
    return $.fn.datebox.create(target);
};

$.fn.textbox.create = function (target) {
    var options = $.fn.datebox.parseOptions(target, {
        type: 'string',
        required: 'false'
    }, {
        type: "string",
        required: "boolean",
        min: "number",
        max: "number",
        pattern: "string"
    });

    //TODO

};

$.fn.textbox.parseOptions = function (target, options, properties) {
    options = options || {};
    properties = properties || {};
    return $.extend(options, $.parser.parseOptions(target, [properties]));
};