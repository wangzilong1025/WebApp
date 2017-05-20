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
    <title>修改密码</title>
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/personal.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/package-style/style-front/css/stepstyle.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=path %>/package-style/style-front/js/jquery-1.7.2.min.js"></script>
    <script src="<%=path %>/package-style/style-front/AmazeUI-2.4.2/assets/js/amazeui.js"></script>

    <script type="text/javascript">
        $(function(){

            $checkplaceholder=function(){
                var input = document.createElement('input');
                return 'placeholder' in input;
            };
            if(!$checkplaceholder()){
                $("textarea[placeholder], input[placeholder]").each(function(index, element){
                    var content=false;
                    if($(this).val().length ===0 || $(this).val()==$(this).attr("placeholder")){content=true};
                    if(content){
                        $(element).val($(element).attr("placeholder"));
                        $(element).css("color","rgb(169,169,169)");
                        $(element).data("pintuerholder",$(element).css("color"));
                        $(element).focus(function(){$hideplaceholder($(this));});
                        $(element).blur(function(){$showplaceholder($(this));});
                    }
                })
            };

            $showplaceholder=function(element){
                if( ($(element).val().length ===0 || $(element).val()==$(element).attr("placeholder")) && $(element).attr("type")!="password"){
                    $(element).val($(element).attr("placeholder"));
                    $(element).data("pintuerholder",$(element).css("color"));
                    $(element).css("color","rgb(169,169,169)");
                }
            };

            var $hideplaceholder=function(element){
                if($(element).data("pintuerholder")){
                    $(element).val("");
                    $(element).css("color", $(element).data("pintuerholder"));
                    $(element).removeData("pintuerholder");
                }
            };

            $('textarea, input, select').blur(function(){
                var e=$(this);
                if(e.attr("data-validate")){
                    e.closest('.am-form-content').find(".input-help").remove();
                    var $checkdata=e.attr("data-validate").split(',');
                    var $checkvalue=e.val();
                    var $checkstate=true;
                    var $checktext="";
                    if(e.attr("placeholder")==$checkvalue){$checkvalue="";}
                    if($checkvalue!="" || e.attr("data-validate").indexOf("required")>=0){
                        for(var i=0;i<$checkdata.length;i++){
                            var $checktype=$checkdata[i].split(':');
                            if(! $pintuercheck(e,$checktype[0],$checkvalue)){
                                $checkstate=false;
                                $checktext=$checktext+"<li>"+$checktype[1]+"</li>";
                            }
                        }
                    };
                    if($checkstate){
                        e.closest('.am-form-group').removeClass("check-error");
                        e.parent().find(".input-help").remove();
                        e.closest('.am-form-group').addClass("check-success");
                    }else{
                        e.closest('.am-form-group').removeClass("check-success");
                        e.closest('.am-form-group').addClass("check-error");
                        e.closest('.am-form-content').append('<div class="input-help"><ul>'+$checktext+'</ul></div>');
                    }
                }
            });

            $pintuercheck=function(element,type,value){
                $pintu=value.replace(/(^\s*)|(\s*$)/g, "");
                switch(type){
                    case "required":return /[^(^\s*)|(\s*$)]/.test($pintu);break;
                    case "chinese":return /^[\u0391-\uFFE5]+$/.test($pintu);break;
                    case "number":return /^\d+$/.test($pintu);break;
                    case "integer":return /^[-\+]?\d+$/.test($pintu);break;
                    case "plusinteger":return /^[+]?\d+$/.test($pintu);break;
                    case "double":return /^[-\+]?\d+(\.\d+)?$/.test($pintu);break;
                    case "plusdouble":return /^[+]?\d+(\.\d+)?$/.test($pintu);break;
                    case "english":return /^[A-Za-z]+$/.test($pintu);break;
                    case "username":return /^[a-z]\w{3,}$/i.test($pintu);break;
                    case "mobile":return /^((\(\d{3}\))|(\d{3}\-))?13[0-9]\d{8}?$|15[89]\d{8}?$|170\d{8}?$|147\d{8}?$/.test($pintu);break;
                    case "phone":return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/.test($pintu);break;
                    case "tel":return /^((\(\d{3}\))|(\d{3}\-))?13[0-9]\d{8}?$|15[89]\d{8}?$|170\d{8}?$|147\d{8}?$/.test($pintu) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/.test($pintu);break;
                    case "email":return /^[^@]+@[^@]+\.[^@]+$/.test($pintu);break;
                    case "url":return /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/.test($pintu);break;
                    case "ip":return /^[\d\.]{7,15}$/.test($pintu);break;
                    case "qq":return /^[1-9]\d{4,10}$/.test($pintu);break;
                    case "currency":return /^\d+(\.\d+)?$/.test($pintu);break;
                    case "zip":return /^[1-9]\d{5}$/.test($pintu);break;
                    case "radio":
                        var radio=element.closest('form').find('input[name="'+element.attr("name")+'"]:checked').length;
                        return eval(radio==1);
                        break;
                    default:
                        var $test=type.split('#');
                        if($test.length>1){
                            switch($test[0]){
                                case "compare":
                                    return eval(Number($pintu)+$test[1]);
                                    break;
                                case "regexp":
                                    return new RegExp($test[1],"gi").test($pintu);
                                    break;
                                case "length":
                                    var $length;
                                    if(element.attr("type")=="checkbox"){
                                        $length=element.closest('form').find('input[name="'+element.attr("name")+'"]:checked').length;
                                    }else{
                                        $length=$pintu.replace(/[\u4e00-\u9fa5]/g,"***").length;
                                    }
                                    return eval($length+$test[1]);
                                    break;
                                case "ajax":
                                    var $getdata;
                                    var $url=$test[1]+$pintu;
                                    $.ajaxSetup({async:false});
                                    $.getJSON($url,function(data){
                                        //alert(data.getdata);
                                        $getdata=data.getdata;
                                    });
                                    if($getdata=="true"){return true;}
                                    break;
                                case "repeat":
                                    return $pintu==jQuery('input[name="'+$test[1]+'"]').eq(0).val();
                                    break;
                                default:return true;break;
                            }
                            break;
                        }else{
                            return true;
                        }
                }
            };

            $('form').submit(function(){
                $(this).find('input[data-validate],textarea[data-validate],select[data-validate]').trigger("blur");
                $(this).find('input[placeholder],textarea[placeholder]').each(function(){$hideplaceholder($(this));});
                var numError = $(this).find('.check-error').length;
                if(numError){
                    $(this).find('.check-error').first().find('input[data-validate],textarea[data-validate],select[data-validate]').first().focus().select();
                    return false;
                }
            });

            $('.form-reset').click(function(){
                $(this).closest('form').find(".input-help").remove();
                $(this).closest('form').find('.form-submit').removeAttr('disabled');
                $(this).closest('form').find('.am-form-group').removeClass("check-error");
                $(this).closest('form').find('.am-form-group').removeClass("check-success");
            });

        })
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

            <div class="am-cf am-padding">
                <div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">修改密码</strong> / <small>Password</small></div>
            </div>
            <hr/>
            <div class="m-progress" style="margin-top:10px;max-height: 30px;min-height: 30px;">
                <div class="m-progress-list" float="center" text-align="center" style="min-height: 30px;max-height: 30px;">
                    <p text-align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span text-align="center" id="message" style="color:red;"></span></p>
                </div>
            </div>
            <!--进度条-->
            <div class="m-progress">
                <div class="m-progress-list">
							<span class="step-1 step">
                                <em class="u-progress-stage-bg"></em>
                                <i class="u-stage-icon-inner">1<em class="bg"></em></i>
                                <p class="stage-name">重置密码</p>
                            </span>
                    <span class="step-2 step">
                                <em class="u-progress-stage-bg"></em>
                                <i class="u-stage-icon-inner">2<em class="bg"></em></i>
                                <p class="stage-name">完成</p>
                            </span>
                    <span class="u-progress-placeholder"></span>
                </div>
                <div class="u-progress-bar total-steps-2">
                    <div class="u-progress-bar-inner">

                    </div>
                </div>
            </div>
            <form id="updatepass" class="am-form am-form-horizontal" action="<%=path %>/userLogin/userUpdatePasswordByUserLoginId.do" method="post">
                <div class="am-form-group">
                    <label for="oldpassword" class="am-form-label">原密码</label>
                    <div class="am-form-content">
                        <input type="hidden" name="userId" id="userId" value="${user.userId}">
                        <input type="hidden" name="userName" id="userName" value="${user.userName}"><!--onblur="passCon();"-->
                        <input type="password" name="oldpassword" id="oldpassword" placeholder="请输入原登录密码"  data-validate="required:请输入原始密码">
                    </div>
                </div>
                <div class="am-form-group">
                    <label for="userNewPass" class="am-form-label">新密码</label>
                    <div class="am-form-content">
                        <input type="password" name="userNewPass" id="userNewPass" placeholder="请输入新密码" data-validate="required:请输入新密码,length#>=5:新密码不能小于5位">
                    </div>
                </div>
                <div class="am-form-group">
                    <label for="reuserNewPass" class="am-form-label">确认密码</label>
                    <div class="am-form-content">
                        <input type="password" name="reuserNewPass" id="reuserNewPass" placeholder="请再次输入新密码" data-validate="required:请再次输入新密码,repeat#userNewPass:两次输入的密码不一致">
                    </div>
                </div>

                    <div class="info-btn"><!--onclick="submitpass()"-->
                        <input class="am-btn am-btn-danger"  type="submit"  value="保存密码" />
                    </div>
            </form>
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