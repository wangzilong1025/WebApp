/**
 * $Id: CfgSrvController.java,v 1.0 2015/7/21 13:54 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.controller;

import com.sandi.web.app.dubbo.DubboManage;
import com.sandi.web.utils.api.tools.ITable2BeanUtilsFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.http.entity.CfgSrvBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: CfgSrvController.java,v 1.1 2015/7/21 13:54 zhangrp Exp $
 *          Created on 2015/7/21 13:54
 */
@Controller
@RequestMapping(value = "tools")
public class Table2BeanController {
    private static final Logger logger = LoggerFactory.getLogger(Table2BeanController.class);

    /*
    * 跳转到table2Bean页面
    * */
    @RequestMapping("/cb")
    public String jump2cb(HttpServletRequest request, HttpServletResponse response) {
        return "tools/ToolTable2Bean";
    }

    /*
    * table2Bean生成列预览
    * */
    @RequestMapping("/takeView")
    @ResponseBody
    public String takeView(String tableNames,String dataSource) throws Exception {
        CfgSrvBase cfgSrvBase = new CfgSrvBase();
        cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.tools.ITable2BeanUtilsFSV");
        ITable2BeanUtilsFSV commUtilsFSV = (ITable2BeanUtilsFSV) DubboManage.getServer(cfgSrvBase);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("tableNames", tableNames);
        map.put("dataSource",dataSource);
        String tableList = commUtilsFSV.getTableInfo(JsonUtil.object2Json(map));
        return tableList;
    }

    /*
    * table2Bean生成entity和dao 简单表
    * */
    @RequestMapping("/createBean")
    @ResponseBody
    public String createSingle(String tableList, String path,String dataSource) throws Exception {
        Map map = new HashMap();
        map.put("tableList", JsonUtil.json2List(tableList));
        map.put("path", path);
        map.put("dataSource",dataSource);
        CfgSrvBase cfgSrvBase = new CfgSrvBase();
        cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.tools.ITable2BeanUtilsFSV");
        ITable2BeanUtilsFSV commUtilsFSV = (ITable2BeanUtilsFSV) DubboManage.getServer(cfgSrvBase);

        String msg = commUtilsFSV.createBean(JsonUtil.object2Json(map));

        return msg;
    }
}
