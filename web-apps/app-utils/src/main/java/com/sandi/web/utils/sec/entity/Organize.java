/**
 * $Id: Organize.java,v 1.0 2016/9/7 13:58 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.sec.entity;

import java.io.Serializable;

/**
 * @author dizl
 * @version $Id: Organize.java,v 1.1 2016/9/7 13:58 dizl Exp $
 * Created on 2016/9/7 13:58
 */
public class Organize implements Serializable{
    private Long organizeId;//组织编号
    private String organizeName;//组织名称
    private String code;
    private Long parentOrganizeId;//父节点组织编号
    private Long provinceId;//省份编号
    private Long cityId;//地市编号
    private Long countyId;//区县编号
    private String shortName;

    public Long getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(Long organizeId) {
        this.organizeId = organizeId;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getParentOrganizeId() {
        return parentOrganizeId;
    }

    public void setParentOrganizeId(Long parentOrganizeId) {
        this.parentOrganizeId = parentOrganizeId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }
}
