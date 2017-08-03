/**
 * @author zhangruiping
 * Created on 17/2/27 下午4:01
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
var dyncConfigureModule = {};


var dfgConfigureWindowModule = {};
//window初始化
(function ($) {

    var attrTypes = "label,dropdownlist,combobox,multiselect,timepicker,datepicker,aipopup,aibcerinput,numerictextbox,textarea,address,";
    var cfgFrame = {
        cfgDyncBusiFrameRel: {
            text: "busiId,operateId,remark",
            dropdownlist: "pageTemplateId,moduleId",

            notnull: "busiId,operateId,remark,pageTemplateId,moduleId",
            editTextDisable: "busiId,operateId"
        }, cfgDyncFrame: {
            text: "remark,version",
            dropdownlist: "buttonsetId,rulesetId",


            notnull: "remark,version"
        }
    };

    var cfgPage = {
        cfgDyncFramePage: {
            text: "pageTitle,pageCode,remark",
            dropdownlist: "isDisplay,rulesetId",

            notnull: "pageTitle,pageCode,isDisplay"
        }, cfgDyncPage: {
            text: "pageName,pageUrl,remark",
            dropdownlist: "pageType,rulesetId,buttonsetId",

            notnull: "pageName,pageType",
            editTextDisable: "pageType"
        }
    };

    var cfgArea = {
        cfgDyncPageArea: {
            text: "remark",
            dropdownlist: "isShowTitle,isEditable,isDisplay",


            notnull: "isShowTitle,isEditable,isDisplay"
        }, cfgDyncArea: {
            text: "areaName,areaCode,initSrvId,remark",
            dropdownlist: "areaType,rulesetId,buttonsetId",


            notnull: "areaName,areaCode,areaType"
        }
    };

    var cfgAttr = {
        cfgDyncAreaAttr: {
            text: "remark,rowSpan,colSpan,defaultValue",
            dropdownlist: "isNullable,isVisible,isEditable,rulesetId",

            notnull: "rowSpan,colSpan,isNullable,isVisible,isEditable"
        }, cfgDyncAttr: {
            text: "attrName,attrCode,editFormat,defaultValue,url,urlParam,resSrc,resMethod,resParam,dataText,dataValue,minValue,maxValue,relatAttrId,placeHolder,initParam,fontClass,remark,iconClass,dataPath",
            dropdownlist: "editType,regexpId,rulesetId,isInit",

            notnull: "attrName,attrCode,editType"
        }
    };


    var defaultValue = {
        cfgDyncBusiFrameRel: {
            moduleId: "1"
        },
        cfgDyncFrame: {},
        cfgDyncFramePage: {
            isDisplay: "1"

        },
        cfgDyncPage: {},
        cfgDyncPageArea: {
            isShowTitle: "1", isEditable: "1", isDisplay: "1"
        },
        cfgDyncArea: {
            areaType: "form"
        },
        cfgDyncAreaAttr: {
            isNullable: "0",
            isVisible: "1",
            isEditable: "1",
            rowSpan: "1",
            colSpan: "2"
        },
        cfgDyncAttr: {}
    };

    var setFormValue = function (select, cfg) {
        for (var key in cfg) {
            var texts = cfg[key].text.split(",");
            if (select[key] == undefined) {
                select[key] = {};
            }
            for (var i = 0; i < texts.length; i++) {
                if (select[key][texts[i]] === undefined) {
                    $("#" + key).find("[name=" + texts[i] + "]").val(defaultValue[key][texts[i]] || "");
                } else {
                    $("#" + key).find("[name=" + texts[i] + "]").val(select[key][texts[i]]);
                }
            }
            var comboboxs = cfg[key].dropdownlist.split(",");
            for (var i = 0; i < comboboxs.length; i++) {
                if (select[key][comboboxs[i]] === undefined) {
                    $("#" + key).find("[name=" + comboboxs[i] + "]").data("kendoDropDownList").value(defaultValue[key][comboboxs[i]] || "");
                } else {
                    $("#" + key).find("[name=" + comboboxs[i] + "]").data("kendoDropDownList").value(select[key][comboboxs[i]]);
                }
            }
        }
    };
    var setEditDisable = function (cfg) {
        var request = comm.tools.getRequest();
        if (request.busiFrameId && request.busiFrameId != "") {
            for (var key in cfg) {
                if (cfg[key].editTextDisable) {
                    var texts = cfg[key].editTextDisable.split(",");
                    if (texts.length > 0) {
                        for (var i = 0; i < texts.length; i++) {
                            $("#" + key).find("[name=" + texts[i] + "]").attr("disabled", "disabled");
                        }
                    }
                }
                if (cfg[key].editDropdownlistDisable) {
                    var comboboxs = cfg[key].editDropdownlistDisable.split(",");
                    if (comboboxs.length > 0) {
                        for (var i = 0; i < comboboxs.length; i++) {
                            $("#" + key).find("[name=" + comboboxs[i] + "]").data("kendoDropDownList").enable(false);
                        }
                    }
                }
            }
        }
    };

    var getFormValue = function (select, cfg) {
        for (var key in cfg) {
            var texts = cfg[key].text.split(",");
            for (var i = 0; i < texts.length; i++) {
                select[key][texts[i]] = $("#" + key).find("[name=" + texts[i] + "]").val();
            }
            var comboboxs = cfg[key].dropdownlist.split(",");
            for (var i = 0; i < comboboxs.length; i++) {
                select[key][comboboxs[i]] = $("#" + key).find("[name=" + comboboxs[i] + "]").data("kendoDropDownList").value();
            }
        }
    };


    var loadPropertyData = function (ttype) {
        if (ttype == "frame") {
            var select = $("#componentTree").data("kendoTreeView").getSelect();
            if (select.cfgDyncBusiFrameRel == undefined) {
                select.cfgDyncBusiFrameRel = {};
            }
            select.cfgDyncBusiFrameRel.remark = select.text;

            if (select.type == "wizardPage") {
                select.cfgDyncBusiFrameRel.pageTemplateId = "50000";
            } else if (select.type == "singlePage") {
                select.cfgDyncBusiFrameRel.pageTemplateId = "50001";
            } else if (select.type == "tabPage") {
                select.cfgDyncBusiFrameRel.pageTemplateId = "50002";
            }
            setFormValue(select, cfgFrame);
            setEditDisable(cfgFrame);
        } else if (ttype == "page") {
            var select = $("#componentTree").data("kendoTreeView").getSelect();
            if (select.cfgDyncFramePage == undefined) {
                select.cfgDyncFramePage = {};
            }
            if (select.cfgDyncPage == undefined) {
                select.cfgDyncPage = {};
            }
            select.cfgDyncFramePage.pageTitle = select.text;
            select.cfgDyncPage.pageType = select.type.substring(4);
            setFormValue(select, cfgPage);
        } else if (ttype == "area") {
            var select = $("#componentTree").data("kendoTreeView").getSelect();
            if (select.cfgDyncArea == undefined) {
                select.cfgDyncArea = {};
            }
            select.cfgDyncArea.areaName = select.text;
            setFormValue(select, cfgArea);
        } else if (ttype == "attr") {
            var select = $("#componentTree").data("kendoTreeView").getSelect();
            if (select.cfgDyncAttr == undefined) {
                select.cfgDyncAttr = {};
            }
            select.cfgDyncAttr.attrName = select.text;
            select.cfgDyncAttr.editType = select.type;
            setFormValue(select, cfgAttr);
        }
    };

    var framePropertyWindowValidator = $("#framePropertyWindow").kendoValidator({
        rules: {}
    }).data("kendoValidator");


    $("#framePropertyWindow").find("[type=button]").click(function () {
        var title = $(this).html();
        if (title == "确定") {
            if (framePropertyWindowValidator.validate()) {
                var select = $("#componentTree").data("kendoTreeView").getSelect();
                select.text = select.cfgDyncBusiFrameRel.remark;
                getFormValue(select, cfgFrame);
                $("#notification").data("kendoNotification").show("保存成功！", "success");
            }
        } else if (title == "取消") {
            loadPropertyData("frame");
        }
    });


    var pagePropertyWindowValidator = $("#pagePropertyWindow").kendoValidator({
        rules: {}
    }).data("kendoValidator");
    $("#pagePropertyWindow").find("[type=button]").click(function () {
        var title = $(this).html();
        if (title == "确定") {
            if (pagePropertyWindowValidator.validate()) {
                var select = $("#componentTree").data("kendoTreeView").getSelect();
                select.text = select.cfgDyncFramePage.pageTitle;
                getFormValue(select, cfgPage);
                $("#notification").data("kendoNotification").show("保存成功！", "success");
            }
        } else if (title == "取消") {
            loadPropertyData("page");
        }
    });


    var areaPropertyWindowValidator = $("#areaPropertyWindow").kendoValidator({
        rules: {}
    }).data("kendoValidator");
    $("#areaPropertyWindow").find("[type=button]").click(function () {
        var title = $(this).html();
        if (title == "确定") {
            if (areaPropertyWindowValidator.validate()) {
                var select = $("#componentTree").data("kendoTreeView").getSelect();
                select.text = select.cfgDyncArea.areaName;
                getFormValue(select, cfgArea);
                $("#notification").data("kendoNotification").show("保存成功！", "success");
            }
        } else if (title == "取消") {
            loadPropertyData("area");
        }

    });

    var attrPropertyWindowValidator = $("#attrPropertyWindow").kendoValidator({
        rules: {}
    }).data("kendoValidator");
    $("#attrPropertyWindow").find("[type=button]").click(function () {
        var title = $(this).html();
        if (title == "确定") {
            if (attrPropertyWindowValidator.validate()) {
                var select = $("#componentTree").data("kendoTreeView").getSelect();
                select.text = select.cfgDyncAttr.attrName;
                getFormValue(select, cfgAttr);
                $("#notification").data("kendoNotification").show("保存成功！", "success");
            }
        } else if (title == "取消") {
            loadPropertyData("attr");
        }
    });


    var initWindowPage = function () {

        $("#framePropertyWindow, #pagePropertyWindow, #areaPropertyWindow, #attrPropertyWindow")
            .find("input[name=isDisplay],input[name=isShowTitle],input[name=isEditable],input[name=isNullable],input[name=isVisible],input[name=isInit]").each(function () {
            $(this).kendoDropDownList({
                dataTextField: "name",
                dataValueField: "id",
                dataSource: [
                    {name: "是", id: '1'},
                    {name: "否", id: '0'}
                ]
            });
        });

        //moduleId
        $("#framePropertyWindow, #pagePropertyWindow, #areaPropertyWindow, #attrPropertyWindow").find("input[name=moduleId]").kendoDropDownList({
            dataTextField: "name",
            dataValueField: "id",
            dataSource: [
                {name: "PC", id: '1'},
                {name: "手机", id: '2'}
            ]
        });

        //pageType
        $("#framePropertyWindow, #pagePropertyWindow, #areaPropertyWindow, #attrPropertyWindow").find("input[name=pageType]").kendoDropDownList({
            dataTextField: "name",
            dataValueField: "id",
            enable: false,
            dataSource: [
                {name: "配置页面", id: '1'},
                {name: "iFrame页面", id: '2'},
                {name: "native页面", id: '3'}
            ]
        });

        $("#framePropertyWindow, #pagePropertyWindow, #areaPropertyWindow, #attrPropertyWindow").find("input[name=areaType]").kendoDropDownList({
            dataTextField: "name",
            dataValueField: "id",
            dataSource: [
                {name: "form", id: 'form'},
                {name: "table", id: 'table'},
                {name: "template", id: 'template'}
            ]
        });

        comm.ajax.ajaxEntity({
            busiCode: "ICFGDYNCCOMMONFSV_GETCFGDYNCRULESET",
            param: {},
            callback: function (data) {
                data = data || [];
                data.push({
                    rulesetName: '无',
                    rulesetId: ""
                });
                $("#framePropertyWindow, #pagePropertyWindow, #areaPropertyWindow, #attrPropertyWindow")
                    .find("input[name=rulesetId]").each(function () {
                    $(this).kendoDropDownList({
                        dataTextField: "rulesetName",
                        dataValueField: "rulesetId",
                        dataSource: data
                    });
                });
            }
        });

        comm.ajax.ajaxEntity({
            busiCode: "ICFGDYNCCOMMONFSV_GETCFGDYNCBUTTONSET",
            param: {},
            callback: function (data) {
                data = data || [];
                data.push({
                    buttonsetName: '无',
                    buttonsetId: ""
                });
                $("#framePropertyWindow, #pagePropertyWindow, #areaPropertyWindow, #attrPropertyWindow")
                    .find("input[name=buttonsetId]").each(function () {
                    $(this).kendoDropDownList({
                        dataTextField: "buttonsetName",
                        dataValueField: "buttonsetId",
                        dataSource: data
                    });
                });
            }
        });

        comm.ajax.ajaxEntity({
            busiCode: "ICFGDYNCCOMMONFSV_GETCFGDYNCPAGETEMPLATE",
            param: {},
            callback: function (data) {
                $("#framePropertyWindow, #pagePropertyWindow, #areaPropertyWindow, #attrPropertyWindow")
                    .find("input[name=pageTemplateId]").each(function () {
                    $(this).kendoDropDownList({
                        dataTextField: "templateName",
                        dataValueField: "templateId",
                        dataSource: data,
                        enable: false
                    });
                });
            }
        });

        comm.ajax.ajaxEntity({
            busiCode: "ICFGDYNCCOMMONFSV_GETCFGDYNCRULEEXP",
            param: {},
            callback: function (data) {
                data = data || [];
                data.push({
                    expName: '无',
                    expid: ""
                });
                $("#framePropertyWindow, #pagePropertyWindow, #areaPropertyWindow, #attrPropertyWindow")
                    .find("input[name=regexpId]").each(function () {
                    $(this).kendoDropDownList({
                        dataTextField: "expName",
                        dataValueField: "expid",
                        dataSource: data
                    });
                });
            }
        });

        $("#framePropertyWindow, #pagePropertyWindow, #areaPropertyWindow, #attrPropertyWindow")
            .find("input[name=editType]").each(function () {
            $(this).kendoDropDownList({
                dataTextField: "name",
                dataValueField: "id",
                dataSource: [
                    {name: "label", id: "label"},
                    {name: "dropdownlist", id: "dropdownlist"},
                    {name: "combobox", id: "combobox"},
                    {name: "multiselect", id: "multiselect"},
                    {name: "timepicker", id: "timepicker"},
                    {name: "datepicker", id: "datepicker"},
                    {name: "aipopup", id: "aipopup"},
                    {name: "textbox", id: "textbox"},
                    {name: "aibcerinput", id: "aibcerinput"},
                    {name: "numerictextbox", id: "numerictextbox"},
                    {name: "textarea", id: "textarea"},
                    {name: "address", id: "address"}
                ],
                enable: false
            });
        });

    };

    initWindowPage();


    var alertError = function (title, message) {
        if (message == undefined) {
            message = title;
            title = "错误";
        }
        comm.dialog.notification({
            title: title,
            type: comm.dialog.type.error,
            content: message
        });
    };

    var checkFrame = function () {
        //frame
        var data = JSON.parse(JSON.stringify($("#componentTree").data("kendoTreeView").dataSource._data[0])).items;
        if (data == undefined || data.length != 1) {
            alertError("必须且只能包含一种框架类型！");
            return false;
        }
        data = data[0];
        var frameType = data.type;
        if (frameType != "singlePage" && frameType != "tabPage" && frameType != "wizardPage") {
            alertError("不能识别的框架类型！[" + frameType + "]");
            return false;
        }
        if (checkPropertyNull(data, cfgFrame) == false) {
            alertError("请校验框架【" + data.text + "】的配置信息！存在空值！");
            return false;
        }
        if (data.items == undefined || data.items.length == 0) {
            alertError("框架下面必须包含页面！");
            return false;
        }
        if (frameType == "singlePage" && data.items.length > 1) {
            alertError("单页面框架下面只能包含一个页面！");
            return false;
        }
        for (var i = 0; i < data.items.length; i++) {
            var page = data.items[i];
            if (checkPage(page) == false) {
                return false;
            }
        }

        return true;
    };

    var checkPage = function (page) {
        //page
        if (page.type != "page1" && page.type != "page2" && page.type != "page3") {
            alertError("不能识别的页面类型！" + page.text + "[" + page.type + "]");
            return false;
        }
        if (checkPropertyNull(page, cfgPage) == false) {
            alertError("请校验页面【" + page.text + "】的配置信息！存在空值！");
            return false;
        }
        if (page.type == "page1" && (page.items == undefined || page.items.length == 0)) {
            alertError("配置页面下至少包含一个域！");
            return false;
        }
        if ((page.type == "page2" || page.type == "page3") && page.items != undefined && page.items.length > 0) {
            alertError("非配置页面下不能包含域！");
            return false;
        }
        if (page.type == "page1") {
            for (var i = 0; i < page.items.length; i++) {
                var area = page.items[i];
                if (checkArea(area) == false) {
                    return false;
                }
            }
        }
        return true;
    };

    var checkArea = function (area) {
        //area
        if (area.type != "area") {
            alertError("不能识别的域类型！" + area.text + "[" + area.type + "]");
            return false;
        }
        if (checkPropertyNull(area, cfgArea) == false) {
            alertError("请校验域【" + area.text + "】的配置信息！存在空值！");
            return false;
        }
        if (area.items == undefined || area.items.length == 0) {
            alertError("域下面至少包含一个属性！");
            return false;
        }
        for (var i = 0; i < area.items.length; i++) {
            var attr = area.items[i];
            if (checkAttr(attr) == false) {
                return false;
            }
        }
        return true;
    };


    var checkAttr = function (attr) {
        //attr
        if (attr.type == undefined || attrTypes.indexOf(attr.type + ",") < 0) {
            alertError("不能识别的属性类型！" + attr.text + "[" + attr.type + "]");
            return false;
        }
        if (checkPropertyNull(attr, cfgAttr) == false) {
            alertError("请校验属性【" + attr.text + "】的配置信息！存在空值！");
            return false;
        }
        return true;
    };


    var checkPropertyNull = function (data, cfg) {
        var success = true;
        for (var key in cfg) {
            if (data[key] == undefined) {
                success = false;
            } else {
                var notnulls = cfg[key].notnull.split(",");
                for (var i = 0; i < notnulls.length; i++) {
                    if (data[key][notnulls[i]] != undefined && data[key][notnulls[i]] != null && "" + data[key][notnulls[i]] != '') {

                    } else {
                        success = false;
                        break;
                    }
                }
            }
            if (success == false) {
                break;
            }
        }
        return success;
    };


    $.extend(dfgConfigureWindowModule, {
        checkFrame: checkFrame,
        loadPropertyData: loadPropertyData
    });

})(jQuery);


//组件树的初始化及增删改查
(function ($) {
    var components = [{
        type: "frame",
        name: "框架",
        parent: 'root',
        kinds: [{
            type: 'singlePage',
            name: '单页面'
        }, {
            type: 'tabPage',
            name: 'TAB页面'
        }, {
            type: 'wizardPage',
            name: '导航页面'
        }]
    }, {
        parent: 'frame',
        type: "page",
        name: "页面",
        kinds: [{
            type: 'page1',
            name: '配置页面'
        }, {
            type: 'page2',
            name: 'iframe页面'
        }, {
            type: 'page3',
            name: 'native页面'
        }]
    }, {
        parent: 'page',
        type: "area",
        name: "域",
        kinds: [{
            type: 'area',
            name: '域'
        }]
    }, {
        parent: 'area',
        type: "attr",
        name: "属性",
        kinds: [{
            type: 'address',
            name: '地址'
        }, {
            type: 'numerictextbox',
            name: '数值'
        }, {
            type: 'multiselect',
            name: '多选下拉框'
        }, {
            type: 'textbox',
            name: '文本'
        }, {
            type: 'label',
            name: '标题'
        }, {
            type: 'datepicker',
            name: '日期'
        }, {
            type: 'combobox',
            name: '单选下拉框'
        }, {
            type: 'dropdownlist',
            name: '下拉框'
        }, {
            type: 'textarea',
            name: '文本域'
        }, {
            type: 'aipopup',
            name: '弹出框'
        }, {
            type: 'aibcerinput',
            name: '远程输入框'
        }]
    }];


    var getCfgDyncData = function (busiFrameId) {
        var dataSource;
        if (busiFrameId && busiFrameId != "") {
            comm.ajax.ajaxEntity({
                param: {
                    busiFrameId: busiFrameId
                },
                busiCode: "ICFGDYNCCOMMONFSV_GETCFGDYNCDATA",
                sync: false,
                callback: function (data, isSucc, msg) {
                    if (isSucc) {
                        dataSource = [{
                            text: "根",
                            id: '0',
                            type: 'root',
                            ptype: '-1',
                            expanded: true,
                            items: [data]
                        }];
                    } else {
                        comm.dialog.notification({
                            title: "错误",
                            type: comm.dialog.type.error,
                            content: "加载数据错误!" + msg
                        });
                        dataSource = [{
                            text: "根",
                            id: '0',
                            type: 'root',
                            ptype: '-1',
                            items: []
                        }]
                    }
                }
            });
        } else {
            dataSource = [{
                text: "根",
                id: '0',
                type: 'root',
                ptype: '-1',
                items: []
            }]
        }
        return dataSource;
    };

    var initUI = function () {

        var request = comm.tools.getRequest();
        var dataSource = getCfgDyncData(request.busiFrameId);

        $("#componentTree").kendoTreeView({
            loadOnDemand: true,
            dragAndDrop: true,
            dataTextField: "text",
            template: "#= item.text #【#= item.type #】",
            dataSource: dataSource,
            change: function (e) {
                onComponentTreeSelect();
            }
        });


        $("#commitWindow").kendoWindow({
            actions: ["Close"],
            animation: false,
            modal: true,
            pinned: true,
            iframe: false,
            visible: false,
            title: "动态页面配置提交保存",
            position: {
                left: ($(window).width() - 850) / 2 + $(document).scrollLeft(),
                top: ($(window).height() - 460) / 2 + $(document).scrollTop()
            },
            width: 850,
            height: 460,
            close: function () {

            },
            open: function () {
                var data = getTreeDataByPType("frame");
                var html = "";
                var pageIds = "";
                var pageNames = {};
                for (var i = 0; i < data.length; i++) {
                    if (data[i].cfgDyncPage.pageId) {
                        html = html +
                            "<div class='col-md-12'>" +
                            "   <div class='box-inside'>" +
                            "       <div class='box-header'>" +
                            "           <h4>" + data[i].text + "[pageId=" + data[i].cfgDyncPage.pageId + "]</h4>" +
                            "       </div>" +
                            "       <div class='box-content clearfix' pageId='" + data[i].cfgDyncPage.pageId + "'></div>" +
                            "   </div>" +
                            "</div>";
                        pageIds = pageIds + data[i].cfgDyncPage.pageId + ",";
                        pageNames[data[i].cfgDyncPage.pageId] = data[i].text;
                    } else {
                        html = html +
                            "<div class='col-md-12'>" +
                            "   <div class='box-inside'>" +
                            "       <div class='box-header'>" +
                            "           <h4>" + data[i].text + "[pageId=-]</h4>" +
                            "       </div>" +
                            "       <div class='box-content clearfix'>新建无复用</div>" +
                            "   </div>" +
                            "</div>";
                    }
                }
                $("#commitPage").html(html);

                initPageFrameChart(pageIds, pageNames);
            }
        });
    };


    var initPageFrameChart = function (pageIds, pageNames) {
        if (pageIds == "") {
            return;
        }
        comm.ajax.ajaxEntity({
            busiCode: "ICFGDYNCCOMMONFSV_QUERYPAGEFRAMERELATION",
            param: {
                pageIds: pageIds
            },
            callback: function (data, isSucc, msg) {
                if (isSucc == false) {
                    comm.dialog.notification({
                        title: "错误",
                        type: comm.dialog.type.error,
                        content: "加载页面复用关系失败！" + msg
                    });
                } else {
                    initPageRelation(data.links, pageIds.split(","), pageNames);
                }
            }
        });

    };

    var initPageRelation = function (links, pageIds, pageNames) {

        var thisBusiFrameId = "";
        try {
            thisBusiFrameId = getTreeDataByPType("root")[0].cfgDyncBusiFrameRel.busiFrameId;
        } catch (e) {

        }
        for (var i = 0; i < pageIds.length; i++) {
            for (var j = 0; j < links.length; j++) {
                if (links[j].PAGE_ID == pageIds[i]) {
                    var state = links[j].STATE;
                    if (state == 1) {
                        state = "启用中";
                    } else if (state == 2) {
                        state = "停用中";
                    }
                    var html = "<input name='createNewPage_" + links[j].PAGE_ID + "' type='checkbox' value='" + links[j].BUSI_FRAME_ID + "'";
                    if (thisBusiFrameId == links[j].BUSI_FRAME_ID) {
                        html = html + " checked disabled"
                    }
                    html = html + " > " + links[j].REMARK + "【busiFrameId=" + links[j].BUSI_FRAME_ID + "】【" + state + "】";
                    if (thisBusiFrameId == links[j].BUSI_FRAME_ID) {
                        html = html + "【当前页面】";
                    }
                    $("#commitPage").find("div[pageId=" + links[j].PAGE_ID + "]").append("<div class='col-md-6'>" + html + "</div>");
                }
            }
        }
    };

    var initEnv = function () {
        $("#addButton").bind($.clickOrTouch(), function () {
            addComponent();
        });

        $("#delButton").click(function () {
            delComponent();
        });

        $("#saveButton").click(function () {
            saveResult(function (busiFrameId) {
                if (busiFrameId) {
                    var dataSource = getCfgDyncData(busiFrameId);
                    $("#componentTree").data("kendoTreeView").setDataSource(new kendo.data.HierarchicalDataSource({
                        data: dataSource
                    }));
                }
            });
        });

        $("#viewButton").click(function () {
            saveResult(function (busiFrameId) {
                if (busiFrameId) {
                    var dataSource = getCfgDyncData(busiFrameId);
                    $("#componentTree").data("kendoTreeView").setDataSource(new kendo.data.HierarchicalDataSource({
                        data: dataSource
                    }));
                    if (dataSource[0] && dataSource[0].items && dataSource[0].items[0] && dataSource[0].items[0].type) {
                        var type = dataSource[0].items[0].type;
                        if (type == "singlePage") {
                            $("#viewFrame").attr("src", "./page/singlePageFrame.html?busiFrameId=" + busiFrameId);
                        } else if (type == "tabPage") {
                            $("#viewFrame").attr("src", "./page/tabPageFrame.html?busiFrameId=" + busiFrameId);
                        } else if (type == "wizardPage") {
                            $("#viewFrame").attr("src", "./page/wizardPageFrame.html?busiFrameId=" + busiFrameId);
                        }
                    }
                }
            });
        });

        $("#commitWindow").find("[type=button]").click(function () {
            var title = $(this).html();
            if (title == "停用并级联更新") {
                $("#commitWindow").data("kendoWindow").close();
                commitResult($("#commitWindow").data("kendoWindow").callback);
            } else if (title == "取消") {
                $("#commitWindow").data("kendoWindow").close();
            }
        });

    };


    var onComponentTreeSelect = function () {
        $("#framePropertyWindow").hide();
        $("#pagePropertyWindow").hide();
        $("#areaPropertyWindow").hide();
        $("#attrPropertyWindow").hide();

        var select = $("#componentTree").data("kendoTreeView").getSelect();
        if (select.type == 'singlePage' || select.type == "tabPage" || select.type == "wizardPage") {
            dfgConfigureWindowModule.loadPropertyData("frame");
            $("#framePropertyWindow").show();
        } else if (select.type == "page1" || select.type == "page2" || select.type == "page3") {
            dfgConfigureWindowModule.loadPropertyData("page");
            $("#pagePropertyWindow").show();
        } else if (select.type == "area") {
            dfgConfigureWindowModule.loadPropertyData("area");
            $("#areaPropertyWindow").show();
        } else if (select.ptype == "area") {
            dfgConfigureWindowModule.loadPropertyData("attr");
            $("#attrPropertyWindow").show();
        }
    };

    var getTreeDataByPType = function (ptype) {
        var that = $("#componentTree").data("kendoTreeView");
        var _data = that.dataSource._data;
        var _target = [];
        while (_data.length > 0) {
            var _p_data = [];
            for (var i = 0; i < _data.length; i++) {
                if (_data[i].ptype === ptype) {
                    _target.push(_data[i]);
                } else if (_data[i].children && _data[i].children._data && _data[i].children._data.length > 0) {
                    for (var k = 0; k < _data[i].children._data.length; k++) {
                        _p_data.push(_data[i].children._data[k]);
                    }
                }
            }
            _data = _p_data;
        }
        return _target;
    };

    var componentWindow;
    var addComponent = function () {
        var select = $("#componentTree").data("kendoTreeView").getSelect();
        if (select.text == undefined) {
            comm.dialog.notification({
                title: "错误",
                type: comm.dialog.type.error,
                content: "请选择要添加的节点!"
            });
            return;
        }
        var selectType = select.type;
        if (select.type == "singlePage" || select.type == "tabPage" || select.type == "wizardPage") {
            selectType = "frame";
        }

        var disableFlag = false;
        if (selectType == "root") {
            //根节点下只能添加一种框架类型
            if (getTreeDataByPType(selectType).length > 0) {
                disableFlag = true;
            }
        }
        if (select.type == "singlePage") {
            //单页面下只能添加一个页面
            if (getTreeDataByPType(selectType).length > 0) {
                disableFlag = true;
            }
        }

        if (select.type == "page2" || select.type == "page3") {
            disableFlag = true;
        }
        if (select.type == "page1" || select.type == "page2" || select.type == "page3") {
            selectType = "page";
        }

        var content = "";
        for (var i = 0; i < components.length; i++) {
            content = content + "<div class='col-md-12'>" +
                "    <div class='box-header'>" +
                "        <h4>" + components[i].name + "</h4>" +
                "    </div>" +
                "    <div class='box-content clearfix'>";
            var kinds = components[i].kinds;
            var disabled = "";
            for (var j = 0; j < kinds.length; j++) {
                if (selectType == components[i].parent && disableFlag == false) {
                    disabled = "";
                } else {
                    disabled = "disabled";
                }
                if (select.type == "page2" || select.type == "page3") {
                    disabled = "disabled";
                }
                content = content + "<button type='button' class='k-button' name='component' " + disabled + " ttype = '" + kinds[j].type + "' ptype='" + components[i].parent + "'>" + kinds[j].name + "</button>&nbsp;";
            }
            content = content + "</div>" + "</div>";
        }
        componentWindow = $.dialog({
            animated: false,
            title: "页面组件添加",
            type: "window",
            width: "600",
            height: "450",
            style: {
                //border: "10px solid #ccc"
            },
            header: {
                hasHeader: true,
                // subTitle: '<small style="color:#fff;">请添加页面组件</small>',
                // icon: '<i class="fa fa-add"></i>',
                titleStyle: "color:#fff;",
                style: "background:red;",
                className: "pui-bg-blue pui-unbordered"
            },
            content: content,
            padding: 20,
            onshow: function () {
                $("[name=component]").click(componentButtonClick);
            }
        });
    };

    var componentButtonClick = function () {
        var ttype = $(this).attr("ttype");
        var ptype = $(this).attr("ptype");
        var componentTree = $("#componentTree").data("kendoTreeView");
        var uid = componentTree.select().attr("data-uid");
        componentWindow.hide();
        comm.dialog.prompt({
            title: "提示",
            content: "请输入组件名称",
            isNotNullFlag: true,
            func: function (flag, value) {
                if (flag == true) {
                    $("#componentTree").data("kendoTreeView").append({
                        text: value,
                        type: ttype,
                        ptype: ptype
                    }, componentTree.findByUid(uid), function (ll) {
                        $("#componentTree").data("kendoTreeView").select(ll);
                    });
                }
            }
        });
    };

    var delComponent = function () {
        var select = $("#componentTree").data("kendoTreeView").getSelect();
        if (select.type == undefined) {
            comm.dialog.notification({
                title: "提示",
                type: comm.dialog.type.warn,
                content: "请选择要删除的节点!"
            });
            return;
        }
        if (select.type == "root") {
            comm.dialog.notification({
                title: "提示",
                type: comm.dialog.type.warn,
                content: "不能删除跟节点!"
            });
            return;
        }
        comm.dialog.confirm({
            title: "提示",
            type: comm.dialog.type.question,
            content: "您确定要删除【" + $("#componentTree").data("kendoTreeView").getSelect().text + "】及其下面的所有节点吗？",
            func: function (f) {
                if (f) {
                    $("#componentTree").data("kendoTreeView").remove($("#componentTree").data("kendoTreeView").select());
                }
            }
        });
    };


    var commitResult = function (callback) {
        var pageData = getTreeDataByPType("frame");
        for (var i = 0; i < pageData.length; i++) {
            if (pageData[i].cfgDyncPage.pageId) {
                $("#commitPage").find("input[name=createNewPage_" + pageData[i].cfgDyncPage.pageId + "]").each(function (i, e) {
                    if (e.checked && e.disabled == false) {
                        this.createNewPage = (this.createNewPage || "") + e.value + ",";
                    }
                }.bind(pageData[i]));
            }
        }

        var data = JSON.parse(JSON.stringify($("#componentTree").data("kendoTreeView").dataSource._data[0].children._data[0]));
        comm.ajax.ajaxEntity({
            busiCode: "ICFGDYNCCOMMONFSV_SAVECFGDYNCDATA",
            param: {
                data: data
            },
            callback: function (data, isSucc, msg) {
                if (isSucc == false) {
                    comm.dialog.notification({
                        title: "错误",
                        type: comm.dialog.type.error,
                        content: msg
                    });
                } else {
                    if (callback) {
                        callback(data);
                    }
                    comm.dialog.notification({
                        title: "成功",
                        type: comm.dialog.type.success,
                        content: "保存成功！"
                    });
                }

            }
        });
    };

    var saveResult = function (callback) {
        if (dfgConfigureWindowModule.checkFrame()) {


            $("#commitWindow").data("kendoWindow").open();

            $("#commitWindow").data("kendoWindow").callback = callback;

        }
    };

    initUI();
    initEnv();

})(jQuery);


(function ($) {
    var initUI = function () {
        $("#splitter").height($(window).height());
        $("#splitter").kendoSplitter({
            orientation: "horizontal",
            panes: [
                {collapsible: false, size: "35%"},
                {collapsible: false}
            ]
        });


        $("#notification").kendoNotification({
            autoHideAfter: 2000
        });
    };

    var initEnv = function () {

    };


    var init = function () {
        initUI();
        initEnv();
    };

    init();

    $.extend(dyncConfigureModule, {});
})(jQuery);