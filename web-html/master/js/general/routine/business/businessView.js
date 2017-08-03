/**
 * Created by dizl on 2017/4/5.
 */
(function(){
    function initUI(){
        var request = comm.tools.getRequest();
        if(request.busiOpportInfoId!=null && request.busiOpportInfoId>0){
            //加载商机信息
            comm.ajax.ajaxEntity({
                busiCode:busiCode.routine.IBUSIOPPORTINFOFSV_QUERYOPPORTINFO,
                param:{
                    busiOpportInfoId:request.busiOpportInfoId
                },
                callback:function(data,isSucc,msg){
                    if(isSucc){
                        comm.tools.setHtmlData(data.list[0]);
                    }
                }
            });
        }
    }

    function initEvent(){

    }
    function init(){
        initUI();
        initEvent();
    }
    init();
})();