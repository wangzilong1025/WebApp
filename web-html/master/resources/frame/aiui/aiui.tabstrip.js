/**
 * @author zhangrp
 * Created on 2015/9/17 14:40
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */

/**
 * tab组件
 *
 * 属性
 *
 *
 *
 *
 * 方法
 *  isExist: 根据title判断某个tab是否存在
 *  selectByTitle: 根据title选中某个tab
 *
 *
 * 事件
 *
 *
 *
 */


$.fn.tabstrip = function () {
    var that = this;
    var target = that[0];


};

$.fn.tabstrip.parseOptions = function (target, options, properties) {
    options = options || {};
    properties = properties || {};
    return $.extend(options, $.parser.parseOptions(target, [properties]));
};


/**
 * 根据title判断某个tab是否存在
 * @param title
 * @returns {boolean}
 */
kendo.ui.TabStrip.prototype.isExist = function(title){
    var that = this;
    var isExist = false;
    $(that.items()).each(function () {
        var title_ = $(this).find(".k-link").text();
        if (title.indexOf(">" + title_ + "<") >= 0 || title.indexOf(">" + title_) >= 0 || title.indexOf(title_ + "<") >= 0 || title == title_) {
            isExist = true;
        }
    });
    return isExist;
};

/**
 * 根据标题选中某个tab
 * @param title
 */
kendo.ui.TabStrip.prototype.selectByTitle = function(title){
    var that = this;
    $(that.items()).each(function () {
        var title_ = $(this).find(".k-link").text();
        if (title.indexOf(">" + title_ + "<") >= 0 || title.indexOf(">" + title_) >= 0 || title.indexOf(title_ + "<") >= 0 || title == title_) {
            that.select($(this));
        }
    });
};