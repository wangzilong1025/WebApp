/**
 * $Id: CfgBusiBase.java,v 1.0 2017/1/16 9:55 lijie Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.http.entity;

/**
 * @author lijie
 * @version $Id: CfgBusiBase.java,v 1.1 2017/1/16 9:55 lijie Exp $
 * Created on 2017/1/16 9:55
 */
public class CfgBusiSrvRel implements java.io.Serializable{

    private String busiSrvRelId;

    private String busiId;

    private String srvId;

    private Integer srvType;//服务类型，1-dubbo,2-http,3-ws

    private Integer sort;

    private Integer state;

    public String getBusiSrvRelId() {return busiSrvRelId;}

    public void setBusiSrvRelId(String busiSrvRelId) {this.busiSrvRelId = busiSrvRelId;}

    public String getBusiId() {return busiId;}

    public void setBusiId(String busiId) {this.busiId = busiId;}

    public String getSrvId() {return srvId;}

    public void setSrvId(String srvId) {this.srvId = srvId;}

    public Integer getSrvType() {return srvType;}

    public void setSrvType(Integer srvType) {this.srvType = srvType;}

    public Integer getSort() {return sort;}

    public void setSort(Integer sort) {this.sort = sort;}

    public Integer getState() {return state;}

    public void setState(Integer state) {this.state = state;}
}
