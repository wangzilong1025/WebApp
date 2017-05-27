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
    <title>科研成果搜索</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
    <script type="text/javascript">
        function searchAchievement() {
            document.getElementById("searchAch").submit();
        }
    </script>
</head>
<body>

    <div class="panel admin-panel">
        <div class="panel-head"><strong class="icon-reorder">科研成果搜索</strong></div>
        <div class="padding border-bottom">
            <ul class="search" style="padding-left:10px;">
                <li>搜索：</li>
                <li>
                    <form method="post" action="<%=path %>/menu/searchAchievementForBehind.do" id="searchAch">
                        <input type="text" id="searchContent" placeholder="输入成果标题/发布人/单位名称" name="searchContent" class="input" style="width:250px; line-height:17px;display:inline-block" />
                        <a href="javascript:void(0)" class="button border-main icon-search" onclick="searchAchievement()" > 搜索</a>
                    </form>
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
                <c:forEach items="${searchList}" var="achievementList">
                    <tr>
                        <td>${achievementList.achievementId}</td>
                        <td>${achievementList.achievementName}</td>
                        <td>${achievementList.unitName}</td>
                        <td><font color="#00CC99">${achievementList.achievementTypeName}</font></td>
                        <td><font color="red">${achievementList.cityTypeName}</font></td>
                        <td>${achievementList.unitName}</td>
                        <td width="">
                            <div class="button-group">
                                <a class="button border-main" href="<%=path %>/achievement/achievementDetailApprove.do?achievementId=${achievementList.achievementId}"><span class="icon-edit"></span> 详情</a>
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