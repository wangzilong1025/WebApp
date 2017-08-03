/**
 * $Id: CfgFunSrv.java,v 1.0 2015/11/6 16:16 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.osdi.entity;


import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

/**
 * @author zhangrp
 * @version $Id: CfgFunSrv.java,v 1.1 2015/11/6 16:16 zhangrp Exp $
 *          Created on 2015/11/6 16:16
 */
public class CfgFunSrv extends BaseEntity {
    private Long funId;
    private String srvId;
    private Date createDate;
    private String state;

    public Long getFunId() {
        return funId;
    }

    public void setFunId(Long funId) {
        this.funId = funId;
    }

    public String getSrvId() {
        return srvId;
    }

    public void setSrvId(String srvId) {
        this.srvId = srvId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
