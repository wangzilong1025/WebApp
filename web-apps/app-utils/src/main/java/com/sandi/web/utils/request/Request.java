/*
 * $Id: Request.java,v 1.0 2015年7月23日 下午4:57:30 zhangrp Exp $
 *
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.request;

import com.sandi.web.utils.common.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author zhangrp
 * @version $Id: Request.java v 1.0 Exp $
 *          Created on 2015年7月23日 下午4:57:30
 */
public class Request implements Serializable {

    protected static final Logger logger = LoggerFactory.getLogger(Request.class);

    private PubInfo pubInfo;
    private RequestInfo requestInfo;

    public Request() {
    }

    public Request(PubInfo pubInfo, RequestInfo requestInfo) {
        this.pubInfo = pubInfo;
        this.requestInfo = requestInfo;
    }

    public static Request getRequest(String json) throws Exception {

        return JsonUtil.json2Object(json, Request.class);
    }

    public String toString() {
        try {
            return JsonUtil.object2Json(this);
        } catch (IOException e) {
            logger.error("", e);
        }
        return "";
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    public PubInfo getPubInfo() {
        return pubInfo;
    }

    public void setPubInfo(PubInfo pubInfo) {
        this.pubInfo = pubInfo;
    }

    public static void main(String args[]) {
        Request request = new Request();
        request.setPubInfo(new PubInfo());
        request.setRequestInfo(new RequestInfo());
        System.out.println(request.toString());
    }
}
