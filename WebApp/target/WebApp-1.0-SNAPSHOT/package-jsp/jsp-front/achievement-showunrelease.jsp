<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">
		<meta name="description" content="Nifty Modal Window Effects with CSS Transitions and Animations" />
		<meta name="keywords" content="modal, window, overlay, modern, box, css transition, css animation, effect, 3d, perspective" />
		<meta name="author" content="Codrops"/>
		<title>成果展示</title>
		<link href="<%=path %>/style/frontStyle/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/style/frontStyle/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/style/frontStyle/css/personal.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/style/frontStyle/css/bilstyle.css" rel="stylesheet" type="text/css">
		<script src="<%=path %>/style/frontStyle/AmazeUI-2.4.2/assets/js/jquery.min.js" type="text/javascript"></script>
		<script src="<%=path %>/style/frontStyle/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
		<script type="text/javascript">
		function deleteAchievement() { 
			var msg = "确定要删除您的科研成果吗?"; 
			if (confirm(msg)==true){ 
				return true; 
			}else{ 
				return false; 
			} 
		} 
		</script>
		<!-- 开始 -->
		<!--<script type="text/javascript">
		function selectAchievement(id){
			window.open(''+id, 'newwindow', 'height=500, width=725, top=100, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=n o, status=no');
		};
		function updateAchievement(id){
			window.open(''+id, 'newwindow', 'height=500, width=725, top=100, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=n o, status=no');
		};
		
		</script>
		--><!--结束-->
	</head>
	<body>
		<!--头 -->
		<header>
			<article>
				<div class="mt-logo">
					<!--顶部导航条 -->
					<div class="am-container header">
						<ul class="message-l">
					   		<div class="topMessage">
								<div class="menu-hd">
									<c:choose>
										<c:when test="${sessionScope.user == null }">
											<a href="<%=path %>/jsp/front/login.jsp" target="_top" class="h">亲，请登录</a>
											<a href="<%=path %>/jsp/front/regist.jsp" target="_top">免费注册</a>
										</c:when>
										<c:otherwise>
											欢迎登陆，${sessionScope.user.userName }
											<a href="<%=path %>/jsp/front/login.jsp" target="_top">退出</a>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</ul>
						<ul class="message-r">
							<div class="topMessage home">
								<div class="menu-hd"><a href="#" target="_top" class="h">商城首页</a></div>
							</div>
							<div class="topMessage my-shangcheng">
								<div class="menu-hd MyShangcheng"><a href="#" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a></div>
							</div>
							<div class="topMessage favorite">
								<div class="menu-hd"><a href="#" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>收藏夹</span></a></div>
							</div>
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
						<li class="index"><a href="#">首页</a></li>
                        <li class="qc"><a href="#">闪购</a></li>
                        <li class="qc"><a href="#">限时抢</a></li>
                        <li class="qc"><a href="#">团购</a></li>
                        <li class="qc last"><a href="#">大包装</a></li>
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
					<div class="am-cf am-padding">
						<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">成果展示</strong> / <small>Achievement&nbsp;Show</small></div>
					</div>
					<hr>
					<!--交易时间	-->
					<div class="order-time">
						<label class="form-label">发布时间：</label>
						<div class="show-input">
							<select class="am-selected" data-am-selected>
								<option value="today">今天</option>
								<option value="sevenDays" selected="">最近一周</option>
								<option value="oneMonth">最近一个月</option>
								<option value="threeMonths">最近三个月</option>
								<option class="date-trigger">自定义时间</option>
							</select>
						</div>
                        <div class="clear"></div>
					</div>
					
					<table width="100%">
						<thead>
							<tr>
								<th class="memo">成果图片</th>
								<th class="time">创建时间</th>
								<th class="name">成果标题</th>
								<th class="amount">作者</th>
								<th class="amount">所在单位</th>
								<th class="action">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${achievementList }" var="list">
								<tr>
									<td class="img" align="center" style="padding: 20px;">
										<c:choose>
											<c:when test="${list.achievementOneImage == null }">
												<i><img src="<%=path %>/style/images/songzi.jpg"></i>
											</c:when>
											<c:otherwise>
												<i><img src="<%=path %>/achievementImage/${list.achievementOneImage }"></i>
											</c:otherwise>
										</c:choose>
									</td>
									<td align="center" style="text-align: center; min-width: 150px; padding: 20px;">
										${list.releaseTime}
									</td>
									<td class="title name" align="center" style="white-space:nowrap; overflow:hidden; text-overflow:ellipsis; width: 180px; max-width: 180px; border: 10px 10px; padding: 20px;">
											${list.achievementName }
									</td>
									<td class="title name" align="center" style="padding: 20px;">
											${list.userNick }
									</td>
									<td class="amount" align="center" style="padding: 20px;">
										<span class="amount-pay">${list.unitName }</span>
									</td>
									<td class="operation" align="center" style="padding: 20px;">
										<a href="<%=path %>/achievement/selectAchievementByAchievementId.do?id=${list.achievementId}">查看</a>
										<a href="<%=path %>/achievement/updateAchievementByAchievementId.do?id=${list.achievementId}">修改</a>
										<a href="<%=path %>/achievement/">发布</a>
										<!--<span style="cursor: pointer;"><a onclick="selectAchievement(${list.achievementId})"><font color="black">查看</font></a></span>
										<span style="cursor: pointer;"><a onclick="updateAchievement(${list.achievementId})"><font color="black">修改</font></a></span>-->
										<a href="<%=path %>/achievement/deleteAchievementByAchievementId.do?id=${list.achievementId}" onclick="javascript:return deleteAchievement()">删除</a>
									</td>
									
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
							<li> <a href="javascript:void();">安全设置</a></li>
							<li> <a href="<%=path %>/findAddress.do">收货地址</a></li>
						</ul>
					</li>
					<li class="person">
						<a href="#"><font style="font-weight: bold">我的成果</font></a>
						<ul>
							<li> <a href="<%=path %>/collectionAll.do">收藏</a></li>
							<li> <a href="<%=path %>/footMark/listFoot.do">足迹浏览</a></li>
							<li> <a href="<%=path %>/achievement/queryAllAchievement.do">已发布成果</a></li>
							<li class="active"> <a href="<%=path %>/achievement/queryAllAchievementUnrelease.do">未发布成果</a></li>
							<li> <a href="<%=path %>/menu/selectMenuOne.do">成果新增</a></li>
						</ul>
					</li>
				</ul>
			</aside>
		</div>
		<div class="md-overlay"></div>
		<script src="<%=path %>/style/scrollStyle/js/classie.js"></script>
		<script src="<%=path %>/style/scrollStyle/js/modalEffects.js"></script>
		<script>
			// this is important for IEs
			var polyfilter_scriptpath = '/js/';
		</script>
		<script src="<%=path %>/style/scrollStyle/js/cssParser.js"></script>
		<script src="<%=path %>/style/scrollStyle/js/css-filters-polyfill.js"></script>
	</body>
</html>