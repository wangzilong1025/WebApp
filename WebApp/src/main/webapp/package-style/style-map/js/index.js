/**
 * Created by fudexuan on 2016/10/19.
 */
$(function(){

    $('.business-header .nian').mouseenter(function(){
        $(this).addClass('highlight');
    });
    $('.business-header .nian').mouseleave(function(){
        $(this).removeClass('highlight');
    });
    // 统计图
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });


    $('#map-r-tbody tr').bind('mouseenter',function(){
        // current = $(this).css('backgroundColor');

        $(this).css("backgroundColor","#87CEFA");
    });
    $('#map-r-tbody tr').bind('mouseleave',function(){
        $(this).css('backgroundColor',"");
    });

    //地图
    require(
        [
            'echarts',
            'echarts/chart/map' //使用饼形图就加载pie模块，按需加载
        ],
        function(ec){
            var mcharts = ec.init(document.getElementById('map-l'));
            var option = {
                title : {
                    text: '各省、市科研成果统计图',
                    subtext: '年统计量',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    x:'left',
                    data:['']
                },

                dataRange: {
                    x: 'left',
                    y: 'bottom',
                    splitList: [
                        {start: 1500},
                        {start: 900, end: 1500},
                        {start: 310, end: 1000},
//                                        {start: 200, end: 300},
                        {start: 10, end: 200, label: '10 到 200（自定义label）'},
                        {start: 5, end: 5, label: '5（自定义特殊颜色）', color: 'black'}
//                                        {end: 10}
                    ],
                    color: ['#E0022B', '#E09107', '#A3E00B']
                },

                roamController: {
                    show: true,
                    x: 'left',
                    mapTypeControl: {
                        'china': true
                    }
                },
                series : [
                    {
                        name: '年统计量',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        itemStyle:{
                            normal:{
                                label:{
                                    show:true,
                                    textStyle: {
                                        color: "rgb(249, 249, 249)"
                                    }
                                }
                            },
                            emphasis:{label:{show:true}}
                        },
                        data:[
                            {name: '北京',value: Math.round(Math.random()*6000)},
                            {name: '天津',value: Math.round(Math.random()*5000)},
                            {name: '上海',value: Math.round(Math.random()*6000)},
                            {name: '重庆',value: Math.round(Math.random()*5000)},
                            {name: '河北',value: 0},
                            {name: '河南',value: Math.round(Math.random()*2000)},
                            {name: '云南',value: 5},
                            {name: '辽宁',value: 305},
                            {name: '黑龙江',value: Math.round(Math.random()*2000)},
                            {name: '湖南',value: 200},
                            {name: '安徽',value: Math.round(Math.random()*2000)},
                            {name: '山东',value: Math.round(Math.random()*2000)},
                            {name: '新疆',value: Math.round(Math.random()*2000)},
                            {name: '江苏',value: Math.round(Math.random()*2000)},
                            {name: '浙江',value: Math.round(Math.random()*2000)},
                            {name: '江西',value: Math.round(Math.random()*2000)},
                            {name: '湖北',value: Math.round(Math.random()*2000)},
                            {name: '广西',value: Math.round(Math.random()*2000)},
                            {name: '甘肃',value: Math.round(Math.random()*2000)},
                            {name: '山西',value: Math.round(Math.random()*2000)},
                            {name: '内蒙古',value: Math.round(Math.random()*2000)},
                            {name: '陕西',value: Math.round(Math.random()*2000)},
                            {name: '吉林',value: Math.round(Math.random()*2000)},
                            {name: '福建',value: Math.round(Math.random()*2000)},
                            {name: '贵州',value: Math.round(Math.random()*2000)},
                            {name: '广东',value: Math.round(Math.random()*2000)},
                            {name: '青海',value: Math.round(Math.random()*2000)},
                            {name: '西藏',value: Math.round(Math.random()*2000)},
                            {name: '四川',value: Math.round(Math.random()*2000)},
                            {name: '宁夏',value: Math.round(Math.random()*2000)},
                            {name: '海南',value: Math.round(Math.random()*2000)},
                            {name: '台湾',value: Math.round(Math.random()*2000)},
                            {name: '香港',value: Math.round(Math.random()*2000)},
                            {name: '澳门',value: Math.round(Math.random()*2000)}
                        ]
                    }
                ]
            };

            mcharts.setOption(option);
        }
    );
    $('.link-table tr').bind('mouseenter',function(){
        $(this).addClass('link-currentTr');
    });
    $('.link-table tr').bind('mouseleave',function(){
        $(this).removeClass('link-currentTr');
    });
});