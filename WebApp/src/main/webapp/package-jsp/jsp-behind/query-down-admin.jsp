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
    <title>下级管理人员</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
    <script type="text/javascript">
        function startAdminUsed() {
            var msg = "您确定要启用该管理人员账号吗？\n\n请确认！";
            if (confirm(msg)==true){
                return true;
            }else{
                return false;
            }
        }

        function stopAdminUsed() {
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
    <div class="panel-head"><strong class="icon-reorder">下级管理人员</strong> <a href="" style="float:right; display:none;">添加字段</a></div>
    <div class="padding border-bottom">
        <ul class="search" style="padding-left:10px;">
            <li>搜索：</li>
            <li>
                <form method="post" action="<%=path %>/menu/searchAchievementForBehind.do" id="searchAch">
                    <input type="text" id="searchContent" placeholder="输入管理人员名称/真实姓名/编号" name="searchContent" class="input" style="width:250px; line-height:17px;display:inline-block" />
                    <a href="javascript:void(0)" class="button border-main icon-search" onclick="searchAchievement()" > 搜索</a>
                </form>
            </li>
        </ul>
    </div>
    <table class="table table-hover text-center">
        <tr>
            <th width="8%">管理员编号</th>
            <th width="8%">管理员名称</th>
            <th width="12%">真实姓名</th>
            <th width="12%">邮箱</th>
            <th width="12%">联系方式</th>
            <th>账号状态</th>
            <th width="14%">当前角色</th>
            <th width="20%">操作</th>
        </tr>
        <c:forEach items="${adminVos }" var="admin">
            <tr>
                <td>${admin.adminId}</td>
                <td>${admin.adminName}</td>
                <td>
                    <c:if test="${admin.realName == null}">
                        <font color="#a9a9a9"><c:out value="暂无信息"></c:out></font>
                    </c:if>
                    <c:if test="${admin.realName != null}">
                        <c:out value="${admin.realName }"></c:out>
                    </c:if>
                </td>
                <td>
                    <c:if test="${admin.adminEmail == null}">
                        <font color="#a9a9a9"><c:out value="暂无信息"></c:out></font>
                    </c:if>
                    <c:if test="${admin.adminEmail != null}">
                        <font color="#00CC99"><c:out value="${admin.adminEmail }"></c:out></font>
                    </c:if>
                </td>
                <td>
                    <c:if test="${admin.adminPhone == null}">
                        <font color="#a9a9a9"><c:out value="暂无信息"></c:out></font>
                    </c:if>
                    <c:if test="${admin.adminPhone != null}">
                        <font color="red"><c:out value="${admin.adminPhone }"></c:out></font>
                    </c:if>
                </td>
                <td>
                    <c:if test="${admin.adminStatus != 0}">
                        <font color="#9acd32"><c:out value="允许使用"></c:out></font>
                    </c:if>
                    <c:if test="${admin.adminStatus == 0}">
                        <font color="#006400"><c:out value="禁止使用"></c:out></font>
                    </c:if>
                </td>
                <td>
                    <c:if test="${admin.roleId == 1}">
                        <font color="#9acd32"><c:out value="超级管理员"></c:out></font>
                    </c:if>
                    <c:if test="${admin.roleId == 2}">
                        <font color="#006400"><c:out value="管理员"></c:out></font>
                    </c:if>
                    <c:if test="${admin.roleId == 3}">
                        <font color="#006400"><c:out value="经理"></c:out></font>
                    </c:if>
                    <c:if test="${admin.roleId == 4}">
                        <font color="#006400"><c:out value="暂无权限人员"></c:out></font>
                    </c:if>
                </td>
                <td width="">
                    <div class="button-group">
                        <a class="button border-main" href="<%=path %>/selectAdminInfoAndAuthorityAndRole.do?adminId=${admin.adminId}">管理员详情</a>&nbsp;&nbsp;
                        <c:forEach items="${map}" var="mapp">
                            <c:if test="${(mapp.authorityId ==3)}">
                                <c:if test="${admin.adminStatus == 0}">
                                    <a class="button border-green" href="<%=path %>/stopOrStartAdminUsed.do?adminId=${admin.adminId}&adminStatus=${admin.adminStatus}" onclick="javascript:return startAdminUsed()">允许使用</a>
                                </c:if>
                                <c:if test="${admin.adminStatus == 1}">
                                    <a class="button border-red" href="<%=path %>/stopOrStartAdminUsed.do?adminId=${admin.adminId}&adminStatus=${admin.adminStatus}" onclick="javascript:return stopAdminUsed()">停止使用</a>
                                </c:if>
                            </c:if>
                        </c:forEach>
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