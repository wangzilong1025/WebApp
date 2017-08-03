/**
 * Created by dizl on 2016/12/25.
 */
var indexModel = indexModel || {};

$(function () {
    var getCollectionParams = function (data) {
        data = data || {};
        if (!data.collectionId) {
            data.collectionId = -1;
        }
        return data;
    };

    function init() {
        indexModel.tabHeight = $(window).innerHeight() /*- ($("#tabs").position().top + 65)*/;
        $.extend(indexModel, {
            addTab: addTab,
            //closeTab: closeTab,
            closeTabByTitle: closeTabByTitle,
            closeAllTab: closeAllTab,
            selectByTitle: selectByTitle,
            getCollectionParams:getCollectionParams,
            openCollectionUrl:openCollectionUrl,
            addCollection:addCollection,
            addCollectionPage:addCollectionPage,
            //deleteCollection:deleteCollection,
            startLock:startLock,
            userInfo:userInfo,
            collection:collection
        });
        //initUI();
        //initEvent();
    }

    function collection() {
        $("#collection").bind("click", function () {
            window.location.href=PROJECT_URL+"/view/general/userinfo/collection.html";
        });
    }

    function userInfo() {
        $("#userInfo").bind("click", function () {
            /*comm.dialog.window({
                title: "用户信息",
                url: PROJECT_URL+"/view/general/userinfo/userinfo.html",
                width: "950",
                height: "500",
                iconClass: "",
                toolbar: {
                    refresh: true,
                    max: true,
                    min: true,
                    close: true
                }
            })*/
            window.location.href=PROJECT_URL+"/view/general/userinfo/usermenu.html";
        });
    }

    /**增加tab页
     * @param title
     * @param url
     * @param closeable 是否出现关闭按钮
     * @param opentype  2-直接通过wind打开
     */
    function addTab(title, url, closeable, opentype) {
        if (url.indexOf("?") != -1) {
            url = url + "&pageName=" + title;
        } else {
            url = url + "?pageName=" + title;
        }
        //每个url都把title作为pageName参数传到子页面中去
        if (opentype != null && opentype == 2) { //弹框
            window.open(url);
        } else if (opentype != null && opentype == 3) { //不提醒，直接替换
            var mainTab = $("#tabs").data("kendoTabStrip");
            var isExist = mainTab.isExist(title);
            if (!isExist) {
                url = encodeURI(url);
                var content = '<iframe id="' + title + '" scrolling="auto" frameborder="0" src="' + url + '" width=100% height=' + indexModel.tabHeight + ' ></iframe>';
                if (closeable == null || closeable == true) {
                    title = title + '<a href="javascript:void(0)"  data-title="' + title + '" onclick="indexModel.closeTab(this)" style="position: absolute;right:5px;top:10px;"><i class="pui-close pui-close-xl"></i></a>';
                }
                mainTab.append({
                    text: title,
                    encoded: false,
                    content: content
                });
            }
            mainTab.selectByTitle(title);
        } else {
            url = encodeURI(url);
            var content = '<iframe id="' + title + '" scrolling="auto" frameborder="0" src="' + url + '" width=100% height=' + indexModel.tabHeight + ' ></iframe>';
            var mainTab = $("#tabs").data("kendoTabStrip");
            var isExist = mainTab.isExist(title);
            if (!isExist) {
                if (closeable == null || closeable == true) {
                    title = title + '<a href="javascript:void(0)" data-title="' + title + '" onclick="indexModel.closeTab(this)" style="position: absolute;right:5px;top:10px;"><i class="pui-close pui-close-xl"></i></a>';
                }
                mainTab.append({
                    text: title,
                    encoded: false,
                    content: content
                });
            } else {
                comm.dialog.confirm({
                    title: "提示",
                    content: "要重新打开" + title + "页面吗？",
                    func: function (flag) {
                        if (flag == true) {
                            $iframe = $("iframe#" + title);
                            $iframe.attr("src", url);
                        }
                    }
                });
            }
            mainTab.selectByTitle(title);
        }
    }

    /**根据标题关闭tab页**/
    function closeTabByTitle(title) {
        //关闭iframe前防止内存泄漏
        var mainTab = $("#tabs").data("kendoTabStrip");
        var tab = $(mainTab.tabGroup.find("li a[data-title=" + title + "]")[0]).parents("li");
        var otherTab = tab.next();
        otherTab = otherTab.length ? otherTab : tab.prev();
        mainTab.remove(tab);
        mainTab.select(otherTab);
    }

    /**关闭所有tab页**/
    function closeAllTab() {
        //关闭iframe之前都应该先释放内存，防止内存泄漏
        var mainTab = $("#tabs").data("kendoTabStrip");
        $(mainTab.items()).each(function (item) {
            mainTab.remove(0);
        });
    }

    /**通过title选中tab页**/
    function selectByTitle(title) {
        var mainTab = $("#tabs").data("kendoTabStrip");
        mainTab.selectByTitle(title);
    }

    /**打开页面**/
    function openCollectionUrl(){
        var selected = $("#collectionTree").tree().getSelect();
        var viewname = "";
        var openFlag = 1;
        var collectionName = "收藏夹管理";
        if(selected!=null && selected.collectionId!=null){
            if(selected.collectionLevel=="1"){//如果为收藏夹
                //打开收藏夹管理页面 TODO

            }else{
                viewname = selected.viewname;
                collectionName = selected.collectionName;
                if(viewname.indexOf("http://")!=-1 || viewname.indexOf("https://")!=-1 || viewname.indexOf("www")!=-1){
                    openFlag = 2;
                }
            }
        }
        if(viewname!=""){
            indexModel.addTab(collectionName,viewname,true,openFlag);
            $("#folderDiv").hide();
        }
    }
    /**添加收藏夹文件夹**/
    function addCollection(){
        //打开收藏夹添加页面
        var selected = $("#collectionTree").tree().getSelect();
        var select = null;
        var parentId = -1;
        if(selected!=null && selected.collectionId!=null){
            if(selected.collectionLevel=="2") {//如果为文件，则找父节点
                if(selected.parent().parent()!=null){
                    parentId =  selected.parent().parent().collectionId;
                    select = selected.parents(".k-item")[0];
                }
            }else{
                parentId = selected.collectionId;
                select = $("#collectionTree").tree().select()
            }
        }

        comm.dialog.window({
            title:"添加收藏夹",
            url:PROJECT_URL+"/view/general/routine/collection/addCollection.html?collectionLevel=1&parentId="+parentId,
            width:"250",
            height:"180",
            callback:function(data){
                $("#collectionTree").tree().append(data,select);
            }
        });
        $("#folderDiv").hide();
    }

    /**添加网页**/
    function addCollectionPage(){
        //打开文件添加页面
        var selected = $("#collectionTree").tree().getSelect();
        var select = null;
        var parentId = -1;
        if(selected!=null && selected.collectionId!=null){
            if(selected.collectionLevel=="2") {//如果为文件，则找父节点
                if(selected.parent().parent()!=null){
                    parentId =  selected.parent().parent().collectionId;
                    select = select.parents(".k-item")[0];
                }
            }else{
                parentId = selected.collectionId;
                select = $("#collectionTree").tree().select()
            }
        }
        var url = "";
        var title = "";
        if($("#tabs").data("kendoTabStrip").select()!=null){
            title = $("#tabs").data("kendoTabStrip").select()[0].textContent;
            url = encodeURIComponent($("#"+title).attr("src"));
            title = encodeURIComponent(title);
        }

        comm.dialog.window({
            title:"添加网页",
            url:PROJECT_URL + "/view/general/routine/collection/addCollection.html?collectionLevel=2&parentId="+parentId+"&url="+url+"&title="+title,
            width:"250",
            height:"180",
            callback:function(data){
                $("#collectionTree").tree().append(data,select);
            }
        });
        $("#folderDiv").hide();
    }

    //调用权限接口判断需要初始化哪些快捷搜索
    function fillAryWithMenuData(ary, operatorId, stationId, groupId, data){
        comm.tools.getPrivs({
            operatorId: operatorId,
            stationId: stationId,
            groupId: groupId,
            callback: function (privs) {
                $.each(privs,function(index,item){
                    $.each(privGroup.quicksearch.privId, function(name,privId) {
                        if (item.PRIV_ID == privId){
                            if (name == "menu") {
                                //菜单初始化
                                var oneList = data;
                                for (var i = 0; i < oneList.length; i++) {
                                    var twoList = oneList[i].childs;
                                    for (var j = 0; j < twoList.length; j++) {
                                        var threeList = twoList[j].childs;
                                        if (threeList) {
                                            for (var k = 0; k < threeList.length; k++) {
                                                var node = threeList[k];
                                                var py = makePy(node.name);
                                                var json = {};
                                                json.text = "【菜单】"+node.name;
                                                json.textFind = json.text+"|"+py;
                                                json.name = node.name;
                                                json.url = node.viewName;
                                                json.openType = node.openType;
                                                ary.push(json);
                                            }
                                        } else {
                                            var node = twoList[j];
                                            var py = makePy(node.name);
                                            var json = {};
                                            json.text = "【菜单】"+node.name;
                                            json.textFind = json.text+"|"+py;
                                            json.name = node.name;
                                            json.url = node.viewName;
                                            json.openType = node.openType;
                                            ary.push(json);
                                        }
                                    }
                                }
                            } else if (name == "group") {

                            }
                        }
                    });
                });
            }
        });
    }

    function highlight(value, term) {
        return value.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + term.replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi, "\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<b><span style='color:red;'><u>$1</u></span></b>");
    }

    //设置自动锁屏，首次取缓存数据，如果重新设置了锁屏信息则实时更新，如果成功解锁延用最后一次信息，如果是重新刷新页面则判断刷新前是否锁屏。
    function initLock() {
        if (comm.storage.SessionStorage.get("LOCK_SCREEN") == 1){
            showLockPrompt();
        } else {
            comm.tools.getCurrUser(function(userInfo) {
                var isAutoLockscreen = userInfo.isAutoLockscreen;
                var lockscreenInteval = userInfo.lockscreenInteval;
                if (isAutoLockscreen == 1 && lockscreenInteval && lockscreenInteval > 0) {
                    lockscreenInteval = 0.5; //测试用
                    startLock(lockscreenInteval);
                }
            });
        }
    }
    function startLock(lockscreenInteval) {
        comm.storage.SessionStorage.set("LOCK_SCREEN",0);
        if (lockscreenInteval) {
            indexModel.delay = lockscreenInteval * 60 * 1000;//delay为需要的时间，单位毫秒
        } else if (!indexModel.delay) {
            initLock();
        }
        document.onmousemove = startTimer; //重新绑定鼠标移动事件
        startTimer(); //重新计时
    }
    function startTimer() {
        clearTimeout(indexModel.timer);
        indexModel.timer = setTimeout(TimerHandler, indexModel.delay);
    }
    function TimerHandler() {
        comm.storage.SessionStorage.set("LOCK_SCREEN",1);
        document.onmousemove = null;//锁定后移除鼠标移动事件
        showLockPrompt();
    }
    function showLockPrompt(){
        comm.dialog.prompt({
            title: "请输入当前操作员密码解锁",
            content: "",
            height: "150px",
            password: true,
            maskOpacity: 0.9,
            isNotNullFlag: true,
            func: function (flag, value) {
                if (flag == true) {
                    comm.tools.getCurrUser(function(userInfo) {
                        var code = userInfo.code;
                        comm.ajax.ajax({
                            url:"/sec/login",
                            param:{
                                username:code,
                                password:value
                            },
                            callback:function(retInfo,isSucc,msg){
                                if(isSucc){
                                    startLock();
                                }else{
                                    comm.dialog.notice({
                                        type:comm.dialog.type.error,
                                        position:"center",
                                        content:msg,
                                        timeout:800
                                    });
                                    showLockPrompt();
                                }
                            }
                        })
                    });
                } else {
                    showLockPrompt();
                }
            }
        });
    }

    init();

});