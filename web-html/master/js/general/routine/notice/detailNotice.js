/**
 * Created by 王子龙 on 2017-03-09.
 */
//根据ID查看公告
/*var initBulletinButtonVisible = function(){
    var bulletinId = comm.tools.getRequest().bulletinId;
    var options = {
        "param":{"bulletinId":bulletinId,"page":0,"pageSize":1},
        "busiCode":busiCode.routine.IENBULLETININFOFSV_QUERYFORNOTICEBYID,
        "callback": function(data, isSucc, msg){
            if(isSucc){
                $("#subject").html(data.data[0].subject);
                $("#content").html(data.data[0].content);
                $("#createDate").html(data.data[0].createDate);
                var extent = data.data[0].extent
                if(extent==0){
                    $("#noticeLevel").html("一般");
                }else{
                    $("#noticeLevel").html("紧急");
                }
                var annex = data.data[0].annex;
                if(annex==null){
                    $("#annexNull").html("无附件");
                }else{
                    $("#annexNull").kendoAIFileView({
                        fileInputId: annex
                    });
                    // $("#annexNull").html(annex);
                }
            }
        }
    }
    comm.ajax.ajaxEntity(options);
};
initBulletinButtonVisible();*/

var noticeInfo =function(){
    var bulletinId = comm.tools.getRequest().bulletinId;
    alert(bulletinId);
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
                    comm.tools.setHtmlData(data.data[0]);
                }
            }
        });
    }
};
noticeInfo();

$("#closeButton").click(function(){
    comm.dialog.unWindow({
       "confirm":true
    });
});
