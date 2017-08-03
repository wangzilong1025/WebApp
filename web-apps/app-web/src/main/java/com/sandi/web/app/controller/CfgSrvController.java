/**
 * $Id: CfgSrvController.java,v 1.0 2015/7/21 13:54 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.controller;

import com.sandi.web.app.dubbo.DubboManage;
import com.sandi.web.app.util.ClassUtil;
import com.sandi.web.app.util.ResourceCollection;
import com.sandi.web.utils.api.osdi.ICfgSrvFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.http.entity.CfgSrvBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: CfgSrvController.java,v 1.1 2015/7/21 13:54 zhangrp Exp $
 *          Created on 2015/7/21 13:54
 */
@Controller
@RequestMapping(value = "srv")
public class CfgSrvController {
    private static final Logger logger = LoggerFactory.getLogger(CfgSrvController.class);

    @RequestMapping("/config")
    public String request(HttpServletRequest request, HttpServletResponse response) {
        return "srv/SrvConfig";
    }


    @RequestMapping("/search")
    @ResponseBody
    public Object search(@RequestParam(value = "value", required = true) String value) {
        String[] ret = null;
        try {
            ret = ResourceCollection.search(value);
        } catch (Exception e) {
            logger.error("", e);
        }
        return ret;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Map<String, String> save(@RequestParam(value = "_class", required = true) String _class,
                                    @RequestParam(value = "method", required = true) String method,
                                    @RequestParam(value = "srvId", required = true) String srvId,
                                    @RequestParam(value = "srvName", required = true) String srvName,
                                    @RequestParam(value = "remark", required = true) String remark,
                                    @RequestParam(value = "parIn", required = true) String parIn,
                                    @RequestParam(value = "parOut", required = true) String parOut) {
        Map<String, String> re = new HashMap<String, String>();
        try {

            if (_class.equals("") || method.equals("") || srvId.equals("") || srvName.equals("")) {
                throw new Exception("存在空参数！");
            }
            if (method.indexOf("(") >= 0) {
                method = method.substring(0, method.indexOf("(")).trim();
            }
            CfgSrvBase cfgSrvBase = new CfgSrvBase();
            cfgSrvBase.setSrvId("CfgOsdiSrv");
            cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.osdi.ICfgSrvFSV");
            ICfgSrvFSV cfgSrvFSV = (ICfgSrvFSV) DubboManage.getServer(cfgSrvBase);
            CfgSrvBase cfgSrvBase_ = new CfgSrvBase();
            cfgSrvBase_.setSrvId(srvId);
            cfgSrvBase_.setSrvName(srvName);
            cfgSrvBase_.setSrvPackage(_class);
            cfgSrvBase_.setSrvMethod(method);
            cfgSrvBase_.setState(1);
            cfgSrvBase_.setRemark(remark);
            cfgSrvBase_.setParIn(parIn);
            cfgSrvBase_.setParOut(parOut);
            String json = cfgSrvFSV.save(cfgSrvBase_);
            String code = "0001";
            if (json.equals("SUCCESS")) {
                code = "0000";
            }
            re.put("code", code);
        } catch (Exception e) {
            logger.error("", e);
            re.put("code", "0001");
            re.put("message", e.getMessage());
        }
        return re;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(@RequestParam(value = "srvId", required = true) String srvId) {
        Map<String, String> re = new HashMap<String, String>();
        try {
            if (srvId.equals("")) {
                throw new Exception("无需要删除的服务！");
            }
            CfgSrvBase cfgSrvBase = new CfgSrvBase();
            cfgSrvBase.setSrvId("CfgOsdiSrv");
            cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.osdi.ICfgSrvFSV");
            ICfgSrvFSV cfgSrvFSV = (ICfgSrvFSV) DubboManage.getServer(cfgSrvBase);
            String json = cfgSrvFSV.delete(srvId);
            Map<String, Object> temp = JsonUtil.json2Map(json);
            String code = "0001";
            if (temp.get("errorInfo") != null && ((Map<String, Object>) temp.get("errorInfo")).get("code") != null) {
                code = ((Map<String, Object>) temp.get("errorInfo")).get("code").toString();
            }
            re.put("code", code);
        } catch (Exception e) {
            logger.error("", e);
            re.put("code", "0001");
            re.put("message", e.getMessage());
        }
        return re;
    }

    @RequestMapping("/srvmethodlist")
    @ResponseBody
    public Object srvmethodlist(@RequestParam(value = "className", required = true) String className) {
        List<Map<String, Object>> relist = new ArrayList<Map<String, Object>>();
        try {
            Class<?> c = Class.forName(className);
            List<Method> ms = ClassUtil.getDeclareMethod(c);
            if (null != ms) {
                for (int i = 0; i < ms.size(); i++) {
                    Map<String, Object> o = new HashMap<String, Object>();
                    o.put("clazz", c.getName());
                    o.put("code", "");
                    o.put("method", ms.get(i).getName());
                    o.put("decliare", ClassUtil.createDecliare(c.getName(), ms.get(i).getName(), ClassUtil.getMethodDesc(ms.get(i))));
                    o.put("parameterClazzes", ClassUtil.class2String(ms.get(i).getParameterTypes()));
                    o.put("modify", false);
                    relist.add(o);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return relist;
    }


    @RequestMapping("/queryAll")
    @ResponseBody
    public Map<String, Object> queryAll() {
        Map<String, Object> re = new HashMap<String, Object>();
        try {
            CfgSrvBase cfgSrvBase = new CfgSrvBase();
            cfgSrvBase.setSrvId("CfgOsdiSrv");
            cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.osdi.ICfgSrvFSV");
            ICfgSrvFSV cfgSrvFSV = (ICfgSrvFSV) DubboManage.getServer(cfgSrvBase);
            List<CfgSrvBase> cfgSrvBaseList = cfgSrvFSV.getAllServer();
            re.put("code", "0000");
            re.put("data", cfgSrvBaseList);
        } catch (Exception e) {
            logger.error("", e);
            re.put("code", "0001");
            re.put("message", e.getMessage());
        }
        return re;
    }


    @RequestMapping("/update")
    @ResponseBody
    public Map<String, String> update(@RequestParam(value = "srvId", required = true) String srvId,
                                      @RequestParam(value = "srvName", required = true) String srvName,
                                      @RequestParam(value = "remark", required = true) String remark,
                                      @RequestParam(value = "parIn", required = true) String parIn,
                                      @RequestParam(value = "parOut", required = true) String parOut) {
        Map<String, String> re = new HashMap<String, String>();
        try {

            if (srvName.equals("")) {
                throw new Exception("存在空参数！");
            }

            CfgSrvBase cfgSrvBase = new CfgSrvBase();
            cfgSrvBase.setSrvId("CfgOsdiSrv");
            cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.osdi.ICfgSrvFSV");
            ICfgSrvFSV cfgSrvFSV = (ICfgSrvFSV) DubboManage.getServer(cfgSrvBase);

            CfgSrvBase cfgSrvBase_ = new CfgSrvBase();
            cfgSrvBase_.setSrvId(srvId);
            cfgSrvBase_.setSrvName(srvName);
            cfgSrvBase_.setRemark(remark);
            cfgSrvBase_.setParIn(parIn);
            cfgSrvBase_.setParOut(parOut);

            String json = cfgSrvFSV.update(cfgSrvBase_);

            String code = "0001";
            if (json.equals("SUCCESS")) {
                code = "0000";
            }
            re.put("code", code);
        } catch (Exception e) {
            logger.error("", e);
            re.put("code", "0001");
            re.put("message", e.getMessage());
        }
        return re;
    }
}
