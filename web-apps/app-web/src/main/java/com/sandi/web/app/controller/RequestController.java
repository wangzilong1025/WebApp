/**
 * $Id: RequestController.java,v 1.0 2015/7/16 11:23 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.controller;

import com.sandi.web.app.dubbo.DubboManage;
import com.sandi.web.app.interceptor.LogInterceptor;
import com.sandi.web.app.server.*;
import com.sandi.web.app.util.CfgBusiBaseUtil;
import com.sandi.web.app.util.WebConstants;
import com.sandi.web.utils.api.syslog.ISysBusiLogFSV;
import com.sandi.web.utils.bp.FileOperateFactory;
import com.sandi.web.utils.bp.entity.CfgWriter;
import com.sandi.web.utils.common.CfgHttpClientUtil;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.http.entity.*;
import com.sandi.web.utils.response.Response;
import com.sandi.web.utils.sec.SessionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhangrp
 * @version $Id: RequestController.java,v 1.1 2015/7/16 11:23 zhangrp Exp $
 *          Created on 2015/7/16 11:23
 */
@Controller
@RequestMapping(value = "/")
public class RequestController {
    private static final Logger logger = Logger.getLogger(RequestController.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("server")
    @ResponseBody
    public String requestin(HttpServletRequest request, HttpServletResponse response) {
        return this.request(request, response, "0");
    }

    @RequestMapping("serverout")
    @ResponseBody
    public String requestout(HttpServletRequest request, HttpServletResponse response) {
        return this.request(request, response, "1");
    }

    @RequestMapping("export")
    public void export(@RequestParam("fileType") Integer fileType,
                       @RequestParam("fileName") String fileName,
                       HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws Exception {
        String json = this.request(httpServletRequest, httpServletResponse, "0");
        Response response = JsonUtil.json2Object(json, Response.class);

        List<List<List<String>>> data = null;
        List<List<List<String>>> headers = null;
        try {
            data = (List<List<List<String>>>) ((Map<String, Object>) response.getData()).get("data");
            headers = (List<List<List<String>>>) ((Map<String, Object>) response.getData()).get("headers");
        } catch (Exception e) {
            logger.error("获取数据失败，请校验数据格式是否正确", e);
        }

        try {
            fileName = URLDecoder.decode(URLDecoder.decode(fileName, "UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.addHeader("expires", "0");
        httpServletResponse.reset();
        httpServletResponse.resetBuffer();
        httpServletResponse.setContentType("application/octet-stream");
        String agent = httpServletRequest.getHeader("USER-AGENT");
        try {
            if (agent != null && agent.indexOf("Firefox") != -1) {
                httpServletResponse.addHeader("Content-Disposition", "attachment; filename*=\"utf8''" + fileName + "\"");
            } else {
                httpServletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            OutputStream outputStream = httpServletResponse.getOutputStream();
            CfgWriter cfgWriter = new CfgWriter(fileType);
            FileOperateFactory.getFileOperator(fileType).doWrite(headers, data, outputStream, cfgWriter);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private String request(HttpServletRequest request, HttpServletResponse response, String visitType) {

        SerParameter serParameter = new SerParameter();
        serParameter.setHttpServletRequest(request);
        serParameter.setHttpServletResponse(response);

        ParaCheckServer paraCheckServer = new ParaCheckServer();
        paraCheckServer.callServer(serParameter);

        boolean isBusi = false;
        String busiCode = serParameter.getBusiCode();
        CfgBusiBase busiBase = null;
        if (busiCode != null) {
            busiBase = CfgBusiBaseUtil.getCfgBusiBase(busiCode);
            if (busiBase != null && busiBase.getBusiSrvRels() != null && busiBase.getBusiSrvRels().size() > 0) {
                isBusi = true;
            }
        }
        if (isBusi) {
            return this.requestBusi(serParameter, busiBase, visitType);
        } else {
            return this.requestSrv(serParameter, 0, visitType);
        }
    }

    private String requestBusi(SerParameter serParameter, CfgBusiBase busiBase, String visitType){
        String json = null;
        List<Map> rtnList = new ArrayList<>();
        if (busiBase != null && busiBase.getBusiSrvRels() != null && busiBase.getBusiSrvRels().size() > 0) {
            int count = 0;
            int busiType = busiBase.getBusiType();
            for (CfgBusiSrvRel busiSrv : busiBase.getBusiSrvRels()) {
                String srvId = busiSrv.getSrvId();
                serParameter.setBusiCode(srvId);
                //处理前置事件
                String eventKey = busiBase.getBusiId() + ":" + srvId + ":" + WebConstants.EventType.BEFORE_EVENT;
                handleEvents(serParameter, busiBase, eventKey);

                //服务调用
                if (busiType == 1) {
                    json = this.requestSrv(serParameter, busiSrv.getSrvType(), visitType);
                } else if (busiType == 2){
                    String currentJson = this.requestSrv(serParameter, busiSrv.getSrvType(), visitType);
                    try {
                        Response response = JsonUtil.json2Object(currentJson, Response.class);
                        if (response.getData() instanceof List) {
                            rtnList.addAll((List)response.getData());
                        } else if (response.getData() instanceof Map) {
                            rtnList.addAll((List)((Map) response.getData()).get("data"));
                            count += (Integer)((Map) response.getData()).get("count");
                        }
                    } catch (Exception e){
                        logger.error("组合数据失败，请校验数据格式是否正确", e);
                    }
                }

                //处理后置事件
                eventKey = busiBase.getBusiId() + ":" + srvId + ":" + WebConstants.EventType.RETURN_EVENT;
                handleEvents(serParameter, busiBase, eventKey);
            }
            if (busiType == 2) {
                Response retResponse = new Response();
                retResponse.setCode(Response.SUCCESS);
                retResponse.setMessage("组合业务调用成功");
                if (count > 0) {
                    Map responseMap = new HashMap();
                    responseMap.put("data", rtnList);
                    responseMap.put("count", count);
                    retResponse.setData(responseMap);
                } else {
                    retResponse.setData(rtnList);
                }
                json = retResponse.toString();
            }
        }
        return json;
    }

    private void handleEvents(SerParameter serParameter, CfgBusiBase busiBase, String eventKey) {
        if (busiBase != null && busiBase.getEventMap() != null && busiBase.getEventMap().containsKey(eventKey)) {
            List<CfgBusiEventRel> events = busiBase.getEventMap().get(eventKey);
            if (events != null && events.size() > 0) {
                for (CfgBusiEventRel event : events) {
                    int eventKind = event.getEventKind();
                    if (eventKind == WebConstants.EventKind.JAVA_CLASS) {
                        this.eventClass(serParameter, busiBase, event);
                    } else if (eventKind == WebConstants.EventKind.JS_FILE) {
                        this.eventJsFile(event);
                    } else if (eventKind == WebConstants.EventKind.JS_CONTENT) {
                        this.eventJsContent(event);
                    }
                }
            }
        }
    }

    private void eventClass(SerParameter serParameter, CfgBusiBase busiBase, CfgBusiEventRel event) {
        try {
            String eventClazz = event.getEventClazz();
            Class<?> aClass = Class.forName(eventClazz);
            Method eventMethod = aClass.getMethod("doEvent", SerParameter.class, CfgBusiBase.class);
            eventMethod.invoke(aClass.newInstance(), serParameter, busiBase);
        } catch (Exception e) {
            logger.error("类文件配置出错！");
        }
    }

    private void eventJsFile(CfgBusiEventRel event) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");

            String classpath = this.getClass().getResource("/").getPath();
            String root = classpath.substring(0, classpath.indexOf("esop-app"));
            String jsFileName = event.getEventFile();
            String jsPath = root + jsFileName;

            FileReader reader = new FileReader(jsPath);
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                String sth = (String) invoke.invokeFunction(event.getEventFunc());
                System.out.println("something is =" + sth);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error("找不到指定的文件！");
        } catch (ScriptException e) {
            logger.error("解析js代码出错！");
        } catch (NoSuchMethodException e) {
            logger.error("调用js方法出错！");
        } catch (IOException e) {
            logger.error("读取js文件出错！");
        }
    }

    private void eventJsContent(CfgBusiEventRel event) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");
            engine.eval(event.getEventContent());
        } catch (ScriptException e) {
            logger.error("解析js代码出错！");
        }
    }

    private String requestSrv(SerParameter serParameter, Integer srvType, String visitType) {

        SecCheckServer secCheckServer = new SecCheckServer();
        secCheckServer.callServer(serParameter);

        if (serParameter.getBusiCode() != null) {
            if (srvType == 2 || CfgHttpClientUtil.getCfgHttpClientByBusiCode(serParameter.getBusiCode()) != null) {
                serParameter.setRequestType(WebConstants.RequestType.HTTP);
                HttpServer httpServer = new HttpServer();
                httpServer.callServer(serParameter);
            } else if (srvType == 3 || CfgHttpClientUtil.getCfgWsClientByBusiCode(serParameter.getBusiCode()) != null) {
                serParameter.setRequestType(WebConstants.RequestType.WS);
                WSServer wsServer = new WSServer();
                wsServer.callServer(serParameter);
            } else {
                serParameter.setRequestType(WebConstants.RequestType.FSV);
                DubboServer dubboServer = new DubboServer();
                dubboServer.callServer(serParameter);
            }
        } else {
            Response retResponse = new Response();
            retResponse.setCode(Response.ERROR);
            retResponse.setMessage("busiCode参数为空");
            serParameter.setResponse(retResponse);
        }
        serParameter.setEndTime(new Date());

        String json = serParameter.getResponse().toString();
        if (serParameter.getHttpServletRequest().getParameter("callback") != null) {
            json = serParameter.getHttpServletRequest().getParameter("callback") + "(" + json + ")";
        }

        try {
            LogInterceptor.put("busiCode", serParameter.getBusiCode());
            LogInterceptor.put("result", serParameter.getResponse().getErrorInfo().getCode());
            LogInterceptor.put("message", serParameter.getResponse().getErrorInfo().getMessage());
            //记录操作日志
            saveSrvLog(serParameter);
        } catch (Exception e) {

        }
        return json;
    }

    /**
     * 记录系统操作日志
     */
    public void saveSrvLog(SerParameter serParameter) {
        try {

            SessionManager.checkLogin(serParameter.getHttpServletRequest().getRequestedSessionId());

            CfgSrvBase cfgSrvBase = new CfgSrvBase();
            cfgSrvBase.setSrvId("ISYSBUSILOGFSV_SRVLOG");
            cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.syslog.ISysBusiLogFSV");
            ISysBusiLogFSV sysBusiLogFSV = (ISysBusiLogFSV) DubboManage.getServer(cfgSrvBase);
            Map params = new HashMap();

            params.put("LOG_TYPE", serParameter.getRequestType());
            params.put("BUSINESS_ID", serParameter.getBusiCode());
            params.put("START_DATE", dateFormat.format(serParameter.getStartTime()));
            params.put("END_DATE", dateFormat.format(serParameter.getEndTime()));
            params.put("IS_SUCCESS", serParameter.getResponse().getErrorInfo().getCode());
            params.put("CONTENT", serParameter.getResponse());

            sysBusiLogFSV.srvLog(JsonUtil.object2Json(params));
        } catch (Exception e) {
            logger.error("服务调度日志保存失败", e);
        }
    }
}
