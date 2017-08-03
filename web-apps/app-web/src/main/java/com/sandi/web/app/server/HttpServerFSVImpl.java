/**
 * $Id: HttpServerFSVImpl.java,v 1.0 2016/8/26 10:05 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.server;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.sandi.web.app.dubbo.DubboManage;
import com.sandi.web.app.interceptor.LogInterceptor;
import com.sandi.web.app.util.WebConstants;
import com.sandi.web.utils.api.osdi.IHttpServerFSV;
import com.sandi.web.utils.api.syslog.ISysBusiLogFSV;
import com.sandi.web.utils.common.CfgHttpClientUtil;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.http.entity.CfgSrvBase;
import com.sandi.web.utils.http.entity.SerParameter;
import com.sandi.web.utils.request.PubInfo;
import com.sandi.web.utils.request.Request;
import com.sandi.web.utils.request.RequestInfo;
import com.sandi.web.utils.response.Response;
import com.sandi.web.utils.sec.SessionManager;
import org.apache.log4j.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: HttpServerFSVImpl.java,v 1.1 2016/8/26 10:05 zhangrp Exp $
 *          Created on 2016/8/26 10:05
 */
@Service
public class HttpServerFSVImpl implements IHttpServerFSV {
    private static final Logger logger = Logger.getLogger(HttpServerFSVImpl.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Response callServer(String busiCode, LinkedHashMap<String, Object> busiParam) throws Exception{
        String sessionId =  RpcContext.getContext().getAttachment(SessionManager.SESSION_KEY);
        SerParameter serParameter = new SerParameter();
        serParameter.setRequest(new Request());
        serParameter.getRequest().setPubInfo(new PubInfo());
        serParameter.getRequest().getPubInfo().setSessionId(sessionId);
        serParameter.getRequest().setRequestInfo(new RequestInfo());
        serParameter.setBusiCode(busiCode);

        serParameter.getRequest().getRequestInfo().setBusiCode(busiCode);
        serParameter.getRequest().getRequestInfo().setBusiParams(busiParam);

        if(StringUtils.isNotEmpty(busiCode)) {
            if (busiCode != null && CfgHttpClientUtil.getCfgHttpClientByBusiCode(busiCode) != null) {
                serParameter.setRequestType(WebConstants.RequestType.HTTP);
                HttpServer httpServer = new HttpServer();
                httpServer.callServer(serParameter);
            } else if (CfgHttpClientUtil.getCfgWsClientByBusiCode(busiCode) != null) {
                serParameter.setRequestType(WebConstants.RequestType.WS);
                WSServer wsServer = new WSServer();
                wsServer.callServer(serParameter);
            } else {
                serParameter.setRequestType(WebConstants.RequestType.FSV);
                DubboServer dubboServer = new DubboServer();
                dubboServer.callServer(serParameter);
            }
        }else{
            Response retResponse = new Response();
            retResponse.setCode(Response.ERROR);
            retResponse.setMessage("busiCode参数为空");
            serParameter.setResponse(retResponse);
        }
        serParameter.setEndTime(new Date());
        try {
            LogInterceptor.put("busiCode", serParameter.getBusiCode());
            LogInterceptor.put("result", serParameter.getResponse().getErrorInfo().getCode());
            LogInterceptor.put("message", serParameter.getResponse().getErrorInfo().getMessage());
            //记录操作日志
            saveSrvLog(serParameter);
        } catch (Exception e) {

        }
      
        return serParameter.getResponse();
    }

    /**
     *记录系统操作日志
     * */
    public void saveSrvLog(SerParameter serParameter){
        try {
            SessionManager.checkLogin(serParameter.getRequest().getPubInfo().getSessionId());
            CfgSrvBase cfgSrvBase = new CfgSrvBase();
            cfgSrvBase.setSrvId("ISYSBUSILOGFSV_SRVLOG");
            cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.syslog.ISysBusiLogFSV");
            ISysBusiLogFSV sysBusiLogFSV = (ISysBusiLogFSV) DubboManage.getServer(cfgSrvBase);
            Map params = new HashMap();

            params.put("LOG_TYPE",serParameter.getRequestType());
            params.put("BUSINESS_ID",serParameter.getBusiCode());
            params.put("START_DATE", dateFormat.format(serParameter.getStartTime()));
            params.put("END_DATE", dateFormat.format(serParameter.getEndTime()));
            params.put("IS_SUCCESS", serParameter.getResponse().getErrorInfo().getCode());
            params.put("CONTENT",serParameter.getResponse());

            sysBusiLogFSV.srvLog(JsonUtil.object2Json(params));
        }catch (Exception e){
            logger.error("服务调度日志保存失败",e);
        }
    }
}
