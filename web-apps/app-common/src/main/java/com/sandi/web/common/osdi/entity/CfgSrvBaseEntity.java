/**
 * $Id: CfgSrvBaseEntity.java,v 1.0 2015/6/11 11:12 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.osdi.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

/**
 * @author zhangrp
 * @version $Id: CfgSrvBaseEntity.java,v 1.1 2015/7/11 11:12 zhangrp Exp $
 *          Created on 2015/7/11 11:12
 */
@SuppressWarnings("serial")
public class CfgSrvBaseEntity extends BaseEntity {

    @Id
    private String srvId;

    private String srvName;

    private String catalogId;

    private String srvPackage;

    private String srvMethod;

    private String state;

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

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
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

