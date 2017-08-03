/**
 * @author zhangrp
 * Created on 2015/12/31 9:35
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */

/**
 * table
 * example: <table class="aiui-datagrid" id="taskGrid" height="400" data-options="srvId:'IWORKFLOWFSV_QUERYTASK',pageSize:5, beforeLoad: getParams"></table>
 *
 * 属性
 *  srvId: 后台服务对应的srvId
 *  pageable: 是否支持分页
 *  pageSize: 页面大小
 *  autoBind: defult false, 不自动加载数据
 *  height: 表格高度，default 400
 *  sortable: 列是否支持排序，default true
 *  selectable: 是否可以选择，defult row,  其它值：
 *              "row" - the user can select a single row.
 *              "cell" - the user can select a single cell.
 *              "multiple, row" - the user can select multiple rows.
 *              "multiple, cell" - the user can select multiple cells.
 *  beforeLoad: 数据加载前封装参数，为方法，必须写在data-options中
 *  schemaData: 数据明细对象。default data.list
 *  schemaTotal: 数据总数对象。default data.count
 *  editable: 是否可以编辑。true or false. default false
 *  filterable：是否可过滤
 *
 *  idField: 如果表格修改时必须要有，否则无法获取到改变的数据
 *
 *  rownumbers: true or false, 是否显示序列，默认不显示
 *  checkbox: true or false,是否有checkbox框，默认没有
 *  detailTemplate: 明细，为script的id
 *
 * clo
 * example: <th field="businessId" data-options="width:80">申请单编号</th>
 * 列属性
 *  field: 列
 *  width: 宽度
 *  hidden: 是否隐藏
 *  template: 数据处理模板，为方法，必须写在data-options中,且方法必须在初始化时可见
 *  pattern: 数据校验，编辑时使用
 *
 *
 *  type: 数据类型，string,number,boolean,date,datetime,email,dynamic,static.        The default is "string".
 *  param : 参数
 *  editable: 是否可编辑，default true
 *  defaultValue: 编辑时默认值
 *  nullable: 可否为null
 *  required: 是否必须
 *  min: 最小值
 *  max: 最大值,
 *  locked: (default: false) If set to true the column will be displayed as locked in the grid.
 *  lockable Boolean (default: true) If set to false the column will remain in the side of the grid into which its own locked configuration placed it.
 *
 *
 *
 * 方法
 *  getChanged: 获取改变数据
 *  exportExcel: 导出数据到excel
 *  load: 加载数据
 *  getSelects: 获取选中行 多行
 *  getSelect: 获取选中行 单行
 *  addRow: 添加一行
 *  addData: 添加数据
 *  getData：获取所有数据
 *  unSelect： 取消选中
 *  isRowExpanded: 判断行是否已展开
 *
 *
 *
 * 事件
 *  dbclick           双击行
 */



$.fn.datagrid = function () {
    var that = this;
    var target = that[0];
    if (that.attr("gridId") && $("#" + that.attr("gridId"))) {
        return $("#" + that.attr("gridId")).data("kendoGrid");
    }
    var gridId = new Date().getTime();
    that.css("display", "none");
    that.attr("gridId", gridId);
    return $.fn.datagrid.create(gridId, target);
};

$.fn.datagrid.create = function (gridId, target) {
    var tableOptions = $.fn.datagrid.parseOptions(target, {
        sortable: true,
        height: 400,
        autoBind: false,
        selectable: 'row',
        editable: false,
        rownumbers: false,
        checkbox: false,
        locked: false,
        filterable:false
    }, {
        srvId: "string",
        pageSize: "number",
        height: "number",
        autoBind: "boolean",
        pageable: "boolean",
        schemaData: 'string',
        schemaTotal: 'string',
        editable: 'boolean',
        sortable: 'boolean',
        selectable: 'string',
        idField: 'string',
        rownumbers: 'boolean',
        checkbox: 'boolean',
        detailTemplate: 'string',
        locked: 'boolean',
        filterable:'boolean'
    });
    var dataSource = {};
    var pageable = false;
    var columns = [];
    var schema = {};

    var model = {};
    model.fields = {};
    if (tableOptions.idField) {
        model.id = tableOptions.idField;
    }


    if (tableOptions.rownumbers != undefined && tableOptions.rownumbers == true) {
        var col = {
            field: "",
            title: "",
            width: 30,
            attributes: {
                //border-right-color: \\#ccc; border-right-style: solid; border-right-width: 1px; background-color: \\#f5f5f5;
                style: "text-align: center; "
            },
            template: function (dataItem) {
                return "" + (dataItem.parent().indexOf(dataItem) + 1);
            },
            locked: tableOptions.locked
        };
        columns.push(col);
    }
    if (tableOptions.checkbox != undefined && tableOptions.checkbox == true && tableOptions.selectable != "false") {
        var col = {
            field: "",
            title: "<input type='checkbox' name='" + gridId + "_header_check'>",
            width: 30,
            template: function (dataItem) {
                return "<input type='checkbox' name='" + gridId + "_check'>";
            },
            locked: tableOptions.locked
        };
        columns.push(col);
    }


    $(target).find("th").each(function () {
        var col = $.fn.datagrid.parseOptions(this, {title: $.trim($(this).text()), editable: true, type: 'string', locked: false, lockable: true }, {
            field: 'string',
            width: 'number',
            hidden: 'boolean',
            pattern: 'string',

            type: 'string',
            param: 'string',
            editable: 'boolean',
            defaultValue: 'string',
            nullable: 'boolean',
            required: 'boolean',
            min: 'number',
            max: 'number',
            srvId: 'string',
            schemaData: 'string',
            dataTextField: 'string',
            dataValueField: 'string',
            valueSplitFlag:"string",
            selectOnly: 'boolean',

            locked: 'boolean',
            lockable: 'boolean',
            filterable:'boolean'
        });
        if (col["type"] && col["field"]) {
            switch (col["type"].toUpperCase()) {
                case 'DATE':
                    col.template = function (dataItem) {
                        if (dataItem[col["field"]]!=undefined && dataItem[col["field"]]!=null) {
                            var param = "yyyy-MM-dd";
                            if (col["param"]) {
                                param = col["param"];
                            }
                            if (dataItem[col["field"]] != '') {
                                if (typeof dataItem[col["field"]] === 'Date') {
                                    return kendo.format("{0:" + param + "}", dataItem[col["field"]]);
                                } else {
                                    return kendo.format("{0:" + param + "}", new Date(dataItem[col["field"]]));
                                }
                            }
                        }
                        if(col["defaultValue"]!=undefined && col["defaultValue"]!=null){
                            return col["defaultValue"];
                        }
                        return "";
                    };
                    if (tableOptions["editable"] == true) {
                        model.fields[col["field"]] = {
                            defaultValue: col["defaultValue"],
                            editable: col["editable"],
                            nullable: col["nullable"],
                            type: 'date',
                            validation: {
                                required: col["required"]
                            }
                        }
                    }else{
                        model.fields[col["field"]] = {
                            type: 'date'
                        }
                    }
                    break;
                case 'DATETIME':
                    col.template = function (dataItem) {
                        if (dataItem[col["field"]]!=undefined && dataItem[col["field"]]!=null) {
                            var param = "yyyy-MM-dd HH:mm:ss";
                            if (col["param"]) {
                                param = col["param"];
                            }
                            if (dataItem[col["field"]] != '') {
                                if (typeof dataItem[col["field"]] === 'Date') {
                                    return kendo.format("{0:" + param + "}", dataItem[col["field"]]);
                                } else {
                                    return kendo.format("{0:" + param + "}", new Date(dataItem[col["field"]]));
                                }
                            }
                        }
                        if(col["defaultValue"]!=undefined && col["defaultValue"]!=null){
                            return col["defaultValue"];
                        }
                        return "";
                    };
                    if (tableOptions["editable"] == true) {
                        model.fields[col["field"]] = {
                            defaultValue: col["defaultValue"],
                            editable: col["editable"],
                            nullable: col["nullable"],
                            type: 'date',
                            validation: {
                                required: col["required"]
                            }
                        };

                        if (col["editable"] == true) {
                            col.editor = function (container, options) {
                                var html = '<input ' + (col["required"] ? 'required="' + col["required"] + '"' : "") + '/>';
                                var input = $(html);
                                input.attr("name", options.field);
                                input.appendTo(container);
                                input.kendoDateTimePicker({
                                    animation: false
                                });
                                var tooltipElement = $('<span class="k-invalid-msg" data-for="' + options.field + '"></span>');
                                tooltipElement.appendTo(container);
                            };
                        }
                    }else{
                        model.fields[col["field"]] = {
                            type: 'date'
                        }
                    }
                    break;
                case 'NUMBER':
                    if (col["template"]!=undefined || col["template"]==null) {
                        col.template = function (dataItem) {
                            if (dataItem[col["field"]]!=undefined && dataItem[col["field"]]!=null) {
                                return dataItem[col["field"]];
                            }else{
                                if(col["defaultValue"]!=undefined && col["defaultValue"]!=null){
                                    return col["defaultValue"];
                                }
                            }
                            return "";
                        }
                    }
                    if (tableOptions["editable"] == true) {
                        model.fields[col["field"]] = {
                            defaultValue: col["defaultValue"],
                            editable: col["editable"],
                            nullable: col["nullable"],
                            type: 'number',
                            validation: {
                                required: col["required"],
                                min: col["min"],
                                max: col["max"],
                                pattern: col["pattern"]
                            }
                        }
                    }else{
                        model.fields[col["field"]] = {
                            type: 'number'
                        }
                    }
                    break;
                case 'BOOLEAN':
                    if (col["template"]!=undefined || col["template"]==null) {
                        col.template = function (dataItem) {
                            if (dataItem[col["field"]]!=undefined && dataItem[col["field"]]!=null) {
                                return dataItem[col["field"]];
                            }else{
                                if(col["defaultValue"]!=undefined && col["defaultValue"]!=null){
                                    return col["defaultValue"];
                                }
                            }
                            return "";
                        }
                    }
                    if (tableOptions["editable"] == true) {
                        model.fields[col["field"]] = {
                            defaultValue: col["defaultValue"],
                            editable: col["editable"],
                            type: 'boolean',
                            validation: {
                                required: col["required"]
                            }
                        }
                    }else{
                        model.fields[col["field"]] = {
                            type: 'boolean'
                        }
                    }
                    break;
                case 'EMAIL':
                    if (col["template"]!=undefined || col["template"]==null) {
                        col.template = function (dataItem) {
                            if (dataItem[col["field"]]!=undefined && dataItem[col["field"]]!=null) {
                                return dataItem[col["field"]];
                            }else{
                                if(col["defaultValue"]!=undefined && col["defaultValue"]!=null){
                                    return col["defaultValue"];
                                }
                            }
                            return "";
                        }
                    }
                    if (tableOptions["editable"] == true) {
                        model.fields[col["field"]] = {
                            defaultValue: col["defaultValue"],
                            editable: col["editable"],
                            type: 'string',
                            validation: {
                                required: col["required"],
                                type: 'email'
                            }
                        }
                    }else{
                        model.fields[col["field"]] = {
                            type: 'string'
                        }
                    }
                    break;
                case 'DYNAMIC':
                    if (col["srvId"]) {
                        var colDataTextField = col["dataTextField"]?col["dataTextField"]:"text";
                        var colDataValueField = col["dataValueField"]?col["dataValueField"]:"value";

                        var comboDataSource = {};
                        var param = {};
                        if(col["param"]!=undefined && col["param"]!=null && col["param"]!="") {
                            try{
                                param = eval(col["param"])(param)
                            }catch(e){
                                param = col["param"] || "";
                            }
                        }
                        comm.ajax.ajaxEntity({
                            sync: false,
                            param: param,
                            busiCode: col["srvId"],
                            callback: function (data) {
                                comboDataSource = data;
                            }
                        });
                        if(col["schemaData"]!=undefined && col["schemaData"]!=null && col["schemaData"]!="") {
                            comboDataSource = eval("comboDataSource." + col["schemaData"]);
                        }

                        col.template = function (dataItem) {
                            if ((dataItem[col["field"]] && dataItem[col["field"]]!=null) || (col["defaultValue"] && col["defaultValue"]!=null)) {
                                var retName = "";
                                var selectedCodes = dataItem[col["field"]];
                                if(selectedCodes==null || selectedCodes==""){
                                    selectedCodes = col["defaultValue"];
                                }
                                if (selectedCodes && selectedCodes != null) {
                                    var valueSplitFlag = col["valueSplitFlag"];
                                    var dataValues = [selectedCodes];
                                    if (valueSplitFlag && valueSplitFlag != null && valueSplitFlag != "") {
                                        dataValues = selectedCodes.split(valueSplitFlag);
                                    } else {
                                        valueSplitFlag = "";
                                    }
                                    if (dataValues != null && dataValues.length > 0 && comboDataSource != null && comboDataSource.length > 0) {
                                        for (var i = 0; i < dataValues.length; i++) {
                                            for (var j = 0; j < comboDataSource.length; j++) {
                                                if (comboDataSource[j][colDataValueField] == dataValues[i]) {
                                                    if (retName != null && retName != "") {
                                                        retName += valueSplitFlag;
                                                    }
                                                    retName += comboDataSource[j][colDataTextField];
                                                }
                                            }
                                        }
                                    }
                                    if (retName == null || retName == "") {
                                        retName = selectedCodes;
                                    }

                                    return retName;
                                }
                            }
                            return "";
                        };

                        if (tableOptions["editable"] == true) {
                            model.fields[col["field"]] = {
                                defaultValue: col["defaultValue"],
                                editable: col["editable"],
                                nullable: col["nullable"],
                                type: 'string',
                                validation: {
                                    required: col["required"],
                                    min: col["min"],
                                    max: col["max"]
                                }
                            };

                            if (col["editable"] == true) {
                                col.editor = function (container, options) {
                                    var input = $("<input/>");
                                    input.attr("name", options["field"]);
                                    if(col["required"] == true){
                                        input.attr("required", "required");
                                    }
                                    input.appendTo(container);
                                    if (col["selectOnly"]) {
                                        input.kendoDropDownList({
                                            dataSource: comboDataSource,
                                            dataTextField: colDataTextField,
                                            dataValueField: colDataValueField
                                        });
                                    } else {
                                        input.kendoComboBox({
                                            dataSource: comboDataSource,
                                            dataTextField: colDataTextField,
                                            dataValueField: colDataValueField
                                        });
                                    }
                                    var tooltipElement = $('<span class="k-invalid-msg" data-for="' + options.field + '"></span>');
                                    tooltipElement.appendTo(container);
                                };
                            }

                        }else{
                            model.fields[col["field"]] = {
                                type: 'string'
                            }
                        }
                    }
                    break;
                case 'STATIC':
                    var param = "";
                    if (col["param"]) {
                        param = col["param"];
                    }
                    col.template = function (dataItem) {
                        var codeValue = dataItem[col["field"]];
                        if(codeValue==undefined || codeValue==null){
                            if(col["defaultValue"]!=undefined && col["defaultValue"]!=null){
                                codeValue = col["defaultValue"];
                            }
                        }
                        if(codeValue!=undefined && codeValue!=null) {
                            var valueSplitFlag = col["valueSplitFlag"];
                            if (valueSplitFlag!=undefined && valueSplitFlag != null && valueSplitFlag != "") {
                                codeValue = codeValue.split(valueSplitFlag);
                            } else {
                                valueSplitFlag = "";
                            }
                            var selectedCodes = comm.tools.getStaticData({
                                sync: false,
                                codeType: param,
                                codeValue: codeValue
                            });
                            if (selectedCodes != null && selectedCodes.length > 0) {
                                var retValue = "";
                                for (var i = 0; i < selectedCodes.length; i++) {
                                    if (i > 0) {
                                        retValue += valueSplitFlag;
                                    }
                                    retValue += selectedCodes[i]["codeName"];
                                }
                                return retValue;
                            } else {
                                if(col["defaultValue"]!=null && col["defaultValue"]!=""){
                                    return col["defaultValue"];
                                }
                                return dataItem[col["field"]];
                            }
                        }
                        return "";
                    };

                    if (tableOptions["editable"] == true) {
                        model.fields[col["field"]] = {
                            defaultValue: col["defaultValue"],
                            editable: col["editable"],
                            nullable: col["nullable"],
                            type: 'string',
                            validation: {
                                required: col["required"],
                                min: col["min"],
                                max: col["max"]
                            }
                        };

                        if (col["editable"] == true) {
                            col.editor = function (container, options) {
                                var input = $("<input/>");
                                input.attr("name", options.field);
                                if(col["required"] == true){
                                    input.attr("required", "required");
                                }
                                input.appendTo(container);
                                if (col["selectOnly"]) {
                                    input.kendoDropDownList({
                                        dataSource: comm.tools.getStaticData({
                                            codeType:param
                                        }),
                                        dataTextField: "codeName",
                                        dataValueField: "codeValue"
                                    });
                                } else {
                                    input.kendoComboBox({
                                        dataSource: comm.tools.getStaticData({
                                            codeType:param
                                        }),
                                        dataTextField: "codeName",
                                        dataValueField: "codeValue"
                                    });
                                }
                                var tooltipElement = $('<span class="k-invalid-msg" data-for="' + options.field + '"></span>');
                                tooltipElement.appendTo(container);
                            };
                        }

                    }else{
                        model.fields[col["field"]] = {
                            type: 'string'
                        }
                    }
                    break;
                case 'STATICDATA':
                    if (col["param"]) {
                        var colDataTextField = col["dataTextField"]?col["dataTextField"]:"text";
                        var colDataValueField = col["dataValueField"]?col["dataValueField"]:"value";
                        var comboDataSource = {};
                        if(col["param"]!=undefined && col["param"]!=null && col["param"]!="") {
                            try{
                                comboDataSource = eval(col["param"])(param)
                            }catch (e) {
                                comboDataSource = eval(col["param"]) || {};
                            }
                        }
                        col.template = function (dataItem) {
                            if ((dataItem[col["field"]]!=undefined && dataItem[col["field"]]!=null) || (col["defaultValue"]!=undefined && col["defaultValue"]!=null)) {
                                var retName = "";
                                var selectedCodes = dataItem[col["field"]];
                                if(selectedCodes==null || selectedCodes==""){
                                    selectedCodes =  col["defaultValue"];
                                }
                                if (selectedCodes!=undefined && selectedCodes != null) {
                                    var valueSplitFlag = col["valueSplitFlag"];
                                    var dataValues = [selectedCodes];
                                    if (valueSplitFlag && valueSplitFlag != null && valueSplitFlag != "") {
                                        dataValues = selectedCodes.split(valueSplitFlag);
                                    } else {
                                        valueSplitFlag = "";
                                    }
                                    if (dataValues != null && dataValues.length > 0 && comboDataSource != null && comboDataSource.length > 0) {
                                        for (var i = 0; i < dataValues.length; i++) {
                                            for (var j = 0; j < comboDataSource.length; j++) {
                                                if (comboDataSource[j][colDataValueField] == dataValues[i]) {
                                                    if (retName != null && retName != "") {
                                                        retName += valueSplitFlag;
                                                    }
                                                    retName += comboDataSource[j][colDataTextField];
                                                }
                                            }
                                        }
                                    }
                                    if (retName == null || retName == "") {
                                        retName = selectedCodes;
                                    }

                                    return retName;
                                }
                            }
                            return "";
                        };

                        if (tableOptions["editable"] == true) {
                            model.fields[col["field"]] = {
                                defaultValue: col["defaultValue"],
                                editable: col["editable"],
                                nullable: col["nullable"],
                                type: 'string',
                                validation: {
                                    required: col["required"],
                                    min: col["min"],
                                    max: col["max"]
                                }
                            };

                            if (col["editable"] == true) {
                                col.editor = function (container, options) {
                                    var input = $("<input/>");
                                    input.attr("name", options["field"]);
                                    if(col["required"] == true){
                                        input.attr("required", "required");
                                    }
                                    input.appendTo(container);
                                    if (col["selectOnly"]) {
                                        input.kendoDropDownList({
                                            dataSource: comboDataSource,
                                            dataTextField: colDataTextField,
                                            dataValueField: colDataValueField
                                        });
                                    } else {
                                        input.kendoComboBox({
                                            dataSource: comboDataSource,
                                            dataTextField: colDataTextField,
                                            dataValueField: colDataValueField
                                        });
                                    }
                                    var tooltipElement = $('<span class="k-invalid-msg" data-for="' + options.field + '"></span>');
                                    tooltipElement.appendTo(container);
                                };
                            }

                        }else{
                            model.fields[col["field"]] = {
                                type: 'string'
                            }
                        }
                    }
                    break;

                case 'STRING':
                    if (col["template"]!=undefined && col["template"]!=null) {
                        col["editable"] = false;
                    }else{
                        col.template = function (dataItem) {
                            if (dataItem[col["field"]]!=undefined && dataItem[col["field"]]!=null) {
                                return dataItem[col["field"]];
                            }else{
                                if(col["defaultValue"]!=undefined && col["defaultValue"]!=null){
                                    return col["defaultValue"];
                                }
                            }
                            return "";
                        }
                    }
                    if (tableOptions["editable"] == true) {
                        model.fields[col["field"]] = {
                            defaultValue: col["defaultValue"],
                            editable: col["editable"],
                            nullable: col["nullable"],
                            type: 'string',
                            validation: {
                                required: col["required"],
                                pattern: col["pattern"]
                            }
                        };
                    }else{
                        model.fields[col["field"]] = {
                            type: 'string'
                        }
                    }
                    break;
            }
        }
        columns.push(col);
    });

    schema = $.extend(schema, {model: model});

    if (tableOptions.srvId) {
        dataSource = $.extend(dataSource, {
            transport: {
                read: {
                    url: SERVER_URL,
                    type: "POST"
                },
                parameterMap: function (data, operation) {
                    if (operation == "read") {
                        if (tableOptions.beforeLoad) {
                            data = tableOptions.beforeLoad(data);
                        }
                        var jsonRequstParam = {
                            "busiParams": data,
                            "busiCode": tableOptions.srvId
                        };
                        return comm.ajax.paramWrap(jsonRequstParam);
                    } else if (data.models) {
                        return JSON.stringify(data.models);
                    }
                }
            },
            batch: true
        });
        if (tableOptions.pageable == undefined || tableOptions.pageable != false) {
            dataSource = $.extend(dataSource, {
                batch: true,
                pageSize: (tableOptions.pageSize ? tableOptions.pageSize : 10),
                serverPaging: true,
                serverSorting: true
            });

            schema = $.extend(schema, {
                data: (tableOptions.schemaData ? tableOptions.schemaData : "data.list"),
                total: (tableOptions.schemaTotal ? tableOptions.schemaTotal : "data.count")
            });
            pageable = {};
            pageable = $.extend(pageable, {
                refresh: true,
                pageSizes: true,
                buttonCount: 5
            });
        } else {
            dataSource = $.extend(dataSource, {
                batch: true
            });
            schema = $.extend(schema, {
                data: (tableOptions.schemaData ? tableOptions.schemaData : "data.list"),
            });
        }
    }

    schema.parse = function (data) {//preprocess
        var fields = schema.model.fields || {};
        var path = schema.data.split(".");
        var rowDatas = data;
        for(var j = 0; j < path.length; j++){
            rowDatas = rowDatas[path[j]];
        }

        for(var p in fields){
            if(fields[p].type == 'date'){
                for(var i = 0; i < rowDatas.length; i++){
                    if(rowDatas[i][p] != null && typeof rowDatas[i][p] != 'Date'){
                        rowDatas[i][p] = new Date(rowDatas[i][p]);
                    }
                }
            }
        }
        return data;
    };
    dataSource = $.extend(dataSource, {
        schema: schema
    });

    var editable = undefined;
    if (tableOptions.editable && tableOptions.editable == true) {
        editable = {
            confirmation: false
        };
    }

    var filterable = undefined;
    if (tableOptions.filterable && tableOptions.filterable == true) {
        filterable = {
            extra: false,
            operators: {
                string: {
                    contains: "包含",
                    eq: "等于",
                    neq: "不等于",
                    startwith:"开始于",
                    endwith:"结束于"
                },
                number:{
                    eq:"等于",
                    neq:"不等于",
                    gte:"大于等于",
                    gt:"大于",
                    lte:"小于等于",
                    lt:"小于"
                },
                date:{
                    eq:"等于",
                    neg:"不等于",
                    gte:"大于等于",
                    gt:"大于",
                    lte:"小于等于",
                    lt:"小于"
                },
                enums:{
                    eq:"等于",
                    neq:"不等于"
                }
            }
        };
    }


    var grid = {
        editable: editable,
        filterable:filterable,
        dataSource: dataSource,
        height: tableOptions.height,
        sortable: tableOptions.sortable,
        autoBind: tableOptions.autoBind,
        pageable: pageable,
        columns: columns,
        selectable: tableOptions.selectable === "false" ? false : tableOptions.selectable
    };

    if (tableOptions.detailTemplate) {
        if ($("#" + tableOptions.detailTemplate) && $("#" + tableOptions.detailTemplate).length > 0) {
            grid["detailTemplate"] = kendo.template($("#" + tableOptions.detailTemplate).html());
        } else {
            grid["detailTemplate"] = kendo.template(tableOptions.detailTemplate);
        }
    }


    $("<div id='" + gridId + "' class='stripeTable'></div>").insertAfter($(target)).kendoGrid(grid);


    $("#" + gridId).data("kendoGrid").bind("dataBound", function (e) {
        //添加checkbox支持 checkbox
        if (tableOptions.checkbox != undefined && tableOptions.checkbox == true && tableOptions.selectable != "false") {

            if (tableOptions.selectable.indexOf("multiple") >= 0) {
                $("input[name=" + gridId + "_header_check]").click(function () {
                    if (this.checked == true) {
                        $("input[name=" + gridId + "_check]").each(function () {
                            this.checked = true;
                            $("#" + gridId).data("kendoGrid").select($(this).parent("td").parent("tr"));
                        });
                    } else {
                        $("input[name=" + gridId + "_check]").each(function () {
                            this.checked = false;
                            $("#" + gridId).data("kendoGrid").unSelect($(this).parent("td").parent("tr"));
                        });
                    }
                });

                $("input[name=" + gridId + "_check]").click(function () {
                    if (this.checked == true) {
                        $("#" + gridId).data("kendoGrid").select($(this).parent("td").parent("tr"));
                    } else {
                        $("#" + gridId).data("kendoGrid").unSelect($(this).parent("td").parent("tr"));
                    }
                    var allFlag = true;
                    $("input[name=" + gridId + "_check]").each(function () {
                        if (this.checked == false) {
                            allFlag = false;
                        }
                    });
                    $("input[name=" + gridId + "_header_check]").each(function () {
                        this.checked = allFlag;
                    });
                });
            } else {
                $("input[name=" + gridId + "_header_check]").each(function () {
                    this.disabled = true;
                })
            }


            $("#" + gridId).data("kendoGrid").element.on('click', '.k-grid-content tr', function () {
                $("input[name=" + gridId + "_check]").each(function () {
                    this.checked = false;
                });

                var selects = $("#" + gridId).data("kendoGrid").select();
                for (var i = 0; i < selects.length; i++) {
                    var select = selects[i];

                    $(select).children("td").children("[name=" + gridId + "_check]").each(function () {
                        this.checked = true;
                    });
                }
                var allFlag = true;
                $("input[name=" + gridId + "_check]").each(function () {
                    if (this.checked == false) {
                        allFlag = false;
                    }
                });
                $("input[name=" + gridId + "_header_check]").each(function () {
                    this.checked = allFlag;
                });
            });

        }
    });


    return $("#" + gridId).data("kendoGrid");
};

$.fn.datagrid.parseOptions = function (target, options, properties) {
    options = options || {};
    properties = properties || {};
    return $.extend(options, $.parser.parseOptions(target, [properties]));
};


/**
 * 导出数据
 * @param fileName
 */
kendo.ui.Grid.prototype["exportExcel"] = function (fileName, formatData) {
    var _this = this;
    var options = _this.options;
    var columns_ = options.columns;

    var columns = [];
    for (var i = 0; i < columns_.length; i++) {
        if (columns_[i].field == "ck" || columns_[i].checkbox == true) {
            continue;
        } else {
            var obj = {};
            if (columns_[i].exportable != undefined && (columns_[i].exportable == 'false' || columns_[i].exportable == false)) {
                continue;
            }
            if (columns_[i].field) {
                obj.field = columns_[i].field;
            } else {
                continue;
            }
            if (columns_[i].title) {
                obj.title = columns_[i].title;
            } else {
                obj.title = columns_[i].field;
            }
            columns.push(obj);
        }
    }

    //take: 10, skip: 0, page: 1, pageSize: 10
    var param = {"pageSize": "-1"};
    param = options.dataSource.transport.parameterMap(param, "read").data;

    if (formatData === undefined) {
        formatData = {};
    }

    kendo.ui.progress($(_this.element), true, "正在导出...");
    //正在导出
    $.post(PROJECT_URL + "/commonWebController/exportToExcel", {
            'columns': encodeURI(encodeURI(JSON.stringify(columns))),
            'formatData': encodeURI(encodeURI(JSON.stringify(formatData))),
            'param': encodeURI(encodeURI(param)),
            'schemaData': options.dataSource ? (options.dataSource.schema ? (options.dataSource.schema.data ? options.dataSource.schema.data : "") : "" ) : "",
            'excelType': "XLS"
        }, function (data) {
            kendo.ui.progress($(_this.element), false);
            if (data.status == "1") {
                aiui.notification('提示', '导出excel失败! ' + data.message, "error");
            } else {
                aiui.notification('提示', '导出成功!', "success");
                window.location.href = PROJECT_URL + "/commonWebController/downloadExcel?"
                + "filePath=" + encodeURI(encodeURI(data.filePath))
                + "&excelType=XLS"
                + "&fileName=" + encodeURI(encodeURI(fileName));
            }
        }
    );
};

/**
 * getChanged 获取改变数据
 * @returns {{}}
 */
kendo.ui.Grid.prototype.getChanged = function () {
    var that = this,
        idx,
        length,
        reObj = {},
        created = [],
        updated = [],
        destroyed = [],
        data = that._data;
    for (idx = 0, length = data.length; idx < length; idx++) {
        if (data[idx].isNew()) {
            created.push(data[idx]);
        } else if (data[idx].dirty) {
            updated.push(data[idx]);
        }
    }
    for (idx = 0, length = that.dataSource._destroyed.length; idx < length; idx++) {
        destroyed.push(that.dataSource._destroyed[idx]);
    }

    reObj.created = created;
    reObj.updated = updated;
    reObj.destroyed = destroyed;
    return reObj;
};


/**
 * 加载数据
 * @param parm
 */
kendo.ui.Grid.prototype["load"] = function () {
    var that = this;
    if (that.options.pageable) {
        that.dataSource.page(1);
    } else {
        that.dataSource.read();
    }
    //that.dataSource.read()
};

/**
 * 获取选中行 多行
 * @returns {Array}
 */
kendo.ui.Grid.prototype["getSelects"] = function () {
    var that = this;
    var data = [];
    var selects = that.select();
    for (var i = 0; i < selects.length; i++) {
        data.push(that.dataItem(selects[i]));
    }
    return data;
};

/**
 * 获取选中行 单行
 * @returns {{}}
 */
kendo.ui.Grid.prototype["getSelect"] = function () {
    var that = this;
    var data = {};
    var selects = that.select();
    if (selects.length > 0) {
        data = that.dataItem(selects[0]);
    }
    return data;
};


/**
 * 设置指定行的背景颜色
 * @param rowIndex 行号，从0开始
 * @param color
 */
kendo.ui.Grid.prototype["setRowBgColor"] = function (rowIndex, color) {
    //grid.select("tr:eq(1), tr:eq(2)");
};


/**
 * 双击事件
 * @param callback
 */
kendo.ui.Grid.prototype["dbclick"] = function (callback) {
    var that = this;
    that.element.on('dblclick', '.k-grid-content tr', function () {
        if (callback && typeof callback === 'function') {
            callback(that.getSelect(), this);
        }
    });
};

/**
 * 单击事件 xiaoke
 * @param callback
 */
kendo.ui.Grid.prototype["click"] = function (callback) {
    var that = this;
    that.element.on('click', '.k-grid-content tr', function () {
        if (callback && typeof callback === 'function') {
            callback(that.getSelect(), this);
        }
    });
};

/**
 * 删除选中行或按照rowNumber删除
 * @param rowNumber
 */
kendo.ui.Grid.prototype["removeRow"] = function (rowNumber) {
    var that = this;
    var row = undefined;
    if (rowNumber && typeof rowNumber === "number") {
        row = "tr:eq(" + (rowNumber + 1) + ")";
    } else {
        if (rowNumber === undefined) {
            if (that.select().length > 0) {
                row = that.select()[0];
            }
        } else {
            row = rowNumber;
        }
    }
    if (row !== undefined) {
        if (!this._confirmation(row)) {
            return;
        }
        this._removeRow(row);
    }
};


/**
 * 添加数据
 * @param data
 */
kendo.ui.Grid.prototype["addData"] = function (data) {
    var that = this;
    that.dataSource.add(data);
};

/**
 * 设置数据
 * @param data
 */
kendo.ui.Grid.prototype["data"] = function (data) {
    var that = this;
    that.dataSource.data(data);
};


/**
 * 获取表格中的全部数据
 * @returns {*}
 */
kendo.ui.Grid.prototype["getData"] = function () {
    var that = this;
    return that.dataSource.data();
};


/**
 * 取消选中
 * @param items
 */
kendo.ui.Grid.prototype["unSelect"] = function (items) {
    var that = this,
        selectable = that.selectable;
    items = $(items);
    if (items.length) {
        selectable._unselect(items)
    }
};

/**
 * 行是否已展开
 * @param tr
 */
kendo.ui.Grid.prototype["isRowExpanded"] = function (tr) {
    if ($(tr).find('> td .k-plus, > td .k-i-expand').length > 0) {
        return false;
    }
    return true;
};
