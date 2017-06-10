<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">
    <title>成果收藏</title>
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/colstyle.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/cpstyle.css" rel="stylesheet" type="text/css">
    <script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
    <script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
    <script type="text/javascript">
        function sdf(sid){
            $.ajax(
                {
                    type: "post",  //get或post
                    async : false,  //可选，默认true  true或false
                    url:  "deleteScollect.do?sid="+sid,   //请求的服务器地址
                    //dataType: "text",
                    data:
                        {					//请求携带的参数，一个或者多个均可
                            Sid:sid
                        } ,
                    success:function(data)
                    {
                        alert("已取消");
                        $("#"+sid).remove();
                    },
                    error:function()
                    {				//失败回调函数
                        alert("出现异常");
                    }
                });
        };
    </script>

    <script type="text/javascript">
        function delCollect(pid){
            $.ajax(
                {
                    type: "post",  //get或post
                    async : false,  //可选，默认true  true或false
                    url:  "deleteAchievementCollectionById.do?pid="+pid,   //请求的服务器地址
                    //dataType: "text",
                    data:
                        {//请求携带的参数，一个或者多个均可
                            Pid:pid
                        } ,
                    success:function(data)
                    {
                        alert("已取消");
                        $("#"+pid).remove();
                    },
                    error:function()
                    {				//失败回调函数
                        alert("出现异常");
                    }
                });
        };
    </script>
</head>
<body>
<!--头 -->
<header>
    <article style="margin: 20px auto">
        <div class="mt-logo">
            <!--顶部导航条 -->
            <div class="am-container header">
                <ul class="message-l">
                    <div class="topMessage">
                        <div class="menu-hd">
                            <c:choose>
                                <c:when test="${sessionScope.user == null }">
                                    <a href="<%=path %>/package-jsp/jsp-front/user-login.jsp" target="_top" class="h">亲，请登录</a>
                                    <a href="<%=path %>/package-jsp/jsp-front/user-regist.jsp" target="_top">免费注册</a>
                                </c:when>
                                <c:otherwise>
                                    欢迎登陆，${sessionScope.user.userName }
                                    <a href="<%=path %>/package-jsp/jsp-front/user-login.jsp" target="_top">退出</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </ul>
                <ul class="message-r">
                    <c:choose>
                        <c:when test="${sessionScope.user == null }">
                            <div class="topMessage home">
                                <div class="menu-hd"><a href="<%=path %>/menu/getMenuList.do" target="_top" class="h">商城首页</a></div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="topMessage home">
                                <div class="menu-hd"><a href="<%=path %>/menu/getMenuList.do" target="_top" class="h">商城首页</a></div>
                            </div>
                            <div class="topMessage my-shangcheng">
                                <div class="menu-hd MyShangcheng"><a href="<%=path %>/userInfo/userCenterInfomation.do" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a></div>
                            </div>
                            <div class="topMessage favorite">
                                <div class="menu-hd"><a href="<%=path %>/achievementCollect/queryAllCollectionAchievement.do" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>收藏夹</span></a></div>
                            </div>
                            <div class="topMessage favorite">
                                <div class="menu-hd"><a href="<%=path %>/fotoPlace/queryAllFotoPlaceAchievementByUserInfoId.do" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>我的足迹</span></a></div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
            <div class="clear"></div>
        </div>
    </article>
</header>

<div class="nav-table">
    <div class="long-title"><span class="all-goods">全部分类</span></div>
    <div class="nav-cont">
        <ul>
            <li class="index"><a href="<%=path %>/menu/getMenuList.do">首页</a></li>
            <li class="qc"><a href="<%=path %>/menu/selectMenuOne.do">登记成果</a></li>
            <li class="qc"><a href="<%=path %>/statistics/statisticsAchievementCount.do">往年统计</a></li>
            <li class="qc"><a href="#">公告</a></li>
            <li class="qc last"><a href="<%=path %>/statistics/selectCityByPie.do">当年排行</a></li>
        </ul>
        <div class="nav-extra">
            <i class="am-icon-user-secret am-icon-md nav-user"></i><b></b>我的福利
            <i class="am-icon-angle-right" style="padding-left: 10px;"></i>
        </div>
    </div>
</div>
<b class="line"></b>
    <div class="center">
        <div class="col-main">
            <div class="main-wrap">

                <div class="am-tabs-d2 am-tabs  am-margin" data-am-tabs>
                    <ul class="am-avg-sm-2 am-tabs-nav am-nav am-nav-tabs">
                        <li class="am-active"><a href="#tab1">成果收藏</a></li>
                        <li><a href="#tab2">发布人收藏</a></li>
                    </ul>

                    <div class="am-tabs-bd">

                        <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                            <div class="s-content">
                                <c:choose>
                                    <c:when test="${empty achieveCollect }">
                                        &nbsp;&nbsp;亲,您还没有收藏过科研成果/(ㄒoㄒ)/~~
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${achieveCollect }" var="listColl">
                                            <c:forEach items="${achieve }" var="listAch">
                                                <c:if test="${listAch.achievementId==listColl.achievementId }">
                                                    <div class="s-item-wrap" id="${listColl.achievementCollectId }" style="width: 199px; height: 290px;">
                                                        <div class="s-item">
                                                            <div class="s-pic">
                                                                <a href="#" class="s-pic-link">
                                                                    <img width="200px" height="200px" src="<%=path %>/achievementImage/${listAch.achievementImages }"  title="${listAch.achievementName }" class="s-pic-img s-guess-item-img">
                                                                </a>
                                                            </div>

                                                            <div class="s-info">
                                                                <div class="s-title"><a href="#" title="${listAch.achievementName }"></a>${listAch.achievementName }</div>
                                                                <div class="s-price-box">
                                                                    <span class="s-price"><%--<em class="s-price-sign">¥</em>--%><em class="s-value">${listAch.userNick }</em></span>
                                                                    <span class="s-history-price"><%--<em class="s-price-sign">¥</em>--%><em class="s-value">${listAch.unitName }</em></span>
                                                                </div>

                                                                <div class="s-extra-box" style="height: 22px;">
                                                                    <span class="s-comment">好评: 99.74%</span>
                                                                    <span class="s-sales">月销: 69</span><br>
                                                                    <span class="s-comment">收藏时间:${listColl.collectionTimeStr }</span>
                                                                </div>
                                                            </div>

                                                            <div class="s-tp">
                                                                <form name="form" action="<%=path %>/addCart.do" method="post">
                                                                    <input type="hidden" name="pcollectid" value="${listColl.achievementCollectId }" />
                                                                    <span class="ui-btn-loading-before"><a  onclick="delCollect(${listColl.achievementCollectId })"><font color="white">取消收藏</font></a></span>
                                                                    <span class="ui-btn-loading-before buy"><a onclick=""><font color="white">查看详情</font></a></span>
                                                                </form>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>

                        <div class="am-tab-panel am-fade" id="tab2">
                            <div class="s-content">



                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <!--底部-->
            <div class="footer ">
                <div class="footer-hd ">
                    <p>
                        <a href="# ">关于国科网</a>
                        <b>|</b>
                        <a href="# ">我们的资源</a>
                        <b>|</b>
                        <a href="# ">我们的服务</a>
                        <b>|</b>
                        <a href="# ">免责声明</a>
                        <b>|</b>
                        <a href="# ">示范基地</a>
                        <b>|</b>
                        <a href="# ">软件下载</a>
                        <b>|</b>
                        <a href="# ">联系我们</a>
                    </p>
                </div>
                <div class="footer-bd ">
                    <p>
                        <span><font style="font-weight: bold">友情链接：</font></span>
                        <a href="# ">中华人民共和国科学技术部</a>
                        <b>|</b>
                        <a href="# ">国家科学技术奖励工作办公室</a>
                        <b>|</b>
                        <a href="# ">北京市奖励办</a>
                        <b>|</b>
                        <a href="# ">北方技术交易市场</a>
                        <b>|</b>
                        <a href="# ">科化网    </a>
                        <b>|</b>
                        <em>© 2017-2025 Hengwang.com 版权所有</em>
                    </p>
                </div>
            </div>
        </div>
        <aside class="menu">
            <ul>
                <li class="person">
                    <a href="<%=path %>/userInfo/userCenterInfomation.do">个人中心</a>
                </li>
                <li class="person">
                    <a href="#"><font style="font-weight: bold">个人资料</font></a>
                    <ul>
                        <li> <a href="<%=path %>/userInfo/selectPersonalCenter.do">个人信息</a></li>
                        <li> <a href="<%=path %>/userLogin/userUpdateSafetyByUserId.do">安全设置</a></li>
                        <li> <a href="<%=path %>/package-jsp/jsp-front/user-safety-pass.jsp">修改密码</a></li>
                        <li> <a href="<%=path %>/achievementCollect/queryAllCollectionAchievement.do">我的收藏</a></li>
                        <li> <a href="<%=path %>/fotoPlace/queryAllFotoPlaceAchievementByUserInfoId.do">足迹浏览</a></li>
                    </ul>
                </li>
                <li class="person">
                    <a href="#"><font style="font-weight: bold">我的成果</font></a>
                    <ul>
                        <li> <a href="<%=path %>/menu/selectMenuOne.do">成果新增</a></li>
                        <li> <a href="<%=path %>/achievement/queryAllAchievementUnreleaseFront.do">未发布成果</a></li>
                        <li> <a href="<%=path %>/achievement/queryAllAchievementByCheckPendingFront.do">待审核成果</a></li>
                        <li> <a href="<%=path %>/achievement/queryAllAchievement.do">已发布成果</a></li>
                        <li> <a href="<%=path %>/achievement/queryAllAchievementNotPass.do">未通过成果</a></li>
                    </ul>
                </li>
            </ul>
        </aside>
    </div>

</body>

</html>