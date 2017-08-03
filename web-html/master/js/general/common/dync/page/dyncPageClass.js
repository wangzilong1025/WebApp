/**
 * Created by haomeng on 2016/08/31.
 */
var DyncPageClass = Class.extend({
    init: function (ruleObject, pageElementId, viewModel, validator) {
        this.nameSpace = "." + pageElementId;
        //组拼报文的时候使用
        this.pageElementId = pageElementId;
        this.pageId = pageElementId.split("_")[1];
        //页面中的参数 ？param1=value1&param2=value2
        this.pageParam = comm.tools.getRequest();
        this.validator = validator || {
                validate: function () {
                    return true;
                }
            };
        //vm，结合界面和数据模型
        this.viewModel = viewModel || kendo.observable({});
        //所有规则文件，不能重复
        this.ruleOnLoad = ruleObject.ruleOnLoad;
        this.ruleOnValueChange = ruleObject.ruleOnValueChange;
        this.ruleOnCommit = ruleObject.ruleOnCommit;
    },

    //注册下一步事件
    regNextPageEvent: function () {
        //先解绑
        $.unsubscribe(DYNC.TOPIC_SO_NEXTPAGE + this.nameSpace);
        var that = this;
        $.subscribe(DYNC.TOPIC_SO_NEXTPAGE + this.nameSpace, function (e, data) {
            if (data.currentPageId == that.pageId) {
                if (!that.validator.validate()) {
                    data.errorMsg.push("请根据提示正确填写");
                    return;
                }
                that.onNextPage(e, data);
                if (data.errorMsg && data.errorMsg.length > 0) {
                    return;
                } else {
                    data.returnValue = true;
                }
            }
        });
    },


    //设置页面元素是否可编辑
    setEnable: function (flag, attrIds) {
        var that = this;
        if (attrIds) {
            $("#" + that.pageElementId).find("[needCommit]").each(function (index, item) {
                var $wigetItem = $(item);
                if ($wigetItem.attr("id")) {
                    for (var i in attrIds) {
                        if (attrIds[i] == $wigetItem.attr("id")) {
                            that.viewModel.enabledData.set($wigetItem.attr("id"), flag);
                        }
                    }
                }
            });
        } else {
            $("#" + that.pageElementId).find("[needCommit]").each(function (index, item) {
                var $wigetItem = $(item);
                if ($wigetItem.attr("id")) {
                    that.viewModel.enabledData.set($wigetItem.attr("id"), flag);
                }
            });
            $("#" + that.pageElementId).find(".area-content.grid-type").each(function (index, item) {
                var $wigetItem = $(item);
                var id = $wigetItem.attr("id");
                $("#" + id).data("kendoGrid").setOptions({"editable": false});
            });
            $("#" + that.pageElementId).find(".k-button.k-grid-add").each(function (index, item) {
                var $wigetItem = $(item);
                $wigetItem.attr("href", "javascript:void(0)");
            });
            $("#" + that.pageElementId).find(".k-button.k-grid-delete").each(function (index, item) {
                var $wigetItem = $(item);
                $wigetItem.attr("href", "javascript:void(0)");
            });
        }
    },

    //通用的恢复数据的方法  data是submtdata类型
    recoverData: function (data) {
        var that = this;
        var nodeinfo = data.submitData[0].nodeinfo;
        for (var i = 0; i < nodeinfo.length; i++) {
            if (nodeinfo[i].name == that.pageElementId) {
                var num = 1;
                for (var x = 0; x < nodeinfo[i].node.length; x++) {
                    var nodeList = nodeinfo[i].node;
                    if (nodeList && nodeList.length > 0) {
                        var nodeType = nodeList[x].type;
                        if (nodeType == "table") {//table恢复数据，需要单独处理
                            var areaId = nodeList[x].name;//table表格ID值
                            var rowList = nodeList[x].row;
                            var columns = $("#" + areaId).data("kendoGrid").columns;
                            for (var y = 0; y < rowList.length; y++) {
                                var colList = rowList[y].col;
                                var data = {};//表格中添加的数据
                                for (var z = 0; z < colList.length; z++) {
                                    var source = [];
                                    var code = colList[z].code;
                                    var displayValue = colList[z].displayValue;
                                    var value = colList[z].value;
                                    if (value && displayValue) {
                                        if (columns[z].type == "multiselect" || columns[z].type == "combobox" || columns[z].type == "dropdownlist") {
                                            var textArray = displayValue.split(",");
                                            var valueArray = value.split(",");
                                            for (var w = 0; w < textArray.length; w++) {
                                                var item = {};
                                                item[columns[z].textField] = textArray[w];
                                                item[columns[z].valueField] = valueArray[w];
                                                source.push(item);
                                            }
                                            data[code] = source;
                                        } else {
                                            data[code] = displayValue;
                                        }
                                    } else {
                                        data[code] = null;
                                    }
                                }
                                if (data) {
                                    $("#" + areaId).data("kendoGrid").dataSource.add(data);
                                }
                            }
                        } else if (nodeType == "template") {
                            var cols = nodeinfo[i].node[x].row[0].col;
                            for (var j = 0; j < cols.length; j++) {
                                //需要先判断属性类型
                                var attrElement = $("#" + cols[j].fullId + "_1");
                                var role = attrElement.data("role");
                                //下拉类的组件恢复，有可能不需要查询，要组拼source
                                if (role == "multiselect") {
                                    //先判断有没有source
                                    var source = that.viewModel.dropdownData.get(cols[j].fullId + "_" + num);
                                    if (source == null) {
                                        var textField = attrElement.data("textField");
                                        var valueField = attrElement.data("valueField");
                                        source = [];
                                        if (cols[j].displayValue != null) {
                                            var texts = cols[j].displayValue.split(",");
                                            var values = cols[j].value.split(",");
                                            for (var z = 0; z < values.length; z++) {
                                                var item = {};
                                                item[textField] = texts[z];
                                                item[valueField] = values[z];
                                                source.push(item);
                                            }
                                        }
                                        that.viewModel.dropdownData.set(cols[j].fullId + "_" + num, source);
                                    }
                                    if (cols[j].value != "" && cols[j].value != null) {
                                        //先清除，后设置
                                        that.viewModel.valueData.set(cols[j].fullId + "_" + num, cols[j].value.split(","));
                                    }
                                } else if (role == "combobox" || role == "dropdownlist") {
                                    //先判断有没有source
                                    var source = that.viewModel.dropdownData.get(cols[j].fullId + "_" + num);
                                    if (source == null) {
                                        var textField = attrElement.data("textField");
                                        var valueField = attrElement.data("valueField");
                                        source = [];
                                        var item = {};
                                        item[textField] = cols[j].displayValue;
                                        item[valueField] = cols[j].value;
                                        source.push(item);
                                        that.viewModel.dropdownData.set(cols[j].fullId + "_" + num, source);
                                    } else {
                                        //debugger;
                                        //if(cols[j].displayValue == "请选择"){
                                        //    var textField = attrElement.data("textField");
                                        //    var valueField = attrElement.data("valueField");
                                        //    var item = {};
                                        //    item[textField] = cols[j].displayValue;
                                        //    item[valueField] = cols[j].value;
                                        //    source.push(item);
                                        //    that.viewModel.dropdownData.set(cols[j].fullId, source);
                                        //}
                                    }
                                    if (cols[j].value) {
                                        that.viewModel.valueData.set(cols[j].fullId + "_" + num, cols[j].value);
                                    }
                                } else {
                                    if (cols[j].value) {
                                        that.viewModel.valueData.set(cols[j].fullId + "_" + num, cols[j].value);
                                    }
                                }
                            }
                            num += 1;
                        } else {
                            var cols = nodeinfo[i].node[x].row[0].col;
                            for (var j = 0; j < cols.length; j++) {
                                //需要先判断属性类型
                                var attrElement = $("#" + cols[j].fullId);
                                var role = attrElement.data("role");
                                //下拉类的组件恢复，有可能不需要查询，要组拼source
                                if (role == "multiselect") {
                                    //先判断有没有source
                                    var source = that.viewModel.dropdownData.get(cols[j].fullId);
                                    if (source == null) {
                                        var textField = attrElement.data("textField");
                                        var valueField = attrElement.data("valueField");
                                        source = [];
                                        var texts = cols[j].displayValue.split(",");
                                        var values = cols[j].value.split(",");
                                        for (var z = 0; z < values.length; z++) {
                                            var item = {};
                                            item[textField] = texts[z];
                                            item[valueField] = values[z];
                                            source.push(item);
                                        }
                                        that.viewModel.dropdownData.set(cols[j].fullId, source);
                                    }
                                    if (cols[j].value != "" && cols[j].value != null) {
                                        //先清除，后设置
                                        that.viewModel.valueData.set(cols[j].fullId, cols[j].value.split(","));
                                    }
                                } else if (role == "combobox" || role == "dropdownlist") {
                                    //先判断有没有source
                                    var source = that.viewModel.dropdownData.get(cols[j].fullId);
                                    if (source == null) {
                                        var textField = attrElement.data("textField");
                                        var valueField = attrElement.data("valueField");
                                        source = [];
                                        var item = {};
                                        if (cols[j].displayValue && cols[j].displayValue != "" && cols[j].value && cols[j].value != "") {
                                            item[textField] = cols[j].displayValue;
                                            item[valueField] = cols[j].value;
                                            source.push(item);
                                            that.viewModel.dropdownData.set(cols[j].fullId, source);
                                        }
                                    } else {
                                        //debugger;
                                        //if(cols[j].displayValue == "请选择"){
                                        //    var textField = attrElement.data("textField");
                                        //    var valueField = attrElement.data("valueField");
                                        //    var item = {};
                                        //    item[textField] = cols[j].displayValue;
                                        //    item[valueField] = cols[j].value;
                                        //    source.push(item);
                                        //    that.viewModel.dropdownData.set(cols[j].fullId, source);
                                        //}
                                    }
                                    if (cols[j].value) {
                                        that.viewModel.valueData.set(cols[j].fullId, cols[j].value);
                                    }
                                } else {
                                    if (cols[j].value) {
                                        that.viewModel.valueData.set(cols[j].fullId, cols[j].value);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    },

    //注册页面加载事件
    regOnLoadEvent: function () {
        //先解绑
        $.unsubscribe(DYNC.TOPIC_PAGE_ONLOAD + this.nameSpace);
        var that = this;
        //页面初始化规则
        $.subscribe(DYNC.TOPIC_PAGE_ONLOAD + this.nameSpace, function (e, data) {
            for (var i = 0; i < that.ruleOnLoad.length; i++) {
                try {
                    eval("var tempMethod=" + that.ruleOnLoad[i].funcName);
                    if (typeof tempMethod == "function") {
                        tempMethod(that.viewModel, that.pageId);
                    }
                } catch (e) {
                    alert("初始化规则错误:" + that.ruleOnLoad[i].funcName + "  " + e);
                }
            }
        });
    },


    //下一页规则
    onNextPage: function (e, data) {
        var tempMethod = null;
        for (var i = 0; i < this.ruleOnCommit.length; i++) {
            try {
                eval("tempMethod=" + this.ruleOnCommit[i].funcName);
                if (typeof tempMethod != "function") {
                    return;
                }
                tempMethod(data, this.viewModel);
                if (data.errorMsg && data.errorMsg.length > 0) {
                    return;
                }
            } catch (e) {
                alert("下一页规则错误:" + that.ruleOnCommit[i].funcName + "  " + e);
            }
        }
    },

    //值改变事件
    onValueChange: function (e) {
        var tempMethod = null;
        for (var i = 0; i < this.ruleOnValueChange.length; i++) {
            try {
                eval("tempMethod=" + this.ruleOnValueChange[i].funcName);
                if (typeof tempMethod != "function") {
                    return;
                }
                var $element = null;
                //kendo控件
                if (e.sender) {
                    $element = $(e.sender.element);
                }
                //原生的html
                else if (e.target) {
                    $element = $(e.target);
                }
                tempMethod($element, this.viewModel, this.pageId);

            } catch (e) {
                alert("值改变规则错误:" + that.ruleOnValueChange[i].funcName + "  " + e);
            }
        }
    },
    //注册代码提交事件
    regSubmitEvent: function () {
        //先解绑
        $.unsubscribe(DYNC.TOPIC_SO_SAVEDATA + this.nameSpace);
        var that = this;
        $.subscribe(DYNC.TOPIC_SO_SAVEDATA + this.nameSpace, function (e, data) {
            if (data.pageIds.length > 0) {
                for (var i = 0; i < data.pageIds.length; i++) {
                    if (data.pageIds[i] == that.pageId) {
                        if (that.validator.validate()) {
                            var nodeinfoArray = that.getPageData();
                            data.nodeinfo = data.nodeinfo.concat(nodeinfoArray);
                        } else {
                            data.errorMsg.push({msg: "请根据提示正确填写", pageId: that.pageId});
                        }
                        return;
                    }
                }
            } else {
                if (that.validator.validate()) {
                    var nodeinfoArray = that.getPageData();
                    data.nodeinfo = data.nodeinfo.concat(nodeinfoArray);
                } else {
                    data.errorMsg.push({msg: "请根据提示正确填写", pageId: that.pageId});
                }
            }
        });
    },

    //注册代码提交事件,不执行校验规则
    regSubmitEventNoCheck: function () {
        //先解绑
        $.unsubscribe(DYNC.TOPIC_SO_SAVEDATA_NO_CHECK + this.nameSpace);
        var that = this;
        $.subscribe(DYNC.TOPIC_SO_SAVEDATA_NO_CHECK + this.nameSpace, function (e, data) {
            var nodeinfoArray = that.getPageData();
            data.nodeinfo = data.nodeinfo.concat(nodeinfoArray);
        });
    },

    regValidateEvent: function () {
        //先解绑
        $.unsubscribe(DYNC.TOPIC_SO_VALIDATE + this.nameSpace);
        var that = this;
        $.subscribe(DYNC.TOPIC_SO_VALIDATE + this.nameSpace, function (e, data) {
            if (data.pageIds.length > 0) {
                for (var i = 0; i < data.pageIds.length; i++) {
                    if (data.pageIds[i] == that.pageId) {
                        if (that.validator.validate()) {

                        } else {
                            data.errorMsg.push({msg: "请根据提示正确填写", pageId: that.pageId});
                        }
                        return;
                    }
                }
            } else {
                if (that.validator.validate()) {
                    return;
                } else {
                    data.errorMsg.push({msg: "请根据提示正确填写", pageId: that.pageId});
                }
            }
        });

    },
    //默认的组拼报文数据方法
    getPageData: function () {
        //统一规范的获取报文数据的方法，这个方法  nodeinfo代表一个页面，node代表一个area
        var that = this;
        //页面
        var nodeinfo = {
            "id": $("#" + that.pageElementId).attr("dataid"),
            "code": $("#" + that.pageElementId).attr("datacode"),
            "name": that.pageElementId,
            "node": []
        };

        //form
        var Node = function () {
            this.id = "";
            this.name = "";
            this.type = "form";
            this.row = [{
                "status": "N",
                "col": []
            }];
        };

        $("#" + this.pageElementId).find(".area-content.form-type").each(function (index, item) {
            var $form = $(item);
            var nodeObject = new Node();
            nodeObject.id = $form.attr("dataid");
            nodeObject.code = $form.attr("datacode");
            nodeObject.name = $form.attr("id");
            nodeObject.row[0].col = that.getAreaData($form);
            nodeinfo.node.push(nodeObject);
        });
        $("#" + this.pageElementId).find(".template-type .area-content.template-item").each(function (index, item) {
            var $form = $(item);
            var nodeObject = new Node();
            nodeObject.id = $form.attr("dataid");
            nodeObject.code = $form.attr("datacode");
            var fullId = $form.attr("id").split("_");
            fullId[fullId.length - 1] = index + 1;
            nodeObject.name = fullId.join("_");
            nodeObject.type = "template";
            nodeObject.row[0].col = that.getAreaData($form, index + 1);
            nodeinfo.node.push(nodeObject);
        });
        $("#" + this.pageElementId).find(".area-content.grid-type").each(function (index, item) {
            var $grid = $(item);
            var rowCount = $grid.data("kendoGrid").dataSource.data().length;
            var GridNode = function () {
                this.id = "";
                this.name = "";
                this.type = "table";
                this.row = [];
            };
            if (rowCount > 0) {//table中有数据
                var nodeObject = new GridNode();
                nodeObject.id = $grid.attr("dataid");
                nodeObject.code = $grid.attr("datacode");
                nodeObject.name = $grid.attr("id");
                nodeObject.type = "table";
                for (var i = 0; i < rowCount; i++) {
                    nodeObject.row[i] = {"status": "N", "col": []};
                    nodeObject.row[i].col = that.getGridAreaData($grid, i);
                }
                nodeinfo.node.push(nodeObject);
            }
        });
        return [nodeinfo];
    },

    getGridAreaData: function ($grid, rowNumber) {//获取table区域的值
        var that = this;
        var colArray = [];
        var Col = function () {
            this.fullId = "";
            this.id = "";
            this.code = "";
            this.value = "";
            this.displayValue = "";
            this.name = "";
        };

        var columnsArray = $grid.data("kendoGrid").columns;
        if (columnsArray && columnsArray.length > 0) {//table的各列
            for (var n = 0; n < columnsArray.length; n++) {
                var column = columnsArray[n];
                if (!column.fullId) {//如果fullId为空，那么说明该列没有数据，跳出循环
                    continue;
                }
                col = new Col();
                col.fullId = column.fullId;
                col.id = column.fullId.split("_")[2];
                col.code = column.field;
                col.name = column.title;
                var columnName = column.field;
                var wigetName = DYNC.WIDGET_TYPE[column.type];
                if (wigetName) {//不是普通的文本类型，是一个对象类型
                    var obj = eval('$grid.data("kendoGrid")._data[rowNumber].' + columnName);
                    if (obj && obj.length > 0 && obj instanceof Object) {
                        var value = "";
                        var name = "";
                        for (var i = 0; i < obj.length; i++) {
                            if (i < obj.length - 1) {
                                value += obj[i].codeValue + ",";
                                name += obj[i].codeName + ",";
                            } else {
                                value += obj[i].codeValue;
                                name += obj[i].codeName;
                            }
                        }
                        col.value = value;
                        col.displayValue = name;
                    } else if (obj && obj instanceof Object) {//mod by lijian
                        col.value = obj.codeValue;
                        col.displayValue = obj.codeName;
                    } else if (obj) {
                        comm.tools.getStaticData({
                            sync: false,
                            codeType: columnName,
                            codeValue: obj,
                            callback: function (data) {
                                col.value = data.codeValue;
                                col.displayValue = data.codeName;
                            }
                        });
                    }
                } else {
                    col.value = eval('$grid.data("kendoGrid")._data[rowNumber].' + columnName);
                    col.displayValue = col.value;
                }
                colArray.push(col);
            }
        }
        return colArray;
    },

    //模版类型数据在提交时要修改fullId后缀
    getAreaData: function ($form, fix) {
        var that = this;
        var colArray = []
        var Col = function () {
            this.fullId = "";
            this.id = "";
            this.code = "";
            this.value = "";
            this.displayValue = "";
            this.name = "";
        };

        $form.find("[needCommit]").each(function (index, item) {
            var $wigetItem = $(item);
            var wigetName = DYNC.WIDGET_TYPE[$wigetItem.data("role")];
            col = new Col();
            if (fix != null) {
                var fullId = $wigetItem.attr("id").split("_");
                fullId[fullId.length - 1] = fix;
                col.fullId = fullId.join("_");
            } else {
                col.fullId = $wigetItem.attr("id");
            }
            col.id = $wigetItem.attr("dataid");
            col.code = $wigetItem.attr("datacode");
            col.name = $wigetItem.closest(".forms").find("label").attr("title");
            if (wigetName) {
                if (wigetName == "kendoDatePicker") {
                    var date = $wigetItem.data(wigetName).value();
                    if (date) {
                        col.value = kendo.toString(date, 'yyyy-MM-dd');
                        col.displayValue = col.value;
                    }
                } else if (wigetName == "kendoComboBox" || wigetName == "kendoDropDownList") {
                    if ($wigetItem.data(wigetName).value() != null) {
                        col.value = $wigetItem.data(wigetName).value();
                        col.displayValue = $wigetItem.data(wigetName).text();
                    }
                } else if (wigetName == "kendoMultiSelect") {
                    if ($wigetItem.data(wigetName).value() != null) {
                        var dataItems = $wigetItem.data(wigetName).dataItems();
                        for (var i = 0; i < dataItems.length; i++) {
                            if (i != dataItems.length - 1) {
                                col.value = col.value + dataItems[i][$wigetItem.data("valueField")] + ","
                                col.displayValue = col.displayValue + dataItems[i][$wigetItem.data("textField")] + ","
                            } else {
                                col.value = col.value + dataItems[i][$wigetItem.data("valueField")]
                                col.displayValue = col.displayValue + dataItems[i][$wigetItem.data("textField")]
                            }
                        }
                    }
                } else {
                    if ($wigetItem.data(wigetName).value() != null) {
                        col.value = $wigetItem.data(wigetName).value();
                        col.displayValue = col.value;
                    }
                }
            } else {
                col.value = $wigetItem.val();
                col.displayValue = col.value;
            }
            colArray.push(col);
        });
        return colArray;
    },


    initRule: function () {
        //somain查询出规则相关代码
    },
    /**
     * 初始化，给外部调用
     */
    initialize: function () {
        this.initRule();
        this.viewModel.set("onChange", this.onValueChange.bind(this));
        this.regOnLoadEvent();
        this.regNextPageEvent();
        this.regSubmitEvent();
        this.regSubmitEventNoCheck();
        this.regValidateEvent();
    }

});

//这里定义全局的公共方法，特殊的就放在frame.js里面
var busiFrameModel = busiFrameModel || {};
(function ($) {
    var getPageModel = function (pageId) {
        eval("var pageModel=page_" + pageId + "_Model");
        return pageModel;
    };

    var getDefine = function (define) {
        eval("var temp=" + define);
        return temp;
    };

    var getAreaAddFunc = function (pageId, areaId) {
        eval("var addFunc=page_" + pageId + "_Model.addArea_" + pageId + "_" + areaId);
        return addFunc;
    };

    var recoverDataForPage = function (pageId, data) {
        getPageModel(pageId).recoverData(data);
    };

    var setEnableForPage = function (pageId, flag) {
        getPageModel(pageId).setEnable(flag);
    };

    var setAttrEnable = function (pageId, attrIds, flag) {
        getPageModel(pageId).setEnable(flag, attrIds);
    };

    var closePage = function () {
        if (top && top.indexModel) {
            var pageName = comm.tools.getRequest().pageName;
            top.indexModel.closeTabByTitle(pageName);
        }
    };

    var getNodeInfos = function (pageIds, errorCallBack) {
        var pages;
        if (pageIds && pageIds instanceof Array) {
            pages = pageIds;
        } else {
            pages = [];
        }
        var data = {nodeinfo: [], pageIds: pages, errorMsg: [], warningMsg: []};
        $.publish(DYNC.TOPIC_SO_SAVEDATA, data);

        //有错误信息直接返回
        if (data.errorMsg.length > 0) {
            comm.dialog.notification({
                title: "提示",
                type: comm.dialog.type.error,
                content: data.errorMsg[0].msg
            });
            if (errorCallBack && errorCallBack instanceof Function) {
                errorCallBack(data.errorMsg[0].pageId);
            }
            return false;
        }

        //显示警告信息，不阻碍流程
        if (data.warningMsg.length > 0) {
            for (var i = 0; i < data.warningMsg.length; i++) {
                comm.dialog.notification({
                    title: "提示",
                    type: comm.dialog.type.warn,
                    content: data.warningMsg[i]
                });
            }
        }
        return data.nodeinfo;
    };

    var getNodeInfosByIframeId = function (iframeId, errorCallBack) {
        var data = {nodeinfo: [], pageIds: [], errorMsg: [], warningMsg: []};
        $.publish(DYNC.TOPIC_SO_SAVEDATA+"." + iframeId, data);

        //有错误信息直接返回
        if (data.errorMsg.length > 0) {
            comm.dialog.notification({
                title: "提示",
                type: comm.dialog.type.error,
                content: data.errorMsg[0].msg
            });
            if (errorCallBack && errorCallBack instanceof Function) {
                errorCallBack(data.errorMsg[0].pageId);
            }
            return false;
        }

        //显示警告信息，不阻碍流程
        if (data.warningMsg.length > 0) {
            for (var i = 0; i < data.warningMsg.length; i++) {
                comm.dialog.notification({
                    title: "提示",
                    type: comm.dialog.type.warn,
                    content: data.warningMsg[i]
                });
            }
        }
        return data.nodeinfo;
    };

    var getChildNodeInfos = function (data, pageIds, errorCallBack) {
        $.publish(DYNC.TOPIC_SO_SAVEDATA, data);

        //有错误信息直接返回
        if (data.errorMsg.length > 0) {
            comm.dialog.notification({
                title: "提示",
                type: comm.dialog.type.error,
                content: data.errorMsg[0].msg
            });
            if (errorCallBack && errorCallBack instanceof Function) {
                errorCallBack(data.errorMsg[0].pageId);
            }
            return false;
        }

        //显示警告信息，不阻碍流程
        if (data.warningMsg.length > 0) {
            for (var i = 0; i < data.warningMsg.length; i++) {
                comm.dialog.notification({
                    title: "提示",
                    type: comm.dialog.type.warn,
                    content: data.warningMsg[i]
                });
            }
        }
        return data.nodeinfo;
    };

    var getNodeInfosNoCheck = function (pageIds) {
        var pages;
        if (pageIds && pageIds instanceof Array) {
            pages = pageIds;
        } else {
            pages = [];
        }
        var data = {nodeinfo: [], pageIds: pages, errorMsg: [], warningMsg: []};
        $.publish(DYNC.TOPIC_SO_SAVEDATA_NO_CHECK, data);
        return data.nodeinfo;
    };

    //调用java规则
    var invokeJavaRule = function (javaRuleList) {
        var checkValue = true;

        if (javaRuleList == null || javaRuleList.length == 0) {
            return checkValue;
        }
        comm.ajax.ajaxEntity({
            sync: false,
            busiCode: "IJavaRuleFSV_invokeJavaRule",
            param: {"paramMap": comm.tools.getRequest(), "javaRule": javaRuleList},
            callback: function (data, isSucc, msg) {
                if (isSucc == flase) {
                    checkValue = false;
                    comm.dialog.notification({
                        title: "错误",
                        type: comm.dialog.type.error,
                        content: msg
                    });
                    return checkValue;
                }
            }
        });
        return checkValue;
    };

    var getValueByAttrCode = function (attrCode) {
        return $("[datacode=" + attrCode + "][needcommit]").val();
    };

    var getTextByAttrCode = function (attrCode) {
        var text = "";
        var $wigetItem = $("[datacode=" + attrCode + "][needcommit]");
        var wigetName = DYNC.WIDGET_TYPE[$wigetItem.data("role")];
        if (wigetName) {
            if (wigetName == "kendoDatePicker") {
                var date = $wigetItem.data(wigetName).value();
                if (date) {
                    text = kendo.toString(date, 'yyyy-MM-dd');
                }
            } else if (wigetName == "kendoComboBox" || wigetName == "kendoDropDownList") {
                if ($wigetItem.data(wigetName).value() != null) {
                    text = $wigetItem.data(wigetName).text();
                }
            } else if (wigetName == "kendoMultiSelect") {
                if ($wigetItem.data(wigetName).value() != null) {
                    var dataItems = $wigetItem.data(wigetName).dataItems();
                    for (var i = 0; i < dataItems.length; i++) {
                        if (i != dataItems.length - 1) {
                            text += dataItems[i][$wigetItem.data("textField")] + ","
                        } else {
                            text += dataItems[i][$wigetItem.data("textField")]
                        }
                    }
                }
            } else {
                if ($wigetItem.data(wigetName).value() != null) {
                    text = $wigetItem.data(wigetName).value();
                }
            }
        } else {
            text = $wigetItem.val();
        }
        return text;
    };

    var setAttrVisiable = function (viewModel, attrCode, flag) {
        var id = $("[datacode=" + attrCode + "][needcommit]").attr("id");
        if ($("#" + id) && $("#" + id).length > 0) {
            viewModel.visibleData.set(id, flag);
        }
    };

    var setAttrVisiableByAttrId = function (viewModel, attrId, flag) {
        var id = $("[id*=" + attrId + "]").attr("id");
        viewModel.visibleData.set(id, flag);
    };

    var validatePage = function (pageIds, errorCallBack) {
        var pages;
        if (pageIds && pageIds instanceof Array) {
            pages = pageIds;
        } else {
            pages = [];
        }
        var data = {nodeinfo: [], pageIds: pages, errorMsg: [], warningMsg: []};
        $.publish(DYNC.TOPIC_SO_VALIDATE, data);

        //有错误信息直接返回
        if (data.errorMsg.length > 0) {
            comm.dialog.notification({
                title: "提示",
                type: comm.dialog.type.error,
                content: data.errorMsg[0].msg
            });
            if (errorCallBack && errorCallBack instanceof Function) {
                errorCallBack(data.errorMsg[0].pageId);
            }
            return false;
        }

        return true;

    };

    var setValueByAttrCode = function (viewModel, attrCode, value) {
        var id = $("[datacode=" + attrCode + "][needcommit]").attr("id");
        if (id) {
            viewModel.valueData.set(id, value);
        }
    };

    var getIdByCondition = function (condition) {
        var id = "";
        if ($("[id$=" + condition + "]")) {
            id = $("[id$=" + condition + "]").attr("id");
        }
        return id;
    };

    var setVisiableForPage = function (pageId, flag) {
        var pageObj = $("div[dataid=" + pageId + "]");
        if (flag) {//可见
            pageObj.show();
        } else {//不可见
            pageObj.hide();
        }
    };

    var createPageModel = function (page, pageRule) {
        //eval("var pageModel=page_" + page.pageId + "_Model");
        var pageModel = {};
        var param = comm.tools.getRequest();
        //定义viewModel,在页面中设置下拉列表，显示隐藏，是否可用，值
        var viewModel = kendo.observable({
            dropdownData: {},
            visibleData: {},
            enabledData: {},
            valueData: {}
        });

        var cascadeDropDown = function (event) {
            var $element = $(event.target);
            // 关联的属性id
            var relatAttrId = $element.attr("relatAttrId");
            var id = $element.attr("id");
            var idArray = id.split("_");
            var value = $element.val();
            idArray[3] = relatAttrId;
            var relatId = idArray.join("_");
            var $relatElement = $("#" + relatId);
            var resParam = $relatElement.attr("resParam");
            var resSrc = $relatElement.attr("resSrc");
            var dataPath = $relatElement.attr("dataPath");
            var listSource = null;
            var paramObject = comm.tools.getRequest();
            paramObject.relatValue = value;
            // 调下拉数据的服务
            if (resSrc != null) {
                var param = {
                    "busiParams": paramObject,
                    "busiCode": resSrc
                };
                comm.ajax.ajaxEntity({
                    busiCode: resSrc,
                    param: paramObject,
                    callback: function (data, isSucc, msg) {
                        if (dataPath != null) {
                            eval("var temp=data." + dataPath);
                            viewModel.dropdownData.set(relatId, temp);
                        } else {
                            viewModel.dropdownData.set(relatId, data);
                        }
                    }
                });
                // 从cfg_static_data中查询
            } else if (resParam != null) {

                var listSource = comm.tools.getStaticData({
                    sync: false,
                    codeType: resParam
                });
                var list = [];
                for (var i = 0; i < listSource.length; i++) {
                    if (listSource[i].parentCode == value) {
                        list.push(listSource[i]);
                    }
                }
                viewModel.dropdownData.set(relatId, list);
            }
        };

        // 用于判断drop初始化要不要执行
        var isInit = function (exp) {
            eval("var result=" + exp);
            return result;
        };
        // 给模版后缀用的序列，用法Model.seq.pop()
        var initSeq = function (start, end) {
            if (start == null) {
                start = 1;
            }
            if (end == null) {
                end = 200;
            }
            var length = end - start + 1;
            var seq = [];
            for (var i = 0; i < length; i++) {
                seq[i] = end - i;
            }
            pageModel.seq = seq;
        };


        // 页面的规则校验
        var validator = $("#page_" + page.pageId).kendoValidator({
            rules: {
                custcheck: function (input) {
                    // 无控件 input textarea
                    if (input.is(".k-textbox[data-custcheck-msg]")) {
                        if (input.is(":visible") && $.trim(input.val()) == "") {
                            return false;
                        } else {
                            return true;
                        }

                    } //有控件 有data-role属性的
                    else if (input.is("[data-custcheck-msg]")) {
                        if (input.closest(".k-widget").is(":visible") && $.trim(input.val()) == "") {
                            return false;
                        } else {
                            return true;
                        }
                    }
                    return true;
                },
                // 支持正则表达式
                regcheck: function (input) {
                    // 可见的才校验
                    if (input.is("[data-regcheck-msg]") && input.is(":visible") && input.is("[mypattern]")) {
                        var reg = new RegExp(input.attr("mypattern"));
                        if (input.val() != "") {
                            return reg.test(input.val());
                        }
                    }
                    return true;
                }
            }
        }).data("kendoValidator");


        var initUI = function () {
            var codeTypes = [];
            var attrIds = [];
            var resSrcs = [];
            var resAttrIds = [];
            var dataPaths = [];
            for (var xx = 0; xx <  page.cfgDyncPageAreaEntities.length; xx++) {
                var x =page.cfgDyncPageAreaEntities[xx].cfgDyncAreaEntity;
                //暂时有form类型，table类型，template模型
                if (x.areaType == "form") {
                    for (var yy = 0; yy< x.cfgDyncAreaAttrEntities.length ;yy++) {
                        var y = $.extend(x.cfgDyncAreaAttrEntities[yy].cfgDyncAttrEntity, x.cfgDyncAreaAttrEntities[yy]);
                        ////设置是否显示
                        if (y.isVisible == 1) {
                            viewModel.visibleData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId, true);
                        } else {
                            viewModel.visibleData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId, false);
                        }
                        ////能否编辑
                        if (y.isEditable == 1) {
                            viewModel.enabledData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId, true);
                        } else {
                            viewModel.enabledData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId, false);
                        }
                        ////默认值
                        if (y.defaultValue && y.defaultValue != "") {
                            if (y.editType == "multiselect") {
                                viewModel.valueData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId, y.defaultValue.split(","));
                            } else {
                                viewModel.valueData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId, y.defaultValue);
                            }
                        }
                        ////下拉框的数据源  暂时只考虑标准的下拉框的值 通过staticData来
                        if (y.editType && (y.editType == "dropdownlist" || y.editType == "multiselect" || y.editType == "combobox" )) {

                            if (isInit(y.isInit && (y.isInit == false || y.isInit == 'false') ? false : true)) {
                                //调服务和调用查询cfg_static_data表的服务分开处理
                                if (y.resSrc) {
                                    resSrcs.push(y.resSrc);
                                    resAttrIds.push("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId);
                                    dataPaths.push(y.dataPath ? y.dataPath : "");
                                } else if (y.resParam) {
                                    codeTypes.push(y.resParam);
                                    attrIds.push("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId);
                                }
                            }
                        }

                        ////下拉框级联
                        if (y.relatAttrId) {
                            $("#AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId).bind("change", cascadeDropDown);
                        }
                    }
                } else if (x.areaType = "table") {
                    var schema = {model: {}};
                    var fields = {};
                    for (var y in x.cfgDyncAttrEntityList) {
                        fields[y.attrCode] = {
                            editable: y.isEditable && y.isEditable == 1 ? true : false,
                            nullable: y.isNullable && y.isNullable === 1 ? true : false
                        }
                    }
                    schema.model.fields = fields;
                    if (x.initSrvId && x.initSrvId != "") {
                        schema.data = "data.list";
                        schema.total = "data.count";
                    }
                    var tempDataSource = new kendo.data.DataSource({
                        transport: {
                            read: {
                                url: SERVER_URL,
                                type: "POST"
                            },
                            parameterMap: function (/**Object*/data, /**String*/operation) {// server
                                if (operation == "read") {
                                    var jsonRequstParam = {
                                        "param": comm.tools.getRequest(),
                                        "busiCode": x.initSrvId ? x.initSrvId : ""
                                    };
                                    return comm.ajax.paramWrap(jsonRequstParam);
                                } else if (data.models) {
                                    return JSON.stringify(data.models);
                                }
                            }
                        },
                        batch: true,
                        serverPaging: true,
                        serverSorting: true,
                        pageSize: 4,
                        schema: schema
                    });
                    var columns = [];
                    for (var y in x.cfgDyncAttrEntityList) {
                        var tempColumn = {
                            field: y.attrCode,
                            title: y.attrName,
                            width: (y.colSpan ? y.colSpan : 20) + "%",
                            fullId: "AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId,
                            //mod by lijian 20160814
                            editable: y.isEditable && y.isEditable == 1 ? true : false,
                            nullable: y.isNullable && y.isNullable == 1 ? true : false,
                            type: y.editType
                        };
                        if (y.editType && (y.editType == "dropdownlist" || y.editType == "multiselect" || y.editType == "combobox")) {
                            tempColumn.textField = y.dataText && y.dataText != "" ? y.dataText : 'codeName';
                            tempColumn.valueField = y.dataValue && y.dataValue != "" ? y.dataValue : 'dataValue';
                            if (y.editType == "combobox") {
                                tempColumn.editor = function (container, options) {
                                    var input = "<input needCommit name='" + options.field + "'";
                                    input += " id = 'AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId + "'";
                                    input += " datacode='" + (y.attrCode ? y.attrCode : "") + "'";
                                    input += " dataid='" + y.attrId + "'";
                                    input += " required";
                                    input += " validationMessage='不能为空'";
                                    input += " data-custcheck-msg='不能为空.'";
                                    input += " ></input>";
                                    $(input).appendTo(container).kendoComboBox({
                                        dataSource: comm.tools.getStaticData({codeType: y.resParam}),
                                        dataTextField: y.dataText && y.dataText != "" ? y.dataText : "",
                                        dataValueField: y.dataValue && y.dataValue != "" ? y.dataValue : "codeValue"
                                    });
                                }
                            } else if (y.editType == "multiselect" || y.editType == "dropdownlist") {
                                tempColumn.editor = function (container, options) {
                                    var input = "<select needCommit name='" + options.field + "'";
                                    input += " id = 'AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId + "'";
                                    input += " datacode='" + (y.attrCode ? y.attrCode : "") + "'";
                                    input += " dataid='" + y.attrId + "'";
                                    input += " required";
                                    input += " validationMessage='不能为空'";
                                    input += " data-custcheck-msg='不能为空.'";
                                    input += " ></select>";

                                    var selectObj = {
                                        dataSource: comm.tools.getStaticData({codeType: y.resParam}),
                                        dataTextField: y.dataText && y.dataText != "" ? y.dataText : "",
                                        dataValueField: y.dataValue && y.dataValue != "" ? y.dataValue : "codeValue"
                                    }
                                    if (y.editType == "multiselect") {
                                        $(input).appendTo(container).kendoMultiSelect(selectObj);
                                    } else if (y.editType == "dropdownlist") {
                                        $(input).appendTo(container).kendoDropDownList(selectObj);

                                    }
                                }
                            }

                            if (y.attrCode && y.attrCode != "") {
                                tempColumn.template = function (a, b) {
                                    var value = "";
                                    if (a && a[y.attrCode] && a[y.attrCode] instanceof Object) {
                                        if (a[y.attrCode].length) {
                                            for (var i = 0; i < a[y.attrCode].length; i++) {
                                                value = value + a[y.attrCode][i].codeName + " ,  ";
                                            }
                                            if (value.length > 0) {
                                                value = value.substr(0, value.length - 4);
                                            }
                                        } else {
                                            value = a[y.attrCode].codeName;
                                        }
                                    } else if (a && a[y.attrCode]) {
                                        comm.ajax.ajaxEntity({
                                            sync: false,
                                            param: {
                                                "codeType": y.attrCode,
                                                "codeValue": a[y.attrCode]
                                            },
                                            busiCode: "ICFGSTATICDATAFSV_GETCFGSTATICDATABYCODEVALUE",
                                            callback: function (data) {
                                                value = data.codeName;
                                            }
                                        })
                                    }
                                    return value;
                                }
                            }
                        } else {
                            tempColumn.editor = function (container, options) {
                                var input = "<input name='" + options.field + "'";
                                input += " class='k-textbox'";
                                input += " required";
                                input += " validationMessage='不能为空'";
                                input += " />";
                                $(input).appendTo(container);
                            }
                        }
                    }
                    columns.push(tempColumn);


                    columns.push({command: "destroy", title: "&nbsp;", width: "10%"});

                    $("#area_" + page.pageId + "_" + x.areaId).kendoGrid({
                        dataSource: x.initSrvId && x.initSrvId != "" ? tempDataSource : {},
                        scrollable: true,
                        sortable: true,
                        toolbar: ["create"],
                        //filterable: true,
                        //pageable:{
                        //    input:true,
                        //    numeric:false
                        //},
                        columns: columns,
                        editable: x.editFlag && x.editFlag == true ? x.editFlag : false
                    });
                } else if (x.areaType = "template") {
                    ////模版中的下拉框都是统一处理
                    for (var y in x.cfgDyncAttrEntityList) {
                        if (y.editType && (y.editType == "dropdownlist" || y.editType == "multiselect" || y.editType == "combobox" )) {
                            if (isInit(y.isInit && y.isInit == false ? false : true)) {
                                if (y.resSrc) {
                                    resSrcs.push(y.resSrc);
                                    resAttrIds.push("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId);
                                    dataPaths.push(y.dataPath ? y.dataPath : "");
                                } else if (y.resParam) {
                                    codeTypes.push(y.resParam);
                                    attrIds.push("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId);
                                }
                            }
                        }
                    }

                    //处理模版类型的域
                    var tempArea = function (index) {
                        var templateId = "template_area_" + page.pageId + "_" + x.areaId;
                        var appendToId = "area_" + page.pageId + "_" + x.areaId;
                        var uid;
                        if (index != null) {
                            uid = index;
                        } else {
                            uid = getPageModel(page.pageId).seq.pop();
                        }
                        var template = $("#" + templateId).html();
                        //运用kendo的template功能会替换正则表达式中的\,所以用替换的方式
                        var area = template.replace(new RegExp("#:uid#", "gm"), uid);
                        $("#" + appendToId).append(area);
                        kendo.bind($("#area_" + page.pageId + "_" + x.areaId + "_" + uid), viewModel);
                        for (var y in x.cfgDyncAttrEntityList) {
                            ////设置是否显示
                            if (y.isVisible == 1) {
                                viewModel.visibleData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId + "_" + uid, true);
                            } else {
                                viewModel.visibleData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId + "_" + uid, false);
                            }
                            ////能否编辑
                            if (y.isEditable == 1) {
                                viewModel.enabledData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId + "_" + uid, true);
                            } else {
                                viewModel.enabledData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId + "_" + uid, false);
                            }
                            ////默认值
                            if (y.defaultValue && y.defaultValue != "") {
                                if (y.editType == "multiselect") {
                                    viewModel.valueData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId + "_" + uid, y.defaultValue.split(","));
                                } else {
                                    viewModel.valueData.set("AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId + "_" + uid, y.defaultValue);
                                }
                            }
                            ////下拉框的数据源  暂时只考虑标准的下拉框的值 通过staticData来
                            ////下拉框级联
                            if (y.relatAttrId) {
                                $("#AT_" + page.pageId + "_" + x.areaId + "_" + y.attrId + "_" + uid).bind("change", cascadeDropDown);
                            }
                        }
                    };
                    tempArea();
                    //暴露到pagemodel中
                    getPageModel(page.pageId)["addArea_" + page.pageId + "_" + x.areaId] = tempArea;
                }
            }
            //加载自定义
            for (var i = 0; i < attrIds.length; i++) {
                viewModel.dropdownData.set(attrIds[i], comm.tools.getStaticData({
                    sync: false,
                    codeType: codeTypes[i],
                }));
            }
            //加载自定义服务类型的下拉框数据据源
            for (var z = 0; z < resAttrIds.length; z++) {
                (function (index) {
                    comm.ajax.ajaxEntity({
                        busiCode: resSrcs[index],
                        callback: function (data) {
                            if (dataPaths[index] != "") {
                                eval("var v=data.retInfo." + dataPaths[index]);
                                viewModel.dropdownData.set(resAttrIds[index], v);
                            } else {
                                viewModel.dropdownData.set(resAttrIds[index], data.retInfo);
                            }
                        }
                    });
                })(z);
            }
        };


        var ruleObject = {};
        //把规则分好类，传递给SoPageClassForDync，用于框架调用
        var initRule = function () {
            var ruleOnLoad = [];
            var ruleOnCommit = [];
            var ruleOnValueChange = [];
            for (var x in pageRule) {
                //初始化规则
                if (x.ruleType == 1 && x.ruleTriggerType == "onLoad") {
                    ruleOnLoad.push({
                        "funcName": x.funcName,
                        "alertCode": x.alertCode ? x.alertCode : "",
                        "alertMsg": x.alertMsg ? x.alertMsg : ""
                    });
                } else if (x.ruleType == 1 && x.ruleTriggerType == "onCommit") {
                    ruleOnCommit.push({
                        "funcName": x.funcName,
                        "alertCode": x.alertCode ? x.alertCode : "",
                        "alertMsg": x.alertMsg ? x.alertMsg : ""
                    });
                } else if (x.ruleType == 1 && x.ruleTriggerType == "onValueChange") {
                    ruleOnValueChange.push({
                        "funcName": x.funcName,
                        "alertCode": x.alertCode ? x.alertCode : "",
                        "alertMsg": x.alertMsg ? x.alertMsg : ""
                    });
                }
            }
            ruleObject.ruleOnLoad = ruleOnLoad;
            ruleObject.ruleOnCommit = ruleOnCommit;
            ruleObject.ruleOnValueChange = ruleOnValueChange;
        };

        var init = function () {
            initSeq();
            initUI();
            initRule();
            var pageObject = new DyncPageClass(ruleObject, "page_" + page.pageId, viewModel, validator);
            pageObject.initialize();
            //bind方法指定所有者，因为方法里面有引用this，this指定bind传递的参数
            pageModel.setEnable = pageObject.setEnable.bind(pageObject);
            pageModel.recoverData = pageObject.recoverData.bind(pageObject);
            pageModel.initSeq = initSeq;
            //界面初始化，采用MVVM的方式，一个语句搞定所有控件，并和viewModel绑定，修改数据只需要修改viewModel中的值
            kendo.bind($("#page_" + page.pageId), viewModel);
        };

        init();

        window["page_" + page.pageId + "_Model"] = pageModel;
    };


    $.extend(busiFrameModel, {
        createPageModel: createPageModel,
        getNodeInfos: getNodeInfos,
        getNodeInfosByIframeId: getNodeInfosByIframeId,
        getChildNodeInfos: getChildNodeInfos,
        getNodeInfosNoCheck: getNodeInfosNoCheck,
        getAreaAddFunc: getAreaAddFunc,
        recoverDataForPage: recoverDataForPage,
        setEnableForPage: setEnableForPage,
        validatePage: validatePage,
        closePage: closePage,
        invokeJavaRule: invokeJavaRule,
        getTextByAttrCode: getTextByAttrCode,
        getValueByAttrCode: getValueByAttrCode,
        setValueByAttrCode: setValueByAttrCode,
        setAttrVisiable: setAttrVisiable,
        setAttrVisiableByAttrId: setAttrVisiableByAttrId,
        getIdByCondition: getIdByCondition,
        setVisiableForPage: setVisiableForPage,
        setAttrEnable: setAttrEnable
    });
})(jQuery);
