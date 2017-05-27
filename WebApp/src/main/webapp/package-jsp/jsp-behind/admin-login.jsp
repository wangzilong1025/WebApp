<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <base href="<%=basePath%>"/>
    <meta charset="utf-8">
    <title>科研成果------后台登录页面</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <%--<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>--%>
    <link rel="stylesheet" href="<%=path%>/package-style/style-behind/admin-login-style/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/package-style/style-behind/admin-login-style/css/supersized.css">
    <link rel="stylesheet" href="<%=path%>/package-style/style-behind/admin-login-style/css/style.css">
    <%--<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>--%>
</head>
<body>
<div class="page-container">
    <input type="hidden" value="${message}" id="message">
    <h1>Admin Login</h1>
    <form id="adminLogin" action="<%=path %>/adminLogin.do" method="post">
        <input autocomplete="off" type="text" name="adminName" class="username" placeholder="AdminName"/>
        <input autocomplete="off" type="password" name="adminPassword" class="password" placeholder="Password"/>
        <button type="submit">登录</button>
        <div class="error"><span>+</span></div>
    </form>
    <div class="connect">
        <p>Or Regist:</p>
        <p>
            <a class="twitter" href="<%=path %>/package-jsp/jsp-behind/admin-regist.jsp"></a>
        </p>
    </div>
</div>

<script src="<%=path%>/package-style/style-behind/admin-login-style/js/jquery-1.8.2.min.js"></script>
<script src="<%=path%>/package-style/style-behind/admin-login-style/js/supersized.3.2.7.min.js"></script>
<script src="<%=path%>/package-style/style-behind/admin-login-style/js/supersized-init.js"></script>
<script src="<%=path%>/package-style/style-behind/admin-login-style/js/scripts.js"></script>
</body>
</html>

