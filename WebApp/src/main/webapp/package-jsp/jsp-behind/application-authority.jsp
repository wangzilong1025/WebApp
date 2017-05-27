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
    <title>申请权限</title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>
    <script type="text/javascript">
        function test(){
            var roleId = $("#roleId").val();
            var realName = $("#realName").val();
            var adminEmail = $("#adminEmail").val();
            var adminPhone = $("#adminPhone").val();
            if(realName != "" && realName != null && adminEmail !="" && adminEmail !=null && adminPhone != "" && adminPhone !=null){
                if(roleId != "" && roleId != null){
                    document.getElementById("applicationAuthority").submit();
                }else{
                    alert("请选择将要申请的管理员角色!")
                    return false;
                }
            }else{
                alert("请完善管理员信息之后再申请权限!")
                return false;
            }

        }
    </script>
</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>申请权限</strong></div>
    <div class="body-content">
        <form id="applicationAuthority" method="post" class="form-x" action="<%=path %>/adminRole/updateAdminRole.do">
            <div class="form-group">
                <div class="label">
                    <label>真实姓名：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" id="realName" value="${adminInfo.realName}" name="realName" style="width: 40%;" readonly="readonly"/>
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label">
                    <label>邮箱地址：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" id="adminEmail" value="${adminInfo.adminEmail}" name="adminEmail" style="width: 40%;" readonly="readonly"/>
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label">
                    <label>手机号码：</label>
                </div>
                <div class="field">
                    <input type="text" class="input w50" id="adminPhone" value="${adminInfo.adminPhone}" name="adminPhone" style="width: 40%;" readonly="readonly"/>
                    <div class="tips"></div>
                </div>
            </div>

            <div class="form-group">
                <div class="label">
                    <label>当前职位：</label>
                </div>
                <div class="field">
                    <c:if test="${realRole ==1}">
                        <input type="text" class="input w50" id="adminStatus" value="超级管理员" style="width: 40%;" readonly="readonly"/>
                    </c:if>
                    <c:if test="${realRole ==2}">
                        <input type="text" class="input w50" id="adminStatus" value="管理员" style="width: 40%;" readonly="readonly"/>
                    </c:if>
                    <c:if test="${realRole ==3}">
                        <input type="text" class="input w50" id="adminStatus" value="经理" style="width: 40%;" readonly="readonly"/>
                    </c:if>
                    <c:if test="${realRole ==4}">
                        <input type="text" class="input w50" id="adminStatus" value="暂无权限人员" style="width: 40%;" readonly="readonly"/>
                    </c:if>

                    <div class="tips"></div>
                </div>
            </div>

            <c:choose>
                <c:when test="${adminRole.roleId ==1}">
                    <div class="form-group">
                        <div class="label">
                            <label>职位：</label>
                        </div>
                        <div class="field">
                            <input class="input w50" type="text" readonly="readonly" value="${role.roleName}" style="width: 40%;">
                            <div class="tips"></div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="label">
                            <font color="#a9a9a9"><h2>角色说明:</h2></font>
                        </div>
                        <div class="field">
                            <font color="#a9a9a9" style="font-size: 20px;"><div style="padding: 3px;border: 10px;"><h2>&nbsp;您已经是超级管理员,拥有最大的权力!!!</h2></div></font>
                            <div class="tips"></div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <div class="label">
                            <label>申请职位：</label>
                        </div>
                        <div class="field">
                            <select id="roleId" name="roleId" class="input w50" style="width: 40%;">
                                <option value="">请选择申请的角色</option>
                                <c:if test="${realRole ==4}">
                                    <option value="3">经理角色</option>
                                </c:if>
                                <c:if test="${realRole ==3}">
                                    <option value="2">管理员角色</option>
                                </c:if>
                                <c:if test="${realRole ==2}">
                                    <option value="1">超级管理员角色</option>
                                </c:if>
                            </select>
                            <div class="tips"></div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="label">
                            <font color="#a9a9a9"><h2>角色说明:</h2></font>
                        </div>
                        <div class="field">
                            <font color="#a9a9a9" style="font-size: 20px;"><div style="padding: 3px;border: 10px;"><h2>&nbsp;申请职位时,请将真实姓名,邮箱地址,联系方式填写正确,审批人经过核实后才能进行授权，请耐心等待!!!</h2></div></font>
                            <font color="#a9a9a9"><div style="padding: 9px;border: 10px;">超级管理员：拥有的权限为增加、删除、修改、查看的功能(包括权限)</div></font>
                            <font color="#a9a9a9"><div style="padding: 9px;border: 10px;">管理员：拥有增加、查看、修改(包括权限)</div></font>
                            <font color="#a9a9a9"><div style="padding: 9px;border: 10px;">部门经理：拥有增加、修改、查看的权利（不包括权限）</div></font>
                            <div class="tips"></div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="label">
                            <label></label>
                        </div>
                        <div class="field">
                            <button class="button bg-main icon-check-square-o" type="button" onclick="test()"> 提交申请</button>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>




        </form>
    </div>
</div>

</body>
</html>