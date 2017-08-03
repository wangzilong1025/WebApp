/**
 * Created by dizl on 2017/2/27.
 */
(function(){
    function init(){
        $("#okBtn").bind("click",function(){

            var paramCode = $("#paramCode");
            var paramName = $("#paramName");
            var paramType = $("#paramType");
            var defaultValue = $("#defaultValue");
            //判断参数是否录入
            if(!comm.validate.validate(paramCode,comm.validate.regexp.notempty)){
                return;
            }
            if(!comm.validate.validate(paramName,comm.validate.regexp.notempty)){
                return;
            }
            if(!comm.validate.validate(paramType,comm.validate.regexp.notempty)){
                return;
            }

            comm.dialog.unWindow({
                confirm:true,
                retVal:{
                    paramCode:paramCode.val(),
                    paramName:paramName.val(),
                    paramType:paramType.val(),
                    defaultValue:defaultValue.val(),
                    text:paramName.val()+"("+paramCode.val()+")"
                }
            });
        });

        $("#cancelBtn").bind("click",function(){
            comm.dialog.unWindow({
                confirm:false
            });
        });
    }

    init();
})();