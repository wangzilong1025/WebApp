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
    <title>管理人员信息</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>管理人员信息</strong></div>
    <div class="body-content">
        <form method="post" class="form-x" action="<%=path %>/updateAdminInfo.do ">
            <div class="form-group">
                <div class="label">
                    <label>管理员名称：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" readonly="readonly" value="${adminInfo.adminName}" name="adminName" data-validate="required:请输入管理员名称" />
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label">
                    <label>真实姓名：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" value="${adminInfo.realName}" name="realName" data-validate="required:请输入真实姓名" />
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label">
                    <label>邮箱地址：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" value="${adminInfo.adminEmail}" name="adminEmail" data-validate="required:请输入邮箱地址" />
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label">
                    <label>手机号码：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" value="${adminInfo.adminPhone}" name="adminPhone" data-validate="required:请输入手机号码" />
                    <div class="tips"></div>
                </div>
            </div>

            <div class="form-group">
                <div class="label">
                    <label>常住地址：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" value="${adminInfo.adminAddress}" name="adminAddress" data-validate="required:请输入常住地址" />
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label">
                    <label></label>
                </div>
                <div class="field">
                    <button class="button bg-main icon-check-square-o" type="submit"> 提交</button>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>