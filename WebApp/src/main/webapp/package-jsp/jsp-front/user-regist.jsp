<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<title>注册</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="format-detection" content="telephone=no">
		<meta name="renderer" content="webkit">
		<meta http-equiv="Cache-Control" content="no-siteapp" />
		<link rel="stylesheet" href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.min.css" />
		<link href="<%=path %>/package-style/style-front/css/dlstyle.css" rel="stylesheet" type="text/css">
		<script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
		<script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/amazeui.min.js"></script>
		<script src="<%=path %>/package-style/style-front/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function(){
			var mail;
			var code;
			$("#userName").change(function(){
				mail = $(this).val();
				var uname= /^[a-zA-z][a-zA-Z0-9_]{2,9}$/;
				var bChk=uname.test(mail);
				if(bChk==false){
					$("#message").html("用户名格式错误,不能输入汉字,请以字母开头.<br>用户名格式例如wzl7788");
					$("#message").val("new value");
				}else{
					$("#message").html("");
					$("#message").val("");
				}
			});
		});
		
		function passCon(){
			var pass = $("#userPassword").val();
			if(pass == ""){
				$("#message").html("请输入密码");
				$("#message").val("new value");
				return;
			}else{
				$("#message").html("");
				$("#message").val("");
			}
			var passCon = $("#passwordRepeat").val();
			if(passCon == ""){
				$("#message").html("请输入确认密码");
				$("#message").val("new value");
				return;
			}else{
				$("#message").html("");
				$("#message").val("");
			}
			if(pass!=passCon){
				$("#message").html("两次密码输入不一致");
				$("#message").val("new value");
				return false;
			}else{
				$("#message").html("");
				return true;
			}
		}
		function validate(){
			var str1 = $("#userName").val();
			var str3 = $("#userPassword").val();
			var str4 = $("#passwordRepeat").val();
			var str5 = $("#message").val();
			if(str5 != "" || str1 == "" || str3 == "" || str4 == ""){
				$("#message").html("请填写正确后再提交表单");
				$("#message").val("new value");
				return false;
			}else{
				$("#message").html("");
				$("#message").val("");
				$("#registForm").submit();
			}
		}
		</script>
	</head>
	<body>
		<div class="login-boxtitle">
		
		</div>
		<div class="res-banner">
			<div class="res-main">
				<div class="login-banner-bg"><span></span><img src="<%=path %>/package-style/style-front/images/big.jpg" /></div>
				<div class="login-box">
					<div class="am-tabs" id="doc-my-tabs">
						<h3 class="title">用户注册</h3>
						<div class="am-tabs-bd">
							<div class="am-tab-panel am-active">
								<form action="<%=path%>/userLogin/addUserLogin.do" method="post" id="registForm">
									<div class="user-email">
										<label for="email"><i class="am-icon-envelope-o"></i></label>
										<input autocomplete="off" type="text" name="userName" id="userName" placeholder="不能以数字,汉字,下划线开头哦">
		                 			</div>				
						            <div class="user-pass">
										<label for="password"><i class="am-icon-lock"></i></label>
										<input autocomplete="off" type="password" name="userPassword" id="userPassword" placeholder="设置密码" onblur="passCon();">
						            </div>										
						            <div class="user-pass">
										<label for="passwordRepeat"><i class="am-icon-lock"></i></label>
										<input autocomplete="off" type="password" name="" id="passwordRepeat" placeholder="确认密码" onblur="passCon();">
						            </div>	
                 				</form>
								<div class="login-links">
									<!-- <h2 id="message"></h2> -->
									<p>
										<span id="message" style="color:red;"></span>
									</p>
									<label for="reader-me">
										<input id="reader-me" checked="checked" type="checkbox"> 点击表示您同意《服务协议》
									</label>
								</div>
								<div class="am-cf">
									<input id="submitButton" onclick="validate()" type="button" name="" value="注册" class="am-btn am-btn-primary am-btn-sm am-fl">
								</div>
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
		</div>
	</body>
</html>
