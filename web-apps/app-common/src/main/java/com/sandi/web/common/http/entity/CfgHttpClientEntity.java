/**
 * $Id: CfgHttpClient.java,v 1.0 2016/8/25 15:37 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.http.entity;


import com.sandi.web.common.persistence.entity.BaseEntity;

/**
 * @author zhangrp
 * @version $Id: CfgHttpClient.java,v 1.1 2016/8/25 15:37 zhangrp Exp $
 *          Created on 2016/8/25 15:37
 */
public class CfgHttpClientEntity extends BaseEntity {

    private String busiCode;
    private String cfgHttpClientCode;
    private String urlAddress;
    private String requestHeader;
    private String responseType;
    private Integer timeoutSeconds;
    private Integer state;
    private String remark;
    private String handlerClass;

    public void setCfgHttpClientCode(String cfgHttpClientCode) {
        this.cfgHttpClientCode = cfgHttpClientCode;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getCfgHttpClientCode() {
        return cfgHttpClientCode;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public String getResponseType() {
        return responseType;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(String handlerClass) {
        this.handlerClass = handlerClass;
    }
}
