/**
 * Created by 王子龙 on 2017-03-09.
 */
//var pageParams = {};
//初始化页面的时候调用
function init(){
    $("#content").kendoEditor();
};
init();

/*var initBulletinButtonVisible = function(){
    var bulletinId = comm.tools.getRequest().bulletinId;
    var options = {
        "param":{"bulletinId":bulletinId,"page":0,"pageSize":1},
        "busiCode":busiCode.routine.IENBULLETININFOFSV_QUERYFORNOTICEBYID,
        "callback": function(data, isSucc, msg){
            if(isSucc){
                $("#bulletinId").val(data.data[0].bulletinId);
                $("#subject").val(data.data[0].subject);
                $("#content").data("kendoEditor").value(data.data[0].content);
                $("#bulletinStartDate").val(data.data[0].bulletinStartDate);
                $("#bulletinEndDate").val(data.data[0].bulletinEndDate);
                $("#bulletinType").combobox().value(data.data[0].bulletinType);
                $("#extent").combobox().value(data.data[0].extent);
                $("#annexName").val(data.data[0].annexName);
            }
        }
    }
    comm.ajax.ajaxEntity(options);
};
initBulletinButtonVisible();*/

var noticeInfo =function(){
    var bulletinId = comm.tools.getRequest().bulletinId;
    if(bulletinId!=null && bulletinId>0){
        //加载公告信息
        comm.ajax.ajaxEntity({
            busiCode:busiCode.routine.IENBULLETININFOFSV_QUERYFORNOTICEBYID,
            param:{
                bulletinId:bulletinId,
                page:0,
                pageSize:1
            },
            callback:function(data,isSucc,msg){
                if(isSucc){
                    $("#updateNoticeForm").form().setData(data.data[0]);
                    $("#content").data("kendoEditor").value(data.data[0].content);
                }
            }
        });
    }
    $("#updateNoticeForm").form().blurValidate();
};
noticeInfo();

(function ($) {
   /* $("#updateNotice").click(function(){
        var bulletinId =$("#bulletinId").val();
        var subject = $("#subject").val();
        var content = encodeURIComponent(encodeURIComponent($("#content").data("kendoEditor").value()));
        var bulletinStartDate = $("#bulletinStartDate").val();
        var bulletinEndDate = $("#bulletinEndDate").val();
        var extent = $("#extent").val();
        var bulletinType = $("#bulletinType").val();
        var partyRoleId = $("#partyRoleId").val();
        var options = {
            "param":{"bulletinId":bulletinId,"subject":subject, "content":content, "bulletinStartDate":bulletinStartDate,"bulletinEndDate":bulletinEndDate, "extent":extent, "bulletinType":bulletinType,"partyRoleId":partyRoleId},
            "busiCode":busiCode.routine.IENBULLETININFOFSV_UPDATENOTICE,
            "moduleName": "common",
            "sync":false,
            "callback": function(data, isSucc, msg){
                if(isSucc){
                   /!* comm.dialog.notification({
                        content:"公告更新成功!!!",
                    });*!/
                    comm.dialog.unWindow({
                        "confirm":true
                    });
                }else{
                    comm.dialog.notice({
                        type:comm.dialog.type.error,
                        position:"center",
                        content:"公告更新失败!"
                    });
                    /!*comm.dialog.notification({
                        content:"公告更新失败!!!"
                    });*!/
                }
            }
        }
        comm.ajax.ajaxEntity(options);
    });*/

    $("#updateNotice").bind("click",function(){
        if($("#updateNoticeForm").form().validate()) {
            var data = $("#updateNoticeForm").form().getData();
            var content = encodeURIComponent(encodeURIComponent($("#content").data("kendoEditor").value()));
            data.content=content;
            data["operateType"] = "save";
            comm.ajax.ajaxEntity({
                busiCode: busiCode.routine.IENBULLETININFOFSV_UPDATENOTICE,
                param: data,
                callback: function (data, isSucc, msg) {
                    if (isSucc) {
                        /*comm.dialog.notification({
                            type: comm.dialog.type.success,
                            content: "公告信息更新成功!",
                            func:function(){
                                top.indexModel.closeTabByTitle("集团成员维护");
                            }
                        });*/
                        comm.dialog.unWindow({
                            "confirm":true
                        });
                    } else {
                        comm.dialog.notification({
                            type: comm.dialog.type.error,
                            content: "公告信息保存失败!" + msg
                        });
                    }
                }
            });
        }
    });

    $("#annex").bind("click", function () {
        comm.dialog.window({
            title: "附件上传",
            url: "/view/general/common/elec/elec.html?fileTypeId=9&fileInputId=" + $("#annex").val(),
            width: 650,
            height: 400,
            isSec: true,
            dataReturn: "fileInputId",
            callback: function (data) {
                $("#annex").val(data);
            }
        });
    });

})(jQuery);

//取消按钮
$("#cancelSaveNotice").click(function(){
    comm.dialog.unWindow({
        "confirm":false
        });
});