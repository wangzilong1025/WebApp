<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<title>科研成果首页</title>

		<link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
		<link href="<%=path %>/package-style/style-front/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="<%=path %>/package-style/style-front/css/hmstyle.css" rel="stylesheet" type="text/css"/>
		<link href="<%=path %>/package-style/style-front/css/skin.css" rel="stylesheet" type="text/css" />
		<!-- 公告滚动样式-->
		<link rel="stylesheet" href="<%=path %>/package-style/style-front/notice/css/style.css">
		<!-- 公告滚动样式结束-->
		<script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
		<script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/amazeui.min.js"></script>
        <script type="text/javascript" src="<%=path %>/package-style/style-front/js/jquery-1.7.min.js"></script>

	    <link rel="stylesheet" href="<%=path %>/package-style/style-map/css/index.css"/>
	    <script src="<%=path %>/package-style/style-map/js/jquery-1.12.4.js"></script>
	    <script src="<%=path %>/package-style/style-map/js/ichartjs.release.v1.2-all-in-one-20130626/ichart.1.2.min.js"></script>
	    <script src="<%=path %>/package-style/style-map/js/echarts-2.2.7/build/dist/echarts.js"></script>
	    <script src="<%=path %>/package-style/style-map/js/index.js"></script>
	</head>
	<body>
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
				<!--悬浮搜索框-->
			<div class="nav white">
				<div class="search-bar pr">
					<a name="index_none_header_sysc" href="#"></a>
					<form action="<%=path %>/menu/selectMenuOneInSearch.do" method="post" id="submit"><!-- /package-jsp/jsp-front/user-search.jsp-->
						<input id="searchContent" name="searchContent" type="text" placeholder="输入成果标题/发布人/单位名称" autocomplete="off">
						<input id="num" type="hidden" name="num" value="1"/>
						<input id="ai-topsearch" class="submit am-btn" value="搜索" type="submit">
					</form>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="banner">
		<!--轮播 -->
			<div class="am-slider am-slider-default scoll" data-am-flexslider id="demo-slider-0">
				<ul class="am-slides">
					<li class="banner1"><a href="#"><img src="<%=path %>/package-style/style-front/images/noticepictureOne.jpg"/></a></li>
					<li class="banner2"><a href="#"><img src="<%=path %>/package-style/style-front/images/noticepictureTwo.jpg"/></a></li>
					<li class="banner3"><a href="#"><img src="<%=path %>/package-style/style-front/images/ad3.jpg"/></a></li>
					<li class="banner4"><a href="#"><img src="<%=path %>/package-style/style-front/images/ad4.jpg"/></a></li>
				</ul>
			</div>
			<div class="clear"></div>
		</div>
		<div class="shopNav">
			<div class="slideall">
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
						  <i class="am-icon-user-secret am-icon-md nav-user"></i><b></b>我的消息
						  <i class="am-icon-angle-right" style="padding-left: 10px;"></i>
					  </div>
				   </div>

					<!--侧边导航 -->
					<div id="nav" class="navfull">
						<div class="area clearfix">
							<div class="category-content" id="guide_2">
								<div class="category">
									<ul class="category-list" id="js_climit_li">
										<c:forEach items="${menuListOne }" var="list1">
											<li class="appliance js_toggle relative" style="width: 100%">
												<div class="category-info">
													<h3 class="category-name b-category-name"><i><img src="<%=path %>/package-style/style-front/images/${list1.topId }${list1.topId }${list1.topId }.png"></i><a class="ml-22" title="${list1.topName }">${list1.topName }</a></h3>
													<em>&gt;</em>
												</div>
												<div class="menu-item menu-in top" style="display: none; width: 650px;">
													<div class="area-in">
														<div class="area-bg">
															<div class="menu-srot">
																 <c:forEach items="${list1.menulist }" var="list2">
																	<div class="sort-side">
																		<dl class="dl-sort" style="width: 100%;">
																			<dt style="width: 100%;"><span title="${list2.topName }">${list2.topName }</span></dt>
																			<c:forEach items="${list2.menulist }" var="list3">
																			   <dd><a title="${list3.topName}" href="<%=path %>/achievement/selectAllAchievementByAchievementTypeForMenu.do?topId=${list3.topId}" ><span>${list3.topName}</span></a></dd>
																			</c:forEach>
																		</dl>
																	</div>
																</c:forEach>
															</div>
														</div>
													</div>
												</div>
												<b class="arrow"></b>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>

					<!--轮播-->
					<script type="text/javascript">
						(function() {
							$('.am-slider').flexslider();
						});
						$(document).ready(function() {
							$("li").hover(function() {
								$(".category-content .category-list li.first .menu-in").css("display", "none");
								$(".category-content .category-list li.first").removeClass("hover");
								$(this).addClass("hover");
								$(this).children("div.menu-in").css("display", "block");
							}, function() {
								$(this).removeClass("hover");
								$(this).children("div.menu-in").css("display", "none");
							});
						});
					</script>

				<!--走马灯 -->
				<div class="marqueen">
					<span class="marqueen-title">科技头条</span>
					<div class="demo">
						<ul>
							<li class="title-first">
								<a target="_blank" href="#">
									<img src="<%=path %>/package-style/style-front/images/TJ2.jpg"></img>
									<span>[焦点]</span>天阳能应用效率提高~~
								</a>
							</li>
							<li class="title-first">
								<a target="_blank" href="#">
									<span>[公告]</span>中国科研成果提高发布率
									<img src="<%=path %>/package-style/style-front/images/TJ.jpg"></img>
									<p>XXXXXXXXXXXXXXXXXX</p>
								</a>
							</li>
							<div class="mod-vip">
								<div class="m-baseinfo">
									<a href="#">
										<img src="<%=path %>/package-style/style-front/images/getAvatar.do.jpg">
									</a>
									<em>
										Hello,<span class="s-name">小叮当</span>
										<a href="#"><p>点击更多科技创新内容</p></a>
									</em>
								</div>
								<div class="member-logout">
									<a class="am-btn-warning btn" href="<%=path %>/package-jsp/jsp-front/user-login.jsp">登录</a>
									<a class="am-btn-warning btn" href="<%=path %>/package-jsp/jsp-front/user-regist.jsp">注册</a>
								</div>
								<div class="member-login">
									<a href="#"><strong>0</strong>待收货</a>
									<a href="#"><strong>0</strong>待发货</a>
									<a href="#"><strong>0</strong>待付款</a>
									<a href="#"><strong>0</strong>待评价</a>
								</div>
								<div class="clear"></div>
							</div>
							<li><a target="_blank" href="#"><span>[特惠]</span>洋河年末大促，低至两件五折</a></li>
							<li><a target="_blank" href="#"><span>[公告]</span>华北、华中部分地区配送延迟</a></li>
							<li><a target="_blank" href="#"><span>[特惠]</span>家电狂欢千亿礼券 买1送1！</a></li>
						</ul>
					<div class="advTip"><img src="<%=path %>/package-style/style-front/images/advTip.jpg"/></div>
					</div>
				</div>
				<div class="clear"></div>
			</div>

			<%--suppress CheckValidXmlInScriptTagBody --%>
			<script type="text/javascript">
				if ($(window).width() < 640) {
					function autoScroll(obj) {
						$(obj).find("ul").animate({
							marginTop: "-39px"
						}, 500, function() {
							$(this).css({
								marginTop: "0px"
							}).find("li:first").appendTo(this);
						});
					}
					$(function(){
						setInterval('autoScroll(".demo")', 3000);
					});
				}
			</script>
		</div>
			<div class="shopMainbg">
				<div class="shopMain" id="shopmain">
					<!--推荐专栏 -->
					<div class="am-g am-g-fixed recommendation">
						<div class="clock am-u-sm-3">
							<img src="<%=path %>/package-style/style-front/images/2017.png "></img>
							<p>推荐<br>专栏</p>
						</div>
						<div class="am-u-sm-4 am-u-lg-3 ">
							<div class="info ">
								<h3>历年登记</h3>
								<h4>成果统计</h4>
							</div>
							<div class="recommendationMain one">
								<a href="#"><img src="<%=path %>/package-style/style-front/images/recommendTwo.jpg "></img></a>
							</div>
						</div>						
						<div class="am-u-sm-4 am-u-lg-3 ">
							<div class="info ">
								<h3>科技成果</h3>
								<h4>知识关联</h4>
							</div>
							<div class="recommendationMain two">
								<a href="#"><img src="<%=path %>/package-style/style-front/images/recommendOne.jpg "></img></a>
							</div>
						</div>
						<div class="am-u-sm-4 am-u-lg-3 ">
							<div class="info ">
								<h3>分享技术</h3>
								<h4>创造价值</h4>
							</div>
							<div class="recommendationMain three">
								<a href="#"><img src="<%=path %>/package-style/style-front/images/recommendThree.jpg "></img></a>
							</div>
						</div>
					</div>
					<div class="clear "></div>
					<!--热门活动 -->
					<div class="am-container activity " style="min-height: 100px;">
						<div class="shopTitle">
							<h4>公告专栏</h4>
							<h3>专栏类型</h3>
							<span class="more ">
                              	<a href="# ">更多专栏<i class="am-icon-angle-right" style="padding-left:10px ;" ></i></a>
                        	</span>
						</div>
					  <div class="am-g am-g-fixed" style="padding: 15px;">
					  		<div style="text-align:center"> 
								<div class="sqBorder"> 
								<!--#####滚动区域#####-->
									<%--<div class="boxed_wrapper">--%>
										<section class="latest-project sec-padd">
												<div class="latest-project-carousel">
													<div class="item" style="padding-bottom: 0px">
														<div class="single-project">
															<figure class="imghvr-shutter-in-out-horiz">
																<img src="<%=path %>/package-style/style-front/notice/images/resource/4.jpg" alt="Awesome Image">
																<figcaption>
																	<div class="content">
																		<a href="<%=path %>/jsp/testSuccess.jsp" style="min-width:160px;"><h4>Latest Technology</h4></a>
																		<p  style="min-width:120px;">Consulting</p>
																	</div>
																</figcaption>
															</figure>
														</div>
													</div>
													<div class="item" style="padding-bottom: 0px">
														<div class="single-project">
															<figure class="imghvr-shutter-in-out-horiz">
																<img src="<%=path %>/package-style/style-front/notice/images/resource/4.jpg" alt="Awesome Image">
																<figcaption>
																	<div class="content">
																		<a href="<%=path %>/jsp/testSuccess.jsp" style="min-width:160px;"><h4>Latest Technology</h4></a>
																		<p style="min-width:120px;">Consulting</p>
																	</div>
																</figcaption>
															</figure>
														</div>
													</div>
													<div class="item" style="padding-bottom: 0px">
														<div class="single-project">
															<figure class="imghvr-shutter-in-out-horiz">
																<img src="<%=path %>/package-style/style-front/notice/images/resource/4.jpg" alt="Awesome Image">
																<figcaption>
																	<div class="content">
																		<a href="<%=path %>/jsp/testSuccess.jsp" style="min-width:160px;"><h4>Latest Technology</h4></a>
																		<p style="min-width:120px;">Consulting</p>
																	</div>
																</figcaption>
															</figure>
														</div>
													</div>
													<div class="item" style="padding-bottom: 0px">
														<div class="single-project">
															<figure class="imghvr-shutter-in-out-horiz">
																<img src="<%=path %>/package-style/style-front/notice/images/resource/4.jpg" alt="Awesome Image">
																<figcaption>
																	<div class="content">
																		<a href="<%=path %>/jsp/testSuccess.jsp" style="min-width:160px;"><h4>Latest Technology</h4></a>
																		<p style="min-width:120px;">Consulting</p>
																	</div>
																</figcaption>
															</figure>
														</div>
													</div>
													<c:forEach items="${noticeList }" var="list">
														<div class="item" style="padding-bottom: 0px">
															<div class="single-latest-project-carousel">
																<div class="single-project">
																	<figure class="imghvr-shutter-in-out-horiz">
																		<img src="<%=path %>/package-style/style-front/notice/images/resource/5.jpg" alt="Awesome Image">
																		<figcaption>
																			<div class="content">
																				<a href="<%=path %>/jsp/testSuccess.jsp" style="min-width:160px;"><h4>${list.noticeTitle}</h4></a>
																				<p style="min-width:120px;">${list.adminId}</p>
																			</div>
																		</figcaption>
																	</figure>
																</div>
															</div>
														</div>
													</c:forEach>
												</div>
										</section>
										<script src="<%=path %>/package-style/style-front/notice/js/jquery.js"></script>
										<script src="<%=path %>/package-style/style-front/notice/js/bootstrap.min.js"></script>
										<script src="<%=path %>/package-style/style-front/notice/js/owl.carousel.min.js"></script>
										<script src="<%=path %>/package-style/style-front/notice/js/custom.js"></script>
									<!--#####滚动区域#####-->
								</div>
							</div> 
					  </div>
                   </div>
					<div class="clear"></div>
                    <div id="f1">
					<!--甜点-->
					<div class="am-container">
						<div class="shopTitle" style="margin-top:0px;">
							<h4>国家科技成果转化服务示范基地</h4>
							<!--<h3>每一道甜品都有一个故事</h3>-->
							<span class="more">
                    			<a href="#">更多信息<i class="am-icon-angle-right" style="padding-left:10px ;" ></i></a>
                        	</span>
						</div>
					</div>
					<div class="am-g am-g-fixed floodFour">
						<div class="am-u-sm-5 am-u-md-4 text-one list ">
							<div class="word">
								<a class="outer" href="#"><span class="inner"><b class="text">厦门</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">济南</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">南宁</b></span></a>	
								<a class="outer" href="#"><span class="inner"><b class="text">宝鸡</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">苏州</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">成都</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">沈阳</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">北京</b></span></a>	
								<a class="outer" href="#"><span class="inner"><b class="text">锦州</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">银川</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">十堰</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">太原</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">贵阳</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">青岛</b></span></a>
								<a class="outer" href="#"><span class="inner"><b class="text">甘肃</b></span></a>									
							</div>
							<a href="# ">
								<img src="<%=path %>/package-style/style-front/images/actaction.png" />
							</a>
							<div class="triangle-topright"></div>						
						</div>
						 <!--流程相关-->
						 <div class="am-u-sm-4 text-four" style="width: 70%; height: 35%;">
						 	 <div class="main-flow">
						        <div class="business-header">
						            <div class="business-header-l fl">流程相关</div>
						            <div class="nian">2017年</div>
						            <ul class="business-header-ul">
						                <li>1月</li>
						                <li>2月</li>
						                <li>3月</li>
						                <li>4月</li>
						                <li>5月</li>
						                <li>6月</li>
						                <li>7月</li>
						                <li>8月</li>
						                <li>9月</li>
						                <li>10月</li>
						                <li>11月</li>
						                <li class="highlight">12月</li>
						            </ul>
						        </div>
						        <div class="map">
						            <div class="map-l fl" id="map-l">
						            </div>
						        </div>
					    	</div>
						 </div>
					</div>
                 <div class="clear "></div>  
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
		</div>
	</body>
</html>
