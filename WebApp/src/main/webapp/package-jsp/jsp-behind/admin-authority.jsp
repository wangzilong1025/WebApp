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
    <title>我的权限</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head"><strong><span class="icon-pencil-square-o"></span>我的权限</strong></div>
    <div class="body-content">
        <form method="post" id="roleAdd" class="form-x" action="<%=path %>/role/addRoleInfo.do">
            <div class="form-group">
                <div class="label">
                    <label>管理员名：</label>
                </div>
                <div class="field" style="padding-top: 8px;">
                    <span  >${admin.adminName}</span>
                    <div class="tips"></div>
                </div>
            </div>

            <div class="form-group">
                <div class="label">
                    <label>真实姓名：</label>
                </div>
                <div class="field" style="padding-top: 8px;">
                    <c:if test="${admin.realName == null}">
                        <span><font color="#a9a9a9"><c:out value="暂无信息"></c:out></font></span>
                    </c:if>
                    <c:if test="${admin.realName != null}">
                        <span><c:out value="${admin.realName }"></c:out></span>
                    </c:if>
                    <div class="tips"></div>
                </div>
            </div>

            <div class="form-group">
                <div class="label">
                    <label>当前拥有权限：</label>
                </div>
                <div class="field" style="padding-top: 8px;">
                    <span>${role.roleName}</span>
                    <div class="tips"></div>
                </div>
            </div>
        </form>
    </div>

</div>
<div class="panel admin-panel">
    <table class="table table-hover text-center">
        <tr>
            <th width="15%">权限编号</th>
            <th width="20%">权限名称</th>
            <th width="20%">权限备注</th>
            <th>权限状态</th>
        </tr>
        <c:forEach items="${adminAuthorities }" var="adminAuthorities">
            <tr style="height: 59.2px;">
                <td>${adminAuthorities.authorityId}</td>
                <td>${adminAuthorities.authorityName}</td>
                <td>
                    <c:if test="${adminAuthorities.authorityNote == null}">
                        <font color="#a9a9a9"><c:out value="暂无权限备注信息"></c:out></font>
                    </c:if>
                    <c:if test="${adminAuthorities.authorityNote != null}">
                        <font color="red"><c:out value="${adminAuthorities.authorityNote }"></c:out></font>
                    </c:if>
                </td>
                <td>
                    <c:if test="${adminAuthorities.authorityState == 0}">
                        <font color="#9acd32"><c:out value="权限已停止使用"></c:out></font>
                    </c:if>
                    <c:if test="${adminAuthorities.authorityState != 0}">
                        <font color="#006400"><c:out value="权限允许使用"></c:out></font>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>