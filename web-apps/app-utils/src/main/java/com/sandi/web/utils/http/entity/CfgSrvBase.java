/**
 * $Id: CfgSrvBase.java,v 1.0 2016/8/20 15:41 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.http.entity;

/**
 * @author zhangrp
 * @version $Id: CfgSrvBase.java,v 1.1 2016/8/20 15:41 zhangrp Exp $
 *          Created on 2016/8/20 15:41
 */
public class CfgSrvBase implements java.io.Serializable{

    private String srvId;

    private String srvName;

    private String catalogId;

    private String srvPackage;

    private String srvMethod;

    private Integer state;

    private String remark;

    private Long invoketimeout;

    private String parIn;
    private String parOut;


    public void setSrvId(String srvId) {
        this.srvId = srvId;
    }

    public String getSrvId() {
        return this.srvId;
    }

    public void setSrvName(String srvName) {
        this.srvName = srvName;
    }

    public String getSrvName() {
        return this.srvName;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogId() {
        return this.catalogId;
    }

    public void setSrvPackage(String srvPackage) {
        this.srvPackage = srvPackage;
    }

    public String getSrvPackage() {
        return this.srvPackage;
    }

    public void setSrvMethod(String srvMethod) {
        this.srvMethod = srvMethod;
    }

    public String getSrvMethod() {
        return this.srvMethod;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setInvoketimeout(Long invoketimeout) {
        this.invoketimeout = invoketimeout;
    }

    public Long getInvoketimeout() {
        return this.invoketimeout;
    }

    public String getParIn() {
        return parIn;
    }

    public void setParIn(String parIn) {
        this.parIn = parIn;
    }

    public String getParOut() {
        return parOut;
    }

    public void setParOut(String parOut) {
        this.parOut = parOut;
    }
}
