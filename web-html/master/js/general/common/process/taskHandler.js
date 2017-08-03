/**
 * Created by dizl on 2017/2/27.
 */
(function(){
    var isInitPoint = false;
    function init(){
        //选择处理人类型
        $("#handlerType").bind("change",function(){
            var handlerType = $("#handlerType").val();
            if(handlerType==0){//指定操作员
                $(".handler").eq(0).show();
                $(".handler").eq(1).hide();
                $(".handler").eq(2).hide();
            }else if(handlerType==1){//指定岗位
                $(".handler").eq(0).hide();
                $(".handler").eq(1).show();
                $(".handler").eq(2).hide();
            }else if(handlerType==2){//流程创建人
                $(".handler").eq(0).hide();
                $(".handler").eq(1).hide();
                $(".handler").eq(2).hide();
            }else if(handlerType==3){//某一节点处理人
                $(".handler").eq(0).hide();
                $(".handler").eq(1).hide();
                $(".handler").eq(2).show();
                if(isInitPoint==false){
                    isInitPoint = true;
                    //为添加值
                    var rects = parent.$.process.q;
                    var data = [];
                    if(rects!=null){
                        for (var c in rects) {
                            if (rects[c]) {
                                var rectJson = JSON.parse(rects[c].toJson());
                                var text = rectJson.props.taskName.value;
                                if(comm.lang.isEmpty(text)){
                                    text = rectJson.props.text.value+"["+c+"]";
                                }
                                if(rectJson.type=="task"){
                                    data.push({
                                        value:c,
                                        text:text
                                    });
                                }
                            }
                        }
                        $("#point").combobox().setDataSource(new kendo.data.DataSource({data:data}));
                    }
                }
            }
        });

        $("#okBtn").bind("click",function(){
            //判断参数是否录入
            if(!comm.validate.validate($("#handlerType"),comm.validate.regexp.notempty)){
                return;
            }
            var handlerType = $("#handlerType").val();
            var handlerTypeName = $("#handlerType").combobox().text();
            var handler = "";
            if(handlerType==0){//指定操作员
                handler = $("#operator").val();
            }else if(handlerType==1){//指定岗位
                handler = $("#station").val();
            }else if(handlerType==2){//流程创建人
                handler = "";
            }else if(handlerType==3){//某一节点处理人
                handler = $("#point").val();
            }
            comm.dialog.unWindow({
                confirm:true,
                retVal:{
                    handlerType:handlerType,
                    handler:handler,
                    text:handlerTypeName+"："+handler
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