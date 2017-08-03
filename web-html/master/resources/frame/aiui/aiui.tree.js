/**
 * @author zhangrp
 * Created on 2015/9/25 15:17
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */

/**
 * tree 树
 *  <div class="aiui-tree" id="xxxx" data-options="srvId:'IEXAMPLEFSV_GETSUBORGANIZATIONTREE',dataTextField:'functionName',dataHasChildrenField:'hasChildrenField',dataIdField:'funcId',checked:'checked'" >
 * 属性
 *  animation: Boolean (defualt false)
 *  autoBind: Boolean (default: true)
 *  loadOnDemand: Boolean (default: false)
 *  template: String| Function
 *
 *
 *  checkboxes: Boolean (defualt false)
 *  checkChildren: Boolean(default: false)
 *  checkboxesName: String, Sets the name attribute of the checkbox inputs. That name will be posted to the server.
 *  checkboxesTemplate: String |Function
 *
 *
 *  dataImageUrlField: String (default: null)
 *  dataSpriteCssClassField: String (default: null)
 *  dataTextField: String |Array (default: null), 有多个时用array
 *  dataUrlField: String (default: null)
 *  dataIdField: String
 *  dataHasChildrenField: string
 *
 *  srvId:
 *
 * 方法
 *  getSelect: 获取选择的树节点
 *
 *
 * 事件
 *  select
 *  change
 *
 */

$.fn.tree = function () {
    var that = this;
    var target = that[0];

    if ($.data(target, 'tree')) {
        return $(target).data("kendoTreeView");
    }
    return $.fn.tree.create(target);
};

$.fn.tree.create = function ( target) {
    var options = $.fn.tree.parseOptions(target, {
        animation: false,
        autoBind: true,
        loadOnDemand: true,
        checkboxes: false,
        checkChildren: false
    }, {
        animation: 'boolean',
        autoBind: 'boolean',
        loadOnDemand: 'boolean',
        template: 'string',
        checkboxes: 'boolean',
        checkChildren: 'boolean',
        checkboxesName: 'string',
        checkboxesTemplate: 'string',

        dataImageUrlField: 'string',
        dataSpriteCssClassField: 'string',
        dataTextField: 'string',
        dataUrlField: 'string',
        dataIdField: 'string',
        dataHasChildrenField: 'string',

        srvId: "string",
        schemaData: 'string'
    });

    var dataSource = {};
    if (options.srvId) {
        dataSource = $.extend(dataSource, {
            transport: {
                read: {
                    url: SERVER_URL,
                    type: "POST"
                },
                parameterMap: function (data) {
                    if (options.beforeLoad) {
                        data = options.beforeLoad(data);
                    }
                    var jsonRequstParam = {
                        "busiParams":data,
                        "busiCode": options.srvId
                    };
                    return comm.ajax.paramWrap(jsonRequstParam);
                }
            },
            batch: true
        });
        dataSource = $.extend(dataSource, {
            batch: true,
            schema: {
                data: (options.schemaData ? options.schemaData : "data"),
                model: {
                    id: options.dataIdField,
                    hasChildren: options.dataHasChildrenField
                }
            }
        });
    }

    var id = $(target).attr("id") ? $(target).attr("id") : new Date().getTime();
    $.data(target, 'tree', id);

    if(options.checkChildren == true || options.checkboxesName || options.checkboxesTemplate){
        options.checkboxes = true;
    }
    var tree = {
        animation: options.animation,
        autoBind: options.autoBind,
        loadOnDemand: options.loadOnDemand,
        dataImageUrlField: options.dataImageUrlField,
        dataSpriteCssClassField: options.dataSpriteCssClassField,
        dataTextField: options.dataTextField,
        dataUrlField: options.dataUrlField,
        dataSource: dataSource,
        checkboxes: (options.checkboxes == true ? {
            checkChildren: options.checkChildren,
            name: options.checkboxesName,
            template: options.checkboxesTemplate
        } : false)
    };
    $(target).kendoTreeView(tree);
    return $(target).data("kendoTreeView");
};


$.fn.tree.parseOptions = function (target, options, properties) {
    options = options || {};
    properties = properties || {};
    return $.extend(options, $.parser.parseOptions(target, [properties]));
};

/**
 * 获取选择的树节点
 * @returns {{}}
 */
kendo.ui.TreeView.prototype["getSelect"] = function () {
    var that = this;
    var uid = that.select().attr("data-uid");
    var _target = {};
    if (uid) {
        var _data = that.dataSource._data;
        var _flag = true;
        while (_flag && _data.length > 0) {
            var _p_data = [];
            for (var i = 0; i < _data.length; i++) {
                if (_data[i].uid === uid) {
                    _target = _data[i];
                    _flag = false;
                } else if (_data[i].children && _data[i].children._data && _data[i].children._data.length > 0) {
                    for (var k = 0; k < _data[i].children._data.length; k++) {
                        _p_data.push(_data[i].children._data[k]);
                    }
                }
            }
            _data = _p_data;
        }
    }
    return _target;
};

function checkedNodeIds(nodes, checkedNodes) {
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].checked) {
            //add by zhangch6
            var obj = nodes[i];
            var result = {};
            for(var p in obj){
                if ( typeof (obj[p]) == "function" || typeof (obj[p]) == "object"){
                    continue;
                }
                result[p] = obj[p];
            }
            //end add by zhangch6
           //var result = { parentId: nodes[i].parentId, funcId: nodes[i].funcId, functionName: nodes[i].functionName};
            checkedNodes.push(result);
        }
        if (nodes[i].hasChildren) {
            checkedNodeIds(nodes[i].children.view(), checkedNodes);
        }
    }
}

/**
 * 多选的情况下使用
 */
kendo.ui.TreeView.prototype["getSelects"] = function () {
    var that = this;
    var checkedNodes = [];
    checkedNodeIds(that.dataSource.view(), checkedNodes);
    return checkedNodes;
};
