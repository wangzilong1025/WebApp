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
    <title>科研成果待审核</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
    <script type="text/javascript">
        function agreeAchievement() {
            var msg = "您确定已经审阅过这项科研成果,并且同意通过吗？\n\n请选择！";
            if (confirm(msg)==true){
                return true;
            }else{
                return false;
            }
        }
        function notAgreeAchievement() {
            var msg = "您确定已经审阅过这项科研成果,并且不同意通过吗？\n\n请选择！";
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
        <div class="panel-head"><strong class="icon-reorder">科研成果待审核</strong> <a href="" style="float:right; display:none;">添加字段</a></div>
        <div class="padding border-bottom">
            <ul class="search" style="padding-left:10px;">
                <li>搜索：</li>
                <li>
                    <input type="text" placeholder="请输入搜索关键字" name="keywords" class="input" style="width:250px; line-height:17px;display:inline-block" />
                    <a href="javascript:void(0)" class="button border-main icon-search" onclick="changesearch()" > 搜索</a>
                </li>
            </ul>
        </div>
        <table class="table table-hover text-center">
            <tr>
                <th width="10%">成果编号</th>
                <th width="20%">成果名称</th>
                <th width="6%">发布人</th>
                <th width="12%">成果类型</th>
                <th width="12%">发布城市</th>
                <th>部门名称</th>
                <th width="25%">操作</th>
            </tr>
            <c:forEach items="${approveAchievementList }" var="list">
                <tr>
                    <td>${list.achievementId}</td>
                    <td>${list.achievementName}</td>
                    <td>${list.unitName}</td>
                    <td><font color="#00CC99">${list.achievementTypeName}</font></td>
                    <td><font color="red">${list.cityTypeName}</font></td>
                    <td>${list.unitName}</td>
                    <td width="">
                        <div class="button-group">
                            <a class="button border-main" href="<%=path %>/achievement/achievementDetailApprove.do?achievementId=${list.achievementId}"><span></span> 详情</a>
                            <a class="button border-green" href="<%=path %>/achievement/achievementAgreementPass.do?achievementId=${list.achievementId}" onclick="javascript:return agreeAchievement()"><span></span> 通过</a>
                            <a class="button border-red" href="<%=path %>/achievement/achievementAgreementNotPass.do?achievementId=${list.achievementId}" onclick="javascript:return notAgreeAchievement()"><span></span>不通过 </a>
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