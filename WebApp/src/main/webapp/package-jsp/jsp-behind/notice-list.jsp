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
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
    <script type="text/javascript">
        function deleteNotice() {
            var msg = "您确定要删除这条公告吗？\n\n请确认！";
            if (confirm(msg)==true){
                return true;
            }else{
                return false;
            }
        }
    </script>
</head>
<body>
<form method="post" action="" id="listform">
    <div class="panel admin-panel">
        <div class="panel-head"><strong class="icon-reorder">发布的公告</strong> <a href="" style="float:right; display:none;">添加字段</a></div>
        <div class="padding border-bottom">
            <ul class="search" style="padding-left:10px;">
                <li> <a class="button border-main icon-plus-square-o" href="<%=path %>/package-jsp/jsp-behind/notice-add.jsp"> 添加内容</a> </li>
                <li>搜索：</li>
                <li>
                    <input type="text" placeholder="请输入搜索关键字" name="keywords" class="input" style="width:250px; line-height:17px;display:inline-block" />
                    <a href="javascript:void(0)" class="button border-main icon-search" onclick="changesearch()" > 搜索</a>
                </li>
            </ul>
        </div>
        <table class="table table-hover text-center">
            <tr>
                <th width="16%">公告标题</th>
                <th width="16%">创建时间</th>
                <th width="6%">创建人</th>
                <th width="16%">发布时间</th>
                <th width="16%">结束时间</th>
                <th>状态</th>
                <th width="25%">操作</th>
            </tr>
                <c:forEach items="${noticeList }" var="list">
                    <tr>
                        <td hidden="hidden">${list.noticeId}</td>
                        <td>${list.noticeTitle}</td>
                        <td>${list.createTimeStr}</td>
                        <td>${list.adminName}</td>
                        <td><font color="#00CC99">${list.noticeReleaseTimeStr}</font></td>
                        <td><font color="red">${list.noticeEndTimeStr}</font></td>
                        <td>
                            <c:if test="${list.noticeStatus ==1}">
                                <c:out value="使用中"></c:out>
                            </c:if>
                            <c:if test="${list.noticeStatus != 1}">
                                <c:out value="未使用"></c:out>
                            </c:if>
                        </td>
                        <td width="">
                            <div class="button-group">
                                <c:forEach items="${map}" var="mapp">
                                    <c:if test="${(mapp.authorityId ==3) || (mapp.authorityId==2) || (mapp.authorityId==4)}">
                                        <c:if test="${mapp.authorityId==2}">
                                            <a class="button border-main" href="<%=path %>/notice/selectNoticeByIdForUpdate.do?noticeId=${list.noticeId}"><span class="icon-edit"></span> 修改</a>
                                        </c:if>
                                        <c:if test="${mapp.authorityId==4}">
                                            <a class="button border-green" href="<%=path %>/notice/selectNoticeById.do?noticeId=${list.noticeId}"><span class="icon-book"></span> 详情</a>
                                        </c:if>
                                        <c:if test="${mapp.authorityId ==3}">
                                            <a class="button border-red" href="<%=path %>/notice/deleteNoticeByNoticeId.do?noticeId=${list.noticeId}" onclick="javascript:return deleteNotice()"><span class="icon-trash-o"></span> 删除</a>
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
</form>
</body>
</html>