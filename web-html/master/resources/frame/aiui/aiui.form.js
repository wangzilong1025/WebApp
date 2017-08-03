/**
 * @author zhangrp
 * Created on 2016/1/12 10:37
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

/**
 * form表单
 * 属性
 *editable: 是否可编辑
 *initial: 是否需要初始化数据,如果为true，调用beforeQuery获取参数，调用querySrvId获取数据，调用AfterQuery方法设置返回的数据到form中
 * valueSplitFlag
 *
 *
 * 方法
 * setData: 设置form中各个元素的数据
 * getData: 获取form中各个元素的数据
 *
 *
 *
 * 事件
 *
 *
 *
 */

$.fn.form = function () {
    var that = this;
    var target = that[0];
    if ($.data(target, 'form')) {
        return $.data(target, 'form');
    }
    return $.fn.form.create(target);
};

$.fn.form.create = function (target) {
    var options = $.fn.form.parseOptions(target, {
        editable: true,
        initial: false,
        schemaData: "data",
        autoBind: true
    }, {
        editable: "boolean",
        initial: "boolean",
        srvId: "string",
        schemaData: "string",
        autoBind: "boolean"
    });

    //如果from设置的不可编辑，需要将form中所有包含的元素改为不可编辑
    if (!options.editable) {
        $(target).find(":input").attr("disabled", "true");
    }

    var from = {};
    from.id = $(target).attr("id") ? $(target).attr("id") : new Date().getTime();
    from.options = options;
    from.target = target;
    $.extend(from, $.fn.form.methods);
    $.data(target, 'form', from);


    if (options.srvId) {
        if (options.autoBind == true) {
            from.load();
        }
    }
    return from;
};

$.fn.form.parseOptions = function (target, options, properties) {
    options = options || {};
    properties = properties || {};
    return $.extend(options, $.parser.parseOptions(target, [properties]));
};

$.fn.form.methods = {

    load: function () {
        var that = this;
        var options = that.options;

        var data;
        if (options.beforeQuery) {
            data = options.beforeQuery(data);
        }
        var param = {
            "param": data,
            "busiCode": options.srvId
        };
        comm.ajax.ajaxEntity(param, function (data) {
            var schema_data = options.schemaData.split(".");
            for (var i = 0; i < schema_data.length; i++) {
                data = data[schema_data[i]];
            }
            if (options.afterQuery) {
                data = options.afterQuery(data);
            }
            that.setData(data);
        });
    },

    /**
     * 从form中获取值，如果传了ID通过ID取值；如果没有传值则返回form所有的值
     * @param id
     * @returns {*}
     */
    getData: function (id) {
        if (id && $("#" + id)) {
            var target = $("#" + id);
            var value = "";
            if(target!=undefined && target!=null) {
                if (target.hasClass("aiui-combobox")) {
                    value = target.combobox().value();
                    if(comm.lang.isArray(value)) {
                        //判断是否有分割符
                        var valueSplitFlag = target.attr("valueSplitFlag");
                        if (valueSplitFlag && valueSplitFlag != null && valueSplitFlag != "") {
                            value = value.join(valueSplitFlag);
                        }
                    }
                } else if (target.hasClass("aiui-address")) {
                    value = target.address().value();
                } else {
                    value = target.val();
                }
            }
            return value;
        } else {
            var obj = {};
            var that = this;
            var inputs = $(this.target).find(":input[role!='listbox']");
            if(inputs!=null && inputs.length>0){
                for(var i=0;i<inputs.length;i++){
                    if(!$(inputs[i]).hasClass("aiui-combobox")) {
                        var id = $(inputs[i]).attr("id");
                        if (comm.lang.isEmpty(id)) {
                            id = $(inputs[i]).attr("name");
                        }
                        obj[id] = $(inputs[i]).val();
                    }
                }
            }
            var selects = $(this.target).find("select");
            if(selects!=null && selects.length>0){
                for(var i=0;i<selects.length;i++){
                    var id = $(selects[i]).attr("id");
                    if(comm.lang.isEmpty(id)){
                        id = $(selects[i]).attr("name");
                    }
                    var value = $(selects[i]).combobox().value();
                    if(comm.lang.isArray(value)) {
                        //判断是否有分割符
                        var valueSplitFlag = $(selects[i]).attr("valueSplitFlag");
                        if (valueSplitFlag && valueSplitFlag != null && valueSplitFlag != "") {
                            value = value.join(valueSplitFlag);
                        }
                    }
                    obj[id] = value;
                }
            }
            var address = $(this.target).find(".aiui-address");
            if(address!=null && address.length>0){
                for(var i=0;i<address.length;i++){
                    var id = $(address[i]).attr("id");
                    if(comm.lang.isEmpty(id)){
                        id = $(address[i]).attr("name");
                    }
                    obj[id] = $(address[i]).address().value();
                }
            }
            return obj;
        }
    },

    /**
     * 设置form的值
     * @param id
     * @param value
     * @returns {boolean}
     */
    setData: function (id, value) {
        if (typeof id == 'string' && value != undefined && $("#" + id)) {
            var target = $("#" + id);
            if(target.hasClass("aiui-combobox")){
                //判断是否有分割符
                var valueSplitFlag = target.attr("valueSplitFlag");
                if (valueSplitFlag && valueSplitFlag != null && valueSplitFlag != "") {
                    value = value.split(valueSplitFlag);
                }else{
                    value = eval(value);
                }
                target.combobox().value(value);
            }else if(target.hasClass("aiui-address")){
                target.address().value(value);
            }else{
                target.val(value);
            }
        } else if (typeof id == 'string' && value == undefined) {
            try {
                var id = $.parseJSON(id);
                $.each(id, function (key, value) {
                    var target = $("#" + key);
                    if(target.hasClass("aiui-combobox")){
                        //判断是否有分割符
                        var valueSplitFlag = target.attr("valueSplitFlag");
                        if (valueSplitFlag && valueSplitFlag != null && valueSplitFlag != "") {
                            value = value.split(valueSplitFlag);
                        }
                        target.combobox().value(value);
                    }else if(target.hasClass("aiui-address")){
                        target.address().value(value);
                    }else{
                        target.val(value);
                    }
                });
            } catch (e) {

            }
        } else if (typeof id == 'object') {
            $.each(id, function (key, value) {
                var target = $("#" + key);
                if(target.hasClass("aiui-combobox")){
                    //判断是否有分割符
                    var valueSplitFlag = target.attr("valueSplitFlag");
                    if (valueSplitFlag && valueSplitFlag != null && valueSplitFlag != "") {
                        value = value.split(valueSplitFlag);
                    }else{
                        value = eval(value);
                    }
                    target.combobox().value(value);
                }else if(target.hasClass("aiui-address")){
                    target.address().value(value);
                }else{
                    target.val(value);
                }
            });
        }
    },

    /**
     * 设置form是否可编辑
     * @param disabled：true--整个form里的内容不可编辑，false-整个form里的内容可编辑
     */
    setFormDisabled: function(disabled) {
        var that = this;
        if (disabled) {
            $(that.target).find(":input").attr("disabled", "true");
            $(that.target).find("select").each(function(){
                $(this).combobox().enable(false);
            });
            $(that.target).find(":input.aiui-datebox").each(function(){
                $(this).datebox().enable(false);
            });
        } else {
            $(that.target).find(":input").removeAttr("disabled");
            $(that.target).find("select").each(function(){
                $(this).combobox().enable(true);
            });
            $(that.target).find(":input.aiui-datebox").each(function(){
                $(this).datebox().enable(true);
            });
        }
    },

    /**
     * 设置某个元素是否可编辑
     * @param id：元素Id
     * @param disabled:true--该元素内容不可编辑，false-该元素内容可编辑
     */
    setDisabled: function (id, disabled) {
        var that = this;
        if (disabled) {
            $(that.target).find("[id=" + id + "]").attr("disabled", "true");
        } else {
            $(that.target).find("[id=" + id + "]").attr("disabled", "false");
        }
    },

    /**
     * 为所有录入要素添加事件，校验输入值是否正确
     * */
    blurValidate:function(){
        $(this.target).find(":input").each(function(){
            var validationType = $(this).attr("validationType");
            var validationMessage = $(this).attr("validationMessage");
            if(!comm.lang.isEmpty(validationType)){
                $(this).bind("blur",function(){
                    var validationTypes = validationType.split(",");
                    if(validationTypes!=null && validationTypes.length>0) {
                        for (var i = 0; i < validationTypes.length; i++) {
                            if(!comm.lang.isEmpty(validationTypes[i])) {
                                if (!comm.validate.validate($(this), comm.validate.regexp[validationTypes[i]])) {
                                    if (!comm.lang.isEmpty(validationMessage)) {
                                        comm.dialog.notice({
                                            type: comm.dialog.type.error,
                                            content: validationMessage,
                                            position: "center",
                                            timeout: 800
                                        })
                                    }
                                    return false;
                                }
                            }
                        }
                    }
                });
            }
        });

        $(this.target).find("select").each(function(){
            var validationType = $(this).attr("validationType");
            var validationMessage = $(this).attr("validationMessage");
            if(!comm.lang.isEmpty(validationType)){
                $(this).bind("blur",function(){
                    var value = $(this).combobox().value();
                    var validationTypes = validationType.split(",");
                    if(validationTypes!=null && validationTypes.length>0){
                        for(var i=0;i<validationTypes.length;i++){
                            if(!comm.lang.isEmpty(validationTypes[i])) {
                                if (!comm.validate.checkReg(comm.validate.regexp[validationTypes[i]], value)) {
                                    if (!comm.lang.isEmpty(validationMessage)) {
                                        comm.dialog.notice({
                                            type: comm.dialog.type.error,
                                            content: validationMessage,
                                            position: "center",
                                            timeout: 800
                                        })
                                    } else {
                                        comm.validate.validateError($(this));
                                    }
                                    return false;
                                }
                            }
                        }
                    }
                });
            }
        });
    },
    validate:function(){
        var inputVals = $(this.target).find(":input");
        if(inputVals!=null && inputVals.length>0){
            for(var i=0;i<inputVals.length;i++){
                var validationType = $(inputVals[i]).attr("validationType");
                var validationMessage = $(inputVals[i]).attr("validationMessage");
                if(!comm.lang.isEmpty(validationType)){
                    var validationTypes = validationType.split(",");
                    if(validationTypes!=null && validationTypes.length>0) {
                        for (var j = 0; j < validationTypes.length; j++) {
                            if(!comm.lang.isEmpty(validationTypes[j])) {
                                if (!comm.validate.validate($(inputVals[i]), comm.validate.regexp[validationTypes[j]])) {
                                    comm.dialog.notice({
                                        type: comm.dialog.type.error,
                                        content: validationMessage,
                                        position: "center",
                                        timeout: 800
                                    })
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        var selectVals = $(this.target).find("select");
        if(selectVals!=null && selectVals.length>0){
            for(var i=0;i<selectVals.length;i++){
                var validationType = $(selectVals[i]).attr("validationType");
                var validationMessage = $(selectVals[i]).attr("validationMessage");
                if(!comm.lang.isEmpty(validationType)){
                    var value = $(selectVals[i]).combobox().value();
                    var validationTypes = validationType.split(",");
                    if(validationTypes!=null && validationTypes.length>0){
                        for(var j=0;j<validationTypes.length;j++){
                            if(!comm.lang.isEmpty(validationTypes[j])) {
                                if (!comm.validate.checkReg(comm.validate.regexp[validationTypes[j]], value)) {
                                    if (!comm.lang.isEmpty(validationMessage)) {
                                        comm.dialog.notice({
                                            type: comm.dialog.type.error,
                                            content: validationMessage,
                                            position: "center",
                                            timeout: 800
                                        })
                                    } else {
                                        comm.validate.validateError($(selectVals[i]));
                                    }
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
};

