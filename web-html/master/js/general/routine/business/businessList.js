/**
 * Created by dizl on 2017/4/5.
 */
function getParams(){
    var data = {};
    data.busiOpportInfoId = $("#busiOpportInfoId").val();
    data.groupId = $("#groupId").val();
    data.groupName = $("#groupName").val();
    data.contPhone = $("#contPhone").val();
    data.createStartDate = $("#createStartDate").val();
    data.createEndDate = $("#createEndDate").val();
    return data;
}

(function(){
    function initUI(){

    }

    function initEvent(){
        //查询商机信息
        $("#queryBusinessBtn").bind("click",function(){

        });
        //新建商机信息
        $("#newBusinessBtn").bind("click",function(){
            parent.indexModel.addTab("新建商机",PROJECT_URL+"/view/general/routine/business/businessEdit.html");
        });

        $("#businessList").datagrid().dbclick(function(item){
            //判断当前是否可编辑，如果可编辑则打开商机编辑页面，否则打开商机查看页面
            parent.indexModel.addTab("商机详情",PROJECT_URL+"/view/general/routine/business/businessView.html?busiOpportInfoId="+item.busiOpportInfoId);
        });
    }

    function init(){
        initUI();
        initEvent();
    }
    init();
})();