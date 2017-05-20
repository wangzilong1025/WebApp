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
    <title>申请权限</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
    <script type="text/javascript">
        function test(){
            var roleId = $("#roleId").val();
            if(roleId != "" && roleId != null){
                document.getElementById("applicationAuthority").submit();
            }else{
                alert("请选择将要申请的管理员角色!")
                return false;
            }

        }

    </script>
</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>管理员信息</strong></div>
    <div class="body-content">
        <form id="applicationAuthority" method="post" class="form-x" action="<%=path %>/adminRole/updateAdminRole.do">
            <div class="form-group">
                <div class="label">
                    <label>真实姓名：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" value="${adminInfo.realName}" name="realName" style="width: 40%;" readonly="readonly"/>
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label">
                    <label>邮箱地址：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" value="${adminInfo.adminEmail}" name="adminEmail" style="width: 40%;" readonly="readonly"/>
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label">
                    <label>手机号码：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" value="${adminInfo.adminPhone}" name="adminPhone" style="width: 40%;" readonly="readonly"/>
                    <div class="tips"></div>
                </div>
            </div>

            <div class="form-group">
                <div class="label">
                    <label>管理员角色：</label>
                </div>
                <div class="field">
                    <select id="roleId" name="roleId" class="input w50" style="width: 40%;">
                        <option value="">请选择申请的角色</option>
                        <option value="3">经理</option>
                        <option value="2">管理员</option>
                        <option value="1">超级管理员</option>
                    </select>
                    <div class="tips"></div>
                </div>
            </div>

            <div class="form-group">
                <div class="label">
                    <label></label>
                </div>
                <div class="field">
                    <button class="button bg-main icon-check-square-o" type="button" onclick="test()"> 提交</button>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>