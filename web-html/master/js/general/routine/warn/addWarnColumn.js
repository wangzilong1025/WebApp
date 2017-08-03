/**
 * @author hyf
 * Created on 2017/3/8 15:23
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

var addWarnColumnModule = {};


// var getStaticParams1 = function (data) {
//     data = data || {};
//     var columnType = $("#warnColumnType").val();
//     if(columnType ==1 ){
//         data.codeType = 'WARN_COLUMN';
//     }else if(columnType ==2){
//         data.codeType = 'WARN_COLUMN_TYPE';
//     }
//     // data.codeType='WARN_COLUMN';
//     return data;
// };


(function ($) {

    var warnTypeId = comm.browser.getParameter("warnTypeId","");
    $("#warnTypeId").val(warnTypeId);
    var getParams = function (data) {
        data = data || {};
        return data;
    };


    $("#addButton").click(function(){
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
        // }
        // else if(comm.lang.isEmpty(datas.warnColumnType)){
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
            "busiCode": busiCode.routine.IWARNCOLUMNDEFINEFSV_ADDWARNCOLUMN,
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
                        content:"告警类型添加失败！"+msg
                    });

                    // comm.dialog.notification({
                    //     content: "告警类型添加失败！"+msg
                    // });
                }
            }
        }
        comm.ajax.ajaxEntity(options);
    });


    // $("#warnColumnType").combobox().bind('change', function (e) {
    //     // $("#warnColumnCode").combobox().param=this.value();
    //     $("#warnColumnCode").combobox().load();//加载
    //     $("#warnColumnCode").combobox().value("");
    //     $("#warnColumnLen").val("");
    // });

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





    $.extend(addWarnColumnModule, {
        getParams: getParams
    });

})(jQuery);