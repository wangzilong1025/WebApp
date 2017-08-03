/**
 * $Id: CfgSrvBaseEntity.java,v 1.0 2015/6/11 11:12 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.osdi.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

/**
 * @author zhangrp
 * @version $Id: CfgSrvBaseEntity.java,v 1.1 2015/7/11 11:12 zhangrp Exp $
 *          Created on 2015/7/11 11:12
 */
@SuppressWarnings("serial")
public class CfgSrvFuncRel extends BaseEntity {

    @Id
    private Long id;

    private String srvId;

    private Long funcId;

    private String state;

    private String remark;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSrvId() {
        return srvId;
    }

    public void setSrvId(String srvId) {
        this.srvId = srvId;
    }

    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

