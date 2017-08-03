/**
 * $Id: HttpServer.java,v 1.0 2016/8/26 8:15 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.server;

import com.sandi.web.utils.common.CfgHttpClientUtil;
import com.sandi.web.utils.http.client.HttpUtil;
import com.sandi.web.utils.http.client.request.HttpRequest;
import com.sandi.web.utils.http.client.response.HttpResponse;
import com.sandi.web.utils.http.entity.CfgHttpClient;
import com.sandi.web.utils.http.entity.SerParameter;
import com.sandi.web.utils.http.handler.Handler;
import com.sandi.web.utils.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangrp
 * @version $Id: HttpServer.java,v 1.1 2016/8/26 8:15 zhangrp Exp $
 *          Created on 2016/8/26 8:15
 */
public class HttpServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public void callServer(SerParameter serParameter) {
        if (serParameter.isEnd() == true) {
            return;
        }
        try {
            if (serParameter.getBusiCode() != null) {
                CfgHttpClient cfgHttpClientEntity = CfgHttpClientUtil.getCfgHttpClientByBusiCode(serParameter.getBusiCode());
                if (cfgHttpClientEntity.getHandlerClass() == null) {
                    throw new Exception("HandlerClass 不能为空！");
                }
                Handler handler = (Handler) Class.forName(cfgHttpClientEntity.getHandlerClass()).newInstance();
                HttpRequest httpRequest = handler.before(cfgHttpClientEntity, serParameter.getRequest().getRequestInfo().getBusiParams());
                HttpResponse httpResponse = HttpUtil.doPost(httpRequest);
                Response response = handler.after(httpResponse);
                serParameter.setResponse(response);
                serParameter.getResponse().getErrorInfo().setCode("0000");
                serParameter.getResponse().getErrorInfo().setMessage("成功");
            } else {
                throw new Exception("参数错误");
            }
        } catch (Exception e) {
            logger.error("调用接口失败", e);
            serParameter.setEnd(true);
            serParameter.getResponse().getErrorInfo().setCode(Response.SYS_ERROR);
            String errorMsg = e.getMessage() == null ? e.toString() : e.getMessage();//e.getCause().toString();
            String msg = errorMsg.length() < 100 ? errorMsg : errorMsg.substring(0, 100);
            serParameter.getResponse().getErrorInfo().setMessage("调用服务异常" + msg);
        }
    }
}
