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
    <title>科研成果详情</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head"><strong><span class="icon-pencil-square-o"></span>科研成果详情</strong></div>
    <div class="body-content">
        <form method="post" class="form-x" action="">

            <div class="form-group">
                <div class="field" style="margin: 0px;text-align: center;">
                    <h3 id="achievementName" style="margin-left: 20px; align-content: center" name="achievementName">${achievement.achievementName}<h3/>
                </div>
            </div>

            <div class="form-group">
                <div class="field">
                    <label style="margin-left: 40px;">成果类型:</label>
                    <label style="margin-left: 5px;" id="achievementTypeName" name="achievementTypeName">${achievement.achievementTypeName}</label>
                </div>
                <div class="field">&nbsp;</div>
                <div class="field">
                    <label style="margin-left: 40px;">发布城市:</label>
                    <label style="margin-left: 5px;" id="cityTypeName" name="cityTypeName">${achievement.cityTypeName}</label>
                </div>
                <div class="field">&nbsp;</div>
                <div class="field">
                    <label style="margin-left: 40px;">所在单位:</label>
                    <label style="margin-left: 5px;" id="unitName" name="unitName">${achievement.unitName}</label>
                </div>
                <div class="field">&nbsp;</div>
                <div class="field">
                    <label style="margin-left: 40px;">发&nbsp;&nbsp;布&nbsp;&nbsp;人:</label>
                    <label style="margin-left: 5px;" id="userNick" name="userNick">${achievement.userNick}</label>
                </div>
                <div class="field">&nbsp;</div>
                <div class="field">
                    <label style="margin-left: 40px;">成果内容:</label>
                    <%--<label style="margin-left: 20px;" id="achievementContent" name="achievementContent">${achievement.achievementContent}</label>--%>
                    <div style="margin-left: 40px;" id="achievementContent" name="achievementContent">${achievement.achievementContent}</div>
                </div>
            </div>
            <div class="form-group">
                <div class="label">
                    <label></label>
                </div>
                <div class="field">
                    <button class="button bg-main icon-check-square-o" type="submit"> 返回</button>
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