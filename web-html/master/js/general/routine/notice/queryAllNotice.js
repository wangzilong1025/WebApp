/**
 * Created by 王子龙 on 2017-03-07.
 */
var noticeModule = {};

(function($){

    var getParams = function (data) {
        data = data || {};
        var bulletinId = $("#bulletinId").val();
        var subject = $("#subject").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var createDate = $("#createDate").val();
        var validDate = $("#validDate").val();
        var expireDate = $("#expireDate").val();
        var bulletinType = $("#bulletinType").val();
        data.bulletinId = bulletinId;
        data.subject = subject;
        data.createDate = createDate;
        data.validDate = validDate;
        data.expireDate = expireDate;
        data.bulletinType = bulletinType;
        data.endTime =endTime;
        data.startTime = startTime;
        return data;
    }

    function initEvent() {
        //添加公告信息系
        $("#addNewNotice").click(function () {
            var url = "/view/general/routine/notice/addNotice.html" ;
            parent.indexModel.addTab("公告添加", url, true);
        });
        //便利所有公告信息
        $("#lookForNotice").click(function () {
            $("#queryAllnotice").datagrid().load();
        });
    }
    //根据ID查看公告详情
    function viewNotice(BULLETIN_ID){
        var url = "detailNotice.html?operType=view&bulletinId="+BULLETIN_ID;
        comm.dialog.window({
            title:"公告详情",
            url:url,
            width:800,
            height:450,
        });
    }
    //工具ID修改公告
    function updateNotice(BULLETIN_ID){
        var url = "updateNotice.html?bulletinId="+BULLETIN_ID;
        comm.dialog.window({
            url:url,
            width:800,
            height:450,
            isSec:true,
            callback:function (e) {
                $("#queryAllnotice").datagrid().load();
                comm.dialog.notice({
                    type:comm.dialog.type.success,
                    position:"center",
                    content:"更新成功！"
                });

            }
         });
    }
    //公告删除
    function deleteNotice(BULLETIN_ID){
        comm.dialog.confirm({
            content: "确定要删除吗",
            func: function (flag) {
                if(flag) {
                    var bulletinId = BULLETIN_ID;
                    var options = {
                        "param": {"bulletinId": bulletinId},
                        "busiCode": busiCode.routine.IENBULLETININFOFSV_DELETENOTICE,
                        "moduleName": "common",
                        "sync": false,
                        "callback": function (data, isSucc, msg) {
                            if (isSucc) {
                                /*comm.dialog.notification({
                                    content: "刪除成功!!!"
                                });*/
                                comm.dialog.notice({
                                    type:comm.dialog.type.success,
                                    position:"center",
                                    content:"删除成功！"
                                });
                                $("#queryAllnotice").datagrid().load();
                            } else {
                                /*comm.dialog.notification({
                                    content: "刪除失败!!!"
                                });*/
                                comm.dialog.notice({
                                    type:comm.dialog.type.error,
                                    position:"center",
                                    content:"删除失败！"
                                });
                            }
                        }
                    }
                    comm.ajax.ajaxEntity(options);
                }
            }
        });
    }
    function init() {
        initEvent();
        $.extend(noticeModule,{
            getParams:getParams,
            deleteNotice:deleteNotice,
            viewNotice:viewNotice,
            updateNotice:updateNotice
        });
    }
    init();
})(jQuery);
