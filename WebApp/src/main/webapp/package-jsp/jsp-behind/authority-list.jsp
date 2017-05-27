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
    <title>权限查看</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
    <script type="text/javascript">
        function deleteAuthority() {
            var msg = "您确定要删除这条权限吗？\n\n请确认！";
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
    <div class="panel-head"><strong class="icon-reorder">权限查看</strong> </div>
    <div class="padding border-bottom">
        <ul class="search" style="padding-left:10px;">
            <li>
                <a href="<%=path %>/package-jsp/jsp-behind/authority-add.jsp" class="button border-main icon-search" >添加权限</a>
            </li>
        </ul>
    </div>
    <table class="table table-hover text-center">
        <tr>
            <th width="15%">权限编号</th>
            <th width="20%">权限名称</th>
            <th width="20%">权限备注</th>
            <th>权限使用状态</th>
            <th width="25%">操作</th>
        </tr>
        <c:forEach items="${authorityList }" var="authority">
            <tr>
                <td>${authority.authorityId}</td>
                <td>${authority.authorityName}</td>

                <td>
                    <c:if test="${authority.authorityNote == null}">
                        <font color="#a9a9a9"><c:out value="暂无备注信息"></c:out></font>
                    </c:if>
                    <c:if test="${authority.authorityNote != null}">
                        <font color="red"><c:out value="${authority.authorityNote }"></c:out></font>
                    </c:if>
                </td>
                <td>
                    <c:if test="${authority.authorityState == 0}">
                        <font color="#9acd32"><c:out value="角色已停止使用"></c:out></font>
                    </c:if>
                    <c:if test="${authority.authorityState != 0}">
                        <font color="#006400"><c:out value="角色正在使用"></c:out></font>
                    </c:if>
                </td>
                <td width="">
                    <div class="button-group">
                        <c:if test="${authority.authorityState == 0}">
                            <a class="button border-green" href="<%=path %>/achievement/.do?achievementId=${authority.authorityId}">开始使用</a>
                        </c:if>
                        <c:if test="${authority.authorityState != 0}">
                            <a class="button border-red" href="javascript:void(0)">停止使用</a>
                        </c:if>
                        <a class="button border-red" href="<%=path %>/authority/deleteAurhority.do?authorityId=${authority.authorityId}" onclick="javascript:return deleteAuthority()">删除权限</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>