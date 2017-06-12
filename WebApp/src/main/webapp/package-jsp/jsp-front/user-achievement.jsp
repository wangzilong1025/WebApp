<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>科研成果详情</title>
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/package-style/style-front/basic/css/demo.css" rel="stylesheet" type="text/css" />
    <link type="text/css" href="<%=path %>/package-style/style-front/css/optstyle.css" rel="stylesheet" />
    <link type="text/css" href="<%=path %>/package-style/style-front/css/style.css" rel="stylesheet" />
    <!--正文样式开始-->
    <link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
    <!--正文样式结束-->
    <script type="text/javascript" src="<%=path %>/package-style/style-front/basic/js/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="<%=path %>/package-style/style-front/basic/js/quick_links.js"></script>
    <script type="text/javascript" src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
    <script type="text/javascript" src="<%=path %>/package-style/style-front/js/jquery.imagezoom.min.js"></script>
    <script type="text/javascript" src="<%=path %>/package-style/style-front/js/jquery.flexslider.js"></script>
    <script type="text/javascript" src="<%=path %>/package-style/style-front/js/list.js"></script>

    <script type="text/javascript">
        function collectAdd(collectId){
            coId = collectId;
            if(coId != ""){
                alert("收藏成功!")
                $.ajax({
                    type: "post",  //get或post
                    async : false,  //可选，默认true  true或false
                    url:  "<%=path %>/achievementCollect/collectAchievementByAchievementId.do",   //请求的服务器地址
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    data:{
                        "collectId":coId,
                        "time":new Date()
                    }
                });
            }
        }
    </script>
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
        </div>
    </article>
</header>

    <!--悬浮搜索框-->
    <div class="nav white">
        <div class="search-bar pr">
            <a name="index_none_header_sysc" href="#"></a>
            <form action="<%=path %>/menu/selectMenuOneInSearch.do" method="post" id="submit">
                <input id="searchContent" name="searchContent" type="text" placeholder="输入成果标题/发布人/单位名称" autocomplete="off">
                <input id="num" type="hidden" name="num" value="1"/>
                <input id="ai-topsearch" class="submit am-btn" value="搜索" type="submit">
            </form>
        </div>
    </div>

    <div class="clear"></div>
    <b class="line"></b>



<div class="listMain">
    <!--分类-->
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
    <ol class="am-breadcrumb am-breadcrumb-slash">
        <li><a href="<%=path %>/menu/getMenuList.do">首页</a></li>
        <li><a href="#">成果分类</a></li>
        <li class="am-active">成果内容</li>
    </ol>

<!-- introduce-->
<!--左边框内容-->
<div class="introduce">
    <div class="browse">
        <div class="mc">
            <ul>
                <div class="mt">
                    <h2>看了又看</h2>
                </div>

                <li class="first">
                    <div class="p-img">
                        <a  href="#"> <img class="" src="<%=path %>/package-style/style-front/images/browse1.jpg"> </a>
                    </div>
                    <div class="p-name"><a href="#">
                        【三只松鼠_开口松子】零食坚果特产炒货东北红松子原味
                    </a>
                    </div>
                    <div class="p-price"><strong>￥35.90</strong></div>
                </li>
                <li>
                    <div class="p-img">
                        <a  href="#"> <img class="" src="<%=path %>/package-style/style-front/images/browse1.jpg"> </a>
                    </div>
                    <div class="p-name"><a href="#">
                        【三只松鼠_开口松子】零食坚果特产炒货东北红松子原味
                    </a>
                    </div>
                    <div class="p-price"><strong>￥35.90</strong></div>
                </li>
                <li>
                    <div class="p-img">
                        <a  href="#"> <img class="" src="<%=path %>/package-style/style-front/images/browse1.jpg"> </a>
                    </div>
                    <div class="p-name"><a href="#">
                        【三只松鼠_开口松子】零食坚果特产炒货东北红松子原味
                    </a>
                    </div>
                    <div class="p-price"><strong>￥35.90</strong></div>
                </li>
                <li>
                    <div class="p-img">
                        <a  href="#"> <img class="" src="<%=path %>/package-style/style-front/images/browse1.jpg"> </a>
                    </div>
                    <div class="p-name"><a href="#">
                        【三只松鼠_开口松子】零食坚果特产炒货东北红松子原味
                    </a>
                    </div>
                    <div class="p-price"><strong>￥35.90</strong></div>
                </li>

            </ul>
        </div>
    </div>

    <div class="introduceMain">
        <div class="am-tabs" data-am-tabs>
            <ul class="am-avg-sm-3 am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active" style="border-bottom: 0px;border-top: 0px;">
                    <a href="#"><span class="index-needs-dt-txt">科研成果详情</span></a>
                </li>
                <li  style="border-bottom: 0px;border-top: 0px;">
                    <a href="#"><span class="index-needs-dt-txt">猜你喜欢</span></a>
                </li>
            </ul>


            <div class="am-tabs-bd">
                <div class="am-tab-panel am-fade am-in am-active">
                        <div class="am-g am-g-fixed blog-g-fixed bloglist" style="min-height: 958px;">
                            <div class="am-u-md-9">
                                <article class="blog-main">
                                    <h1 style="font-size: 25px;" align="center">
                                        <font style="font-size: 25px;">${achievement.achievementName}</font>
                                    </h1>
                                    <div align="right">
                                        <h4 class="am-article-meta blog-meta"><font style="font-size: 12px;">发布人:${achievement.userNick}</font></h4>
                                    </div>
                                    <div align="right">
                                        <h4 class="am-article-meta blog-meta"><font style="font-size: 12px;">发布时间:${achievement.timeToString}</font></h4>
                                    </div>
                                    <div align="right">
                                        <h4 class="am-article-meta blog-meta"><font style="font-size: 12px;">成果类型:${menuNameOne}-->${menuNameTwo}-->${menuNameThree}</font></h4>
                                    </div>

                                    <div class="pay">
                                        <li style="float: right">
                                            <div class="clearfix tb-btn tb-btn-buy theme-login">
                                                <a id="LikBuy" title="收藏成果" href="javascript:(0)" onclick="collectAdd(${achievement.achievementId})">收藏成果</a>
                                            </div>
                                        </li>
                                        <li style="float: right;">
                                            <div class="clearfix tb-btn tb-btn-basket theme-login">
                                                <a id="LikBasket" title="查找相似" href="#"><i></i>查找相似</a>
                                            </div>
                                        </li>
                                    </div>

                                    <div class="am-g blog-content">
                                        <div class="am-u-sm-12">
                                            <p align="left">${achievement.achievementContent}</p>
                                            <p align="left">5月16日上午，为加速科技成果转化，助推国家科技成果转化服务（太原）示范基地建设，受太原市科技局邀请，国家科技成果网在太原举行科技成果推介发布会，共推出300项国家科技成果。</p>
                                            <div class="Row">
                                                <li style="float: left;width: 33.3%;padding: 5px;"><img  src="<%=path %>/package-style/style-front/images/blog01.jpg"/></li>
                                                <li style="float: left;width: 33.3%;padding: 5px;"><img src="<%=path %>/package-style/style-front/images/blog02.jpg"/></li>
                                                <li style="float: left;width: 33.3%;padding: 5px;"><img src="<%=path %>/package-style/style-front/images/blog03.jpg"/></li>
                                            </div>
                                            <p align="left">国家科技成果转化服务（太原）示范基地于2013年8月正式获得批复，基地按照“科技创新、应用转化、技术服务”三位一体的运行模式，不断完善全链条、多要素、网络化的科技成果转化服务体系，围绕装备制造、新材料、节能环保、生物医药、电子信息及物联网技术应用、新能源汽车等战略性新兴产业，建立和壮大了一批产业特点突出、技术水平领先、示范效应显著的科技成果转化示范项目和企业，进一步增强了科技创新对转型发展的支撑力和贡献度。此次国家科技成果转化服务（太原）示范基地成果推介发布活动中，现场发布电子信息、先进制造、新材料、节能环保等领域科技成果300项。项目贴近太原市优势产业，技术先进，成熟度高，产业化前景好，并具有明确的转化需求。</p>
                                            <p align="left">目前，太原示范基地与国家科技成果网签订长期合作协议，按照下一步太原示范基地建设的工作安排，国家科技成果网将作好针对性的服务，为太原示范基地提供项目对接、信息咨询、科技专家人才引进等方面的支持。</p>
                                        </div>
                                    </div>
                                </article>
                            </div>
                        </div>
                    <div class="clear"></div>
                </div>

                <div class="am-tab-panel am-fade">
                    <div class="like">
                        <ul class="am-avg-sm-2 am-avg-md-3 am-avg-lg-4 boxes">
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                            <li>
                                <div class="i-pic limit">
                                    <img src="<%=path %>/package-style/style-front/images/imgsearch1.jpg" />
                                    <p>【良品铺子_开口松子】零食坚果特产炒货
                                        <span>东北红松子奶油味</span></p>
                                    <p class="price fl">
                                        <b>¥</b>
                                        <strong>298.00</strong>
                                    </p>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="clear"></div>
                    <!--分页 -->
                    <ul class="am-pagination am-pagination-right">
                        <li class="am-disabled"><a href="#">&laquo;</a></li>
                        <li class="am-active"><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">&raquo;</a></li>
                    </ul>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<div class="clear"></div>
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