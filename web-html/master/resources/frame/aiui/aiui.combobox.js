/**
 * @author zhangrp
 * Created on 2015/9/28 15:32
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */


/**
 * combobox
 *
 *
 * 【属性】
 *
 * srvId: 服务ID
 * beforeLoad: 加载参数
 * dataTextField:
 * dataValueField:
 * defaultValue:默认值
 * hasAll: 是否有 ”全选“ 选项
 *
 * type: static 默认， 从cfg_static_data中取, 配合param使用。如果涉及级联通过parentCode使用
 *       dynamic 通过srvId取数据
 * param: 参数
 * parentCode: 级联父元素
 *
 *
 *
 *
 *
 *
 * 【方法】
 * getSelect: 获取选中值，返回的是对象， 注意区别直接获取值
 * value: Gets or sets the value of the ComboBox
 * text: Gets or sets the text of the ComboBox. Widget will select the item with same text.
 *             If there are no matches then the text will be considered as a custom value of the widget.
 * readonly: true/false
 * enable: true/false
 * load: 加载数据
 *
 * 【事件】
 * select: 选择时触发,通过$("#id").combobox().bind('select', function(e){todo})使用
 * change: 改变时触发
 *
 */
$.fn.combobox = function () {
    var that = this;
    var target = that[0];
    if ($(target).is("select") === false && ($(target).is("input") === false || $(target).attr("tabindex") !== undefined )) {
        return;
    }

    var multFlag = $(target).attr("multFlag");
    if(!comm.lang.isEmpty(multFlag) && (multFlag==true || multFlag=="true")){
        if ($.data(target, 'combobox')) {
            return $(target).data("kendoMultiSelect");
        }
    }else{
        if ($.data(target, 'combobox')) {
            return $(target).data("kendoComboBox");
        }
    }
    return $.fn.combobox.create(target);
};

$.fn.combobox.create = function (target) {
    var options = $.fn.combobox.parseOptions(target, {
        schemaData: 'data',
        autoBind: true,
        hasAll: false,
        multFlag:false,
        filter: 'contains',
        invokeType: 'in'
    }, {
        srvId: "string",
        dataTextField: "string",
        dataValueField: "string",
        defaultValue: "string",
        schemaData: 'string',
        autoBind: 'boolean',
        type: 'string',
        codeType:'string',
        codeValue:'string',
        parentCode:'string',
        parentCodeField:'string',
        param: 'string',
        hasAll: 'boolean',
        index: 'string',
        filter: 'string',
        invokeType: 'string',
        multFlag:'boolean'
    });
    var dataSource = undefined;
    if (options.srvId) {
        dataSource = $.extend({}, {
            transport: {
                read: {
                    url: options.invokeType === "out" ? SERVER_URL_OUT : SERVER_URL,
                    type: "POST"
                },
                parameterMap: function (data, operation) {
                    if (operation == "read") {
                        if (options.beforeLoad) {
                            data = options.beforeLoad(data);
                        }
                        var jsonRequstParam = {
                            "param": data,
                            "busiCode": options.srvId
                        };
                        return comm.ajax.paramWrap(jsonRequstParam);
                    } else if (data.models) {
                        return JSON.stringify(data.models);
                    }
                }
            },
            batch: true
        });
        dataSource = $.extend(dataSource, {
            batch: true,
            schema: {
                data: options.schemaData
            }
        });
    } else if (options.type == 'static') {
        var codeType = options.codeType;
        var codeValue = options.codeValue;
        var parentCode = options.parentCode;
        var param;
        if (options.beforeLoad) {
            param = options.beforeLoad({});
        }else if(!comm.lang.isEmpty(options.param)){
            param = JSON.parse(options.param);
        }
        if(param && !comm.lang.isEmpty(param.codeType)){
            codeType = param.codeType;
            codeValue = param.codeValue;
            parentCode = param.parentCode;
        }

        if(!comm.lang.isEmpty(options.parentCodeField)){
            parentCode = $("#"+options.parentCodeField).val();
        }

        options.codeType = codeType;
        options.codeValue = codeValue;
        if (options.dataTextField === undefined) {
            options.dataTextField = "codeName";
        }
        if (options.dataValueField === undefined) {
            options.dataValueField = "codeValue";
        }
        if (!comm.lang.isEmpty(options.parentCodeField)) {
            //事件
            $("#" + options.parentCodeField).combobox().bind('change', function (e) {
                $(target).combobox().load();
            });
        }
        var retData = comm.tools.getStaticData({
            codeType:codeType,
            codeValue:codeValue,
            parentCode:parentCode,
            sync:false
        });
        if (options.hasAll == true) {
            var obj = {};
            obj[options.dataTextField] = "全部";
            obj[options.dataValueField] = "";
            var temp = [];
            temp.push(obj);
            for (var p = 0; p < retData.length; p++) {
                temp.push(retData[p]);
            }
            retData = temp;
        }
        dataSource = retData;
    }
    var id = $(target).attr("id") ? $(target).attr("id") : new Date().getTime();
    $.data(target, 'combobox', id);


    var index = 0;
    if (options.index) {
        options.index = parseInt(options.index);
        if (options.index < 0) {
            index = undefined;
        } else {
            index = options.index;
        }
    }
    var filter = "contains";
    if (options.filter) {
        options.filter = options.filter;
        if (options.filter != "startswith" && options.filter != "contains" && options.filter != "eq") {
            filter = "contains";
        } else {
            filter = options.filter;
        }
    }
    if(options.multFlag==true || options.multFlag=="true"){
        $(target).kendoMultiSelect({
            autoBind: options.autoBind,
            dataSource: dataSource,
            dataTextField: options.dataTextField,
            dataValueField: options.dataValueField,
            value: options.defaultValue,
            index: index,
            filter: filter
        });
        $(target).data("kendoMultiSelect")._aiui_options = options;
        return $(target).data("kendoMultiSelect");
    }else{
        $(target).kendoComboBox({
            autoBind: options.autoBind,
            dataSource: dataSource,
            dataTextField: options.dataTextField,
            dataValueField: options.dataValueField,
            value: options.defaultValue,
            index: index,
            filter: filter
        });
        $(target).data("kendoComboBox")._aiui_options = options;
        return $(target).data("kendoComboBox");
    }
};

$.fn.combobox.parseOptions = function (target, options, properties) {
    options = options || {};
    properties = properties || {};
    return $.extend(options, $.parser.parseOptions(target, [properties]));
};

$.fn.combobox.load = function () {
    var that = this;
    var options = that._aiui_options;
    var target = that.element;
    if (options.srvId) {
        that.dataSource.read();
    } else if (options.parentCodeField && $("#" + options.parentCodeField).length > 0) {
        var parentCodeValue = $("#" + options.parentCodeField).val();
        var data_ = comm.tools.getStaticData({
            codeType:options.codeType,
            codeValue:options.codeValue,
            parentCode:parentCodeValue,
            sync:false
        });

        if (options.hasAll == true) {
            var obj = {};
            obj[options.dataTextField] = "全部";
            obj[options.dataValueField] = "";
            var temp = [];
            temp.push(obj);
            for (var p = 0; p < data_.length; p++) {
                temp.push(data_[p]);
            }
            data_ = temp;
        }
        $(target).combobox().dataSource.data(data_);
    }
};


/**
 * 获取选中值，返回的是对象， 注意区别直接获取值
 * @returns {{}}
 */
$.fn.combobox.getSelect = function () {
    var that = this;
    var _select_value = that.value();
    var _select_text = that.text();
    var _data = that.dataSource._data;
    var _target = {};
    for (var i = 0; i < _data.length; i++) {
        if (_data[i][that.options.dataTextField] == _select_text && _data[i][that.options.dataValueField] == _select_value) {
            _target = _data[i];
            break;
        }
    }
    return _target;
};

kendo.ui.ComboBox.prototype["getSelect"] = $.fn.combobox.getSelect;
kendo.ui.ComboBox.prototype["load"] = $.fn.combobox.load;
kendo.ui.MultiSelect.prototype["load"] = $.fn.combobox.load;
