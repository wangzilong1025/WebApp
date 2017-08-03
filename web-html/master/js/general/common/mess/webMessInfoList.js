/**
 * Created by dizl on 2017/2/15.
 */
(function(){
    function initUI(){
        comm.ajax.ajaxEntity({
            busiCode:busiCode.common.IMESSINFOFSV_GETMESSINFO,
            param:{
                type:"WEB"
            },
            mask:true,
            callback:function(data){
                var template = kendo.template($("#mess-info-temp").html());
                var menu = $(template(data));
                menu.appendTo($("#mess-info"));

                if(data!=null && data.length>0){
                    for(var i=0;i<data.length;i++){
                        for(var j=0;j<data[i].msgInfo.length;j++){
                            $("#msg_"+data[i].msgInfo[j].msgInfoId+"_span").html(data[i].msgInfo[j].msgText);
                        }
                    }
                }
            }
        });
    }

    function init(){
        initUI();
    }

    init();
})();

/**
 * 显示或隐藏消息详细信息
 */
function showMessInfo(target){
    $(target).next().toggle();
}

/***
 * 标记当前消息为已阅读状态
 * */
function readMsg(target){
    target = $(target);
    var msgId = target.attr("msgId");
    var msgInfoId = target.attr("msgInfoId");
    var isRead = target.attr("isRead");

    if(isRead==false || isRead=="false"){
        $("#msg_"+msgInfoId+"_small").text("处理中...");//设置为已读
        $("#msg_"+msgInfoId+"_span").removeClass("font-bold");//取消粗体
        //调用后台接口将消息设置为已读
        comm.ajax.ajaxEntity({
            busiCode:busiCode.common.IMESSINFOFSV_READMESS,
            param:{
                msgInfoId:msgInfoId
            },
            mask:false,
            callback:function(){
                $("#msg_"+msgInfoId+"_small").text("已读");//设置为已读
                var msgCountSpan = $("#msg_"+msgId+"_count");
                var msgCount = eval(msgCountSpan.attr("msgCount")-1);//重新设置数量
                if(msgCount<0){
                    msgCount = 0;
                }
                msgCountSpan.attr("msgCount",msgCount);
                msgCountSpan.text(msgCount);
                target.attr("isRead","true");
            }
        });
    }
}
