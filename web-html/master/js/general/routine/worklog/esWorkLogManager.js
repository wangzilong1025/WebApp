/**
 * Created by liumj on 2017/3/7.
 */

var EsWorkLog = {};
(function ($) {
    var getParams = function (data) {
        data = data || {};
        var logOrigin = $("#LOG_ORIGIN").combobox().value();
        var createDate = $("#CREATE_DATE").val();
        var workDesc = $("#WORK_DESC").val();
        data.logOrigin = logOrigin;
        data.createDate = createDate;
        data.workDesc = workDesc;
        return data;
    };

    $.extend(EsWorkLog, {
        getParams: getParams
    });

    //保存工作日志
   /* $("#save").click(function () {
        var param = {};

        var logTitle = $("#LOG_TITLE").val();
        var logOrigin = $("#LOG_ORIGIN").combobox().value();
        var createDate = $("#CREATE_DATE").val();
        var custTouchFlag = $("#CUST_TOUCH_FLAG").val();
        var workDesc = $("#WORK_DESC").val();
        param.logTitle = logTitle;
        param.logOrigin = logOrigin;
        param.custTouchFlag = custTouchFlag;
        param.createDate = createDate;
        param.workDesc = workDesc;

        if ($("#LOG_TITLE").val() == "") {
            alert("标题不可以为空！");
            return false;
        }
        if ($("#LOG_ORIGIN").combobox().value() == "") {
            alert("日志来源不可以为空！");
            return false;
        }
        if ($("#CUST_TOUCH_FLAG").val() == "") {
            alert("是否接触客户不可以为空！");
            return false;
        }
        if ($("#CREATE_DATE").val() == "") {
            alert("创建时间不可以为空！");
            return false;
        }
        if ($("#WORK_DESC").val() == "") {
            alert("工作描述不可以为空！");
            return false;
        }

        //调用接口
        comm.ajax.ajaxEntity({
            busiCode:"IESWORKLOGFSV_SAVEESWORKLOGINFO",
            param:param,
            mask:false,
            callback:function(data,isSucc){
                if(isSucc) {
                    comm.dialog.unWindow({
                        confirm:true,
                        retVal:1
                    });
                }
            }
        });
    });*/

    $("#save").bind("click",function(){
        if($("#workLogInfoForm").form().validate()) {
            var data = $("#workLogInfoForm").form().getData();
            data["operateType"] = "save";
            comm.ajax.ajaxEntity({
                busiCode: busiCode.routine.IESWORKLOGFSV_SAVEESWORKLOGINFO,
                param: data,
                callback: function (data, isSucc, msg) {
                    if (isSucc) {
                        /*comm.dialog.notification({
                            type: comm.dialog.type.success,
                            content: "工作日志保存成功!",
                            func:function(){
                                top.indexModel.closeTabByTitle("集团成员维护");
                            }
                        });*/
                        comm.dialog.unWindow({
                            confirm:true,
                            retVal:1
                        });
                    } else {
                        /*comm.dialog.notification({
                            type: comm.dialog.type.error,
                            content: "集团成员信息保存失败!" + msg
                        });*/
                        comm.dialog.unWindow({
                            confirm:false,
                            retVal:1
                        });
                    }
                }
            });
        }
    });


    //详细信息展示
    var showLogDetail = function (logId) {
        var param = {};
        param.logId = logId;
        param.page = 1;
        param.pageSize = 1;
        //调用接口
        comm.ajax.ajaxEntity({
            busiCode:"IESWORKLOGFSV_QUERYESWORKLOGINFO",
            param:param,
            mask:false,
            callback:function(data,isSucc){
                if(isSucc) {
                    $("#LOG_TITLE").val(data.data[0]["logTitle"]);
                    $("#CUST_TOUCH_FLAG").combobox().value(data.data[0]["custTouchFlag"]);
                    $("#WORK_DESC").val(data.data[0]["workDesc"]);
                    $("#CREATE_DATE").val(data.data[0]["createDate"]);
                    $("#LOG_ORIGIN").combobox().value(data.data[0]["logOrigin"]);

                    //置灰
                    $(":input").attr("disabled", true);
                }
            }
        });
    };

    var init = function () {
        //判断查看工作日志详情
        var operType = comm.tools.getRequest().operType;
        var logId = comm.tools.getRequest().logId;
        if(operType == "query"){
            showLogDetail(logId);
        }
    };
    init();

})(jQuery);