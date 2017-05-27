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
    <title>角色查看</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
    <script type="text/javascript">
        function deleteRole() {
            var msg = "您确定要删除该角色吗？\n\n请确认！";
            if (confirm(msg)==true){
                return true;
            }else{
                return false;
            }
        }

        function stopRole() {
            var msg = "您确定要停止使用该角色吗？\n\n请确认！";
            if (confirm(msg)==true){
                return true;
            }else{
                return false;
            }
        }

        function startRole() {
            var msg = "您确定要重新使用该角色吗？\n\n请确认！";
            if (confirm(msg)==true){
                return true;
            }else{
                return false;
            }
        }
    </script>
</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head"><strong class="icon-reorder">角色查看</strong> </div>
    <div class="padding border-bottom">
        <ul class="search" style="padding-left:10px;">
            <li>
                <a href="<%=path %>/package-jsp/jsp-behind/role-add.jsp" class="button border-main icon-search" >添加角色</a>
            </li>
        </ul>
    </div>
    <table class="table table-hover text-center">
        <tr>
            <th width="15%">角色编号</th>
            <th width="20%">角色名称</th>
            <th width="20%">拥有权限</th>
            <th>角色使用状态</th>
            <th width="25%">操作</th>
        </tr>
        <c:forEach items="${role }" var="role">
            <tr>
                <td>${role.roleId}</td>
                <td>${role.roleName}</td>

                <td>
                    <c:if test="${role.roleNote == null}">
                        <font color="#a9a9a9"><c:out value="暂无备注信息"></c:out></font>
                    </c:if>
                    <c:if test="${role.roleNote != null}">
                        <font color="red"><c:out value="${role.roleNote }"></c:out></font>
                    </c:if>
                </td>
                <td>
                    <c:if test="${role.roleState == 0}">
                        <font color="#9acd32"><c:out value="角色已停止使用"></c:out></font>
                    </c:if>
                    <c:if test="${role.roleState != 0}">
                        <font color="#006400"><c:out value="角色正在使用"></c:out></font>
                    </c:if>
                </td>
                <td width="">
                    <div class="button-group">
                        <c:if test="${role.roleState == 0}">
                            <a class="button border-green" href="<%=path %>/role/startRoleUsed.do?roleId=${role.roleId}" onclick="javascript:return startRole()">开始使用</a>
                        </c:if>
                        <c:if test="${role.roleState != 0}">
                            <a class="button border-red" href="<%=path %>/role/stopRoleUsed.do?roleId=${role.roleId}" onclick="javascript:return stopRole()">停止使用</a>
                        </c:if>
                        <a class="button border-red" href="<%=path %>/role/deleteRoleInfo.do?roleId=${role.roleId}" onclick="javascript:return deleteRole()">删除角色</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>