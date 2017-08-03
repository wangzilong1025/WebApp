/**
 * Created by liumj on 2017/3/7.
 */

var EsWorkLog = {};
(function ($) {
    var getParams = function (data) {
        data = data || {};
        var logOrigin = $("#LOG_ORIGIN").combobox().value();
        var opId = $("#OP_ID").val();
        var validDate = $("#VALID_DATE").val();
        var expireDate = $("#EXPIRE_DATE").val();
        data.logOrigin = logOrigin;
        data.opId = opId;
        data.validDate = validDate;
        data.expireDate = expireDate;
        return data;
    };

    $.extend(EsWorkLog, {
        getParams: getParams
    });

    //查询日志
    $("#QUERY").click(function () {
        $("#resultGrid").datagrid().load();
    });

    //新增日志
    $("#add").bind("click",function(){
        comm.dialog.window({
            title:"新增工作日志",
            url:"/view/general/routine/worklog/esWorkLogManager.html?operType=add",
            width:800,
            height:450,
            isSec:true,
            callback:function(data){
                if(data){
                    var msgcontent = "添加成功!";
                    comm.dialog.notice({
                        type:comm.dialog.type.success,
                        content:msgcontent,
                        position:'center'
                    });
                    $("#resultGrid").datagrid().load();
                }
            }
        });
    });

    //详细信息展示
    var showLogDetail = function (select) {
        comm.dialog.window({
            title:"工作日志明细",
            url:"/view/general/routine/worklog/esWorkLogManager.html?operType=query" + "&logId=" + select.logId,
            width:800,
            height:450,
            isSec:true,
            callback:function(data){

            }
        });
    };

    //双击事件
    $("#resultGrid").datagrid().dbclick(function () {
        var select = $("#resultGrid").datagrid().getSelects();
        showLogDetail(select[0]);
    });

    //查询日志
    $("#queryById").click(function () {
        var select = $("#resultGrid").datagrid().getSelects();
        if (select.length > 0) {
            showLogDetail(select[0]);
        }
    });

})(jQuery);