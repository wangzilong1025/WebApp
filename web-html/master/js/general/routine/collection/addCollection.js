/**
 * Created by liumj on 2017/3/7.
 */
(function ($) {

    function init(){
        var request = comm.tools.getRequest();
        if(request!=null && request.collectionLevel=="1"){//如果为文件夹
            $("#viewNameLi").hide();
        }
        if(request.url!=null && request.url!=""){
            $("#viewName").val(decodeURIComponent(request.url));
        }
        if(request.title!=null && request.title!=""){
            $("#collectionName").val(decodeURIComponent(request.title));
        }

        $("#save").bind("click",function(){
            if(!comm.validate.validate($("#collectionName"),comm.validate.regexp.notempty)){
                return;
            }
            if(request.collectionLevel=="2"){//如果为页面
                //判断是否录入页面路径
                if(!comm.validate.validate($("#viewName"),comm.validate.regexp.notempty)){
                    return;
                }
            }
            comm.ajax.ajaxEntity({
                busiCode:busiCode.routine.IESCOLLECTIONFSV_ADDCOLLECTIONINFO,
                param:{
                    collectionName:$("#collectionName").val(),
                    viewname:encodeURIComponent($("#viewName").val()),
                    collectionLevel:request.collectionLevel,
                    parentId:request.parentId
                },
                callback:function(data,isSucc,msg){
                    if(isSucc){
                        var hasSubChildren = false;
                        var sprite = "fa fa-file tree-icon";
                        if(data!=null && data.collectionLevel=="1"){
                            hasSubChildren = true;
                            sprite = "fa fa-folder tree-icon";
                        }
                        var retVal = {
                            collectionId:data.collectionId,
                            collectionName:data.collectionName,
                            viewname:data.viewname,
                            parentId:data.parentId,
                            collectionLevel:data.collectionLevel,
                            hasSubChildren:hasSubChildren,
                            sprite:sprite
                        }

                        comm.dialog.unWindow({
                            confirm:true,
                            retVal:retVal
                        })
                    }else{
                        comm.dialog.notice({
                            type:comm.dialog.type.error,
                            content:msg,
                            position:"center"
                        });
                    }
                }
            })
        });

        $("#cancel").bind("click",function(){
           comm.dialog.unWindow({
               confirm:false
           });
        });
    }

    init();

})(jQuery);