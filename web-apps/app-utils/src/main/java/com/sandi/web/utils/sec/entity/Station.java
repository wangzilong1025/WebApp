/**
 * $Id: Station.java,v 1.0 2016/9/7 13:57 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.sec.entity;

import java.io.Serializable;

/**
 * @author dizl
 * @version $Id: Station.java,v 1.1 2016/9/7 13:57 dizl Exp $
 * Created on 2016/9/7 13:57
 */
public class Station implements Serializable {
    private Long stationId;//岗位编号
    private Long organizeId;//组织编号
    private Long stationTypeId;//岗位类型
    private String name;//名称
    private String stationTypeName;//岗位类型名称

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(Long organizeId) {
        this.organizeId = organizeId;
    }

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

    public String getStationTypeName() {
        return stationTypeName;
    }

    public void setStationTypeName(String stationTypeName) {
        this.stationTypeName = stationTypeName;
    }

}
