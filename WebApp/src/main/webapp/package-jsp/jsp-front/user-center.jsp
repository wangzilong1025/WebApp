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
		<title>个人中心</title>
		<link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/package-style/style-front/css/systyle.css" rel="stylesheet" type="text/css">
	</head>
<body>
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
					<li class="qc"><a href="<%=path %>/statistics/statisticsAchievementCount.do">往年统计</a></li>
					<li class="qc"><a href="#">公告</a></li>
					<li class="qc last"><a href="<%=path %>/statistics/selectCityByPie.do">当年排行</a></li>
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
					<div class="wrap-left">
						<div class="wrap-list">
							<div class="m-user">
								<!--个人信息 -->
								<div class="m-bg"></div>
								<div class="m-userinfo">
									<div class="m-baseinfo">
										<a href="<%=path %>/userInfo/userCenterInfomation.do">
											<c:choose>
												<c:when test="${userInfo.userImage == null}">
													<img class="am-circle am-img-thumbnail" id="userImage" src="<%=path %>/package-style/style-front/images/getAvatar.do.jpg"/>
												</c:when>
												<c:otherwise>
													<img class="am-circle am-img-thumbnail" id="userImage" src="<%=path %>/userInfoImage/${userInfo.userImage }"/>
												</c:otherwise>
											</c:choose>
										</a>
										<em class="s-name">(${userInfo.userNick })<span class="vip1"></span></em>
										<div class="s-prestige am-btn am-round">
											</span>会员福利</div>
									</div>
									<div class="m-right">
										<div class="m-new">
											<a href="news.html"><i class="am-icon-bell-o"></i>消息</a>
										</div>
										<div class="m-address">
											<a href="address.html" class="i-trigger">我的收货地址</a>
										</div>
									</div>
								</div>
								<!--个人资产-->
								<div class="m-userproperty">
									<div class="s-bar">
										<i class="s-icon"></i>个人资产
									</div>
									<p class="m-bonus">
										<a href="bonus.html">
											<i><img src="<%=path %>/package-style/style-front/images/bonus.png"/></i>
											<span class="m-title">红包</span>
											<em class="m-num">2</em>
										</a>
									</p>
									<p class="m-coupon">
										<a href="coupon.html">
											<i><img src="<%=path %>/package-style/style-front/images/coupon.png"/></i>
											<span class="m-title">优惠券</span>
											<em class="m-num">2</em>
										</a>
									</p>
									<p class="m-bill">
										<a href="bill.html">
											<i><img src="<%=path %>/package-style/style-front/images/wallet.png"/></i>
											<span class="m-title">钱包</span>
											<em class="m-num">2</em>
										</a>
									</p>
									<p class="m-big">
										<a href="#">
											<i><img src="<%=path %>/package-style/style-front/images/day-to.png"/></i>
											<span class="m-title">签到有礼</span>
										</a>
									</p>
									<p class="m-big">
										<a href="#">
											<i><img src="<%=path %>/package-style/style-front/images/72h.png"/></i>
											<span class="m-title">72小时发货</span>
										</a>
									</p>
								</div>
							</div>
							<div class="box-container-bottom"></div>
							<!--订单 -->
							<div class="m-order">
								<div class="s-bar">
									<i class="s-icon"></i>我的订单
									<a class="i-load-more-item-shadow" href="<%=path %>/order.html">全部成果</a>
								</div>
								<ul>
									<li><a href="order.html"><i><img src="<%=path %>/package-style/style-front/images/pay.png"/></i><span>成果新增</span></a></li>
									<li><a href="order.html"><i><img src="<%=path %>/package-style/style-front/images/send.png"/></i><span>待发布成果<em class="m-num">1</em></span></a></li>
									<li><a href="order.html"><i><img src="<%=path %>/package-style/style-front/images/receive.png"/></i><span>待审核成果</span></a></li>
									<li><a href="order.html"><i><img src="<%=path %>/package-style/style-front/images/comment.png"/></i><span>已通过成果<em class="m-num">3</em></span></a></li>
									<li><a href="change.html"><i><img src="<%=path %>/package-style/style-front/images/refund.png"/></i><span>未通过成果</span></a></li>
								</ul>
							</div>
							<!--九宫格-->
							<div class="user-patternIcon">
								<div class="s-bar">
									<i class="s-icon"></i>我的常用
								</div>
								<ul>
									<a href="home/shopcart.html"><li class="am-u-sm-4"><i class="am-icon-shopping-basket am-icon-md"></i><img src="<%=path %>/package-style/style-front/images/iconbig.png"/><p>购物车</p></li></a>
									<a href="collection.html"><li class="am-u-sm-4"><i class="am-icon-heart am-icon-md"></i><img src="<%=path %>/package-style/style-front/images/iconsmall1.png"/><p>我的收藏</p></li></a>
									<a href="home/home.html"><li class="am-u-sm-4"><i class="am-icon-gift am-icon-md"></i><img src="<%=path %>/package-style/style-front/images/iconsmall0.png"/><p>为你推荐</p></li></a>
									<a href="comment.html"><li class="am-u-sm-4"><i class="am-icon-pencil am-icon-md"></i><img src="<%=path %>/package-style/style-front/images/iconsmall3.png"/><p>好评宝贝</p></li></a>
									<a href="foot.html"><li class="am-u-sm-4"><i class="am-icon-clock-o am-icon-md"></i><img src="<%=path %>/package-style/style-front/images/iconsmall2.png"/><p>我的足迹</p></li></a>
								</ul>
							</div>




							<!--收藏夹 -->
							<div class="you-like">
								<div class="s-bar">我的收藏
									<a class="i-load-more-item-shadow" href="#"><i class="am-icon-refresh am-icon-fw"></i>更多</a>
								</div>
								<div class="s-content">
									<div class="s-item-wrap">
										<div class="s-item">

											<div class="s-pic">
												<a href="#" class="s-pic-link">
													<img src="<%=path %>/package-style/style-front/images/0-item_pic.jpg_220x220.jpg" alt="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰" title="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰" class="s-pic-img s-guess-item-img">
												</a>
											</div>
											<div class="s-price-box">
												<span class="s-price"><em class="s-price-sign">¥</em><em class="s-value">42.50</em></span>
												<span class="s-history-price"><em class="s-price-sign">¥</em><em class="s-value">68.00</em></span>

											</div>
											<div class="s-title"><a href="#" title="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰">包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰</a></div>
											<div class="s-extra-box">
												<span class="s-comment">好评: 98.03%</span>
												<span class="s-sales">月销: 219</span>

											</div>
										</div>
									</div>

									<div class="s-item-wrap">
										<div class="s-item">

											<div class="s-pic">
												<a href="#" class="s-pic-link">
													<img src="<%=path %>/package-style/style-front/images/1-item_pic.jpg_220x220.jpg" alt="s925纯银千纸鹤锁骨链短款简约时尚韩版素银项链小清新秋款女配饰" title="s925纯银千纸鹤锁骨链短款简约时尚韩版素银项链小清新秋款女配饰" class="s-pic-img s-guess-item-img">
												</a>
											</div>
											<div class="s-price-box">
												<span class="s-price"><em class="s-price-sign">¥</em><em class="s-value">49.90</em></span>
												<span class="s-history-price"><em class="s-price-sign">¥</em><em class="s-value">88.00</em></span>

											</div>
											<div class="s-title"><a href="#" title="s925纯银千纸鹤锁骨链短款简约时尚韩版素银项链小清新秋款女配饰">s925纯银千纸鹤锁骨链短款简约时尚韩版素银项链小清新秋款女配饰</a></div>
											<div class="s-extra-box">
												<span class="s-comment">好评: 99.74%</span>
												<span class="s-sales">月销: 69</span>

											</div>
										</div>
									</div>

									<div class="s-item-wrap">
										<div class="s-item">

											<div class="s-pic">
												<a href="#" class="s-pic-link">
													<img src="<%=path %>/package-style/style-front/images/-0-saturn_solar.jpg_220x220.jpg" alt="4折抢购!十二生肖925银女戒指,时尚开口女戒" title="4折抢购!十二生肖925银女戒指,时尚开口女戒" class="s-pic-img s-guess-item-img">
												</a>
											</div>
											<div class="s-price-box">
												<span class="s-price"><em class="s-price-sign">¥</em><em class="s-value">378.00</em></span>
												<span class="s-history-price"><em class="s-price-sign">¥</em><em class="s-value">1888.00</em></span>

											</div>
											<div class="s-title"><a href="#" title="4折抢购!十二生肖925银女戒指,时尚开口女戒">4折抢购!十二生肖925银女戒指,时尚开口女戒</a></div>
											<div class="s-extra-box">
												<span class="s-comment">好评: 99.93%</span>
												<span class="s-sales">月销: 278</span>

											</div>
										</div>
									</div>

									<div class="s-item-wrap">
										<div class="s-item">

											<div class="s-pic">
												<a href="#" class="s-pic-link">
													<img src="<%=path %>/package-style/style-front/images/0-item_pic.jpg_220x220.jpg" alt="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰" title="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰" class="s-pic-img s-guess-item-img">
												</a>
											</div>
											<div class="s-price-box">
												<span class="s-price"><em class="s-price-sign">¥</em><em class="s-value">42.50</em></span>
												<span class="s-history-price"><em class="s-price-sign">¥</em><em class="s-value">68.00</em></span>
											</div>
											<div class="s-title"><a href="#" title="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰">包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰</a></div>
											<div class="s-extra-box">
												<span class="s-comment">好评: 98.03%</span>
												<span class="s-sales">月销: 219</span>
											</div>
										</div>
									</div>
								</div>
							</div>




							<!--足迹展示-->
							<div class="you-like">
								<div class="s-bar">浏览足迹
									<a class="i-load-more-item-shadow" href="#"><i class="am-icon-refresh am-icon-fw"></i>更多</a>
								</div>
								<div class="s-content">
									<div class="s-item-wrap">
										<div class="s-item">

											<div class="s-pic">
												<a href="#" class="s-pic-link">
													<img src="<%=path %>/package-style/style-front/images/0-item_pic.jpg_220x220.jpg" alt="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰" title="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰" class="s-pic-img s-guess-item-img">
												</a>
											</div>
											<div class="s-price-box">
												<span class="s-price"><em class="s-price-sign">¥</em><em class="s-value">42.50</em></span>
												<span class="s-history-price"><em class="s-price-sign">¥</em><em class="s-value">68.00</em></span>

											</div>
											<div class="s-title"><a href="#" title="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰">包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰</a></div>
											<div class="s-extra-box">
												<span class="s-comment">好评: 98.03%</span>
												<span class="s-sales">月销: 219</span>

											</div>
										</div>
									</div>

									<div class="s-item-wrap">
										<div class="s-item">

											<div class="s-pic">
												<a href="#" class="s-pic-link">
													<img src="<%=path %>/package-style/style-front/images/1-item_pic.jpg_220x220.jpg" alt="s925纯银千纸鹤锁骨链短款简约时尚韩版素银项链小清新秋款女配饰" title="s925纯银千纸鹤锁骨链短款简约时尚韩版素银项链小清新秋款女配饰" class="s-pic-img s-guess-item-img">
												</a>
											</div>
											<div class="s-price-box">
												<span class="s-price"><em class="s-price-sign">¥</em><em class="s-value">49.90</em></span>
												<span class="s-history-price"><em class="s-price-sign">¥</em><em class="s-value">88.00</em></span>

											</div>
											<div class="s-title"><a href="#" title="s925纯银千纸鹤锁骨链短款简约时尚韩版素银项链小清新秋款女配饰">s925纯银千纸鹤锁骨链短款简约时尚韩版素银项链小清新秋款女配饰</a></div>
											<div class="s-extra-box">
												<span class="s-comment">好评: 99.74%</span>
												<span class="s-sales">月销: 69</span>

											</div>
										</div>
									</div>

									<div class="s-item-wrap">
										<div class="s-item">

											<div class="s-pic">
												<a href="#" class="s-pic-link">
													<img src="<%=path %>/package-style/style-front/images/-0-saturn_solar.jpg_220x220.jpg" alt="4折抢购!十二生肖925银女戒指,时尚开口女戒" title="4折抢购!十二生肖925银女戒指,时尚开口女戒" class="s-pic-img s-guess-item-img">
												</a>
											</div>
											<div class="s-price-box">
												<span class="s-price"><em class="s-price-sign">¥</em><em class="s-value">378.00</em></span>
												<span class="s-history-price"><em class="s-price-sign">¥</em><em class="s-value">1888.00</em></span>

											</div>
											<div class="s-title"><a href="#" title="4折抢购!十二生肖925银女戒指,时尚开口女戒">4折抢购!十二生肖925银女戒指,时尚开口女戒</a></div>
											<div class="s-extra-box">
												<span class="s-comment">好评: 99.93%</span>
												<span class="s-sales">月销: 278</span>

											</div>
										</div>
									</div>

									<div class="s-item-wrap">
										<div class="s-item">

											<div class="s-pic">
												<a href="#" class="s-pic-link">
													<img src="<%=path %>/package-style/style-front/images/0-item_pic.jpg_220x220.jpg" alt="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰" title="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰" class="s-pic-img s-guess-item-img">
												</a>
											</div>
											<div class="s-price-box">
												<span class="s-price"><em class="s-price-sign">¥</em><em class="s-value">42.50</em></span>
												<span class="s-history-price"><em class="s-price-sign">¥</em><em class="s-value">68.00</em></span>
											</div>
											<div class="s-title"><a href="#" title="包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰">包邮s925纯银项链女吊坠短款锁骨链颈链日韩猫咪银饰简约夏配饰</a></div>
											<div class="s-extra-box">
												<span class="s-comment">好评: 98.03%</span>
												<span class="s-sales">月销: 219</span>
											</div>
										</div>
									</div>
								</div>
							</div>



						</div>
					</div>
					<div class="wrap-right">
						<!-- 日历-->
						<div class="day-list">
							<div class="s-bar">
								<a class="i-history-trigger s-icon" href="#"></a>我的日历
								<a class="i-setting-trigger s-icon" href="#"></a>
							</div>
							<div class="s-care s-care-noweather">
								<div class="s-date">
									<em>${day}</em>
									<span>星期一</span>
									<span>${year}.${month}</span>
								</div>
							</div>
						</div>
						<!--新品 -->
						<div class="new-goods">
							<div class="s-bar">
								<i class="s-icon"></i>今日新品
								<a class="i-load-more-item-shadow">15款新品</a>
							</div>
							<div class="new-goods-info">
								<a class="shop-info" href="#" target="_blank">
									<div class="face-img-panel">
										<img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" alt="">
									</div>
									<span class="new-goods-num ">4</span>
									<span class="shop-title">剥壳松子</span>
								</a>
								<a class="follow " target="_blank">关注</a>
							</div>
						</div>
						<!--热卖推荐 -->
						<div class="new-goods">
							<div class="s-bar">
								<i class="s-icon"></i>热卖推荐
							</div>
							<div class="new-goods-info">
								<a class="shop-info" href="#" target="_blank">
									<div >
										<img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" alt="">
									</div>
                                    <span class="one-hot-goods">￥9.20</span>
								</a>
							</div>
						</div>
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