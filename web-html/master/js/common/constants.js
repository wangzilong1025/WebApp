/**
 * @author zhangruiping
 * Created on 17/3/8 下午12:22
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */


//动态表单涉及常量
var DYNC = {
    TOPIC_PAGE_ONLOAD: "TOPIC_PAGE_ONLOAD",
    TOPIC_SO_NEXTPAGE: "TOPIC_SO_NEXTPAGE",
    TOPIC_SO_SAVEDATA: "TOPIC_SO_SAVEDATA",
    TOPIC_SO_SAVEDATA_NO_CHECK: "TOPIC_SO_SAVEDATA_NO_CHECK",
    TOPIC_SO_VALIDATE: "TOPIC_SO_VALIDATE",
    WIDGET_TYPE: {
        "numerictextbox": "kendoNumericTextBox",
        "dropdownlist": "kendoDropDownList",
        "combobox": "kendoComboBox",
        "grid": "kendoGrid",
        "multiselect": "kendoMultiSelect",
        "datepicker": "kendoDatePicker",
        "datetimepicker": "kendoDateTimePicker",
        "aiaddress": "kendoAIAddress"
    }
};



var userInfoNameMap = {
    name : "姓　　名",
    operatorId : "编　　号",
    code : "账　　号",
    email : "邮　　箱",
    billId : "手机号码",
    officeTel : "办公电话",
    staffType : "员工类型",
    staffLevel : "员工级别",
    organizeName : "所属组织"
    //sessionId : "用户登录sessionId",
    //staffId : "员工编号",
    //password : "密码",
    //recentPassTimes : "最近使用密码登录次数",
    //lockFlag : "账号锁定状态",
    //isAutoLockscreen : "是否自动锁屏",
    //lockscreenInteval : "锁屏时长",
    //isMultiLogin : "是否可重复登陆",
    //homeTel : "住宅电话",
    //organizeId : "组织编号",
    //provinceId : "省份编码",
    //proviceName : "省份名称",
    //cityId : "地市编码",
    //cityName : "地市名称",
    //countyId : "区县编码",
    //countyName : "区县名称",
    //moduleId : "渠道编号1-PC2-APP",
};

var privGroup = {
    quicksearch: {
        groupId: 16,
        privId: {
            menu: 1601,
            group: 1602
        }
    },
    button: {
        groupId: 1,
        privId: {
            testBtn: 1001,
            testBtn2: 100000
        }
    }
};
