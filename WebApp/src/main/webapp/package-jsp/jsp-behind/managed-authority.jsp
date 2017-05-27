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
    <title>已授权管理人员</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
    <script type="text/javascript">
        function changeAdminStatus() {
            var msg = "您确定要禁用该管理人员账号吗？\n\n请确认！";
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
    <div class="panel-head"><strong class="icon-reorder">已授权管理人员</strong> <a href="" style="float:right; display:none;">添加字段</a></div>
    <table class="table table-hover text-center">
        <tr>
            <th width="7">编号</th>
            <th width="15%">管理员名称</th>
            <th width="15%">真实姓名</th>
            <th width="15%">邮箱</th>
            <th width="15%">联系方式</th>
            <th width="10%">当前角色</th>
            <th width="20%">操作</th>
        </tr>
        <c:forEach items="${adminVos }" var="list">
            <tr>
                <td>${list.adminId}</td>
                <td>${list.adminName}</td>
                <td>
                    <c:if test="${list.realName == null}">
                        <font color="#a9a9a9"><c:out value="暂无信息"></c:out></font>
                    </c:if>
                    <c:if test="${list.realName != null}">
                        <c:out value="${list.realName }"></c:out>
                    </c:if>
                </td>
                <td>
                    <c:if test="${list.adminEmail == null}">
                        <font color="#a9a9a9"><c:out value="暂无信息"></c:out></font>
                    </c:if>
                    <c:if test="${list.adminEmail != null}">
                        <font color="#00CC99"><c:out value="${list.adminEmail }"></c:out></font>
                    </c:if>
                </td>
                <td>
                    <c:if test="${list.adminPhone == null}">
                        <font color="#a9a9a9"><c:out value="暂无信息"></c:out></font>
                    </c:if>
                    <c:if test="${list.adminPhone != null}">
                        <font color="red"><c:out value="${list.adminPhone }"></c:out></font>
                    </c:if>
                </td>
                <td>
                    <c:if test="${list.roleId == 1}">
                        <font color="#9acd32"><c:out value="超级管理员"></c:out></font>
                    </c:if>
                    <c:if test="${list.roleId == 2}">
                        <font color="#006400"><c:out value="管理员"></c:out></font>
                    </c:if>
                    <c:if test="${list.roleId == 3}">
                        <font color="#006400"><c:out value="部门经理"></c:out></font>
                    </c:if>
                    <c:if test="${list.roleId == 4}">
                        <font color="#006400"><c:out value="暂无权限人员"></c:out></font>
                    </c:if>
                </td>

                <td>
                    <div class="button-group">
                        <a class="button border-main" href="<%=path %>/selectAdminInfoAndAuthorityAndRole.do?adminId=${list.adminId}">详情</a>&nbsp;&nbsp;
                        <a class="button border-red" href="<%=path %>/stopOrStartAdminBan.do?adminId=${list.adminId}" onclick="javascript:return changeAdminStatus()">删除管理人员</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="8"><div class="pagelist"> <a href="">上一页</a> <span class="current">1</span><a href="">2</a><a href="">3</a><a href="">下一页</a><a href="">尾页</a> </div></td>
        </tr>
    </table>
</div>

</body>
</html>