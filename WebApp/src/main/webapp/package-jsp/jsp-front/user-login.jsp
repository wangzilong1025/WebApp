<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head lang="en">
  		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title>登录</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="format-detection" content="telephone=no">
		<meta name="renderer" content="webkit">
		<meta http-equiv="Cache-Control" content="no-siteapp" />
		<link rel="stylesheet" href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" />
		<link href="<%=path %>/package-style/style-front/css/dlstyle.css" rel="stylesheet" type="text/css">
		<script src="<%=path %>/package-style/style-front/js/jquery-1.3.1.js" type="text/javascript"></script>
		<script src="<%=path %>/package-style/style-front/js/jquery.cookie.js" type="text/javascript"></script>
		<script type="text/javascript">
			function saveUserInfo() {
			    if ($("#remember-me").attr("checked") == true) {
			        var userName = $("#userName").val();
			        var passWord = $("#userPassword").val();
			        $.cookie("rmbUser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie
			        $.cookie("userName", userName, { expires: 7 }); // 存储一个带7天期限的 cookie
			        $.cookie("userPassword", userPassword, { expires: 7 }); // 存储一个带7天期限的 cookie
			    }
			    else {
			        $.cookie("rmbUser", "false", { expires: -1 });        // 删除 cookie
			        $.cookie("userName", '', { expires: -1 });
			        $.cookie("userPassword", '', { expires: -1 });
			    }
			}
			$(function(){
				var mes = $("#message").val();
				if(mes != ""){
					alert(mes);
				}
				$("#submitButton").click(function(){
					$("#loginForm").submit();
				});
			});
			$(function() {
			    if ($.cookie("rmbUser") == "true") {
			        $("#remember-me").attr("checked", true);
			        $("#userName").val($.cookie("userName"));
			        $("#userPassword").val($.cookie("userPassword"));
			    }
			});
		</script>
	</head>
	<body>
		<input type="hidden" value="${message}" id="message">
		<div class="login-boxtitle">
		<span id="testid" name="testid" style="display: none"></span>
		</div>
		<div class="login-banner">
			<div class="login-main">
				<div class="login-banner-bg"><span></span><img src="<%=path %>/package-style/style-front/images/big.jpg" /></div>
					<div class="login-box">
							<h3 class="title">登录科研成果管理系统</h3>
							<div class="clear"></div>
						<div class="login-form">
						  <form id="loginForm" action="<%=path %>/userLogin/login.do" method="post">
							   <div class="user-name">
								    <label for="userName"><i class="am-icon-user"></i></label>
								    <input autocomplete="off" type="text" name="userName" id="userName" placeholder="用户名/手机号">
                 			   </div>
                 			   <div class="user-pass">
								    <label for="userPassword"><i class="am-icon-lock"></i></label>
								    <input autocomplete="off" type="password" name="userPassword" id="userPassword" placeholder="请输入密码">
                               </div>
              			 </form>
           			   </div>
			           <div class="login-links">
			                <label for="remember-me"><input id="remember-me" checked="checked" type="checkbox">记住密码</label>
							<a href="#" class="am-fr">忘记密码</a>
							<a href="<%=path %>/package-jsp/jsp-front/user-regist.jsp" class="zcnext am-fr am-btn-default">注册</a>
							<br/>
			           </div>
					   <div class="am-cf">
							<input id="submitButton" type="button" onclick="saveUserInfo()" name="" value="登 录" class="am-btn am-btn-primary am-btn-sm">
					   </div>
					   <div class="partner">		
								<h3>合作账号</h3>
							<div class="am-btn-group">
								<li><a href="javascript:alert('还没开始合作！');"><i class="am-icon-qq am-icon-sm"></i><span>QQ登录</span></a></li>
								<li><a href="javascript:alert('还没开始合作！');"><i class="am-icon-weibo am-icon-sm"></i><span>微博登录</span> </a></li>
								<li><a href="javascript:alert('还没开始合作！');"><i class="am-icon-weixin am-icon-sm"></i><span>微信登录</span> </a></li>
							</div>
					   </div>	
				</div>
			</div>
		</div>
		<div class="footer ">
			<div class="footer-hd ">
				<p>
					<a href="# ">关于国科网</a>
					<b>|</b>
					<a href="# ">我们的资源</a>
					<b>|</b>
					<a href="# ">我们的服务</a>
					<b>|</b>
					<a href="# ">免责声明</a>
					<b>|</b>
					<a href="# ">示范基地</a>
					<b>|</b>
					<a href="# ">软件下载</a>
					<b>|</b>
					<a href="# ">联系我们</a>
				</p>
			</div>
			<div class="footer-bd ">
				<p>
					<span><font style="font-weight: bold">友情链接：</font></span>
					<a href="# ">中华人民共和国科学技术部</a>
					<b>|</b>
					<a href="# ">国家科学技术奖励工作办公室</a>
					<b>|</b>
					<a href="# ">北京市奖励办</a>
					<b>|</b>
					<a href="# ">北方技术交易市场</a>
					<b>|</b>
					<a href="# ">科化网    </a>
					<b>|</b>
					<em>© 2017-2025 Hengwang.com 版权所有</em>
				</p>
			</div>
		</div>
	</body>
</html>
