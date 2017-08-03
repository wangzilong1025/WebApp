/**
 * Created by dizl on 2016/5/15.
 */
var PROJECT_URL="";
var SERVER_URL = "/server";
var MODULE_NAME = "core";
var isIE8 = false;
var isIE9 = false;
var IS_CIPHER = false;//数据传输是否加密

(function(){
    function init() {
        //调用服务获取当前登录用户的样式
        var theme = "metro";
        document.write("<style>" +
            "@-webkit-keyframes window-load-bar {from {width: 0%;background-color:#84a71a;} to {width: 100%;background-color:#dbf784;}}" +
            "@-moz-keyframes window-load-bar {from {width: 0%;background-color:#84a71a;} to {width: 100%;background-color:#dbf784;}}" +
            "@keyframes window-load-bar {from {width: 0%;background-color:#84a71a;} to {width: 100%;background-color:#dbf784;}}" +
            "</style>");
        document.write("<div id='__window_load_tip__' style='width:100%;margin-bottom:0;height:4px;position:absolute;animation:window-load-bar 2s infinite ease-out;-webkit-animation:window-load-bar 2s infinite ease-out;'></div>");
        document.write("<link rel='stylesheet' href='" + PROJECT_URL + "/resources/frame/planeui/css/planeui.css'>");
        document.write("<link rel='stylesheet' href='" + PROJECT_URL + "/resources/frame/kendo/styles/kendo.common-material.min.css'>");
        document.write("<link rel='stylesheet' href='" + PROJECT_URL + "/resources/frame/kendo/styles/kendo." + theme + ".min.css'>");
        document.write("<link rel='stylesheet' href='" + PROJECT_URL + "/resources/frame/planeui/plugins/fullpager/fullpager.css'>");
        document.write("<link rel='stylesheet' href='" + PROJECT_URL + "/resources/frame/planeui/plugins/scrollbar/scrollbar.css'>");
        document.write("<link rel='stylesheet' href='" + PROJECT_URL + "/resources/theme/"+theme+"/css/common.css'>");
        document.write("<script type='text/javascript' src='" + PROJECT_URL + "/js/common/busicode.js'></script>");
        document.write("<script type='text/javascript' src='" + PROJECT_URL + "/resources/frame/jquery/jquery.min.js'></script>");
        document.write("<script type='text/javascript' src='" + PROJECT_URL + "/resources/frame/planeui/planeui.js'></script>");
        document.write("<script type='text/javascript' src='" + PROJECT_URL + "/resources/frame/planeui/plugins/fullpager/fullpager.js'></script>");
        document.write("<script type='text/javascript' src='" + PROJECT_URL + "/resources/frame/planeui/plugins/scrollbar/scrollbar.js'></script>");
        document.write("<script type='text/javascript' src='" + PROJECT_URL + "/resources/frame/kendo/kendo.all.min.js'></script>");
        document.write("<script type='text/javascript' src='" + PROJECT_URL + "/resources/frame/kendo/cultures/kendo.culture.zh-CN.min.js'></script>");
        document.write("<script type='text/javascript' src='" + PROJECT_URL + "/resources/frame/kendo/messages/kendo.messages.zh-CN.min.js'></script>");
        document.write("<script type='text/javascript' src='" + PROJECT_URL + "/resources/frame/aiui/aiui.all.js'></script>");
        document.write("<script type='text/javascript' src=" + PROJECT_URL + "'/resources/common/json2.js'></script>");
        document.write("<script type='text/javascript' src=" + PROJECT_URL + "'/resources/common/tripledes.js'></script>");
        document.write("<script type='text/javascript' src=" + PROJECT_URL + "'/resources/common/mode-ecb.js'></script>");
        document.write("<script type='text/javascript' src=" + PROJECT_URL + "'/resources/common/jsencrypt.js'></script>");
        document.write("<script type='text/javascript' src=" + PROJECT_URL + "'/resources/common/common.js'></script>");
        document.write("<script type='text/javascript' src=" + PROJECT_URL + "'/resources/common/common.class.js'></script>");
        document.write("<script type='text/javascript' src=" + PROJECT_URL + "'/js/common/constants.js'></script>");
        document.write("<script type='text/javascript' src=" + PROJECT_URL + "'/resources/common/common.pubsub.js'></script>");

        window.onload = function(){
            window.setTimeout(function(){
                $("#__window_load_tip__").remove();
            }, 500);
            //国际化、zh-CN
            kendo.culture("zh-CN");
        };
    }
    init();
})();

function openLoginPage(){
    window.location.href = PROJECT_URL+"/login.html";
}