/**
 * @author hyf
 * Created on 2017/3/7 14:51
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */






(function ($) {
    var warnId = comm.browser.getParameter("warnId","");
    var warnTypeId = comm.browser.getParameter("warnTypeId","");
    var state = comm.browser.getParameter("state","");
    var boolState=false;
    var html_v = "";
    var options = {
        "param": {"warnId": warnId},
        "busiCode": busiCode.routine.IWARNCOLUMNVALUEFSV_GETWARNDEALBYWARNID,
        "moduleName": "common",
        "sync": false,
        "callback": function (data, isSucc, msg) {
            if (!isSucc) {
                comm.dialog.notification({
                    content: "数据加载失败！"+msg
                });
            } else {
                var detailData = data.data;
                if(detailData.length==0){
                    comm.dialog.notice({
                        type:comm.dialog.type.warn,
                        position:"center",
                        content:"此告警没有告警详情！"
                    });
                }
                var options2 = {
                    "param": {"warnTypeId": warnTypeId,"warnId":warnId},
                    "busiCode": busiCode.routine.IWANDEALRULEFSV_GETWARNDEALEXT,
                    "moduleName": "common",
                    "sync": false,
                    "callback": function (data, isSucc, msg) {
                        if (!isSucc) {
                            comm.dialog.notification({
                                content: "数据加载失败！"+msg
                            });
                        } else {
                            html_v+="<div class='box-header'><h4>告警反馈</h4></div><div class='box-content clearfix'><div class='row'>";
                            var dealData = data.data;
                            if(dealData.length>0){
                                boolState=true;
                            }

                            var template = kendo.template($("#warnDtlPortlet").html());
                            var portlet = $(template({
                                detailData:detailData,
                                dealData:dealData,
                                state:state
                            }));


                            $("#warnShow").html(portlet);

                        }
                    }
                };
                comm.ajax.ajaxEntity(options2);
            }
        }
    };
    comm.ajax.ajaxEntity(options);
    $("#okButton").click(function(){
        if(state != 1){
            comm.dialog.unWindow({
                "confirm":false
            });
            return;
        }
        var datas={};
        if(boolState){
            datas = $("#warnDealDetailForm").form().getData();
        }
        datas.warnId=warnId;
        datas.warnTypeId = warnTypeId;
        var options = {
            "param": datas,
            "busiCode": busiCode.routine.IWARNDEALDETAILFSV_ADDDEALDETAIL,
            "moduleName": "common",
            "sync": false,
            "callback": function (data, isSucc, msg) {
                if (isSucc) {
                    comm.dialog.unWindow({
                        "confirm":true
                    });
                } else {
                    comm.dialog.notice({
                        type:comm.dialog.type.error,
                        position:"center",
                        content:"处理失败！"+msg
                    });
                }
            }
        }
        comm.ajax.ajaxEntity(options);
    });

})(jQuery);