/**
 * Created by 王子龙 on 2017-03-07.
 */
(function ($) {
    //初始化页面的时候调用
    function initUI(){
        $("#content").kendoEditor();
    };
    function initEvent(){
        //添加公告方法
       /* $("#saveNotice").click(function(){
            var subject = $("#subject").val();
            var content = encodeURIComponent(encodeURIComponent($("#content").data("kendoEditor").value()));
            var bulletinStartDate = $("#bulletinStartDate").val();
            var bulletinEndDate = $("#bulletinEndDate").val();
            var extent = $("#extent").val();
            var bulletinType = $("#bulletinType").val();
            var partyRoleId = $("#partyRoleId").val();
            var options = {
                "param": {
                    "subject": subject,
                    "content": content,
                    "bulletinStartDate": bulletinStartDate,
                    "bulletinEndDate": bulletinEndDate,
                    "extent": extent,
                    "bulletinType": bulletinType,
                    "partyRoleId": partyRoleId,
                    "annex":  $("#annexName").val()
                },
                "busiCode":busiCode.routine.IENBULLETININFOFSV_ADDNOTICEINFO,
                "moduleName": "common",
                "sync":false,
                "callback": function(data, isSucc, msg){
                    if(isSucc){
                            comm.dialog.notification({
                                content:"公告添加成功!!!",
                                func:function(){
                                    top.indexModel.closeTabByTitle("公告添加");
                                }
                            });
                    }else{
                        comm.dialog.notification({
                            content:"公告添加失败!!!"
                        });
                    }
                }
            }
            comm.ajax.ajaxEntity(options);
        });*/
        //form类型的公告保存方法
        $("#saveNotice").bind("click",function(){
            if($("#noticeForm").form().validate()) {
                var data = $("#noticeForm").form().getData();
                var content = encodeURIComponent(encodeURIComponent($("#content").data("kendoEditor").value()));
                data.content=content;
                data["operateType"] = "save";
                comm.ajax.ajaxEntity({
                    busiCode: busiCode.routine.IENBULLETININFOFSV_ADDNOTICEINFO,
                    param: data,
                    callback: function (data, isSucc, msg) {
                        if (isSucc) {
                            comm.dialog.notification({
                                type: comm.dialog.type.success,
                                content: "公告信息保存成功!",
                                func:function(){
                                    top.indexModel.closeTabByTitle("公告添加");
                                }
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

    }
    function init(){
        initUI();
        initEvent();
    }
    init();
})(jQuery);
