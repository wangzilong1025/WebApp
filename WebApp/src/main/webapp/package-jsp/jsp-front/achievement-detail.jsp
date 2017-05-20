<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">
		<title>科研成果详情</title>
		<link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/package-style/style-front/css/addstyle.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/package-style/style-front/css/refstyle.css" rel="stylesheet" type="text/css">
		<script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/jquery.min.js" type="text/javascript"></script>
		<script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
		<script>
			function setImagePreview() {
		        var docObj=document.getElementById("file");
		        var imgObjPreview=document.getElementById("userImage");
               	if(docObj.files && docObj.files[0]){
                	imgObjPreview.style.display = 'block';
                    imgObjPreview.style.width = '100px';
                    imgObjPreview.style.height = '100px';                    
   					imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
                }else{
                    docObj.select();
                    var imgSrc = document.selection.createRange().text;
                    var localImagId = document.getElementById("localImag");
                    localImagId.style.width = "100px";
                    localImagId.style.height = "100px";
					try{
	                    localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
	                    localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                    }catch(e){
                        alert("这是怎么了!");
                        return false;
                    }
                    imgObjPreview.style.display = 'none';
                    document.selection.empty();
				}
             	return true;
	       	}
			function setImagePreview1() {
		        var docObj=document.getElementById("file1");
		        var imgObjPreview=document.getElementById("userImage1");
               	if(docObj.files && docObj.files[0]){
                	imgObjPreview.style.display = 'block';
                    imgObjPreview.style.width = '100px';
                    imgObjPreview.style.height = '100px';                    
   					imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
                }else{
                    docObj.select();
                    var imgSrc = document.selection.createRange().text;
                    var localImagId = document.getElementById("localImag1");
                    localImagId.style.width = "100px";
                    localImagId.style.height = "100px";
					try{
	                    localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
	                    localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                    }catch(e){
                        alert("这是怎么了!");
                        return false;
                    }
                    imgObjPreview.style.display = 'none';
                    document.selection.empty();
				}
             	return true;
	       	}
			function setImagePreview2() {
		        var docObj=document.getElementById("file2");
		        var imgObjPreview=document.getElementById("userImage2");
               	if(docObj.files && docObj.files[0]){
                	imgObjPreview.style.display = 'block';
                    imgObjPreview.style.width = '100px';
                    imgObjPreview.style.height = '100px';                    
   					imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
                }else{
                    docObj.select();
                    var imgSrc = document.selection.createRange().text;
                    var localImagId = document.getElementById("localImag2");
                    localImagId.style.width = "100px";
                    localImagId.style.height = "100px";
					try{
	                    localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
	                    localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                    }catch(e){
                        alert("这是怎么了!");
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
		<!--头 -->
		<header>
			<article style="margin: 20px auto">
				<div class="mt-logo">
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
							<c:choose>
								<c:when test="${sessionScope.user == null }">
									<div class="topMessage home">
										<div class="menu-hd"><a href="<%=path %>/menu/getMenuList.do" target="_top" class="h">商城首页</a></div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="topMessage home">
										<div class="menu-hd"><a href="<%=path %>/menu/getMenuList.do" target="_top" class="h">商城首页</a></div>
									</div>
									<div class="topMessage my-shangcheng">
										<div class="menu-hd MyShangcheng"><a href="<%=path %>/userInfo/userCenterInfomation.do" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a></div>
									</div>
									<div class="topMessage favorite">
										<div class="menu-hd"><a href="<%=path %>/achievementCollect/queryAllCollectionAchievement.do" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>收藏夹</span></a></div>
									</div>
									<div class="topMessage favorite">
										<div class="menu-hd"><a href="<%=path %>/fotoPlace/queryAllFotoPlaceAchievementByUserInfoId.do" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>我的足迹</span></a></div>
									</div>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>

					<div class="clear"></div>
				</div>
			</article>
		</header>
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
					<div class="user-address">
						<!--标题 -->
						<div class="clear"></div>
						<a class="new-abtn-type" data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0}">添加科研成果</a>
						<!--例子-->
						<div class="am-modal am-modal-no-btn" id="doc-modal-1">
							<div class="add-dress">
								<!--标题 -->
								<div class="am-cf am-padding">
									<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">成果详情</strong> / <small>Achievement&nbsp;detail</small></div>
								</div>
								<hr/>
								<div class="am-u-md-12 am-u-lg-8" style="margin-top: 20px;" align="center">
									<form class="am-form am-form-horizontal" id="achievementAdd" action="<%=path %>/achievement/addAchievement.do" enctype="multipart/form-data" method="post">
										
										<div class="am-form-group">
											<label for="user-name" class="am-form-label">&nbsp;成果标题</label>
											<div class="am-form-content">
												<input type="text" style="border: 0px;" align="left" id="achievementName" readonly="readonly" value="${achievement.achievementName}">
											</div>
										</div>
										<div class="am-form-group">
											<label for="user-name" class="am-form-label">&nbsp;单位名称</label>
											<div class="am-form-content">
												<input type="text" style="border: 0px;" id="unitName" name="unitName" readonly="readonly" value="${achievement.unitName}" placeholder="所在单位名称">
											</div>
										</div>
										<div class="am-form-group">
											<label for="user-address" class="am-form-label">&nbsp;成果分类</label>
											<div class="am-form-content address">
												<div class="birth-select">
													<%--<input type="text" id="menuNameOne" name="unitName" readonly="readonly" value="${menuNameOne}">--%>
													<select data-am-selected="{maxHeight: 120}" id="type1" disabled="disabled" style="border: 0px;">
														<option value="${menuNameOne}" selected="selected">${menuNameOne}</option>
													</select>
												</div>
												<div class="birth-select">
													<select data-am-selected="{maxHeight: 120}" id="type2" disabled="disabled" style="border: 0px;">
														<option value="${menuNameTwo}" selected="selected">${menuNameTwo}</option>
													</select>
												</div>
												<div class="birth-select">
													<select data-am-selected="{maxHeight: 120}" id="type3" disabled="disabled" style="border: 0px;">
														<option value="${menuNameThree}" selected="selected">${menuNameThree}</option>
													</select>
												</div>
											</div>						
										</div>
										<div class="am-form-group">
											<label for="user-birth" class="am-form-label">&nbsp;所在地区</label>
											<div class="am-form-content birth">
												<div class="birth-select">
													<select id="cityOne" data-am-selected="{maxHeight: 120}">
													   <option value="China" selected="selected">中国</option>
													</select>
												</div>
												<div class="birth-select2">
													<select id="cityTwo" data-am-selected="{maxHeight: 120}">
														<option value="${addressTwo}" selected="selected">${addressTwo}</option>
													</select>
												</div>
												<div class="birth-select2">
													<select id="cityThree" data-am-selected="{maxHeight: 120}" name="locationCity">
													   <option value="${addressThree}" selected="selected">${addressThree}</option>
													</select>
												</div>
											</div>
										</div>
										<div class="am-form-group">
											<label for="user-intro" class="am-form-label">&nbsp;成果内容</label>
											<div class="am-form-content"><!--rows="3" -->
												<textarea class="" id="achievementContent" name="achievementContent" style="height: 353px;">${achievement.achievementContent}</textarea>
												<small>请写出你的科研成果设计依据和思路...</small>
											</div>
										</div>
										<div class="am-form-group">
											<label for="user-intro" class="am-form-label">&nbsp;成果图片</label>&nbsp;&nbsp;&nbsp;
											<!--<div class="refund-tip">-->
											<div class="am-form-content">
												<div class="filePic" id="localImag" style="width: 110px; max-width: 110px; float: left; max-height: 110px; height: 110px;">
													<c:choose>
														<c:when test="${achievement.achievementImages == null}">
															<img id="userImage" src="<%=path %>/package-style/style-front/images/image.jpg">
														</c:when>
														<c:otherwise>
															<img class="am-circle am-img-thumbnail" id="userImage" src="<%=path %>/achievementImage/${achievement.achievementImages }"/>
														</c:otherwise>
													</c:choose>
												</div>
												<div class="filePic" id="localImag1" style="width: 110px; max-width: 110px; float: left; max-height: 110px; height: 110px;">
													<c:choose>
														<c:when test="${achievement.achievementOneImage == null}">
															<img id="userImage" src="<%=path %>/package-style/style-front/images/image.jpg">
														</c:when>
														<c:otherwise>
															<img class="am-circle am-img-thumbnail" id="userImage" src="<%=path %>/achievementImage/${achievement.achievementOneImage }"/>
														</c:otherwise>
													</c:choose>
												</div>
												<div class="filePic" id="localImag2" style="width: 110px; max-width: 110px; float: left; max-height: 110px; height: 110px;">
													<c:choose>
														<c:when test="${achievement.achievementTwoImage == null}">
															<img id="userImage" src="<%=path %>/package-style/style-front/images/image.jpg">
														</c:when>
														<c:otherwise>
															<img class="am-circle am-img-thumbnail" id="userImage" src="<%=path %>/achievementImage/${achievement.achievementTwoImage }"/>
														</c:otherwise>
													</c:choose>
												</div>
												<div class="filePic" style="width: 100px; max-width: 100px; float: left;" align="left">上传图片<br>最多三张</div>
											</div>
										</div>
										<div class="am-form-group">
											<div class="am-u-sm-9 am-u-sm-push-3">
												<input class="am-btn am-btn-danger"  onclick="javascript:history.back(-1);" type="button" value="返回上一页" />
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<script type="text/javascript">
						$(document).ready(function() {							
							$(".new-option-r").click(function() {
								$(this).parent('.user-addresslist').addClass("defaultAddr").siblings().removeClass("defaultAddr");
							});
							var $ww = $(window).width();
							if($ww>640) {
								$("#doc-modal-1").removeClass("am-modal am-modal-no-btn");
							}
						});
					</script>
					<div class="clear"></div>
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
						<a href="<%=path %>/userInfo/userCenterInfomation.do">个人中心</a>
					</li>
					<li class="person">
						<a href="#"><font style="font-weight: bold">个人资料</font></a>
						<ul>
							<li> <a href="<%=path %>/userInfo/selectPersonalCenter.do">个人信息</a></li>
							<li> <a href="<%=path %>/userLogin/userUpdateSafetyByUserId.do">安全设置</a></li>
							<li> <a href="<%=path %>/package-jsp/jsp-front/user-safety-pass.jsp">修改密码</a></li>
							<li> <a href="<%=path %>/achievementCollect/queryAllCollectionAchievement.do">我的收藏</a></li>
							<li> <a href="<%=path %>/fotoPlace/queryAllFotoPlaceAchievementByUserInfoId.do">足迹浏览</a></li>
						</ul>
					</li>
					<li class="person">
						<a href="#"><font style="font-weight: bold">我的成果</font></a>
						<ul>
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