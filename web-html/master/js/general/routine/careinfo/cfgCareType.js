/**
 * Created by liumj on 2017/3/7.
 */

var EsWorkLog = {};
(function ($) {
    var getParams = function (data) {
        data = data || {};
        var careName = $("#CARE_NAME").val();
        var careType = "";
        var careClass = "";
        try{
            careType = $("#CARE_TYPE").tilecombobox().value();
            careClass = $("#CARE_CLASS").tilecombobox().value();
        }catch(e){

        }
        data.careName = careName;
        data.careType = careType;
        data.careClass = careClass;
        return data;
    };

    $.extend(EsWorkLog, {
        getParams: getParams
    });

    //查询客户关怀
    $("#QUERY").click(function () {
        $("#resultGrid").datagrid().load();
    });

    //新增客户关怀
    $("#addValue").bind("click", function () {
        comm.dialog.window({
            title: "新增客户关怀",
            url: "/view/general/routine/careinfo/cfgCareTypeManage.html?operType=add",
            width: 800,
            height: 450,
            isSec: true,
            callback: function (data) {
                if (data) {
                    var msgcontent = "添加成功!";
                    comm.dialog.notice({
                        type: comm.dialog.type.success,
                        content: msgcontent,
                        position: 'center'
                    });
                    $("#resultGrid").datagrid().load();
                }
            }
        });
    });

    //变更客户关怀
    $("#updateValue").bind("click", function () {
        var select = $("#resultGrid").datagrid().getSelects();
        if (select[0] == null) {
            alert("请选择要变更的客户关怀信息！");
            return;
        }
        comm.dialog.window({
            title: "变更客户关怀",
            url: "/view/general/routine/careinfo/cfgCareTypeManage.html?operType=update" + "&careId=" + select[0].careId,
            width: 800,
            height: 450,
            isSec: true,
            callback: function (data) {
                if (data) {
                    var msgcontent = "变更成功!";
                    comm.dialog.notice({
                        type: comm.dialog.type.success,
                        content: msgcontent,
                        position: 'center'
                    });
                    $("#resultGrid").datagrid().load();
                }
            }
        });
    });

    //删除客户关怀
    $("#delValue").bind("click", function () {
        var select = $("#resultGrid").datagrid().getSelects();
        if (select[0] == null) {
            alert("请选择要删除的客户关怀信息！");
            return;
        }
        comm.dialog.confirm({
            content: "你确定要删除这条客户关怀细信息？",
            title: "删除客户关怀",
            func: function (flag) {
                if (flag == true) {
                    var param = {};
                    param.careId = select[0].careId;
                    param.page = 1;
                    param.pageSize = 1;

                    //调用接口
                    comm.ajax.ajaxEntity({
                        busiCode: "ICFGCARETYPEFSV_DELCFGCARETYPEINFO",
                        param: param,
                        mask: false,
                        callback: function (data, isSucc) {
                            if (isSucc) {
                                var msgcontent = "删除成功!";
                                comm.dialog.notice({
                                    type: comm.dialog.type.success,
                                    content: msgcontent,
                                    position: 'center'
                                });
                                $("#resultGrid").datagrid().load();
                            }
                        }
                    });
                }
            }
        })
    });

    //详细信息展示
    var showLogDetail = function (select) {
        comm.dialog.window({
            title: "客户关怀明细",
            url: "/view/general/routine/careinfo/cfgCareTypeManage.html?operType=query" + "&careId=" + select.careId,
            width: 800,
            height: 450,
            isSec: true,
            callback: function (data) {

            }
        });
    };

    //双击事件
    $("#resultGrid").datagrid().dbclick(function () {
        var select = $("#resultGrid").datagrid().getSelects();
        showLogDetail(select[0]);
    });

    //查询客户关怀
    $("#queryById").click(function () {
        var select = $("#resultGrid").datagrid().getSelects();
        if (select.length > 0) {
            showLogDetail(select[0]);
        }
    });

})(jQuery);