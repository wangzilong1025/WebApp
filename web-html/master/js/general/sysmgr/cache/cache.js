/**
 *
 * @author wangjian13
 * Created on 2015/10/12
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
(function ($) {
    var popupNotification = $("#popupNotification").kendoNotification().data("kendoNotification");

    $("#pageGrid").kendoGrid({
        dataSource: {
            transport: {
                read: {
                    url: SERVER_URL,
                    type: "POST"
                },
                parameterMap: function (data, operation) {
                    if (operation == "read") {
                        var cacheName = $("#cacheName").val();
                        data.cacheName = cacheName;
                        var jsonRequstParam = {
                            "param": data,
                            "busiCode": "ICFGCACHELOADFSV_RELOADCACHELIST"
                        };
                        return comm.ajax.paramWrap(jsonRequstParam);
                    } else if (data.models) {
                        return JSON.stringify(data.models);
                    }
                }
            },
            batch: true,
            pageSize: 10,
            schema: {
                data: "data.list",
                total: "data.count"
            }
        },
        height: 350,
        sortable: true,
        pageable: {
            refresh: true,
            pageSizes: true,
            buttonCount: 5
        },
        columns: [{
            field: "cacheName",
            title: "缓存名称",
            width: 250
        }, {
            field: "cacheType",
            title: "缓存加载方式",
            width: 100
        }, {
            field: "cacheImplClass",
            title: "缓存处理类",
            width: 400
        }, {
            field: "moduleName",
            title: "模块编号",
            width: 100
        }, {
            field: "cronExpression",
            title: "定时解析器",
            width: 100
        }, {
            field: "saveType",
            title: "存储类型",
            width: 70
        }, {
            field: "state",
            title: "状态",
            width: 50
        }, {
            command: [{
                name: "reload",
                text: "重载",
                width: 100,
                click: function (e) {
                    // $("#pageGrid").attr("disabled", true);
                    var tr = $(e.target).closest("tr");
                    var data = this.dataItem(tr);
                    var param = {
                        "cacheName": data.cacheName
                    };
                    var requstParam = {
                        "busiParams": param,
                        "busiCode": "ICFGCACHELOADFSV_RELOADCACHE"
                    };
                    comm.ajax.ajaxEntity({
                        "param": param,
                        "busiCode": "ICFGCACHELOADFSV_RELOADCACHE",
                        callback: function (data) {
                            if (data.status && data.status == "1") {
                                popupNotification.show("重载成功", "info");
                            }
                        }
                    });
                }
            }, {
                name: "lookData",
                text: "查看",
                width: 100,
                click: function (e) {
                    var tr = $(e.target).closest("tr");
                    var data = this.dataItem(tr);
                    if (data.saveType == 2) {
                        popupNotification.show("数据存储类型为2的无法直接获取所有的缓存数据", "warning");
                        return false;
                    }
                    var url = "/view/general/sysmgr/cache/reloadDataDetail.html?" + "cacheName=" + data.cacheName + "&saveType=" + data.saveType;
                    window.location.href = url;
                }
            }],
            title: "操作",
            width: "160px"
        }]
    });


    $("#sysCacheGrid").kendoGrid({
        dataSource: {
            transport: {
                read: {
                    url: SERVER_URL,
                    type: "POST"
                },
                parameterMap: function (data, operation) {
                    if (operation == "read") {
                        var cacheName = $("#cacheName").val();
                        data.cacheName = "UserInfoCache_sec_" + cacheName;
                        var busiCode = "ICFGCACHELOADFSV_LOADIDORSYSCACHEDATA";
                        var param = {
                            "busiCode": busiCode,
                            "param": data
                        };
                        return comm.ajax.paramWrap(param);
                    } else if (data.models) {
                        return JSON.stringify(data.models);
                    }
                }
            },
            pageSize: 10,
            schema: {
                data: "data.cacheList",
                total: "data.count"
            },
            batch: true
        },
        autoBind: false,
        pageable: {
            refresh: true,
            pageSizes: true,
            buttonCount: 5
        },
        height: 350,
        columns: [{
            field: "key",
            text: "key",
            width: "25%"
        }, {
            field: "value",
            text: "value",
            width: "50%",
            template: function (dataItem) {
                if (dataItem.value) {
                    var value = JSON.stringify(dataItem.value);
                    if (value.length > 100) {
                        return value.substring(0, 100) + "...";
                    } else {
                        return JSON.stringify(dataItem.value);
                    }
                } else {
                    return "";
                }
            }
        }, {
            command: [{
                name: "lookDetailData",
                text: "详细",
                click: function (e) {
                    var tr = $(e.target).closest("tr");
                    var data = this.dataItem(tr);
                    var str = JSON.stringify(data.value);
                    var detailUrl = "/view/general/sysmgr/cache/dataDetail.html?cacheName=UserInfoCache&cacheKey=" + data.key;
                    window.location.href = detailUrl;
                }
            }, {
                name: "deleteFromCache",
                text: "删除",
                click: function (e) {
                    var tr = $(e.target).closest("tr");
                    var data = this.dataItem(tr);
                    var dataParam = {};
                    dataParam.cacheKey = "UserInfoCache_sec_" + data.key;
                    comm.ajax.ajaxEntity({
                        "busiCode": "ICFGCACHELOADFSV_DELETEFROMCACHE",
                        "param": dataParam,
                        callback: function (data, isSucc, msg) {
                            if (isSucc) {
                                $("#sysCacheGrid").data("kendoGrid").dataSource.read();
                                $("cacheName").val("");
                            } else {
                                comm.dialog.notice({
                                    type: comm.dialog.type.error,
                                    position: "center",
                                    content: msg,
                                    timeout: 800
                                });
                            }
                        }
                    });
                }
            }],
            title: "操作"
        }
        ]
    });


    $("#idCacheGrid").kendoGrid({
        dataSource: {
            transport: {
                read: {
                    url: SERVER_URL,
                    type: "POST"
                },
                parameterMap: function (data, operation) {
                    if (operation == "read") {
                        data.cacheName = "_ID_CACHE_FLAG_";
                        var cacheName = $("#cacheName").val();
                        if (cacheName != null && cacheName != "")
                            data.cacheName = "_ID_CACHE_FLAG_" + cacheName;
                        var param = {
                            "busiCode": "ICFGCACHELOADFSV_LOADIDORSYSCACHEDATA",
                            "param": data
                        };
                        return comm.ajax.paramWrap(param);
                    } else if (data.models) {
                        return JSON.stringify(data.models);
                    }
                }
            },
            pageSize: 10,
            schema: {
                data: "data.cacheList",
                total: "data.count"
            },
            batch: true
        },
        pageable: {
            refresh: true,
            pageSizes: true,
            buttonCount: 5
        },
        height: 350,
        columns: [{
            field: "key",
            text: "key",
            width: "25%"
        }, {
            field: "value",
            text: "value",
            width: "50%",
            template: function (dataItem) {
                if (dataItem.value) {
                    var value = JSON.stringify(dataItem.value);
                    if (value.length > 100) {
                        return value.substring(0, 100) + "...";
                    } else {
                        return JSON.stringify(dataItem.value);
                    }
                } else {
                    return "";
                }
            }
        }, {
            command: [{
                name: "lookDetailData",
                text: "详细",
                click: function (e) {
                    var tr = $(e.target).closest("tr");
                    var data = this.dataItem(tr);
                    var str = JSON.stringify(data.value);
                    var detailUrl = "/view/general/sysmgr/cache/dataDetail.html?value=" + str;
                    window.location.href = detailUrl;
                }
            }, {
                name: "deleteFromCache",
                text: "删除",
                click: function (e) {
                    var tr = $(e.target).closest("tr");
                    var data = this.dataItem(tr);
                    var dataParam = {};
                    dataParam.cacheKey = "_ID_CACHE_FLAG_" + data.key;
                    comm.ajax.ajaxEntity({
                        busiCode: "ICFGCACHELOADFSV_DELETEFROMCACHE",
                        params: dataParam,
                        callback: function (data, isSucc, msg) {
                            if (isSucc) {
                                $("cacheName").val("");
                                $("#idCacheGrid").data("kendoGrid").dataSource.read();
                            } else {
                                comm.dialog.notice({
                                    type: comm.dialog.type.error,
                                    position: "center",
                                    content: msg,
                                    timeout: 800
                                });
                            }
                        }
                    });
                }
            }],
            title: "操作"

        }]
    });


    $("#idCacheGrid").css("display", "none");
    $("#sysCacheGrid").css("display", "none");

    $("[type=radio]").each(function () {
        $(this).click(function () {
            var id = $(this).attr("id");
            if (id == "sysCache") {
                $("#pageGrid").css("display", "none");
                $("#idCacheGrid").css("display", "none");
                $("#sysCacheGrid").css("display", "block");
                $("#cacheName").val("");
            } else if (id == "idCache") {
                $("#pageGrid").css("display", "none");
                $("#sysCacheGrid").css("display", "none");
                $("#idCacheGrid").css("display", "block");
                $("#cacheName").val();
            } else {
                $("#pageGrid").css("display", "block");
                $("#sysCacheGrid").css("display", "none");
                $("#idCacheGrid").css("display", "none");
                $("#cacheName").val("");
                $("#pageGrid").data("kendoGrid").dataSource.read();
            }
        });
    });
    $("#queryButton2").click(function () {
        var id = $("input[name=cache]:checked").attr("id");
        if (id == "commonCache") {
            $("#pageGrid").data("kendoGrid").dataSource.read();
        } else if (id == "idCache") {
            $("#idCacheGrid").data("kendoGrid").dataSource.read();
        } else {
            var value = $("#cacheName").val();
            if (value == "") {
                popupNotification.show("因为数据量比较大，请输入操作员的code关键字", "warning");
                return false;
            } else {
                $("#sysCacheGrid").data("kendoGrid").dataSource.read();
            }
        }
    });

})(jQuery);
