/**
 * $Id: StationType.java,v 1.0 2016/9/7 13:57 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.sec.entity;

import java.io.Serializable;

/**
 * @author dizl
 * @version $Id: StationType.java,v 1.1 2016/9/7 13:57 dizl Exp $
 * Created on 2016/9/7 13:57
 */
public class StationType implements Serializable {
    private Long stationTypeId;//岗位类型编号
    private String name;//岗位名称

    public Long getStationTypeId() {
        return stationTypeId;
    }

    public void setStationTypeId(Long stationTypeId) {
        this.stationTypeId = stationTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
