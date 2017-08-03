/*
 * $Id: PubInfo.java,v 1.0 2015年7月24日 上午10:12:04 zhangrp Exp $
 *
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.request;

import java.io.Serializable;

/**
 * @author zhangrp
 * @version $Id: PubInfo.java v 1.0 Exp $
 *          Created on 2015年7月24日 上午10:12:04
 */
public class PubInfo implements Serializable {
    private String transactionId;
    private String interfaceId;
    private String interfaceType;
    private Long opId;
    private Long orgId;
    private String countyCode;
    private String clientIP;
    private String transactionTime;
    private String regionCode;
    private String sessionId;
    private Integer module;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Long getOpId() {
        return opId;
    }

    public void setOpId(Long opId) {
        this.opId = opId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }
}
