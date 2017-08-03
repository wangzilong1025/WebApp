/**
 * @author zhangrp
 * Created on 2016/9/11 11:47
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

var elecModule = {};

(function ($) {

    var cfgElec;
    var request = comm.tools.getRequest();
    $("#effectiveDate").datebox().value(new Date());//生效时间默认为当前时间


    var initViewType = function () {
        var type = request.viewType || "edit";
        if (type == "view") {
            $("#uploadDiv").hide();
        } else {
            $("#uploadDiv").show();
        }

        if (request.fileTypeId && request.fileTypeId != "") {
            $("#treeDiv").hide();
            $("#contentDiv").removeClass("col-md-10");
            $("#contentDiv").addClass("col-md-12");
        }

        if (cfgElec && cfgElec.fileTypeId) {
            var fileTypeName = cfgElec.fileTypeName;
            $("#title").html("【" + fileTypeName + "】");
            $("#title2").html("【" + fileTypeName + "】");
            if (cfgElec.hasExpireDate && cfgElec.hasExpireDate == "1") {
                $("#hasExpireDate").show();
            } else {
                $("#hasExpireDate").hide();
            }
            $("#elecGird").datagrid().load();

            if ($("#files").data("kendoUpload")) {
                $("#files").data("kendoUpload").enable();
            }
        }

    };

    initViewType();

    if (request.fileTypeId && request.fileTypeId != "") {

        comm.ajax.ajaxEntity({
            sync: true,
            busiCode: busiCode.common.IELECFSV_QUERYCFGELEC,
            param: {
                fileTypeId: request.fileTypeId
            },
            callback: function (data, isSucc, msg) {
                if (isSucc && data) {
                    if (data.length > 0) {
                        cfgElec = data[0];
                        initViewType();
                    }
                } else {
                    comm.dialog.notification({
                        title: "错误",
                        type: comm.dialog.type.error,
                        content: "无效的fileTypeId! " + msg
                    });
                }
            }
        });

    } else {
        $("#fileTypeTree").kendoTreeView({
            loadOnDemand: true,
            dataTextField: "fileTypeName",
            dataImageUrlField: "imgUrl",
            template: "<span style='#= item.showStyle #'>#= item.fileTypeName #</span>",
            dataSource: {
                transport: {
                    read: {
                        url: SERVER_URL,
                        type: "POST"
                    },
                    parameterMap: function (data, operation) {
                        data = data || {};
                        var param = {};
                        if (data.fileTypeId) {
                            param.parentFileTypeId = data.fileTypeId;
                        } else {
                            if (request.fileTypeId && request.fileTypeId != "") {
                                param.fileTypeId = request.fileTypeId;
                            } else {
                                param.parentFileTypeId = 0;
                            }
                        }

                        if (operation == "read") {
                            var jsonRequstParam = {
                                "param": param,
                                "busiCode": busiCode.common.IELECFSV_QUERYCFGELEC
                            };
                            return comm.ajax.paramWrap(jsonRequstParam);
                        } else if (data.models) {
                            return JSON.stringify(data.models);
                        }
                    }
                },
                batch: true,
                schema: {
                    data: function (d) {
                        return d.data;
                    },
                    model: {
                        id: "fileTypeId",
                        hasChildren: "hasChildren"
                    }
                }
            },
            change: function (e) {
                var that = this;
                var select = that.getSelect();
                cfgElec = select;
                initViewType();
            }
        });
    }


    var fileInputId;
    if (request.fileInputId && request.fileInputId != "") {
        fileInputId = request.fileInputId;
        $("#fileInputId").val(fileInputId);
    } else {
        comm.ajax.ajaxEntity({
            sync: true,
            busiCode: busiCode.common.IELECFSV_GETNEWFILEINPUTID,
            callback: function (data) {
                if (data) {
                    fileInputId = data;
                    $("#fileInputId").val(fileInputId);
                } else {
                    comm.dialog.notification({
                        title: "错误",
                        type: comm.dialog.type.error,
                        content: "生成文件ID失败!"
                    });
                }
            }
        });
    }


    var getParams = function (data) {
        data = data || {};
        data.fileInputId = fileInputId;
        data.fileTypeId = cfgElec.fileTypeId;
        return data;
    };


    var type = request.viewType || "edit";
    if (type != "view") {
        $("#files").kendoUpload({
            multiple: false,
            async: {
                saveUrl: PROJECT_URL + "/elec/upload"
            }, success: function (e) {
                if (e.response.status == "1") {
                    comm.dialog.notification({
                        title: "错误",
                        type: comm.dialog.type.error,
                        content: "上传失败！" + e.response.message
                    });
                } else {
                    $("#elecGird").datagrid().load();
                    comm.dialog.notification({
                        title: "恭喜",
                        type: comm.dialog.type.success,
                        content: "上传成功！"
                    });
                }
            }, error: function (e) {
                comm.dialog.notification({
                    title: "错误",
                    type: comm.dialog.type.error,
                    content: "上传失败！"
                });
            }, select: function (e) {
                var extension = e.files[0].extension;
                if (cfgElec.fileSuffixes && cfgElec.fileSuffixes != "") {
                    var tem = cfgElec.fileSuffixes + ",";
                    if (tem.indexOf(extension + ",") < 0) {
                        comm.dialog.notification({
                            title: "错误",
                            type: comm.dialog.type.error,
                            content: "文件类型不符合要求！"
                        });
                        e.preventDefault();
                    }
                }

                var size = e.files[0].size;
                if (cfgElec.fileMaxSize && cfgElec.fileMaxSize > 0 && size > cfgElec.fileMaxSize * 1024 * 1024) {
                    comm.dialog.notification({
                        title: "错误",
                        type: comm.dialog.type.error,
                        content: "文件大小超过限制！限制大小为" + cfgElec.fileMaxSize + "M。"
                    });
                    e.preventDefault();
                }
            }, complete: function (e) {
                //上传完成！
            }, upload: function (e) {
                var expireDate = "";
                var effectiveDate = "";
                if (cfgElec.hasExpireDate && cfgElec.hasExpireDate == "1") {
                    expireDate = $("#expireDate").datebox().value();
                    effectiveDate = $("#effectiveDate").datebox().value();
                    if (expireDate == "" || expireDate == null || expireDate == "null" ||
                        effectiveDate == "" || effectiveDate == null || effectiveDate == "null") {
                        comm.dialog.notification({
                            title: "警告",
                            type: comm.dialog.type.warn,
                            content: "生失效时期均不能为空！" + e.response.message
                        });
                        e.preventDefault();
                        return;
                    }
                    expireDate = expireDate.format("yyyy-MM-dd");
                    effectiveDate = effectiveDate.format("yyyy-MM-dd");
                }
                var saveUrl = PROJECT_URL + "/elec/upload?fileTypeId=" + cfgElec.fileTypeId
                    + "&fileInputId=" + fileInputId
                    + "&expireDate=" + expireDate
                    + "&effectiveDate=" + effectiveDate
                    + "&moduleId=1";
                e.sender.options.async.saveUrl = saveUrl;
            }
        });

        $("#files").data("kendoUpload").disable();

    }


    var viewButtonHandle = function () {
        var select = $("#elecGird").datagrid().getSelect();
        var imgTypes = ".jpg,.png,.bmp,.jpeg,";
        if (select.elecInstId) {
            if (imgTypes.indexOf(select.fileSuffix + ",") < 0) {
                comm.dialog.notification({
                    title: "警告",
                    type: comm.dialog.type.warn,
                    content: "目前仅支持查看图片格式的电子资料！"
                });
                return;
            }
            var url = PROJECT_URL + "/elec/download?fileTypeId=" + select.fileTypeId + "&fileSaveName=" + select.fileSaveName + "&fileName=" + encodeURI(encodeURI(select.fileName));
            $.dialog({
                type: "image",
                height: "500px",
                width: "600px",
                animated: false,
                //top : "bottom",
                padding: 20,
                content: url
            });
        } else {
            comm.dialog.notification({
                title: "警告",
                type: comm.dialog.type.warn,
                content: "请选择需要查看的电子资料！"
            });
        }
    };

    var delButtonHandle = function () {
        try {
            var select = $("#elecGird").datagrid().getSelect();
            if (select.elecInstId) {
                $.ajax({
                    url: PROJECT_URL + "/elec/del",
                    data: {
                        fileTypeId: select.fileTypeId,
                        fileSaveName: select.fileSaveName,
                        elecInstId: select.elecInstId
                    },
                    type: "POST",
                    success: function (data) {
                        if (data.status == "1") {
                            comm.dialog.notification({
                                title: "错误",
                                type: comm.dialog.type.error,
                                content: "删除失败！" + data.message
                            });
                        } else {
                            $("#elecGird").datagrid().load();
                            comm.dialog.notification({
                                title: "恭喜",
                                type: comm.dialog.type.success,
                                content: "删除成功！"
                            });
                        }
                    },
                    error: function (data) {
                        comm.dialog.notification({
                            title: "错误",
                            type: comm.dialog.type.error,
                            content: "服务调用异常！"
                        });
                    }
                });
            } else {
                comm.dialog.notification({
                    title: "错误",
                    type: comm.dialog.type.error,
                    content: "请选择需要删除电子资料！"
                });
            }
        } catch (e) {
            comm.dialog.notification({
                title: "错误",
                type: comm.dialog.type.error,
                content: "删除电子资料失败！"
            });
        }
    };

    var downloadButtonHandle = function () {
        try {
            var select = $("#elecGird").datagrid().getSelect();
            if (select.elecInstId) {
                comm.browser.reload(PROJECT_URL + "/elec/download?fileTypeId=" + select.fileTypeId + "&fileSaveName=" + select.fileSaveName + "&fileName=" + encodeURI(encodeURI(select.fileName)));
            } else {
                comm.dialog.notification({
                    title: "警告",
                    type: comm.dialog.type.warn,
                    content: "请选择需要下载的电子资料！"
                });
            }
        } catch (e) {
            comm.dialog.notification({
                title: "错误",
                type: comm.dialog.type.error,
                content: "下载电子资料失败！"
            });
        }
    };


    if (type == "view") {
        $("#toolbar").kendoToolBar({
            items: [
                {type: "button", text: "查看", click: viewButtonHandle},
                {type: "button", text: "下载", click: downloadButtonHandle}
            ]
        });
    } else {
        $("#toolbar").kendoToolBar({
            items: [
                {type: "button", text: "删除", click: delButtonHandle},
                {type: "button", text: "查看", click: viewButtonHandle},
                {type: "button", text: "下载", click: downloadButtonHandle}
            ]
        });
    }


    $.extend(elecModule, {
        getParams: getParams
    });
})(jQuery);
