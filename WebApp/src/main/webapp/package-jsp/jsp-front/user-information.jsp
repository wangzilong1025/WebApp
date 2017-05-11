<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">
		<title>个人资料</title>
		<link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/package-style/style-front/css/infstyle.css" rel="stylesheet" type="text/css">
		<script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/jquery.min.js" type="text/javascript"></script>
		<script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/amazeui.js" type="text/javascript"></script>
		<script type="text/javascript">
	    $(function(){
     	var hidRadio = $("#usersex").val();
     	var radios = document.getElementsByName("usersex"); 
     	if (hidRadio == 0) {  
            radios[0].checked = true;  
        } else if(hidRadio == 1){  
            radios[1].checked = true;  
        } else if(hidRadio == 2) {
            radios[2].checked = true;
       	}   	
	    });
	</script>	
	<script type="text/javascript">
	function setImagePreview() {
        var docObj=document.getElementById("file");
        var imgObjPreview=document.getElementById("userImage");
                if(docObj.files &&    docObj.files[0]){
	                imgObjPreview.style.display = 'block';
	                imgObjPreview.style.width = '60%';
	                imgObjPreview.style.height = '60%';                    
	                //imgObjPreview.src = docObj.files[0].getAsDataURL();
					imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
                }else{
                	docObj.select();
	                var imgSrc = document.selection.createRange().text;
	                var localImagId = document.getElementById("localImag");
	                localImagId.style.width = "200px";
	                localImagId.style.height = "200px";
					try{
	                	localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
	                    localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
	                }catch(e){
	                    alert("这是sha图片啊!!!");
	                   	return false;
	                }
	                imgObjPreview.style.display = 'none';
	                document.selection.empty();
                }
                return true;
        }
</script>
	</head>
	<body>
		<div class="mt-logo">
			<div class="hmtop">
			<!--顶部导航条 -->
				<div class="am-container header">
					<ul class="message-l">
						<div class="topMessage">
							<div class="menu-hd">
								<c:choose>
									<c:when test="${sessionScope.user == null }">
										<a href="<%=path %>/package-jsp/jsp-front/user-login.jsp" target="_top" class="h">亲，请登录</a>
										<a href="<%=path %>/package-jsp/jsp-front/user-regist.jsp" target="_top">免费注册</a>
									</c:when>
									<c:otherwise>
										欢迎登陆，${sessionScope.user.userName }
										<a href="<%=path %>/package-jsp/jsp-front/user-login.jsp" target="_top">退出</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</ul>
					<ul class="message-r">
						<div class="topMessage home">
							<div class="menu-hd"><a href="<%=path %>/getType.do" target="_top" class="h">商城首页</a></div>
						</div>
						<div class="topMessage my-shangcheng">
							<div class="menu-hd MyShangcheng"><a href="<%=path %>/getCustom.do" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a></div>
						</div>
						<div class="topMessage mini-cart">
							<div class="menu-hd"><a id="mc-menu-hd" href="<%=path %>/viewCart.do" target="_top"><i class="am-icon-shopping-cart  am-icon-fw"></i><span>购物车</span><strong id="J_MiniCartNum" class="h">${sessionScope.num }</strong></a></div>
						</div>
						<div class="topMessage my-shangcheng">
							<div class="menu-hd MyShangcheng">
							<c:choose>  
							   <c:when test="${sessionScope.user.userStatus == 2 }"> 
							   		<a href="<%=path %>/shop/findByCustomId.do" target="_top"><i class="am-icon-user am-icon-fw"></i>用户中心</a>  
							   </c:when>  
							   <c:when test="${sessionScope.user.userStatus == 1 }"> 
							   		<a href="<%=path %>/shop/findByCustomId.do" target="_top"><i class="am-icon-user am-icon-fw"></i>用户中心</a>
							   </c:when> 
							   <c:when test="${sessionScope.user.userStatus == 0 }"> 
							   		<a href="<%=path %>/shop/findByCustomId.do" target="_top"><i class="am-icon-user am-icon-fw"></i>用户中心</a>
							   </c:when> 
							   <c:otherwise>
							  		<a href="<%=path %>/jsp/front/registSeller.jsp" target="_top"><i class="am-icon-user am-icon-fw"></i>用户中心</a>
							   </c:otherwise>  
							</c:choose>   
							</div>
						</div>
						<div class="topMessage favorite">
							<div class="menu-hd"><a href="<%=path %>/collectionAll.do" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>收藏夹</span></a></div>
						</div>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
		</div>
        <div class="nav-table">
			<div class="long-title"><span class="all-goods">全部分类</span></div>
			<div class="nav-cont">
				<ul>
					<li class="index"><a href="<%=path %>/menu/getMenuList.do">首页</a></li>
					<li class="qc"><a href="<%=path %>/menu/selectMenuOne.do">登记成果</a></li>
					<li class="qc"><a href="#">统计</a></li>
					<li class="qc"><a href="#">公告</a></li>
					<li class="qc last"><a href="#">排行</a></li>
				</ul>
				<div class="nav-extra">
					<i class="am-icon-user-secret am-icon-md nav-user"></i><b></b>我的福利
					<i class="am-icon-angle-right" style="padding-left: 10px;"></i>
				</div>
			</div>
		</div>
		<b class="line"></b>
		<div class="center">
			<div class="col-main">
				<div class="main-wrap">
					<div class="user-info">
						<!--标题 -->
						<div class="am-cf am-padding">
							<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">个人资料</strong> / <small>Personal&nbsp;information</small></div>
						</div>
						<hr/>
						
						<form name="reg_testdate" action="<%=path %>/userInfo/updatePersonalUserInfo.do" class="am-form am-form-horizontal" method="post" enctype="multipart/form-data">
						<!--头像 -->
						<div class="user-infoPic">

							<div class="filePic" id="localImag">
								<input class="inputPic" type="file" id="file" name="file" onchange="javascript:setImagePreview();" allowexts="gif,jpeg,jpg,png,bmp" accept="image/*">
								<c:choose>
									<c:when test="${userInfo.userImage == null}">
										<img class="am-circle am-img-thumbnail" id="userImage" src="<%=path %>/package-style/style-front/images/getAvatar.do.jpg"/>
									</c:when>
									<c:otherwise>
										<img class="am-circle am-img-thumbnail" id="userImage" src="<%=path %>/userInfoImage/${userInfo.userImage }"/>
									</c:otherwise>
								</c:choose>
							</div>
							<p class="am-form-help">头像</p>
							<div class="info-m">
								<div><b>用户名：<i>${sessionScope.user.userName }</i></b></div>
							</div>
						</div>
						<!--个人信息 -->
						<div class="info-main">
							<!--<form class="am-form am-form-horizontal">-->
								<div class="am-form-group">
									<label for="user-name" class="am-form-label">昵称</label>
									<div class="am-form-content">
									<input type="hidden" name="userinfoId" value="${userInfo.userinfoId }" />	
										<input type="text" id="userNick" placeholder="Name" value="${userInfo.userNick }">
									</div>
								</div>
								<div class="am-form-group">
									<label class="am-form-label">性别</label>
									<div class="am-form-content sex">
										<label class="am-radio-inline">
											<input type="hidden" id="usersex" value="${userInfo.userSex }" />
											<input type="radio" name="usersex" value="0" data-am-ucheck> 男
										</label>
										<label class="am-radio-inline">
											<input type="radio" name="usersex" value="1" data-am-ucheck> 女
										</label>
										<label class="am-radio-inline">
											<input type="radio" name="usersex" value="2" data-am-ucheck> 保密
										</label>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-birth" class="am-form-label">生日</label>
									<div class="am-form-content birth">
										<div class="birth-select">
											<select data-am-selected>
												<option value="a">2015</option>
												<option value="b">1987</option>
											</select>
											<em>年</em>
										</div>
										<div class="birth-select2">
											<select data-am-selected>
												<option value="a">12</option>
												<option value="b">8</option>
											</select>
											<em>月</em>
										</div>
										<div class="birth-select2">
											<select data-am-selected>
												<option value="a">21</option>
												<option value="b">23</option>
											</select>
											<em>日</em>
										</div>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-email" class="am-form-label">电话</label>
									<div class="am-form-content">
										<input id="userTelphone" name="userTelphone" placeholder="Telphone" type="text" value="${userInfo.userTelphone }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-email" class="am-form-label">职业</label>
									<div class="am-form-content">
										<input id="userProfession" name="userProfession" placeholder="Profession" type="text" value="${userInfo.userProfession }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-email" class="am-form-label">学历</label>
									<div class="am-form-content">
										<input id="userAcademicdegree" name="userAcademicdegree" placeholder="Academicdegree" type="text" value="${userInfo.userAcademicdegree }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-email" class="am-form-label">地址</label>
									<div class="am-form-content">
										<input id="userAddress" name="userAddress" placeholder="Address" type="text" value="${userInfo.userAddress }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-email" class="am-form-label">邮箱</label>
									<div class="am-form-content">
										<input id="userEmail" name="userEmail" placeholder="Email" type="text" value="${userInfo.userEmail }">
									</div>
								</div>
								<div class="info-btn">
								    <div><input class="am-btn am-btn-danger" type="submit" value="保存信息" /></div>
							    </div>
							</div>
						</form>
					</div>
				</div>
				<!--底部-->
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
			<aside class="menu">
				<ul>
					<li class="person">
						<a href="<%=path %>/getCustom.do ">个人中心</a>
					</li>
					<li class="person">
						<a href="#"><font style="font-weight: bold">个人资料</font></a>
						<ul>
							<li> <a href="<%=path %>/userInfo/selectPersonalCenter.do">个人信息</a></li>
							<li> <a href="<%=path %>/package-jsp/jsp-front/user-safety.jsp">安全设置</a></li>
							<li> <a href="<%=path %>/package-jsp/jsp-front/user-safety-pass.jsp">修改密码</a></li>
							<li> <a href="<%=path %>/findAddress.do">收货地址</a></li>
						</ul>
					</li>
					<li class="person">
						<a href="#"><font style="font-weight: bold">我的成果</font></a>
						<ul>
							<li> <a href="<%=path %>/collectionAll.do">我的收藏</a></li>
							<li> <a href="<%=path %>/footMark/listFoot.do">足迹浏览</a></li>
							<li> <a href="<%=path %>/menu/selectMenuOne.do">成果新增</a></li>
							<li> <a href="<%=path %>/achievement/queryAllAchievementUnreleaseFront.do">未发布成果</a></li>
							<li> <a href="<%=path %>/achievement/queryAllAchievementByCheckPendingFront.do">待审核成果</a></li>
							<li> <a href="<%=path %>/achievement/queryAllAchievement.do">已发布成果</a></li>
							<li> <a href="<%=path %>/achievement/queryAllAchievementNotPass.do">未通过成果</a></li>
						</ul>
					</li>
				</ul>
			</aside>
		</div>
	</body>
</html>