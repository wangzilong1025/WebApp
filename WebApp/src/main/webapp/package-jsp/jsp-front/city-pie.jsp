<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>统计-柱状图</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>

    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">


    <!--饼状图样式开始-->
    <link href="<%=path %>/package-style/style-pie/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="<%=path %>/package-style/style-pie/css/style.css"/>
    <link href="<%=path %>/package-style/style-pie/assets/css/codemirror.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=path %>/package-style/style-pie/assets/css/ace.min.css" />
    <link rel="stylesheet" href="<%=path %>/package-style/style-pie/font/css/font-awesome.min.css" />
    <script src="<%=path %>/package-style/style-pie/js/jquery-1.9.1.min.js"></script>
    <script src="<%=path %>/package-style/style-pie/assets/js/typeahead-bs2.min.js"></script>
    <script src="<%=path %>/package-style/style-pie/js/lrtk.js" type="text/javascript" ></script>
    <script src="<%=path %>/package-style/style-pie/assets/js/jquery.dataTables.min.js"></script>
    <script src="<%=path %>/package-style/style-pie/assets/js/jquery.dataTables.bootstrap.js"></script>
    <script src="<%=path %>/package-style/style-pie/assets/layer/layer.js" type="text/javascript" ></script>
    <script src="<%=path %>/package-style/style-pie/assets/dist/echarts.js"></script>
    <!--饼状图样式结束 -->
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
<!--文章 -->
<div class="am-g am-g-fixed blog-g-fixed bloglist">
    <div class="am-u-md-9">
        <h1 style="text-align: center;font-size: 20px;padding-top: 20px;" align="center"><font size="20px;">2017年各省、市成果统计---饼状图</font></h1>
        <div class="grading_style" style="padding-top: 100px;padding-bottom: 100px;">
            <!--右侧样式-->
            <div class="page_right_style right_grading" style="padding-left: 40%;">
                <div class="Statistics_style" id="Statistic_pie">

                    <div id="Statistics" class="Statistics"></div>
                </div><!--列表样式-->
            </div>
        </div>
        <hr class="am-article-divider blog-hr">
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
</body>
</html>
<script type="text/javascript">
    $(function() {
        $("#category").fix({
            //float : 'left',
            //minStatue : true,
            skin : 'green',
            durationTime :false,
            spacingw:20,
            spacingh:240,
            set_scrollsidebar:'.right_grading',
        });
    });
    $(function() {
        $("#Statistic_pie").fix({
            //float : 'top',
            //minStatue : true,
            skin : 'green',
            durationTime :false,
            spacingw:0,
            spacingh:0,
            close_btn:'.top_close_btn',
            show_btn:'.top_show_btn',
            side_list:'.Statistics',
            close_btn_width:80,
            side_title:'.Statistic_title',
        });
    });

</script>
<script type="text/javascript">
    //初始化宽度、高度
    $(".widget-box").height($(window).height());
    $(".page_right_style").width($(window).width()-220);
    //$(".table_menu_list").width($(window).width()-240);
    //当文档窗口发生改变时 触发
    $(window).resize(function(){
        $(".widget-box").height($(window).height());
        $(".page_right_style").width($(window).width()-220);
        //$(".table_menu_list").width($(window).width()-240);
    })
    /**************/
    require.config({
        paths: {
            echarts: '<%=path %>/package-style/style-pie/assets/dist'
        }
    });
    require(
        [
            'echarts',
            'echarts/theme/macarons',
            'echarts/chart/pie',   // 按需加载所需图表，如需动态类型切换功能，别忘了同时加载相应图表
            'echarts/chart/funnel'
        ],
        function (ec,theme) {
            var myChart = ec.init(document.getElementById('Statistics'),theme);
            option = {
                title : {
                    text: '科研成果总量各省、市统计数据',
                    subtext: '实时更新最新数据',
                    x:'left'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    x : 'center',
                    y : 'bottom',
                    /*['普通用户','铁牌用户','铜牌用户','银牌用户','金牌用户','钻石用户','蓝钻用户','红钻用户','金钻用户']*/
                    data:${city }
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: false},
                        dataView : {show: false, readOnly: true},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 10000000
                                }
                            }
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : [
                    {
                        name:'科研成果数量',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '39%'],
                        data:${cityCount}
                    }
                ]
            };
            myChart.setOption(option);
        }
    );
</script>
