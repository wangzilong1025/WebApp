/**
 * @author zhangrp
 * Created on 2016/3/10 14:06
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */


(function ($) {
    var i = "";
    var kendo = window.kendo,
        ui = kendo.ui,
        proxy = $.proxy,
        Widget = ui.Widget;

    var AIAddress = Widget.extend({
        init: function (element, options) {
            var that = this;
            Widget.fn.init.call(that, element, options);

            element = that.element;
            options = that.options;
            that.wrapper = $(element);

            var invokeType = options.invokeType || "in";
            var targetWidth = $(element).width();
            if (options.width) {
                targetWidth = options.width;
            }
            var width = targetWidth;
            if (width < 470) {
                width = 470;
            }
            width = width - 5;
            var _id = $(element).attr("id");
            if(_id==undefined || _id==null || _id==""){
                _id = new Date().getTime();
            }
            var provinceId = "province_" + _id;
            var cityId = "city_" + _id;
            var countyId = "county_" + _id;
            //var spanId = "span_" + _id;
            var detailId = "detail_" + _id;
            options.provinceId = provinceId;
            options.cityId = cityId;
            options.countyId = countyId;
            //options.spanId = spanId;
            options.detailId = detailId;
            options.invokeType = invokeType;

            var required = $(element).attr("required");
            // var addressName = $(element).attr("name");
            var title = $(element).attr("title");
            if(comm.lang.isEmpty(title)){
                title = "";
            }
            i = i + " ";
            //if(addressName === undefined){
            var addressName = i;
            //}else {
            //addressName = " " + addressName + " ";
            //}
            var html = "<div>" +
                "	<select class='aiui-combobox' id='" + provinceId + "'" + (required ? "required" : "") +
                "           validationType="+(required?"'notempty'":"''")+" validationMessage='"+title+"省份不能为空'"+
                "	        name='" + addressName + "'" +
                "	        type='static'" +
                "	        codeType='PROVINCE'" +
                "	        dataTextField='codeName'" +
                "	        dataValueField='codeValue'" +
                "	        placeholder='--省/直辖市--'" +
                "	        defaultValue='上海市'" +
                "	        invokeType='" + invokeType + "'" +
                "	        style='width: 100px;'>" +
                "	</select>" +
                "	<select class='aiui-combobox' id='" + cityId + "'" + (required ? "required" : "") +
                "           validationType="+(required?"'notempty'":"''")+" validationMessage='"+title+"地市不能为空'"+
                "	        name='" + addressName + "'" +
                "	        type='static'" +
                "	        codeType='CITY'" +
                "	        dataTextField='codeName'" +
                "	        dataValueField='codeValue'" +
                "	        placeholder='--市--'" +
                "	        parentCodeField='" + provinceId + "'" +
                "	        defaultValue='上海'" +
                "	        invokeType='" + invokeType + "'" +
                "	        style='width: 100px;'>" +
                "	</select>" +
                "	<select class='aiui-combobox' id='" + countyId + "'" + (required ? "required" : "") +
                "           validationType="+(required?"'notempty'":"''")+" validationMessage='"+title+"区县不能为空'"+
                // "	<select class='aiui-combobox' id='" + countyId + "'" +
                "	        name='" + addressName + "'" +
                "	        type='static'" +
                "	        codeType='COUNTY'" +
                "	        dataTextField='codeName'" +
                "	        dataValueField='codeValue'" +
                "	        placeholder='--区/县--'" +
                "	        parentCodeField='" + cityId + "'" +
                "	        invokeType='" + invokeType + "'" +
                "	        style='width: 120px'>" +
                "	</select>" +
                //"</div>" +
                //"<div style='padding-top: 2px;'>" +
                //"	<span id='" + spanId + "'>上海市</span>" +
                "	<input id='" + detailId + "' name='" + addressName + "'  data-custcheck-msg='不能为空.'  validationType="+(required?"'notempty'":"''")+ " validationMessage='"+title+"地址不能为空' class='k-textbox' value='' " + (required ? "required" : "") + " style='width:" + (width-330) + "px;'>" +
                "</div>";
            $(element).html(html);
            $("#" + provinceId).combobox();
            $("#" + cityId).combobox();
            $("#" + countyId).combobox();


            $("#" + provinceId).combobox().bind('change', function () {
                $("#" + countyId).combobox().load();
            });
            $("#" + provinceId).on('change', proxy(that._change, that));
            $("#" + cityId).on('change', proxy(that._change, that));
            $("#" + countyId).on('change', proxy(that._change, that));
            $("#" + detailId).on('change', proxy(that._change, that));


            //$("#" + provinceId).on('blur', proxy(that._blur, that));
            //$("#" + cityId).on('blur', proxy(that._blur, that));
            //$("#" + countyId).on('blur', proxy(that._blur, that));
            //$("#" + detailId).on('blur', proxy(that._blur, that));
            //$(element).on('blur', proxy(that._blur, that));


            that.readonly(options.readonly);
            that.enable(options.enabled);

            $(element).width("auto");

        },
        options: {
            name: "AIAddress"
        },
        _change: function () {
            var that = this;
            that.trigger("change");
        },
        _blur: function () {
            var that = this;
            that.trigger("blur");
        },
        readonly: function (flag) {
            var that = this;
            var options = that.options;
            if (flag !== undefined) {
                that.options.readonly = flag;
                $("#" + options.provinceId).combobox().readonly(flag);
                $("#" + options.cityId).combobox().readonly(flag);
                $("#" + options.countyId).combobox().readonly(flag);
                if (flag == false) {
                    $("#" + options.detailId).removeAttr("readonly");
                } else {
                    $("#" + options.detailId).attr("readonly", "readonly");
                }
            }
            return that.options.readonly;
        },
        enable: function (flag) {
            var that = this;
            var options = that.options;
            if (flag !== undefined) {
                that.options.enabled = flag;
                $("#" + options.provinceId).combobox().enable(flag);
                $("#" + options.cityId).combobox().enable(flag);
                $("#" + options.countyId).combobox().enable(flag);
                if (flag == true) {
                    $("#" + options.detailId).removeAttr("disabled");
                } else {
                    $("#" + options.detailId).attr("disabled", "disabled");
                }
            }
            return that.options.enabled;
        },
        value: function (value) {
            var that = this;
            var options = that.options;
            if (value === undefined) {
                if ($("#" + options.provinceId).combobox().text() == "" || $("#" + options.cityId).combobox().text() == "" ||
                    //$("#" + options.countyId).combobox().text() == "" ||
                    $("#" + options.detailId).val() == "") {
                    return "";
                }
                return $("#" + options.provinceId).combobox().text() + " " +
                    $("#" + options.cityId).combobox().text() + " " +
                    $("#" + options.countyId).combobox().text() + " " +
                    $("#" + options.detailId).val();
            }
            if(value == null){
                return ;
            }
            var vlaues = value.split(" ");
            if (vlaues.length > 3) {
                //$("#" + options.spanId).text(vlaues[0] + " " + vlaues[1] + " " + vlaues[2]);
                $("#" + options.provinceId).combobox().text(vlaues.shift());
                $("#" + options.cityId).combobox().load();
                $("#" + options.cityId).combobox().text(vlaues.shift());

                $("#" + options.countyId).combobox().load();
                $("#" + options.countyId).combobox().text(vlaues.shift());

                $("#" + options.detailId).val(vlaues.join(""));
            } else {
                $("#" + options.detailId).val(value);
            }
        },
        provinceText: function (text) {
            var that = this;
            var options = that.options;
            return $("#" + options.provinceId).combobox().text(text);
        },
        province: function (value) {
            var that = this;
            var options = that.options;
            return $("#" + options.provinceId).combobox().value(value);
        },
        cityText: function (text) {
            var that = this;
            var options = that.options;
            return $("#" + options.cityId).combobox().text(text);
        },
        city: function (value) {
            var that = this;
            var options = that.options;
            return $("#" + options.cityId).combobox().value(value);
        },
        countyText: function (text) {
            var that = this;
            var options = that.options;
            return $("#" + options.countyId).combobox().text(text);
        },
        county: function (value) {
            var that = this;
            var options = that.options;
            return $("#" + options.countyId).combobox().value(value);
        },
        detail: function (value) {
            var that = this;
            var options = that.options;
            if(value){
                $("#" + options.detailId).val(value);
            }
            return $("#" + options.detailId).val();
        }
    });
    ui.plugin(AIAddress);


})(jQuery);


$.fn.address = function () {
    var that = this;
    var target = that[0];
    if ($(target).is("div") == false) {
        return;
    }
    if ($.data(target, 'aiaddress')) {
        return $(target).data("kendoAIAddress");
    }
    return $.fn.address.create(target);
};

$.fn.address.create = function (target) {
    var options = $.fn.address.parseOptions(target, {
        readonly: false,
        enabled: true,
        invokeType: 'in'
    }, {
        readonly: "boolean",
        enabled: "boolean",
        width: "number",
        invokeType: "string"
    });

    $(target).kendoAIAddress({
        readonly: options.readonly,
        enabled: options.enabled,
        width: options.width,
        invokeType: options.invokeType
    });

    var id = $(target).attr("id") ? $(target).attr("id") : new Date().getTime();
    $.data(target, 'aiaddress', id);

    return $(target).data("kendoAIAddress");
};

$.fn.address.parseOptions = function (target, options, properties) {
    options = options || {};
    properties = properties || {};
    return $.extend(options, $.parser.parseOptions(target, [properties]));
};


