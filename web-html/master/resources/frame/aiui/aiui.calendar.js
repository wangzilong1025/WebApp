/**
 * @author zhangrp
 * Created on 2016/1/12 10:37
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

/**
 * 日历
 * * example: <input  class="aiui-calendar"  id="testDate"  default="now"  min="-5" max="5" minid="startDate" maxid="endDate"/>
 * 属性
 *defaultValue: 默认值（now:当天；数字N：后N天；数字-N：前N天；日期：指定日期，如"2016-01-16"）
 *min：最小值，取值范围同default
 *max: 最大值，取值范围同default
 *minid：最小日期限制为其它日历框的id
 *maxid：最大日期限制为其它日历框的id
 *
 * 方法
 *
 *
 *
 *
 *
 * 事件
 *
 *
 *
 */

$.fn.calendar = function () {
    var that = this;
    var target = that[0];
    //kendo.culture("zh-CN");
    $.fn.calendar.create(target);

};

$.fn.calendar.create = function (target) {
    var calendarOptions = $.fn.calendar.parseOptions(target, {
        culture:"zh-CN", //en-US,zh-CN
        format: "yyyy-MM-dd",
        defaultValue:"",
        min: "",
        max: "",
        maxid:"",
        minid:""
    }, {
        culture: "string",
        format: "string",
        defaultValue: "string",
        min: "string",
        max: "string",
        maxid: "string",
        minid: "string"
    });
    var now = new Date(), curDate, minDate, maxDate;
    if (calendarOptions.defaultValue){
        if (calendarOptions.defaultValue=="now"){
            curDate = new Date();
        }else if (calendarOptions.defaultValue.length==10){
            curDate = new Date(calendarOptions.defaultValue.substr(0,4),calendarOptions.defaultValue.substr(5,2),calendarOptions.defaultValue.substr(7,2));
        }else if (calendarOptions.defaultValue.isNumber() || calendarOptions.defaultValue.substr(0,1)=="-" || calendarOptions.defaultValue.substr(0,1)=="+"){
            curDate = new Date();
            curDate.setDate(new Date().getDate() + parseInt(calendarOptions.defaultValue));
        }
    }
    if (calendarOptions.min){
        if (calendarOptions.min=="now"){
            minDate = new Date();
        }else if (calendarOptions.min.length==10){
            minDate = new Date(calendarOptions.min.substr(0,4),calendarOptions.min.substr(5,2),calendarOptions.min.substr(7,2));
        }else if (calendarOptions.min.isNumber() || calendarOptions.min.substr(0,1)=="-" || calendarOptions.min.substr(0,1)=="+"){
            minDate = new Date();
            minDate.setDate(new Date().getDate() + parseInt(calendarOptions.min));
        }
    }
    if (calendarOptions.max){
        if (calendarOptions.max=="now"){
            maxDate = new Date();
        }else if (calendarOptions.max.length==10){
            maxDate = new Date(calendarOptions.max.substr(0,4),calendarOptions.max.substr(5,2),calendarOptions.max.substr(7,2));
        }else if (calendarOptions.max.isNumber() || calendarOptions.max.substr(0,1)=="-" || calendarOptions.max.substr(0,1)=="+"){
            maxDate = new Date();
            maxDate.setDate(new Date().getDate() + parseInt(calendarOptions.max));
        }
    }
    $(target).kendoDatePicker({
        animation: false,
        culture: calendarOptions.culture,
        format: calendarOptions.format,
        value: curDate,
        min: minDate,
        max: maxDate,
        change: function (e) {
            if (calendarOptions.maxid) {
                $("#"+calendarOptions.maxid).data("kendoDatePicker").min(this.value());
            }
            if (calendarOptions.minid) {
                $("#"+calendarOptions.minid).data("kendoDatePicker").max(this.value());
            }
        }
    });

};



$.fn.calendar.parseOptions = function (target, options, properties) {
    options = options || {};
    properties = properties || {};
    return $.extend(options, $.parser.parseOptions(target, [properties]));
};
