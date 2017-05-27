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
    <title>新增权限</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>

    <script type="text/javascript" src="<%=path %>/package-style/style-behind/time-style/jedate/jedate.js"></script>
    <script type="text/javascript" src="<%=path %>/package-style/style-behind/time-style/jedate/jedate.min.js"></script>
    <script type="text/javascript">
        function test(){
            var roleState = $("#authorityState").val();
            if(roleState != "" && roleState != null){
                document.getElementById("authorityAdd").submit();
            }else{
                alert("请选择是否使用该权限!");
                return false;
            }
        }
    </script>
</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head"><strong><span class="icon-pencil-square-o"></span>新增权限</strong></div>
    <div class="body-content">
        <form method="post" id="authorityAdd" class="form-x" action="<%=path %>/authority/addAuthorityInfo.do">

            <div class="form-group">
                <div class="label">
                    <label>权限名称：</label>
                </div>
                <div class="field">
                    <input type="text" class="input" id="authorityName" name="authorityName" value="" data-validate="required:请输入角色名称"/>
                    <div class="tips"></div>
                </div>
            </div>

            <div class="form-group">
                <div class="label">
                    <label>权限备注：</label>
                </div>
                <div class="field">
                    <input type="text" class="input" id="authorityNote" name="authorityNote" value="" data-validate="required:请输入角色备注"/>
                </div>
            </div>

            <div class="form-group">
                <div class="label">
                    <label>是否使用该权限：</label>
                </div>
                <div class="field">
                    <select id="authorityState" name="authorityState" class="input w50">
                        <option value="">请选择是否使用该权限</option>
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                    <div class="tips"></div>
                </div>
            </div>

            <div class="form-group">
                <div class="label">
                    <label></label>
                </div>
                <div class="field">
                    <button class="button bg-main icon-check-square-o" type="button" style="margin-left: 30%;margin-top: 50px;" onclick="test()"> 保存权限</button>
                    <button class="button bg-main icon-check-square-o" type="button" style="margin-top: 50px;" onclick="javascript:history.back(-1);"> 取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>