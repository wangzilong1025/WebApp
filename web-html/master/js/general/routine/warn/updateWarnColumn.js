/**
 * @author hyf
 * Created on 2017/3/8 15:23
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

var updateWarnColumnModule = {};



(function ($) {


    var warnColumnId = comm.browser.getParameter("warnColumnId","");
    var options = {
        "param": {"warnColumnId": warnColumnId},
        "busiCode": busiCode.routine.IWARNCOLUMNDEFINEFSV_GETWARNCOLUMNDEFINE,
        "moduleName": "common",
        "sync": false,
        "callback": function (data, isSucc, msg) {
            if (!isSucc) {
                comm.dialog.notification({
                    content: "数据加载失败！"+msg
                });
            } else {
                $("#warnColumnForm").form().setData(data.data[0]);
            }
        }
    }
    comm.ajax.ajaxEntity(options);



    // var getParams = function (data) {
    //     data = data || {};
    //     var warnTypeId = $("#warnTypeId").val();
    //     // var email = $("#email").val();
    //     // data.name = name;
    //     // data.email= email;
    //     data.warnTypeId = warnTypeId;
    //     return data;
    // };

    // $("#warnColumnType").combobox().bind('change', function (e) {
    //     // $("#warnColumnCode").combobox().param=this.value();
    //     $("#warnColumnCode").combobox().load();//加载
    //     $("#warnColumnCode").combobox().value("");
    //     $("#warnColumnLen").val("");
    // });


    $("#updateButton").click(function(){
        var datas = $("#warnColumnForm").form().getData();


        if(comm.lang.isEmpty(datas.warnColumnName)){
            comm.validate.validateError($("#warnColumnName"));
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"告警字段名称名称不能为空！"
            });
            return;
        }
        // else if(comm.lang.isEmpty(datas.warnColumnAlias)){
        //     comm.validate.validateError($("#warnColumnAlias"));
        //     comm.dialog.notice({
        //         type:comm.dialog.type.error,
        //         position:"center",
        //         content:"告警字段英文名！"
        //     });
        //     return;
        // }else if(comm.lang.isEmpty(datas.warnColumnType)){
        //     comm.validate.validateError($("#warnColumnType"));
        //     comm.dialog.notice({
        //         type:comm.dialog.type.error,
        //         position:"center",
        //         content:"请选择告警字段类型！"
        //     });
        //     return;
        // }
        else if(comm.lang.isEmpty(datas.warnColumnCode)){
            comm.validate.validateError($("#warnColumnCode"));
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"请选择告警字段编码！"
            });
            return;
        }



        var options = {
            "param": datas,
            "busiCode": busiCode.routine.IWARNCOLUMNDEFINEFSV_UPDATEWARNCOLUMN,
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
                        content:"告警字段修改失败！"+msg
                    });

                    // comm.dialog.notification({
                    //     content: "告警字段修改失败！"+msg
                    // });
                }
            }
        }
        comm.ajax.ajaxEntity(options);
    });

    // $("#warnColumnCode").combobox().bind('change', function (e) {
    //     comm.tools.getStaticData({
    //         codeType: 'WARN_COLUMN',
    //         codeValue: this.value(),
    //         sync:true,
    //         callback: function (data) {
    //             if (data != null && data.length > 0) {
    //                 $("#warnColumnLen").val(data[0]["extendCode2"]);
    //             }
    //         }
    //     });
    // });



    $.extend(updateWarnColumnModule, {
        getParams: getParams
    });

})(jQuery);