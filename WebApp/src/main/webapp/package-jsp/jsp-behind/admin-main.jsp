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
    <title></title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/package-style/style-behind/canvas/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/package-style/style-behind/canvas/css/demo.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
</head>
<body>
<div class="panel admin-panel margin-top">
    <!-- 数字时钟开始-->
    <div class="htmleaf-content" style="float: right;width: 300px;max-width: 300px;">
        <canvas id="clock1_" width="300px" height="300px" style="float: right">
        </canvas>
    </div>
    <script type="text/javascript" src="<%=path %>/package-style/style-behind/canvas/js/canvas_clock.js"></script>
    <script type="text/javascript">
        clockd1_={
            "indicate": true,
            "indicate_color": "#222",
            "dial1_color": "#666600",
            "dial2_color": "#81812e",
            "dial3_color": "#9d9d5c",
            "time_add": 1,
            "time_24h": true,
            "date_add":3,
            "date_add_color": "#999",
        };
        var c = document.getElementById('clock1_');
        cns1_ = c.getContext('2d');
        clock_conti(300,cns1_,clockd1_);

    </script>

</div>
</body></html>