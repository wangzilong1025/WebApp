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
    <title>Admin Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- CSS -->
    <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
    <link rel="stylesheet" href="<%=path%>/package-style/style-behind/admin-login-style/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/package-style/style-behind/admin-login-style/css/supersized.css">
    <link rel="stylesheet" href="<%=path%>/package-style/style-behind/admin-login-style/css/style.css">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <script type="text/javascript">
        function passCon(){
            var pass = $("#adminpass").val();
            if(pass == ""){
                $("#message").html("请输入密码");
                $("#message").val("new value");
                return;
            }else{
                $("#message").html("");
                $("#message").val("");
            }
            var passCon = $("#adminpassrepeat").val();
            if(passCon == ""){
                $("#message").html("请输入确认密码");
                $("#message").val("new value");
                return;
            }else{
                $("#message").html("");
                $("#message").val("");
            }
            if(pass!=passCon){
                $("#message").html("两次密码输入不一致");
                $("#message").val("new value");
                return false;
            }else{
                $("#message").html("");
                return true;
            }
        }
        function validate(){
            var str1 = $("#adminname").val();
            var str3 = $("#adminpass").val();
            var str4 = $("#adminpassrepeat").val();
            var str5 = $("#message").val();
            if(str5 != "" || str1 == "" || str3 == "" || str4 == ""){
                $("#message1").html("请填写正确后再提交");
                $("#message").val("new value");
                return false;
            }else{
                $("#message").html("");
                $("#message").val("");
                $("#adminRegist").submit();
            }
        }
    </script>
</head>
<body>
<div class="page-container">
    <h1>Admin Login</h1>
    <form id="adminRegist" action="<%=path %>/addAdmin.do" method="post">
        <p>
            <span id="message" style="color:red;"></span>
            <span id="message1" style="color:red"></span>
        </p>
        <input autocomplete="off" id="adminname" type="text" name="adminName" class="username" placeholder="Username">
        <input autocomplete="off" id="adminpass" type="password" name="adminPassword" class="password" placeholder="Password" onblur="passCon()">
        <input autocomplete="off" id="adminpassrepeat" type="password" name="" class="password" placeholder="Password" onblur="passCon()">
        <%--<button type="submit">注册</button>--%>
        <span style="cursor: pointer"><input id="submitButton" onclick="validate()" type="button" name="" value="注册" style="background: green;cursor: pointer;"/></span>
        <div class="error"><span>+</span></div>
    </form>

</div>
<!-- Javascript -->
<script src="<%=path%>/package-style/style-behind/admin-login-style/js/jquery-1.8.2.min.js"></script>
<script src="<%=path%>/package-style/style-behind/admin-login-style/js/supersized.3.2.7.min.js"></script>
<script src="<%=path%>/package-style/style-behind/admin-login-style/js/supersized-init.js"></script>
<script src="<%=path%>/package-style/style-behind/admin-login-style/js/scripts.js"></script>
</body>
</html>

