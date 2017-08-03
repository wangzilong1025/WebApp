/**
 * @author hyf
 * Created on 2017/3/8 15:23
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

var addWarnDealRuleModule = {};

// var getStaticParams = function (data) {
//     data = data || {};
//
//     data.codeType='WARN_ROLR_TYPE';
//     return data;
// };
// var getRuleObject = function (data) {
//     data = data || {};
//
//     data.codeType='WARN_RULEOBJECT';
//     return data;
// };
//
// var getDetailObject = function (data) {
//     data = data || {};
//
//     data.codeType='WARN_DETAILOBJECT';
//     return data;
// };

(function ($) {

    var warnDealRuleInfoId = comm.browser.getParameter("warnDealRuleInfoId","");
    if(!comm.lang.isEmpty(warnDealRuleInfoId)){
        var options = {
            "param": {"warnDealRuleInfoId": warnDealRuleInfoId},
            "busiCode": busiCode.routine.IWANDEALRULEFSV_GETWARNDEALRULE,
            "moduleName": "common",
            "sync": false,
            "callback": function (data, isSucc, msg) {
                if (!isSucc) {
                    comm.dialog.notification({
                        content: "数据加载失败！"+msg
                    });
                } else {
                    $("#warnDealRuleForm").form().setData(data.data[0]);
                }
            }
        }
        comm.ajax.ajaxEntity(options);
    }else{
        var warnTypeId = comm.browser.getParameter("warnTypeId","");
        $("#warnTypeId").val(warnTypeId);
    }


    var getParams = function (data) {
        data = data || {};
        return data;
    };


    $("#addButton").click(function(){
        var datas = $("#warnDealRuleForm").form().getData();
        // if(comm.lang.isEmpty(datas.warnDealRuleType)){
        //     comm.validate.validateError($("#warnDealRuleType"));
        //     comm.dialog.notice({
        //         type:comm.dialog.type.error,
        //         position:"center",
        //         content:"规则类型不能为空！"
        //     });
        //     return;
        // }else
            if(comm.lang.isEmpty(datas.warnDealRuleName)){
            comm.validate.validateError($("#warnDealRuleName"));
            comm.dialog.notice({
                type:comm.dialog.type.error,
                position:"center",
                content:"规则名称不能为空！"
            });
            return;
        }
        // else if(comm.lang.isEmpty(datas.warnDealRuleObject)){
        //     comm.validate.validateError($("#warnDealRuleObject"));
        //     comm.dialog.notice({
        //         type:comm.dialog.type.error,
        //         position:"center",
        //         content:"请选择规则对象！"
        //     });
        //     return;
        // }else if(comm.lang.isEmpty(datas.warnDealRuleParam)){
        //     comm.validate.validateError($("#warnDealRuleParam"));
        //     comm.dialog.notice({
        //         type:comm.dialog.type.error,
        //         position:"center",
        //         content:"请选择规则对象参数！"
        //     });
        //     return;
        // }
        var bsCode = busiCode.routine.IWANDEALRULEFSV_ADDWARNDEALRULE;
        if(!comm.lang.isEmpty(warnDealRuleInfoId)){
            bsCode=busiCode.routine.IWANDEALRULEFSV_UPDATEWARNDEALRULE;
        }
        var options = {
            "param": datas,
            "busiCode": bsCode,
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
                }
            }
        }
        comm.ajax.ajaxEntity(options);
    });


    // $("#warnColumnType").combobox().bind('change', function (e) {
    //     // $("#warnColumnCode").combobox().param=this.value();
    //     $("#warnColumnCode").combobox().load();//加载
    //     $("#warnColumnCode").combobox().value("")
    // });


    $.extend(addWarnDealRuleModule, {
        getParams: getParams
    });

})(jQuery);