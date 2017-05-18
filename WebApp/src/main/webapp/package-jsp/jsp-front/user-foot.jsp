<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <title>我的足迹</title>
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/footstyle.css" rel="stylesheet" type="text/css">
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
            <li class="qc"><a href="#">统计</a></li>
            <li class="qc"><a href="#">公告</a></li>
            <li class="qc last"><a href="#">排行</a></li>
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

            <div class="user-foot">
                <!--标题 -->
                <div class="am-cf am-padding">
                    <div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">我的足迹</strong> / <small>Browser&nbsp;History</small></div>
                </div>
                <hr/>
                <!--足迹列表 -->
                <div class="goods">
                    <div class="goods-date" data-date="2015-12-21">
                        <span><i class="month-lite">12</i>.<i class="day-lite">21</i><i class="date-desc">今天</i></span>
                        <del class="am-icon-trash"></del>
                        <s class="line"></s>
                    </div>

                    <div class="goods-box first-box">
                        <div class="goods-pic">
                            <div class="goods-pic-box">
                                <a class="goods-pic-link" target="_blank" href="#" title="意大利费列罗进口食品巧克力零食24粒  进口巧克力食品">
                                    <img src="<%=path %>/package-style/style-front/images/TB1_pic.jpg_200x200.jpg" class="goods-img"></a>
                            </div>
                            <a class="goods-delete" href="javascript:void(0);"><i class="am-icon-trash"></i></a>
                            <div class="goods-status goods-status-show"><span class="desc">宝贝已下架</span></div>
                        </div>

                        <div class="goods-attr">
                            <div class="good-title">
                                <a class="title" href="#" target="_blank">意大利费列罗进口食品巧克力零食24粒  进口巧克力食品</a>
                            </div>
                            <div class="goods-price">
										<span class="g_price">
                                        <span>¥</span><strong>71</strong>
										</span>
                                <span class="g_price g_price-original">
                                        <span>¥</span><strong>142</strong>
										</span>
                            </div>
                            <div class="clear"></div>
                            <div class="goods-num">
                                <div class="match-recom">
                                    <a href="#" class="match-recom-item">找相似</a>
                                    <a href="#" class="match-recom-item">找搭配</a>
                                    <i><em></em><span></span></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>




                <c:choose>
                    <c:when test="${empty fotoList }">
                        &nbsp;&nbsp;亲,您还没有浏览过科研成果/(ㄒoㄒ)/~~
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${fotoList }" var="foto">
                            <c:forEach items="${achieveList }" var="listAch">
                                <c:if test="${listAch.achievementId==foto.achievementId }">
                                    <div class="goods">
                                        <div class="goods-date" data-date="2015-12-21">
                                            <s class="line"></s>
                                        </div>

                                        <div class="goods-box">
                                            <div class="goods-pic">
                                                <div class="goods-pic-box">
                                                    <a class="goods-pic-link" target="_blank" href="<%=path %>/package-jsp/jsp-front/user-achievement.jsp?fotoId=${foto.fotoPlaceId}" title="${listAch.achievementName}">
                                                        <img src="<%=path %>/package-style/style-front/images/TB1_pic.jpg_200x200.jpg" class="goods-img"></a>
                                                </div>
                                                <a class="goods-delete" href="javascript:void(0);"><i class="am-icon-trash"></i></a>
                                                <div class="goods-status goods-status-show"><span class="desc">科研成果详情</span></div>
                                            </div>

                                            <div class="goods-attr">
                                                <div class="good-title">
                                                    <a class="title" href="#" target="_blank">${listAch.achievementName}</a>
                                                </div>
                                                <div class="goods-price">
                                                            <span class="g_price">
                                                            <span>¥</span><strong>71</strong>
                                                            </span>
                                                    <span class="g_price g_price-original">
                                                            <span>¥</span><strong>142</strong>
                                                            </span>
                                                </div>
                                                <div class="clear"></div>
                                                <div class="goods-num">
                                                    <div class="match-recom">
                                                        <a href="#" class="match-recom-item">找相似</a>
                                                        <a href="#" class="match-recom-item">找搭配</a>
                                                        <i><em></em><span></span></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>


               <%-- <div class="goods">
                    <div class="goods-date" data-date="2015-12-21">
                        <s class="line"></s>
                    </div>

                    <div class="goods-box">
                        <div class="goods-pic">
                            <div class="goods-pic-box">
                                <a class="goods-pic-link" target="_blank" href="#" title="意大利费列罗进口食品巧克力零食24粒  进口巧克力食品">
                                    <img src="<%=path %>/package-style/style-front/images/TB1_pic.jpg_200x200.jpg" class="goods-img"></a>
                            </div>
                            <a class="goods-delete" href="javascript:void(0);"><i class="am-icon-trash"></i></a>
                            <div class="goods-status goods-status-show"><span class="desc">宝贝已下架</span></div>
                        </div>

                        <div class="goods-attr">
                            <div class="good-title">
                                <a class="title" href="#" target="_blank">意大利费列罗进口食品巧克力零食24粒  进口巧克力食品</a>
                            </div>
                            <div class="goods-price">
										<span class="g_price">
                                        <span>¥</span><strong>71</strong>
										</span>
                                <span class="g_price g_price-original">
                                        <span>¥</span><strong>142</strong>
										</span>
                            </div>
                            <div class="clear"></div>
                            <div class="goods-num">
                                <div class="match-recom">
                                    <a href="#" class="match-recom-item">找相似</a>
                                    <a href="#" class="match-recom-item">找搭配</a>
                                    <i><em></em><span></span></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>--%>


                <div class="clear"></div>





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