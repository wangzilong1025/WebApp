/**
 * $Id: SHAztHandler.java,v 1.0 2016/9/8 10:33 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.http.handler.sh;

import com.sandi.web.utils.http.client.request.HttpRequest;
import com.sandi.web.utils.http.client.response.HttpResponse;
import com.sandi.web.utils.http.entity.CfgHttpClient;
import com.sandi.web.utils.http.entity.CfgWsClient;
import com.sandi.web.utils.http.handler.Handler;
import com.sandi.web.utils.response.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dizl
 * @version $Id: SHAztHandler.java,v 1.1 2016/9/8 10:33 dizl Exp $
 * Created on 2016/9/8 10:33
 */
public class SHAztHandler implements Handler {

    /**
     * 调用HTTP接口前，需要执行该方法
     *
     * @param cfgHttpClientEntity
     * @param busiParam
     */
    @Override
    public HttpRequest before(CfgHttpClient cfgHttpClientEntity, Map<String, Object> busiParam) throws Exception {
        return null;
    }

    /***
     * 调用HTTP接口后，需要执行该方法
     *
     * @param httpResponse
     */
    @Override
    public Response after(HttpResponse httpResponse) throws Exception {
        return null;
    }

    /**
     * 调用WS接口前，需要执行该方法
     *
     * @param cfgWsClient
     * @param busiParam
     */
    @Override
    public HttpRequest before(CfgWsClient cfgWsClient, Map<String, Object> busiParam) throws Exception {
        Map header = new HashMap();
        header.put("methodName", cfgWsClient.getMethodName());
        header.put("methodParameter", cfgWsClient.getMethodParameter());
        header.put("methodReturnType", cfgWsClient.getMethodReturnType());
        header.put("operationStyle", cfgWsClient.getOperationStyle());
        header.put("operationUse", cfgWsClient.getOperationUse());
        header.put("registerTypeMapping", cfgWsClient.getRegisterTypeMapping());

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(cfgWsClient.getUrlAddress());
        httpRequest.setTimeoutSeconds(cfgWsClient.getTimeoutSeconds());
        httpRequest.setHeader(header);
        httpRequest.setParameters(busiParam);
        httpRequest.setBusiCode(cfgWsClient.getCfgWsClientCode());
        httpRequest.setBody(null);
        return httpRequest;
    }

    /**
     * 调用WS接口后，需要执行该方法
     *
     * @param obj
     */
    @Override
    public Response after(Object obj) throws Exception {
        Response response = new Response();
        response.setData(obj);
        return response;
    }
}
