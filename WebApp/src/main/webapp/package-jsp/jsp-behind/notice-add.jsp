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
        <title>网站信息</title>
        <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
        <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
        <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
        <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>

        <script type="text/javascript" src="<%=path %>/package-style/style-behind/time-style/jedate/jedate.js"></script>
        <script type="text/javascript" src="<%=path %>/package-style/style-behind/time-style/jedate/jedate.min.js"></script>
    </head>
    <body>
        <div class="panel admin-panel">
            <div class="panel-head"><strong><span class="icon-pencil-square-o"></span> 网站信息</strong></div>
            <div class="body-content">
                <form method="post" class="form-x" action="<%=path %>/notice/addNoticeByAdmin.do">

                    <div class="form-group">
                        <div class="label">
                            <label>公告标题：</label>
                        </div>
                        <div class="field">
                            <input type="text" class="input" name="noticeTitle" value="" data-validate="required:请输入公告标题"/>
                            <div class="tips"></div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="label">
                            <label>公告内容：</label>
                        </div>
                        <div class="field">
                            <input type="text" class="input" name="noticeContent" value="" data-validate="required:请输入公告内容"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="label">
                            <label>公告发布时间：</label>
                        </div>
                        <div class="field">
                            <input type="text" class="input" placeholder="请选择" id="noticeReleaseTimeStr" name="noticeReleaseTimeStr" value="" data-validate="required:请输入公告内容"/>
                            <div class="tips"></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="label">
                            <label>公告结束时间：</label>
                        </div>
                        <div class="field">
                            <input type="text" class="input" placeholder="请选择" id="noticeEndTimeStr" name="noticeEndTimeStr" value="" data-validate="required:请输入公告内容"/>
                            <div class="tips"></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="label">
                            <label>发布人：</label>
                        </div>
                        <div class="field">
                            <input type="text" class="input" readonly="readonly" name="adminName" value="${sessionScope.admin.adminName }" data-validate="required:请输入公告内容"/>
                        </div>
                    </div>
                    <div class="form-group" style="display:none;">
                        <div class="label">
                            <label>发布人ID：</label>
                        </div>
                        <div class="field">
                            <input type="text" class="input" name="adminId" value="${sessionScope.admin.adminId }" data-validate="required:请输入公告内容"/>
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
                <script type="text/javascript">
                    jeDate({
                        dateCell:"#noticeReleaseTimeStr",
                        format:"YYYY-MM-DD hh:mm:ss",
                        isinitVal:false,
                        isTime:true, //isClear:false,
                        minDate:"2014-09-19 00:00:00",
                        okfun:function(val){alert(val);}
                    });
                    jeDate({
                        dateCell:"#noticeEndTimeStr",
                        format:"YYYY-MM-DD hh:mm:ss",
                        isinitVal:false,
                        isTime:true, //isClear:false,
                        minDate:"2014-09-19 00:00:00",
                        okfun:function(val){alert(val);}
                    });
                </script>
            </div>
        </div>
    </body>
</html>