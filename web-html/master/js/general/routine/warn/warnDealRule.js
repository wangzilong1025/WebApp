/**
 * @author hyf
 * Created on 2017/3/8 15:23
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */

var warnDealRuleModule = {};

(function ($) {


    var warnTypeId = comm.browser.getParameter("warnTypeId","");
    $("#warnTypeId").val(warnTypeId);


    var getParams = function (data) {
        data = data || {};
        var warnTypeId = $("#warnTypeId").val();
        data.warnTypeId = warnTypeId;
        return data;
    };


    $("#delButton").click(function(){
        var data = $("#resultGrid").datagrid().getSelects();
        if(data == '' ){
            comm.dialog.notice({
                type:comm.dialog.type.warn,
                position:"center",
                content:"没有选择要删除的规则！"
            });
            // comm.dialog.notification({
            //     content: "没有选择要删除的规则！"
            // });
        }else{
            comm.dialog.confirm({
                title: "删除确认",
                content: "确认删除所选记录吗？",
                okBtnText:"删除",
                cancelBtnText:"取消",
                func:function (isdel) {
                    if(isdel){
                        var options = {
                            "param": {"warnDealRuleInfoId": data[0].warnDealRuleInfoId},
                            "busiCode": busiCode.routine.IWANDEALRULEFSV_DELWARNDEALRULE,
                            "moduleName": "common",
                            "sync": false,
                            "callback": function (data, isSucc, msg) {
                                if (isSucc) {
                                    comm.dialog.notice({
                                        type:comm.dialog.type.success,
                                        position:"center",
                                        content:"删除成功！"
                                    });

                                    // comm.dialog.notification({
                                    //     content: "删除成功！"
                                    // });
                                    $("#resultGrid").datagrid().load();
                                } else {
                                    comm.dialog.notice({
                                        type:comm.dialog.type.error,
                                        position:"center",
                                        content:"删除失败！"
                                    });
                                    // comm.dialog.notification({
                                    //     content: "删除失败！"+msg
                                    // });
                                }
                            }
                        }
                        comm.ajax.ajaxEntity(options);
                    }
                }
            });

        }
    });



    $("#addButton").click(function(){
        comm.dialog.window({
            title:"添加反馈规则",
            url:"/view/general/routine/warn/addOrUpdateWarnDealRule.html?warnTypeId="+$("#warnTypeId").val(),
            width:350,
            height:280,
            isSec:true,
            callback:function () {
                $("#resultGrid").datagrid().load();

                comm.dialog.notice({
                    type:comm.dialog.type.success,
                    position:"center",
                    content:"增加成功！"
                });


                // comm.dialog.notification({
                //     content: "增加成功！"
                // });
            }
        });
    });


    $("#updateButton").click(function() {
        var data = $("#resultGrid").datagrid().getSelects();
        if (data == '' || data.warnColumnId == '') {
            // comm.dialog.notification({
            //     content: "请选择要修改的数据！"
            // });
            comm.dialog.notice({
                type:comm.dialog.type.warn,
                position:"center",
                content:"请选择要修改的数据！"
            });

        } else {
            comm.dialog.window({
                title:"修改反馈规则",
                url:"/view/general/routine/warn/addOrUpdateWarnDealRule.html?warnDealRuleInfoId="+data[0].warnDealRuleInfoId,
                width:350,
                height:280,
                isSec:true,
                callback:function () {
                    $("#resultGrid").datagrid().load();
                    comm.dialog.notice({
                        type:comm.dialog.type.success,
                        position:"center",
                        content:"更新成功！"
                    });
                }
            });
        }
    });


    $.extend(warnDealRuleModule, {
        getParams: getParams
    });

})(jQuery);