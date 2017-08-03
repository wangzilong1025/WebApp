/**
 * Created by liuqi on 2017/2/22.
 */
var mainModel = {};
(function ($) {
    var request = comm.tools.getRequest();
    //显示工作台及工作台组件
    function init() {
        var gridster = {};
        comm.ajax.ajaxEntity({
            busiCode: busiCode.sec.ICFGPORTALFSV_LISTINSPORTLET,
            param:{
                insPortalId:request.insPortalId,
                portalId:request.portalId
            },
            moduleName: "sec",
            sync: true,
            callback: function (data, isSucc,msg) {
                if(isSucc){
                    if(data!=null && data.length>0) {
                        var template = kendo.template($("#portlet").html());
                        var portlet = $(template({
                            templateId: request.templateId,
                            portletList: data
                        }));
                        portlet.appendTo($("#wrapper"));
                        var width = Math.round(($("body").width()-160)/12);
                        if(width<80){
                            width = 80;
                        }
                        gridster = $(".gridster > ul").gridster({
                            widget_margins: [5, 5],
                            widget_base_dimensions: [width, 200],
                            min_cols: 12,
                            max_size_x:12
                        }).data('gridster').disable();
                        $(".portlet-content").each(function(){
                            var that = this;
                            var src = $(that).attr("src");
                            comm.ajax.getRemoteHtmlBody(src,true,function(html){
                               $(that).html(html);
                            });
                        });
                    }
                }else{
                    comm.dialog.notification({
                        type:comm.dialog.type.error,
                        content: msg
                    });
                }
            }
        });

        //保存新布局
        $("#saveNewLayout").bind("click",function(){
            var obj = [];
            $(".gridster > ul > li").each(function(){
                var that = $(this);
                var oldDataRow = that.attr("old-data-row");
                var oldDataCol = that.attr("old-data-col");
                var dataRow = that.attr("data-row");
                var dataCol = that.attr("data-col");
                if(dataRow!=oldDataRow || dataCol!=oldDataCol){
                    obj.push({
                        insPortletId: that.attr("insPortletId"),
                        insPortalId:that.attr("insPortalId"),
                        insRowNum:dataRow,
                        insColNum:dataCol
                    });
                }
            });
            if(obj!=null && obj.length>0){
                comm.ajax.ajaxEntity({
                    busiCode:busiCode.sec.ICFGPORTALFSV_SAVENEWLAYOUT,
                    param:{"portlets":obj},
                    callback:function(data,isSucc,msg){
                        if(isSucc){
                            $("#saveLayoutBtn").hide();
                            $(".gridster > ul").gridster().data('gridster').disable();//设置为不可拖动
                            $(".gridster > ul > li .pui-card-title").css("cursor","");
                        }else{
                            comm.dialog.notice({
                                type:comm.dialog.type.info,
                                content:msg,
                                position:"center"
                            });
                        }
                    }
                });
            }else{
                comm.dialog.notice({
                    type:comm.dialog.type.info,
                    content:"工作台布局未发生变化",
                    position:"center"
                });
                $("#saveLayoutBtn").hide();
                $(".gridster > ul").gridster().data('gridster').disable();//设置为不可拖动
                $(".gridster > ul > li .pui-card-title").css("cursor","");
            }
        });
        $("#cancelLayout").bind("click",function(){
            $("#saveLayoutBtn").hide();
            $(".gridster > ul").gridster().data('gridster').disable();//设置为不可拖动
            $(".gridster > ul > li .pui-card-title").css("cursor","");
        });

        mainModel = {
            refreh:refresh,
            edit:edit,
            config:config,
            more:more,
            min:min,
            max:max,
            close:close,
            reloadPortlet:reloadPortlet,
            updateLayout:updateLayout,
            addPortlet:addPortlet
        }
    }

    //重新加载portlet组件
    function reloadPortlet(){
        comm.browser.reload();
    }

    //修改组件布局
    function updateLayout(){
        $(".gridster > ul").gridster().data('gridster').enable();
        $(".gridster > ul > li .pui-card-title").css("cursor","move");//标记为可移动状态
        $("#saveLayoutBtn").show();
    }


    //添加组件
    function addPortlet(){
        //打开编辑页面
        var url = PROJECT_URL+"/view/general/sec/workbench/settingSecPortal.html?insPortalId="+request.insPortalId+"&portalId="+request.portalId+"&flag=1";
        comm.dialog.window({
            url:url,
            title:"工作台组件",
            width:"500",
            height:"460",
            toolbar:{
                max:true
            },
            callback:function(){
                reloadPortlet();
            }
        });
    }

    //刷新页面
    function refresh(insPortalId,insPortletId,url){
        var src = $("#portlet-content-"+insPortalId+"-"+insPortletId).attr("src");
        comm.ajax.getRemoteHtmlBody(src,true,function(html){
            $("#portlet-content-"+insPortalId+"-"+insPortletId).html(html);
        });
    }
    //打开编辑页面
    function edit(insPortalId,insPortletId,portletName){
        //打开编辑页面
        var url = PROJECT_URL+"/view/general/sec/workbench/settingInsPortlet.html?insPortalId="+insPortalId+"&insPortletId="+insPortletId;
        comm.dialog.window({
            url:url,
            title:portletName+"【属性配置】",
            width:"500",
            height:"460",
            toolbar:{
                max:true
            },
            callback:function(){
                reloadPortlet();
            }
        });
    }

    //打开配置页面
    function config(insPortalId,insPortletId,portletName,confUrl){
        if(confUrl!=null && confUrl!="" && confUrl!="null"){
            comm.dialog.window({
                url:confUrl,
                title:portletName+"【页面配置】",
                width:"800",
                height:"350",
                toolbar:{
                    max:true
                }
            });
        }else{
            comm.dialog.notice({
                type:comm.dialog.type.warn,
                content:"组件"+portletName+"的配置页面为空",
                position:"center"
            });
        }
    }

    //打开更多页面
    function more(insPortalId,insPortletId,portletName,moreUrl,portletUrl){
        var url = moreUrl;
        if(url==null || url=="" || url=="null"){
            url = portletUrl;
        }
        parent.indexModel.addTab(portletName,url);
    }

    //最小化
    function min(insPortalId,insPortletId){
        var that = this;
        $("#portlet-content-"+insPortalId+"-"+insPortletId).slideToggle();
    }
    //最大化
    function max(insPortalId,insPortletId,portletName,url){
        comm.dialog.window({
            url:url,
            title:portletName,
            maskClickClosed:true,
            width:"500",
            height:"350",
            toolbar:{
                max:true
            }
        });
    }
    //删除组件
    function close(insPortalId,insPortletId,portletName){
        comm.dialog.confirm({
            content:"确认要删除【"+portletName+"】组件吗？",
            func:function(flag){
                if(flag){
                    //调用后台删除组件信息
                    comm.ajax.ajaxEntity({
                        busiCode:busiCode.sec.ICFGPORTALFSV_REMOVESECPORTLET,
                        param:{
                            insPortletId:insPortletId
                        },
                        callback:function(){
                            //将当前组件html中移除
                            $("#portlet-li-"+insPortalId+"-"+insPortletId).remove();
                        }
                    });
                }
            }
        });
    }

    init();
})(jQuery);

