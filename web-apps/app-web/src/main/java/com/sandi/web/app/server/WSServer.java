/**
 * $Id: WSServer.java,v 1.0 2016/9/6 15:13 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.server;

import com.sandi.web.utils.common.CfgHttpClientUtil;
import com.sandi.web.utils.http.client.WsClientUtil;
import com.sandi.web.utils.http.client.request.HttpRequest;
import com.sandi.web.utils.http.entity.CfgWsClient;
import com.sandi.web.utils.http.entity.SerParameter;
import com.sandi.web.utils.http.handler.Handler;
import com.sandi.web.utils.response.Response;
import org.apache.log4j.Logger;

/**
 * @author dizl
 * @version $Id: WSServer.java,v 1.1 2016/9/6 15:13 dizl Exp $
 * Created on 2016/9/6 15:13
 */
public class WSServer {
    private static final Logger logger = Logger.getLogger(WSServer.class);

    public void callServer(SerParameter serParameter) {
        if (serParameter.isEnd() == true) {
            return;
        }
        try {
            if (serParameter.getBusiCode() != null) {
                CfgWsClient cfgWsClient = CfgHttpClientUtil.getCfgWsClientByBusiCode(serParameter.getBusiCode());
                if (cfgWsClient.getHandlerClass() == null) {
                    throw new Exception("HandlerClass 不能为空！");
                }
                Handler handler = (Handler) Class.forName(cfgWsClient.getHandlerClass()).newInstance();
                HttpRequest httpRequest = handler.before(cfgWsClient, serParameter.getRequest().getRequestInfo().getBusiParams());
                Object obj = WsClientUtil.invoke(httpRequest);
                Response response = handler.after(obj);

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
