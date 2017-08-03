/**
 * $Id: SHCrmHadndler.java,v 1.0 2016/8/26 8:23 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.http.handler.sh;

import com.alibaba.dubbo.rpc.RpcContext;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.http.client.request.HttpRequest;
import com.sandi.web.utils.http.client.response.HttpResponse;
import com.sandi.web.utils.http.client.response.HttpResponseByte;
import com.sandi.web.utils.http.client.response.HttpResponseString;
import com.sandi.web.utils.http.entity.CfgHttpClient;
import com.sandi.web.utils.http.entity.CfgWsClient;
import com.sandi.web.utils.http.handler.Handler;
import com.sandi.web.utils.response.Response;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: SHCrmHadndler.java,v 1.1 2016/8/26 8:23 zhangrp Exp $
 *          Created on 2016/8/26 8:23
 */
public class SHCrmHandler implements Handler {

    protected static final Logger logger = LoggerFactory.getLogger(SHCrmHandler.class);

    @Override
    public HttpRequest before(CfgHttpClient cfgHttpClientEntity, Map<String, Object> busiParam) throws Exception {
        String clientCode = "";

        //将内部CODE转换为CRM请求CODE BUSI_CODE---> HTTP_CLIENT_CODE
        if (null != cfgHttpClientEntity && StringUtils.isNotBlank(cfgHttpClientEntity.getCfgHttpClientCode())) {
            clientCode = cfgHttpClientEntity.getCfgHttpClientCode();
        }

        //封装报文数据
        ReqParamBean reqParamBean = new ReqParamBean();
        PubInfoBean pubInfoBean = initReqPubInfo(cfgHttpClientEntity.getBusiCode(), clientCode);
        RequestBean requestBean = initRequest(clientCode, busiParam);
        reqParamBean.setPubInfo(pubInfoBean);
        reqParamBean.setRequest(requestBean);
        //将报文数据转换为JSON对象
        String reqParamStr = JsonUtil.beanToJsonString(reqParamBean);
        if (logger.isInfoEnabled()) {
            logger.info(cfgHttpClientEntity.getBusiCode() + "ESOP调用HTTTP接口开始");
            logger.info(cfgHttpClientEntity.getBusiCode() + "ESOP调用HTTTP接口请求入参JSON：" + reqParamStr);
        }


        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(cfgHttpClientEntity.getUrlAddress());
        httpRequest.setTimeoutSeconds(cfgHttpClientEntity.getTimeoutSeconds());
        httpRequest.setResponseType(cfgHttpClientEntity.getResponseType());
        httpRequest.setHeader(parseString2Map(cfgHttpClientEntity.getRequestHeader()));
        httpRequest.setParameters(null);
        httpRequest.setBusiCode(cfgHttpClientEntity.getBusiCode());
        httpRequest.setBody(reqParamStr.getBytes("UTF-8"));

        return httpRequest;
    }

    @Override
    public Response after(HttpResponse httpResponse) throws Exception {
        String responseStr = null;
        if (httpResponse instanceof HttpResponseString) {
            responseStr = ((HttpResponseString) httpResponse).getResponseBody();
        } else if (httpResponse instanceof HttpResponseByte) {
            responseStr = new String(((HttpResponseByte) httpResponse).getResponseBody(), "UTF-8");
        }
        logger.info(responseStr);

        Response response = new Response();
        response.setData(responseStr);
        return response;
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
        header.put("methodName",cfgWsClient.getMethodName());
        header.put("methodParameter",cfgWsClient.getMethodParameter());
        header.put("methodReturnType",cfgWsClient.getMethodReturnType());
        header.put("operationStyle",cfgWsClient.getOperationStyle());
        header.put("operationUse",cfgWsClient.getOperationUse());
        header.put("registerTypeMapping",cfgWsClient.getRegisterTypeMapping());

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


    //TODO 还需要增加操作员地市编码 操作员编号等 目前写死
    private PubInfoBean initReqPubInfo(String busiCode, String clientCode) throws Exception {
        PubInfoBean pubInfoBean = new PubInfoBean();
        pubInfoBean.setClientIP(InetAddress.getLocalHost().getHostAddress());
        pubInfoBean.setTransactionId("esop_" + clientCode);
        pubInfoBean.setInterfaceId("48");
        pubInfoBean.setInterfaceType("4");

        String opId = RpcContext.getContext().getAttachment("OP_ID");
        if (StringUtils.isNotEmpty(opId)) {
            pubInfoBean.setOpId(opId);
        } else {
            pubInfoBean.setOpId("999990078");
        }
        pubInfoBean.setCountyCode("571001");
        pubInfoBean.setOrgId("0");
        pubInfoBean.setTransactionTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmm"));
        pubInfoBean.setRegionCode("571");
        pubInfoBean.setEsopBusiCode(busiCode);
        return pubInfoBean;
    }

    private RequestBean initRequest(String clientCode, Map<String, Object> busiMap) {
        RequestBean request = new RequestBean();
        request.setBusiCode(clientCode);
        request.setBusiParams(busiMap);
        return request;
    }

    private HashMap<String, String> parseString2Map(String str) throws Exception {
        HashMap<String, String> map = new HashMap();
        String tmp1[] = StringUtils.getParamFromString(str, "[", "]");
        for (int i = 0; i < tmp1.length; i++) {
            String tmp2[] = StringUtils.getParamFromString(tmp1[i], "{", "}");
            for (int j = 0; j < tmp2.length; j++) {
                String tmp3[] = StringUtils.getParamFromString(tmp2[j], "'", "'");
                map.put(tmp3[0], tmp3[1]);
            }
        }
        return map;
    }


}




