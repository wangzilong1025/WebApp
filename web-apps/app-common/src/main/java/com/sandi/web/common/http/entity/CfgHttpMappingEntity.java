/**
 * $Id: CfgHttpMappingEntity.java,v 1.0 2016/8/25 15:39 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.http.entity;

import com.sandi.web.common.persistence.entity.BaseEntity;

/**
 * @author zhangrp
 * @version $Id: CfgHttpMappingEntity.java,v 1.1 2016/8/25 15:39 zhangrp Exp $
 *          Created on 2016/8/25 15:39
 */
public class CfgHttpMappingEntity extends BaseEntity {

    private Long mappingId;
    private String cfgHttpCode;
    private String mappingName;
    private String mappingValue;
    private Integer state;

    public CfgHttpMappingEntity() {
    }

    public void setMappingId(Long mappingId) {
        this.mappingId = mappingId;
    }

    public void setCfgHttpCode(String cfgHttpCode) {
        this.cfgHttpCode = cfgHttpCode;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

    public void setMappingValue(String mappingValue) {
        this.mappingValue = mappingValue;
    }

    public Long getMappingId() {
        return mappingId;
    }

    public String getCfgHttpCode() {
        return cfgHttpCode;
    }

    public String getMappingName() {
        return mappingName;
    }

    public String getMappingValue() {
        return mappingValue;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
