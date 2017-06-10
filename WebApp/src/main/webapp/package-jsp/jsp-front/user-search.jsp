<%--suppress CheckValidXmlInScriptTagBody --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>科研成果搜索</title>
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/package-style/style-front/basic/css/demo.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/package-style/style-front/css/seastyle.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/package-style/style-front/basic/js/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="<%=path %>/package-style/style-front/js/script.js"></script>
</head>
<script type="text/javascript">
    function type1(topId1){
        $("dd[name='ccc']").remove();
        $.ajax({
            type :"post",
            async : false,
            url: "<%=path %>/menu/selectMenuTwoInSearch.do?topId1="+topId1,
            data:
                {//请求携带的参数，一个或者多个均可
                    "topId1":topId1
                },
                success:function(data){
                //alert("快快进来！！！啦啦啦啦啦啦");
                var json = eval(data);
                if(data.length==0){
                    alert("没有发现商品子类");
                }else{
                    for(var i = 0;i<json.length;i++){
                        var topId2 = json[i].topId;
                        var topName3 = json[i].topName;
                        $(".searchType1").append("<dd name='ccc'><a href='javascript:void();' value='"+topId2+"' onclick='type2("+topId2+")' name='aaa'>"+topName3+"</a></dd>");
                    }
                }
            }
        });
    };

    function type2(topId2){
        $("dd[name='ddd']").remove();
        $.ajax({
            type:"post",
            async:false,
            url: "<%=path %>/menu/selectMenuThreeInSearch.do?topId2="+topId2,
            data:
                {//请求携带的参数，一个或者多个均可
                    "topId2":topId2
                } ,
            success:function(data2){
                var json2 = eval(data2);
                if(data2.length==0){
                    alert("没有发现商品子类");
                }else{
                    for(var i = 0;i<json2.length;i++){
                        var topId3 = json2[i].topId;
                        var topName3 = json2[i].topName;
                        $(".searchType2").append("<dd name='ddd'><a href="+"<%=path%>/achievement/selectAllAchievementByAchievementTypeForMenu.do?topId="+topId3 +" value='+topId3+'  name='bbb'>"+topName3+"</a></dd>");
                    }
                }
            }
        });
    };
</script>
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
            <div class="search-bar pr"><!--/achievement/findLikeAchievement.do-->
                <a name="index_none_header_sysc" href="#"></a>
                <form action="<%=path %>/menu/selectMenuOneInSearch.do" method="post" id="submit">
                    <input id="searchContent" name="searchContent" type="text" value="${searchContent}" placeholder="输入成果标题/发布人/单位名称" autocomplete="off">
                    <input id="num" type="hidden" name="num" value="1"/><!--默认查询全部的科研新成果-->
                    <input id="ai-topsearch" class="submit am-btn" value="搜索" type="submit">
                </form>
            </div>
        </div>

        <div class="clear"></div>
        <b class="line"></b>
<div class="search">
    <div class="search-list">
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
        <div class="am-g am-g-fixed">
            <div class="am-u-sm-12 am-u-md-12">
                <div class="theme-popover">
                    <div class="searchAbout">
                        <span class="font-pale">相关搜索：</span>
                        <a title="太阳能发电" href="#">太阳能发电&nbsp;&nbsp;&nbsp;</a>
                        <a title="高分子材料" href="#">高分子材料&nbsp;&nbsp;&nbsp;</a>
                        <a title="提高电池利用率" href="#">提高电池利用率</a>
                    </div>

                    <ul class="select">
                        <p class="title font-normal">
                            <span class="fl">${name } </span>
                            <span class="total fl">搜索到 <strong class="num">${total }</strong> 件相关商品</span>
                        </p>
                        <li class="select-list">
                            <dl id="select1">
                                <dt class="am-badge am-round">类别</dt>
                                <div class="dd-conent">
                                    <c:forEach items="${oneMenuInSearch }" var="list1">
                                        <dd><a class="gettype" href="javascript:void();" onclick="type1(${list1.topId })">${list1.topName }</a></dd>
                                    </c:forEach>
                                </div>
                            </dl>
                        </li>
                        <div class="clear"></div>
                        <li class="select-list" style="min-height: 46px;">
                            <dl id="select2">
                                <dt class="am-badge am-round">已选</dt>
                                <div class="dd-conent">
                                    <div class="searchType1">

                                    </div>
                                </div>
                            </dl>
                        </li>
                        <div class="clear"></div>
                        <li class="select-list" style="min-height: 46px;">
                            <dl id="select3">
                                <dt class="am-badge am-round">已选</dt>
                                <div class="dd-conent">
                                    <div class="searchType2">

                                    </div>
                                </div>
                            </dl>
                        </li>
                    </ul>

                    <div class="clear"></div>
                </div>
                <div class="search-content">
                    <div class="sort">
                        <li class="first"><a title="综合">综合排序</a></li>
                        <li><a title="销量">销量排序</a></li>
                        <li><a title="价格">价格优先</a></li>
                        <li class="big"><a title="评价" href="#">评价为主</a></li>
                    </div>
                    <div class="clear"></div>
                    <ul class="am-avg-sm-2 am-avg-md-3 am-avg-lg-4 boxes">
                        <c:choose>
                            <c:when test="${empty searchList }">
                                &nbsp;&nbsp;亲,没有搜索到相关科研成果报告/(ㄒoㄒ)/~~
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${searchList}" var="achievementList">
                                    <li>
                                        <a href="<%=path%>/achievement/findAchievementDetailByAchievementId.do?achievementId=${achievementList.achievementId}">
                                            <div class="i-pic limit">
                                                <img width="218px;" height="218px;" src="<%=path %>/achievementImage/${achievementList.achievementImages}" />
                                                <p class="title fl"><b>标题:</b>${achievementList.achievementName}</p>
                                                <p class="title fl"><b>类型:</b>${achievementList.achievementTypeName}</p>
                                                <p class="title fl"><b>时间:</b>${achievementList.timeToString}</p>
                                                <p class="price fl">
                                                    <b>作者:</b>
                                                    <strong>${achievementList.userNick}</strong>
                                                </p>
                                            </div>
                                        </a>
                                    </li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
                <div class="search-side">
                    <div class="side-title">
                        经典成果
                    </div>

                    <li>
                        <div class="i-pic check">
                            <img src="<%=path %>/package-style/style-front/images/cp.jpg" />
                            <p class="check-title">PLoS-成果分析</p>
                            <p class="price fl">
                                <b>访问量:</b>
                                <strong>2189753</strong>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="i-pic check">
                            <img src="<%=path %>/package-style/style-front/images/cp2.jpg" />
                            <p class="check-title">机械-机械优化</p>
                            <p class="price fl">
                                <b>访问量:</b>
                                <strong>1677834</strong>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="i-pic check">
                            <img src="<%=path %>/package-style/style-front/images/cp3.jpg" />
                            <p class="check-title">中国食品-成果展</p>
                            <p class="price fl">
                                <b>访问量:</b>
                                <strong>111079</strong>
                            </p>
                        </div>
                    </li>
                </div>
                <div class="clear"></div>
                <!--分页 -->
                <ul class="am-pagination am-pagination-right">
                    <li class="am-disabled"><a href="#">&laquo;</a></li>
                   <%-- <li class="am-active"><a href="<%=path %>/menu/selectMenuOneInSearch.do">1</a></li>
                    <li><a href="<%=path %>/menu/selectMenuOneInSearch.do">2</a></li>
                    <li><a href="<%=path %>/menu/selectMenuOneInSearch.do">3</a></li>
                    <li><a href="<%=path %>/menu/selectMenuOneInSearch.do">4</a></li>
                    <li><a href="<%=path %>/menu/selectMenuOneInSearch.do">5</a></li>
                    <li><a href="<%=path %>/menu/selectMenuOneInSearch.do">&raquo;</a></li>--%>
                    <c:forEach var="1" begin="1" end="${page}">
                        <li><a href="<%=path %>/menu/selectMenuOneInSearch.do?currentPage="+${i}>${i}</a></li>
                    </c:forEach>
                    <li><a href="<%=path %>/menu/selectMenuOneInSearch.do">&raquo;</a></li>
                </ul>

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
</div>
</body>
</html>