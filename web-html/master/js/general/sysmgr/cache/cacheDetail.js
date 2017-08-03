/**
 * created by wangjian13  2016/1/19
 * @param $
 */
(function ($) {
    var popupNotification = $("#popupNotification").kendoNotification().data("kendoNotification");


    var cacheNameArray = comm.tools.getRequest();
    var cacheName;
    var saveType = cacheNameArray.saveType;
    if (cacheNameArray) {
        cacheName = cacheNameArray.cacheName;
        $("#cacheName").text(cacheName);
    }
    $("#return").click(function () {
        window.location.href = "/view/general/sysmgr/cache/cacheDataList.html";
    });
    $("#detailData").kendoGrid({
        dataSource: {
            transport: {
                read: {
                    url: SERVER_URL,
                    type: "POST"
                },
                parameterMap: function (data, operation) {
                    if (operation == "read") {
                        data.cacheName = cacheName;
                        data.key = $("#key").val();
                        data.saveType = saveType;
                        var param = {
                            "busiCode": "ICFGCACHELOADFSV_RELOADDETAILDATA",
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
                data: "data.data.cacheList",
                total: "data.data.count"
            },
            batch: true
        },
        pageable: {
            refresh: true,
            pageSizes: true,
            buttonCount: 5

        },
        sortable: true,
        height: 300,
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
                    var detailUrl = "/view/general/sysmgr/cache/dataDetail.html?cacheName=" + $("#cacheName").text() + "&cacheKey=" + data.key;
                    window.location.href = detailUrl;
                }
            }, {
                name: "deleteFromCache",
                text: "删除",
                click: function (e) {
                    var tr = $(e.target).closest("tr");
                    var data = this.dataItem(tr);
                    var dataParam = {};
                    dataParam.cacheName = $("#cacheName").text();
                    dataParam.cacheKey = data.key;
                    var param = {
                        "busiCode": "ICFGCACHELOADFSV_DELETEFROMCACHE",
                        "param": dataParam
                    };
                    comm.ajax.ajaxEntity({
                        "busiCode": "ICFGCACHELOADFSV_DELETEFROMCACHE",
                        "param": dataParam,
                        callback:function (data) {
                            var code = data.errorInfo.code;
                            if (code == "0000") {
                                var message = data.data.errorInfo.message;
                                popupNotification.show(message, "info");
                                $("#detailData").data("kendoGrid").dataSource.read();
                            } else {
                                var message = data.errorInfo.message;
                                popupNotification.show(message, "waring");
                            }
                        }
                    });
                }
            }],
            title: "操作"
        }]
    });
    $("#queryButton2").click(function () {
        $("#detailData").data("kendoGrid").dataSource.read();
    });
})(jQuery);