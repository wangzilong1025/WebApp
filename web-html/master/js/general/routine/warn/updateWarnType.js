/**
 * @author hyf
 * Created on 2017/3/8 15:23
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

var secModule = {};

var getStaticParams = function (data) {
    data = data || {};
    data.warnLevel=0
    return data;
};

(function ($) {


    var warnTypeId = comm.browser.getParameter("warnTypeId","");
    $("#warnTypeId").val(warnTypeId);
    var options = {
        "param": {"warnTypeId": warnTypeId},
        "busiCode": busiCode.routine.IWARNTYPEDEFINEFSV_GETWARNTYPEDEFINE,
        "moduleName": "common",
        "sync": false,
        "callback": function (data, isSucc, msg) {
            if (!isSucc) {
                comm.dialog.notification({
                    content: "数据加载失败！"+msg
                });
            } else {
                $("#warnTypeForm").form().setData(data.data[0]);
                if(data.data[0].warnLevel == 1){
                    $("#parentWarnType").combobox().enable(false);
                }
            }
        }
    }
    comm.ajax.ajaxEntity(options);



    var getParams = function (data) {
        data = data || {};
        var warnTypeId = $("#warnTypeId").val();
        // var email = $("#email").val();
        // data.name = name;
        // data.email= email;
        data.warnTypeId = warnTypeId;
        return data;
    };


    $("#updateButton").click(function(){
        var datas = $("#warnTypeForm").form().getData();

        if(comm.lang.isEmpty(datas.warnTypeName)){
            comm.validate.validateError($("#warnTypeName"));
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"告警类型名称不能为空！"
            });
            return;
        }else if(comm.lang.isEmpty(datas.warnLevel)){
            comm.validate.validateError($("#warnLevel"));
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"请选择告警等级！"
            });
            return;
        }else if(datas.warnLevel ==1 && comm.lang.isEmpty(datas.parentWarnType)){
            comm.validate.validateError($("#parentWarnType"));
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"请选择父告警等级！"
            });
            return;
        }else if(comm.lang.isEmpty(datas.warnClass)){
            comm.validate.validateError($("#warnClass"));
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"请选择告警分类！"
            });
            return;
        }else if(comm.lang.isEmpty(datas.deadline) || (!comm.validate.checkReg(comm.validate.regexp.intege1,datas.deadline))){
            comm.validate.validateError($("#deadline"));
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"告警期限只能为整数天！"
            });
            return;
        }else if(comm.lang.isEmpty(datas.delayTime) || (!comm.validate.checkReg(comm.validate.regexp.intege1,datas.delayTime))){
            comm.validate.validateError($("#delayTime"));
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"延长时间只能为整数天！"
            });
            return;
        }else if(comm.lang.isEmpty(datas.needDeal)){
            comm.validate.validateError($("#needDeal"));
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"请选择是否需处理！"
            });
            return;
        }

        var options = {
            "param": datas,
            "busiCode": busiCode.routine.IWARNTYPEDEFINEFSV_UPDATEWARNTYPE,
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
                        content:"告警类型修改失败！"
                    });
                    // comm.dialog.notification({
                    //     content: "告警类型修改失败！"+msg
                    // });
                }
            }
        }
        comm.ajax.ajaxEntity(options);
    });

    $("#warnLevel").combobox().bind('change', function (e) {
        var value = this.value();
        if(value == 1){
            $("#parentWarnType").combobox().enable();
        }else{
            $("#parentWarnType").combobox().enable(false);
            $("#parentWarnType").combobox().value("");
        }
    });



    $.extend(secModule, {
        getParams: getParams
    });

})(jQuery);