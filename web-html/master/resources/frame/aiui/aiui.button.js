/**
 * @author haomeng
 * Created on 2016/1/21 15:17
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */

/**
 * button 按钮
 *  <input class="aiui-button" id="queryButton" onclick="clickButton()" disabled="true" visiable="true" data-options="privId:privGroup.button.privId.testBtn" value="查询"/>
 *  <button class="aiui-button k-button k-primary" id="testPriv" data-options="privId:privGroup.button.privId.testBtn"/>&nbsp;&nbsp;有权限看我哦&nbsp;&nbsp;</button>
 * 属性
 * disabled: 是否可编辑
 * visible: 是否可见
 * privId:privGroup.button.privId.testBtn,groupId:privGroup.button.groupId  操作员如果有该权限可见
 * ctype: 按钮的显示样式，暂时支持： default,query和confirm三中类型
 *
 * 方法
 *
 *
 * 事件
 *
 *
 */

$.fn.button = function () {
    var that = this;
    var target = that[0];

    if ($.data(target, 'button')) {
        return $.data(target, 'button');
    }
    return $.fn.button.create(target);
};

$.fn.button.create = function (target) {
    var options = $.fn.button.parseOptions(target, {
        disabled: false,
        visible: true,
        type: "default"
    }, {
        ctype: "string",
        privId: "string",
        groupId: "string",
        disabled: "boolean",
        visible: "boolean"
    });

    if (options.disabled && options.disabled == true) {//不可编辑
        $(target).attr("disabled", "true");
    }

    if (options.visible && options.visible == false) {
        //不可见
        $(target).css("display", "none");
    } else if (options.visible && options.visible == true) {
        //如果按钮可见，根据配置的实体编号和操作编号，判断操作员是否查看该按钮的权限

        //判断操作员是否有权限，如果有权限则展示按钮，没有权限则影藏按钮
        if (options.privId) {
            comm.tools.getPrivs({
                privId: options.privId,
                callback: function(privs){
                    if (!privs || privs.length==0) {//没有权限
                        $(target).css("display", "none");
                        $(target).attr("disabled", "true");
                    }
                }
            });
        }
    }
    if (options.ctype) {
        switch (options.ctype.toUpperCase()) {
            case "DEFAULT":
                $(target).addClass("btn btn-default");
                break;
            case "CONFIRM":
                $(target).addClass("btn btn-confirm");
                break;
            default:
                $(target).addClass("btn btn-default");
        }
    } else {
        $(target).addClass("btn btn-default");
    }
    //$(target).text(options.value ? options.value : "");


    var button = {};
    button.options = options;
    button.target = target;
    $.data(target, 'button', button);

    return button;
};

$.fn.button.parseOptions = function (target, options, properties) {
    options = options || {};
    properties = properties || {};
    return $.extend(options, $.parser.parseOptions(target, [properties]));
};