<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>公告详情</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
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
<!--文章 -->
<div class="am-g am-g-fixed blog-g-fixed bloglist">
    <div class="am-u-md-9">
        <article class="blog-main">
            <h3 class="am-article-title blog-title">
                <a href="#">${notice.noticeTitle}</a>
            </h3>
            <h4 class="am-article-meta blog-meta">${notice.createTimeStr}</h4>
            <c:if test="${notice.noticeType == 1}">
                <h4 class="am-article-meta blog-meta">普通公告</h4>
            </c:if>
            <c:if test="${notice.noticeType == 2}">
                <h4 class="am-article-meta blog-meta">系统公告</h4>
            </c:if>
            <c:if test="${notice.noticeType == 3}">
                <h4 class="am-article-meta blog-meta">系统通知</h4>
            </c:if>

            <div class="am-g blog-content">
                <div class="am-u-sm-12">
                    <p>${notice.noticeContent}</p>
                    <p>5月16日上午，为加速科技成果转化，助推国家科技成果转化服务（太原）示范基地建设，受太原市科技局邀请，国家科技成果网在太原举行科技成果推介发布会，共推出300项国家科技成果。</p>
                    <div class="Row">
                        <li><img src="<%=path %>/package-style/style-front/images/blog01.jpg"/></li>
                        <li><img src="<%=path %>/package-style/style-front/images/blog02.jpg"/></li>
                        <li><img src="<%=path %>/package-style/style-front/images/blog03.jpg"/></li>
                    </div>
                    <p>国家科技成果转化服务（太原）示范基地于2013年8月正式获得批复，基地按照“科技创新、应用转化、技术服务”三位一体的运行模式，不断完善全链条、多要素、网络化的科技成果转化服务体系，围绕装备制造、新材料、节能环保、生物医药、电子信息及物联网技术应用、新能源汽车等战略性新兴产业，建立和壮大了一批产业特点突出、技术水平领先、示范效应显著的科技成果转化示范项目和企业，进一步增强了科技创新对转型发展的支撑力和贡献度。此次国家科技成果转化服务（太原）示范基地成果推介发布活动中，现场发布电子信息、先进制造、新材料、节能环保等领域科技成果300项。项目贴近太原市优势产业，技术先进，成熟度高，产业化前景好，并具有明确的转化需求。</p>
                    <p>目前，太原示范基地与国家科技成果网签订长期合作协议，按照下一步太原示范基地建设的工作安排，国家科技成果网将作好针对性的服务，为太原示范基地提供项目对接、信息咨询、科技专家人才引进等方面的支持。</p>
                </div>
            </div>
        </article>
        <hr class="am-article-divider blog-hr">
        <ul class="am-pagination blog-pagination">
            <li class="am-pagination-prev"><a href="">&laquo; 上一页</a></li>
            <li class="am-pagination-next"><a href="">下一页 &raquo;</a></li>
        </ul>
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

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="{{assets}}js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/amazeui.min.js"></script>
</body>
</html>
