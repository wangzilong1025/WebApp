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


    //业务相关右侧表格
    var data = [
        {
            "name":"报销流程",
            "way":"222",
            "finish":"3000",
            "over":'20',
            "goback":'85',
            "hours":"3天1小时35分"
        },
        {
            "name":"请假流程",
            "way":"123",
            "finish":"1000",
            "over":'10',
            "goback":'0',
            "hours":"3小时35分"
        },
        {
            "name":"出差申请流程",
            "way":"123",
            "finish":"88",
            "over":'888',
            "goback":'11',
            "hours":"3小时55分"
        },
        {
            "name":"报销流程",
            "way":"222",
            "finish":"3000",
            "over":'20',
            "goback":'85',
            "hours":"3天1小时35分"
        },
        {
            "name":"请假流程",
            "way":"123",
            "finish":"1000",
            "over":'10',
            "goback":'0',
            "hours":"3小时35分"
        },
        {
            "name":"出差申请流程",
            "way":"123",
            "finish":"88",
            "over":'888',
            "goback":'11',
            "hours":"3小时55分"
        },{
            "name":"报销流程",
            "way":"222",
            "finish":"3000",
            "over":'20',
            "goback":'85',
            "hours":"3天1小时35分"
        },
        {
            "name":"请假流程",
            "way":"123",
            "finish":"1000",
            "over":'10',
            "goback":'0',
            "hours":"3小时35分"
        },
        {
            "name":"出差申请流程",
            "way":"123",
            "finish":"88",
            "over":'888',
            "goback":'11',
            "hours":"3小时55分"
        },{
            "name":"报销流程",
            "way":"222",
            "finish":"3000",
            "over":'20',
            "goback":'85',
            "hours":"3天1小时35分"
        },
        {
            "name":"请假流程",
            "way":"123",
            "finish":"1000",
            "over":'10',
            "goback":'0',
            "hours":"3小时35分"
        },
        {
            "name":"出差申请流程",
            "way":"123",
            "finish":"88",
            "over":'888',
            "goback":'11',
            "hours":"3小时55分"
        },{
            "name":"报销流程",
            "way":"222",
            "finish":"3000",
            "over":'20',
            "goback":'85',
            "hours":"3天1小时35分"
        },
        {
            "name":"请假流程",
            "way":"123",
            "finish":"1000",
            "over":'10',
            "goback":'0',
            "hours":"3小时35分"
        },
        {
            "name":"出差申请流程",
            "way":"123",
            "finish":"88",
            "over":'888',
            "goback":'11',
            "hours":"3小时55分"
        },{
            "name":"报销流程",
            "way":"222",
            "finish":"3000",
            "over":'20',
            "goback":'85',
            "hours":"3天1小时35分"
        },
        {
            "name":"请假流程",
            "way":"123",
            "finish":"1000",
            "over":'10',
            "goback":'0',
            "hours":"3小时35分"
        },
        {
            "name":"....",
            "way":"....",
            "finish":"....",
            "over":'....',
            "goback":'....',
            "hours":"...."
        }
    ];
    function addTbody(ele){
        var html = '';
        $.each(data,function(i,v){
            html+= '<tr>';
            html+='<td><a href="javascript:void(0)">'+ v.name+'</a></td>';
            html+= '<td>'+ v.way+'</td>';
            html+='<td>'+ v.finish+'</td>';
            html+='<td>'+ v.over+'</td>';
            html+='<td>'+ v.goback+'</td>';
            html+='<td>'+ v.hours+'</td>';
            html+='</tr>';
        });
        ele.html(html);
    }
    addTbody($('#map-r-tbody'));
//            var current;
//
//            $('#map-r-tbody tr:odd').css("backgroundColor","#FFE4E1");
//
//            $('#map-r-tbody tr:even').css("backgroundColor","#EEE8AA");

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
                    text: '各省市科研成果统计图',
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
                //color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                //    offset: 0, color: 'red' // 0% 处的颜色
                //}, {
                //    offset: 1, color: 'blue' // 100% 处的颜色
                //}], false),
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
                            {name: '北京',value: Math.round(Math.random()*2000)},
                            {name: '天津',value: Math.round(Math.random()*2000)},
                            {name: '上海',value: Math.round(Math.random()*4000)},
                            {name: '重庆',value: Math.round(Math.random()*2000)},
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