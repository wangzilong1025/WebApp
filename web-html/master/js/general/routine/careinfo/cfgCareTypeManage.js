/**
 * Created by liumj on 2017/3/7.
 */

(function ($) {
    //保存客户关怀
    $("#save").click(function () {
        /*var param = {};

        var careName = $("#CARE_NAME").val();
        var careType = $("#CARE_TYPE").combobox().value();
        var careClass = $("#CARE_CLASS").combobox().value();
        var createDate = $("#CREATE_DATE").val();
        var careWay = $("#CARE_WAY").combobox().value();
        var careValue1 = $("#CARE_VALUE1").val();
        var careDay = $("#CARE_DAY").val();
        param.careName = careName;
        param.careType = careType;
        param.careClass = careClass;
        param.createDate = createDate;
        param.careWay = careWay;
        param.careValue1 = careValue1;
        param.careDay = careDay;

        if ($("#CARE_NAME").val() == "") {
            alert("标题不可以为空！");
            return false;
        }
        if ($("#CARE_TYPE").combobox().value() == "") {
            alert("关怀类型不可以为空！");
            return false;
        }
        if ($("#CARE_CLASS").combobox().value() == "") {
            alert("关怀分类不可以为空！");
            return false;
        }

        if($("#CARE_CLASS").combobox().value() == 2){
            if ($("#CARE_VALUE1").combobox().value() == "") {
                alert("关怀值不可以为空！");
                return false;
            }
        }

        if ($("#CREATE_DATE").val() == "") {
            alert("创建时间不可以为空！");
            return false;
        }
        if ($("#CARE_WAY").combobox().value() == "") {
            alert("关怀方式不可以为空！");
            return false;
        }
        if ($("#CARE_DAY").val() == "") {
            alert("提前日期不可以为空！");
            return false;
        }*/
        if($("#caerInfoForm").form().validate()) {
            var data = $("#caerInfoForm").form().getData();
            data["operateType"] = "save";
            var operType = comm.tools.getRequest().operType;
            if (operType == "update") {
                var careId = comm.tools.getRequest().careId;
                data.careId = careId;
                //调用接口
                comm.ajax.ajaxEntity({
                    busiCode: busiCode.routine.ICFGCARETYPEFSV_UPDATECFGCARETYPEINFO,
                    param: data,
                    mask: false,
                    callback: function (data, isSucc, msg) {
                        if (isSucc) {
                            comm.dialog.unWindow({
                                confirm: true,
                                retVal: 1
                            });
                        }else{

                        }
                    }
                });
            } else {
                //调用接口
                comm.ajax.ajaxEntity({
                    busiCode: busiCode.routine.ICFGCARETYPEFSV_SAVECFGCARETYPEINFO,
                    param: data,
                    mask: false,
                    callback: function (data, isSucc, msg) {
                        if (isSucc) {
                            comm.dialog.unWindow({
                                confirm: true,
                                retVal: 1
                            });
                        }else{

                        }
                    }
                });
            }
        }
    });

   //关怀信息的保存form方式
   /* $("#saveCustContInfo").bind("click",function(){
        if($("#contCustMemberForm").form().validate()) {
            var data = $("#contCustMemberForm").form().getData();
            data["operateType"] = "save";
            if(operType == "update") {
                var careId = comm.tools.getRequest().careId;
                param.careId = careId;
                comm.ajax.ajaxEntity({
                    busiCode: busiCode.custMgr.IGROUPMEMBERINFOFSV_ADDMEMBERINFO,
                    param: data,
                    callback: function (data, isSucc, msg) {
                        if (isSucc) {
                            comm.dialog.notification({
                                type: comm.dialog.type.success,
                                content: "集团成员信息保存成功!",
                                func: function () {
                                    top.indexModel.closeTabByTitle("集团成员维护");
                                }
                            });
                        } else {
                            comm.dialog.notification({
                                type: comm.dialog.type.error,
                                content: "集团成员信息保存失败!" + msg
                            });
                        }
                    }
                });
            }
        }
    });*/




    //详细信息展示
    var showLogDetail = function (careId) {
        var param = {};
        param.careId = careId;
        param.page = 1;
        param.pageSize = 1;
        //调用接口
        comm.ajax.ajaxEntity({
            busiCode:busiCode.routine.ICFGCARETYPEFSV_QUERYCFGCARETYPEINFO,
            param:param,
            mask:false,
            callback:function(data,isSucc){
                if(isSucc) {
                   /* $("#CARE_NAME").val(data.data[0]["careName"]);
                    $("#CARE_TYPE").combobox().value(data.data[0]["careType"]);
                    $("#CARE_CLASS").combobox().value(data.data[0]["careClass"]);
                    $("#CREATE_DATE").val(data.data[0]["createDate"]);
                    $("#CARE_WAY").combobox().value(data.data[0]["careWay"]);
                    $("#CARE_VALUE1").val(data.data[0]["careValue1"]);
                    $("#CARE_VALUE2").val(data.data[0]["careValue2"]);
                    $("#CARE_DAY").val(data.data[0]["careDay"]);
*/
                    //置灰
                    //$(":input").attr("disabled", true);
                    $("#caerInfoForm").form().setFormDisabled(true);
                    $("#caerInfoForm").form().setData(data.data[0]);
                }
            }
        });
    };

    //变更关怀信息
    var updateLogDetail = function (careId) {
        var param = {};
        param.careId = careId;
        param.page = 1;
        param.pageSize = 1;
        //调用接口
        comm.ajax.ajaxEntity({
            busiCode:busiCode.routine.ICFGCARETYPEFSV_QUERYCFGCARETYPEINFO,
            param:param,
            mask:false,
            callback:function(data,isSucc){
                if(isSucc) {
                   /* $("#CARE_NAME").val(data.data[0]["careName"]);
                    $("#CARE_TYPE").combobox().value(data.data[0]["careType"]);
                    $("#CARE_CLASS").combobox().value(data.data[0]["careClass"]);
                    $("#CREATE_DATE").val(data.data[0]["createDate"]);
                    $("#CARE_WAY").combobox().value(data.data[0]["careWay"]);
                    $("#CARE_VALUE1").val(data.data[0]["careValue1"]);
                    $("#CARE_VALUE2").val(data.data[0]["careValue2"]);
                    $("#CARE_DAY").val(data.data[0]["careDay"]);*/
                    $("#caerInfoForm").form().setData(data.data[0]);
                }
            }
        });
    };

    var init = function () {
        //判断查看客户关怀详情
        var operType = comm.tools.getRequest().operType;
        var careId = comm.tools.getRequest().careId;
        if(operType == "query"){
            showLogDetail(careId);
        }else if(operType == "update"){
            updateLogDetail(careId);
        }
    };
    init();

    // $("#CARE_CLASS").blur( function () {
    //     alert($("#CARE_CLASS").combobox().value());
    // } );

    $("#careClass").change( function() {
        if ($("#careClass").combobox().value() == 1){
            $("#careValue").hide();
        }else {
            $("#careValue").show();
        }
    })

})(jQuery);