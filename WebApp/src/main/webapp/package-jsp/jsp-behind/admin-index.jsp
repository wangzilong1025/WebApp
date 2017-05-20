<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>后台管理中心</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
</head>
<body style="background-color:#f2f9fd;">
<div class="header bg-main">
    <div class="logo margin-big-left fadein-top">
        <c:choose>
            <c:when test="${sessionScope.admin.adminImage==null}">
                <h1><img src="<%=path %>/package-style/style-behind/images/y.jpg" class="radius-circle rotate-hover" height="50" alt="" />后台管理中心</h1>
            </c:when>
            <c:otherwise>
                <h1><img src="<%=path %>/adminImage/${sessionScope.admin.adminImage}" class="radius-circle rotate-hover" height="50" alt="" />后台管理中心</h1>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="head-l">
        <a class="button button-little bg-green" href="<%=path %>/adminLogin.do" target="_blank"><span class="icon-home"></span> 前台首页</a> &nbsp;&nbsp;
        <a href="##" class="button button-little bg-blue"><span class="icon-wrench"></span> 清除缓存</a> &nbsp;&nbsp;
        <a class="button button-little bg-red" href="<%=path %>/package-jsp/jsp-behind/admin-login.jsp"><span class="icon-power-off"></span> 退出登录</a> &nbsp;&nbsp;
        <a style="float: right" class="button button-little bg-blue"><span>欢迎您,${sessionScope.admin.adminName }</span></a>
    </div>
</div>
<div class="leftnav">
    <div class="leftnav-title"><strong><span class="icon-list"></span>菜单列表</strong></div>
    <h2><span class="icon-user"></span>基本设置</h2>
    <ul style="display:block">
        <li><a href="<%=path %>/selectAdminInfo.do" target="right"><span class="icon-caret-right"></span>个人信息</a></li>
        <li><a href="<%=path %>/adminApplicationAuthority.do" target="right"><span class="icon-caret-right"></span>申请权限</a></li>
        <li><a href="book.html" target="right"><span class="icon-caret-right"></span>留言管理</a></li>
    </ul>
    <h2><span class="icon-pencil-square-o"></span>公告管理</h2>
    <ul>
        <li><a href="<%=path %>/notice/queryAllNoticeByStatus.do" target="right"><span class="icon-caret-right"></span>发布的公告</a></li>
        <li><a href="<%=path %>/package-jsp/jsp-behind/notice-add.jsp" target="right"><span class="icon-caret-right"></span>添加公告</a></li>
    </ul>
    <h2><span class="icon-pencil-square-o"></span>科研成果管理</h2>
    <ul>
        <li><a href="<%=path %>/achievement/queryAllApproveAchievement.do" target="right"><span class="icon-caret-right"></span>成果待审批</a></li>
        <li><a href="<%=path %>/achievement/queryAllReleasedAchievement.do" target="right"><span class="icon-caret-right"></span>成果已通过</a></li>
        <li><a href="<%=path %>/achievement/queryAllUnreleasedAchievement.do" target="right"><span class="icon-caret-right"></span>成果未通过</a></li>
        <li><a href="add.html" target="right"><span class="icon-caret-right"></span>成果搜索</a></li>
    </ul>
    <h2><span class="icon-pencil-square-o"></span>管理员管理</h2>
    <ul>
        <li><a href="list.html" target="right"><span class="icon-caret-right"></span>全限管理</a></li>
        <li><a href="add.html" target="right"><span class="icon-caret-right"></span>管理员列表</a></li>
    </ul>
</div>
<script type="text/javascript">
    $(function(){
        $(".leftnav h2").click(function(){
            $(this).next().slideToggle(200);
            $(this).toggleClass("on");
        })
        $(".leftnav ul li a").click(function(){
            $("#a_leader_txt").text($(this).text());
            $(".leftnav ul li a").removeClass("on");
            $(this).addClass("on");
        })
    });
</script>
<ul class="bread">
    <li><a href="{:U('Index/info')}" target="right" class="icon-home"> 首页</a></li>
    <li><a href="##" id="a_leader_txt">网站信息</a></li>
    <li><b>当前语言：</b><span style="color:red;">中文</span>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;切换语言：<a href="##">中文</a> &nbsp;&nbsp;<a href="##">英文</a> </li>
</ul>
<div class="admin">
    <iframe scrolling="auto" rameborder="0" src="<%=path %>/package-jsp/jsp-behind/admin-main.jsp" name="right" width="100%" height="100%"></iframe>
</div>
</body>
</html>