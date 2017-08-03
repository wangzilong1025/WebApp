/**
 * @author zhangruiping
 * Created on 17/2/16 下午4:19
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */


var bpManageModule = {};
(function ($) {


    var cfgElec;
    var fileInputId;
    var hasSaved = false;


    var getParams = function (data) {

        var startDate = $("#startDate").data("kendoDatePicker").value();
        var endDate = $("#endDate").data("kendoDatePicker").value();

        if (startDate != "" && startDate != null && startDate != "null") {
            startDate = startDate.format("yyyy-MM-dd");
        } else {
            startDate = "";
        }

        if (endDate != "" && endDate != null && endDate != "null") {
            endDate = endDate.format("yyyy-MM-dd");
        } else {
            endDate = "";
        }

        data = data || {};
        data.startDate = startDate;
        data.endDate = endDate;
        data.templateId = $("#fileTemplate2").combobox().value();
        return data;
    };

    var exportErrorData = function () {
        var select = $("#businessProcessGird").datagrid().getSelect();
        if (select.DATA_ID) {

            if (select.STATE != 6 && select.STATE != 7) {
                comm.dialog.notification({
                    title: "警告",
                    type: comm.dialog.type.warn,
                    content: "业务处理操作为完成！请等待业务处理完成后再导出错误数据！"
                });
                return;
            }

            if (select.DATA_ERROR && select.DATA_ERROR > 0) {
                var param = {
                    "busiCode": "IBPIMPORTFSV_EXPORTERRORDATA",
                    "param": {
                        dataId: select.DATA_ID
                    }
                };

                var url = PROJECT_URL + "/export?fileType=10&fileName=" + encodeURI(encodeURI('错误数据.xls')) + "&data=" + comm.ajax.paramWrap(param).data;
                window.location.href = url;
            } else {
                comm.dialog.notification({
                    title: "警告",
                    type: comm.dialog.type.warn,
                    content: "无错误数据，无需导出！"
                });
                return;
            }
        } else {
            comm.dialog.notification({
                title: "警告",
                type: comm.dialog.type.warn,
                content: "请选择具体的批量业务！"
            });
        }
    };


    var initUI = function () {


        $("#tabstrip").kendoTabStrip({});
        var tabStrip = $("#tabstrip").kendoTabStrip().data("kendoTabStrip");
        tabStrip.select(0);


        $("#files").kendoUpload({
            multiple: true,
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
                    if (hasSaved == false) {

                        comm.ajax.ajaxEntity({
                            sync: true,
                            busiCode: "IBPIMPORTFSV_SAVEBPDATA",
                            param: {
                                fileInputId: fileInputId,
                                templateId: $("#fileTemplate").combobox().getSelect().templateId,
                                priority: $("#fileTemplate").combobox().getSelect().priority
                            },
                            callback: function (data) {
                                if (data) {
                                    comm.dialog.notification({
                                        title: "恭喜",
                                        type: comm.dialog.type.success,
                                        content: "上传成功！"
                                    });
                                } else {
                                    comm.dialog.notification({
                                        title: "错误",
                                        type: comm.dialog.type.error,
                                        content: "上传失败，信息保存失败！"
                                    });
                                }
                            }
                        });

                    } else {
                        comm.dialog.notification({
                            title: "恭喜",
                            type: comm.dialog.type.success,
                            content: "上传成功！"
                        });
                    }
                }
            }, error: function (e) {
                comm.dialog.notification({
                    title: "错误",
                    type: comm.dialog.type.error,
                    content: "上传失败！"
                });
            }, select: function (e) {
                for (var j = 0; j < e.files.length; j++) {
                    var extension = e.files[j].extension;
                    if ($("ul[class='k-upload-files k-reset']").find("span[title='" + e.files[j].name + "']").length > 0) {
                        comm.dialog.notification({
                            title: "警告",
                            type: comm.dialog.type.warn,
                            content: "所选文件和刚刚已经上传成功的文件名字相同！请确认！"
                        });
                        e.preventDefault();
                        return;
                    }


                    if (cfgElec.fileSuffixes && cfgElec.fileSuffixes != "") {
                        var tem = cfgElec.fileSuffixes + ",";
                        if (tem.indexOf(extension + ",") < 0) {
                            comm.dialog.notification({
                                title: "错误",
                                type: comm.dialog.type.error,
                                content: "文件类型不符合要求！"
                            });
                            e.preventDefault();
                            break;
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
                }
            }, complete: function (e) {
                //上传完成！
            }, upload: function (e) {
                var expireDate = "";
                var effectiveDate = "";
                var saveUrl = PROJECT_URL + "/elec/upload?fileTypeId=" + cfgElec.fileTypeId
                    + "&fileInputId=" + fileInputId
                    + "&expireDate=" + expireDate
                    + "&effectiveDate=" + effectiveDate
                    + "&moduleId=1";
                e.sender.options.async.saveUrl = saveUrl;
            }
        });
        $("#files").data("kendoUpload").disable();

        $("#toolbar").kendoToolBar({
            items: [
                {type: "button", text: "导出错误数据", click: exportErrorData}
            ]
        });
    };

    var initEnv = function () {
        $("#fileTemplate").combobox().bind('change', function (e) {
            getCfgElec();
        });

        $("#fileTemplate").combobox().bind('dataBound', function (e) {
            getCfgElec();
        });

        $("#queryButton").click(function () {
            $("#businessProcessGird").datagrid().load();
        });
    };


    var getCfgElec = function () {
        getNewFileInputId();
        cfgElec = {};
        $("#files").data("kendoUpload").disable();
        $("ul[class='k-upload-files k-reset']").remove();
        $("#templateDownload").attr("src", "");

        comm.ajax.ajaxEntity({
            sync: true,
            busiCode: "IELECFSV_QUERYCFGELEC",
            param: {
                fileTypeId: $("#fileTemplate").combobox().getSelect().fileTypeId
            },
            callback: function (data) {
                if (data && data.length > 0) {
                    cfgElec = data[0];
                    if (cfgElec && cfgElec.fileTypeId && cfgElec.fileTypeId != "") {
                        $("#files").data("kendoUpload").enable();

                        $("#templateDownload").attr("src", cfgElec.templateUrl);
                    }
                } else {
                    comm.dialog.notification({
                        title: "错误",
                        type: comm.dialog.type.error,
                        content: "获取文件配置信息失败!"
                    });
                }
            }
        });
    };

    var getNewFileInputId = function () {
        fileInputId = "";
        comm.ajax.ajaxEntity({
            sync: true,
            busiCode: "IELECFSV_GETNEWFILEINPUTID",
            callback: function (data) {
                if (data) {
                    fileInputId = data;
                } else {
                    comm.dialog.notification({
                        title: "错误",
                        type: comm.dialog.type.error,
                        content: "生成文件ID失败!"
                    });
                }
            }
        });
    };

    var init = function () {
        getNewFileInputId();

        initUI();
        initEnv();
    };

    init();

    $.extend(bpManageModule, {
        getParams: getParams
    });

})(jQuery);