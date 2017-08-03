/**
 * $Id: HttpRequest.java,v 1.0 2016/8/25 16:53 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.http.client.request;

import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: HttpRequest.java,v 1.1 2016/8/25 16:53 zhangrp Exp $
 *          Created on 2016/8/25 16:53
 */
public class HttpRequest {
    private String url;
    private Integer timeoutSeconds;
    private String responseType;
    private String busiCode;
    private Map<String, String> header;
    private Map<String, Object> parameters;
    private byte body[];

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }
}
