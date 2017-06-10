<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title></title>
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/pintuer.css">
    <link rel="stylesheet" href="<%=path %>/package-style/style-behind/css/admin.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/package-style/style-behind/canvas/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/package-style/style-behind/canvas/css/demo.css">
    <script src="<%=path %>/package-style/style-behind/js/jquery.js"></script>
    <script src="<%=path %>/package-style/style-behind/js/pintuer.js"></script>

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
<div class="panel admin-panel margin-top">
    <div class="grading_style" style="padding-top: 30px;">
        <!-- 数字时钟开始-->
        <div class="htmleaf-content" style="float: right;width: 200px;max-width: 200px;">
            <canvas id="clock1_" width="300px" height="300px" style="float: right">
            </canvas>
        </div>

    </div>
    <!--右侧样式-->
    <div class="page_right_style right_grading" style="float: none">
        <div class="Statistics_style" id="Statistic_pie">

            <div id="Statistics" class="Statistics"></div>
        </div><!--列表样式-->
    </div>
    <script type="text/javascript" src="<%=path %>/package-style/style-behind/canvas/js/canvas_clock.js"></script>
    <script type="text/javascript">
        clockd1_={
            "indicate": true,
            "indicate_color": "#222",
            "dial1_color": "#666600",
            "dial2_color": "#81812e",
            "dial3_color": "#9d9d5c",
            "time_add": 1,
            "time_24h": true,
            "date_add":3,
            "date_add_color": "#999",
        };
        var c = document.getElementById('clock1_');
        cns1_ = c.getContext('2d');
        clock_conti(300,cns1_,clockd1_);
    </script>

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
</div>
</body></html>