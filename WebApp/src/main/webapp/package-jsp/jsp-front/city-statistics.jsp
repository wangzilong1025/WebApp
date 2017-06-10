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
    <title>统计-柱状图</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>

    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
    <!--地区统计图样式开始-->
    <link rel="stylesheet" type="text/css" href="<%=path %>/package-style/style-statistics/style.css">
    <script src="<%=path %>/package-style/style-statistics/jquery.min.js"></script>
    <script src="<%=path %>/package-style/style-statistics/zingchart.min.js"></script>
    <script src="<%=path %>/package-style/style-statistics/zingchart.jquery.js"></script>
    <!--地区统计图样式结束-->
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
            <h1 style="text-align: center;size: 20px;" align="center"><font size="20px;">2016年各地区统计---柱状图</font></h1>


            <table class="table table-hover text-center">
                <tr>
                    <th width="200px" style="font-weight: bold; padding-top: 30px;padding-bottom: 30px;">发布地区</th>
                    <th width="300px" style="font-weight: bold; padding-top: 30px;padding-bottom: 30px;">年份</th>
                    <th width="500px;" style="font-weight: bold; padding-top: 30px;padding-bottom: 30px;">科研成果柱状图统计</th>
                </tr>
                <c:forEach items="${statisticsVoList}" var="list">
                    <tr align="center" style="text-align: center">
                        <td style="align-content: center"><font color="#00CC99" style="font-size: 14px; font-weight: bold">${list.cityName }</font></td>
                        <td style="text-align: center;padding-bottom: 20px;" align="center">
                               ${2016}
                        </td>
                        <td >
                            <table width="600px;" height="150px;" border="1px;" align="center" bordercolor="blue" rules="rows" bgcolor="#f5f5f5">
                                <tr>
                                    <th>
                                        <div id="${list.cityId}" class="zingchart"></div>
                                        <script>
                                            $(document).ready( function() {
                                                var a = ${list.year_Ach_Count};
                                                console.log(a);
                                                var ex1 = {
                                                    "type": "bar",
                                                    "legend":{},
                                                    "series": [
                                                        {
                                                            "values":a
                                                        }
                                                    ]
                                                };
                                                $('#${list.cityId }').zingchart({
                                                    data:ex1
                                                });
                                                function makeLabel(x) {
                                                    return {
                                                        "visible":1,
                                                        "id":"label1",
                                                        "text":x.event.value,
                                                        "font-size":"20px",
                                                        "color":"white",
                                                        "background-color":"#444",
                                                        "callout":1,
                                                        "shadow":0,
                                                        "border-radius":5,
                                                        "width":10,
                                                        "padding":6,
                                                        "offset-y":-30,
                                                        "hook":"node:plot="+x.event.plotindex+";index="+x.event.nodeindex
                                                    };
                                                }
                                            });
                                        </script>
                                    </th>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </article>
        <hr class="am-article-divider blog-hr">
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
</body>
</html>
