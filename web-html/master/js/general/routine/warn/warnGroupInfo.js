/**
 * @author houlf3
 * Created on 2017/3/7 14:51
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
var warnModule = {};

var getStaticParams = function (data) {
    data = data || {};
    data.warnLevel=0;
    return data;
};

var getParamsForWarn = function (data) {
    data = data || {};
    data.parentWarnType = $("#warnTypeId").val();
    return data;
};

var getParams = function (data) {
    data = data || {};
    var parWarnId = $("#warnTypeId").val();//告警类型ID
    var warnTypeId = $("#warnTypeBId").val();//告警子类
    var groupId = $("#groupId").val();//集团编号
    var partyName = $("#partyName").val();//集团名称
    var state = $("#state").val();//状态
    var startDate = $("#startDate").val();//告警开始时间
    var endDate = $("#endDate").val();//告警结束时间

    data.parWarnId = parWarnId;
    data.warnTypeId = warnTypeId;
    data.groupId = groupId;
    data.partyName = partyName;
    data.state = state;
    data.startDate = startDate;
    data.endDate = endDate;
    return data;
};


(function ($) {

    var warnTypeId = comm.browser.getParameter("warnTypeId","");
    var parTypeId = comm.browser.getParameter("parTypeId","");
    if(warnTypeId !='' && warnTypeId != undefined && parTypeId != "" && warnTypeId != undefined){
        $("#warnTypeId").combobox().load();//加载
        $("#warnTypeId").combobox().value(parTypeId);
        $("#warnTypeBId").combobox().load();//加载
        $("#warnTypeBId").combobox().value(warnTypeId);
        $("#resultGrid").datagrid().load();
    }


    $("#queryButton").click(function () {
        var state = $("#state").val();//状态
        var startDate = $("#startDate").val().substr(0,7);//告警开始时间
        var endDate = $("#endDate").val().substr(0,7);;//告警结束时间
        if(state ==0 && startDate ==""){
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"请选择告警起始日期！"
            });
            return;
        }
        if(state ==0 && endDate == ""){
            comm.dialog.notice({
                type:comm.dialog.type.warn,
                position:"center",
                content:"此次请求只查询选择日期到月末的数据！"
            });
        }else{
            if(state ==0 && startDate != endDate){
                comm.dialog.notice({
                    type:comm.dialog.type.error,
                    position:"center",
                    content:"处理完成数据查询时不支持跨月查询！"
                });
                return;
            }
        }

        $("#resultGrid").datagrid().load();
    });


    $("#resultGrid").datagrid().dbclick(function () {
        var data = $("#resultGrid").datagrid().getSelect();

        comm.dialog.window({
            title:"告警详情",
            url:"/view/general/routine/warn/warnGroupDtl.html?warnId="+data.warnId+"&warnTypeId="+data.warnTypeId+"&state="+data.state,
            width:800,
            height:450,
            isSec:true,
            callback:function (e) {
                $("#resultGrid").datagrid().load();

                comm.dialog.notice({
                    type:comm.dialog.type.success,
                    position:"center",
                    content:"处理成功！"
                });
            }
        });
    });


    $("#warnTypeId").combobox().bind('change', function (e) {
        $("#warnTypeBId").combobox().value("");
        $("#warnTypeBId").combobox().load();//加载
    });




    $.extend(warnModule, {
        getParams: getParams
    });


})(jQuery);