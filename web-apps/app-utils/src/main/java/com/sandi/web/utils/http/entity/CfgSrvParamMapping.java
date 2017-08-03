/**
 * $Id: CfgSrvParamMapping.java,v 1.0 2017/1/16 9:55 lijie Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.http.entity;

/**
 * @author lijie
 * @version $Id: CfgSrvParamMapping.java, 1.1 2017/1/16 9:55 lijie Exp $
 * Created on 2017/1/16 9:55
 */
public class CfgSrvParamMapping implements java.io.Serializable{
    private String mappingId;

    private String busiId;

    private String srvId;

    private Integer paramType;//参数类型，1-入参，2-出参

    private String paramCode;

    private String paramName;

    private String srvParamCode;

    private String srvParamName;

    private String dataType;

    private Integer transitivity;//参数传递性,1-需要传递

    private Integer sort;

    private Integer state;

    public String getMappingId() {return mappingId;}

    public void setMappingId(String mappingId) {this.mappingId = mappingId;}

    public String getBusiId() {return busiId;}

    public void setBusiId(String busiId) {this.busiId = busiId;}

    public String getSrvId() {return srvId;}

    public void setSrvId(String srvId) {this.srvId = srvId;}

    public Integer getParamType() {return paramType;}

    public void setParamType(Integer paramType) {this.paramType = paramType;}

    public String getParamCode() {return paramCode;}

    public void setParamCode(String paramCode) {this.paramCode = paramCode;}

    public String getParamName() {return paramName;}

    public void setParamName(String paramName) {this.paramName = paramName;}

    public String getSrvParamCode() {return srvParamCode;}

    public void setSrvParamCode(String srvParamCode) {this.srvParamCode = srvParamCode;}

    public String getSrvParamName() {return srvParamName;}

    public void setSrvParamName(String srvParamName) {this.srvParamName = srvParamName;}

    public String getDataType() {return dataType;}

    public void setDataType(String dataType) {this.dataType = dataType;}

    public Integer getTransitivity() {return transitivity;}

    public void setTransitivity(Integer transitivity) {this.transitivity = transitivity;}

    public Integer getSort() {return sort;}

    public void setSort(Integer sort) {this.sort = sort;}

    public Integer getState() {return state;}

    public void setState(Integer state) {this.state = state;}
}
