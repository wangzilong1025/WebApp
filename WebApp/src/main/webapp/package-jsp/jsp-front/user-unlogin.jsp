<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">
    <title>去登录</title>
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
    <script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/jquery.min.js" type="text/javascript"></script>
    <script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/amazeui.js" type="text/javascript"></script>
    <link href="<%=path %>/package-style/style-front/basic/css/demo.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/package-style/style-front/css/cartstyle.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/package-style/style-front/css/jsstyle.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/package-style/style-front/js/address.js"></script>
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

<div class="center">
    <div class="col-main">
        <div class="main-wrap" style="margin-left: 0px;">
            <div class="paycont">
                <div class="address">
                    <h2>亲，您还没有登录或注册呢！！！ </h2>
                    <div class="control">
                        <div id="user-login" class="tc-btn createAddr theme-login am-btn am-btn-danger">立即登录</div>
                    </div>
                    <div class="control">
                        <div id="user-regist" class="tc-btn createAddr theme-login am-btn am-btn-danger">立即注册</div>
                    </div>
                    <div class="clear"></div>
                    <ul>
                        <div class="per-border"></div>
                        <div class="per-border"></div>
                    </ul>
                    <div class="clear"></div>
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
        <div class="theme-popover-mask"></div>
        <div class="theme-popover">
            <!--标题 -->
            <div class="am-cf am-padding">
                <div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">登录</strong> / <small>User Login</small></div>
            </div>
            <hr/>
            <div class="am-u-md-12">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label for="user-name" class="am-form-label">收货人</label>
                        <div class="am-form-content">
                            <input type="text" id="user-name" placeholder="收货人">
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label for="user-phone" class="am-form-label">手机号码</label>
                        <div class="am-form-content">
                            <input id="user-phone" placeholder="手机号必填" type="email">
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label for="user-phone" class="am-form-label">所在地</label>
                        <div class="am-form-content address">
                            <select data-am-selected>
                                <option value="a">浙江省</option>
                                <option value="b">湖北省</option>
                            </select>
                            <select data-am-selected>
                                <option value="a">温州市</option>
                                <option value="b">武汉市</option>
                            </select>
                            <select data-am-selected>
                                <option value="a">瑞安区</option>
                                <option value="b">洪山区</option>
                            </select>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label for="user-intro" class="am-form-label">详细地址</label>
                        <div class="am-form-content">
                            <textarea class="" rows="3" id="user-intro" placeholder="输入详细地址"></textarea>
                            <small>100字以内写出你的详细地址...</small>
                        </div>
                    </div>
                    <div class="am-form-group theme-poptit">
                        <div class="am-u-sm-9 am-u-sm-push-3">
                            <div class="am-btn am-btn-danger">保存</div>
                            <div class="am-btn am-btn-danger close">取消</div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>