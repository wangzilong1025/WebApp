/**
 * $Id: CfgBusiBase.java,v 1.0 2017/1/16 9:55 lijie Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.http.entity;

import java.util.List;
import java.util.Map;

/**
 * @author lijie
 * @version $Id: CfgBusiBase.java,v 1.1 2017/1/16 9:55 lijie Exp $
 * Created on 2017/1/16 9:55
 */
public class CfgBusiBase implements java.io.Serializable{
    private String busiId;

    private String busiName;

    private String des;

    private Integer state;

    private Integer busiType;//1.顺序调用，2.合值调用

    private List<CfgBusiSrvRel> busiSrvRels;

    private Map<String, List<CfgBusiEventRel>> eventMap;

    private Map<String, List<CfgSrvParamMapping>> paramMap;

    public String getBusiId() {return busiId;}

    public void setBusiId(String busiId) {this.busiId = busiId;}

    public String getBusiName() {return busiName;}

    public void setBusiName(String busiName) {this.busiName = busiName;}

    public String getDes() {return des;}

    public void setDes(String des) {this.des = des;}

    public Integer getState() {return state;}

    public void setState(Integer state) {this.state = state;}

    public Integer getBusiType() {
        return busiType;
    }

    public void setBusiType(Integer busiType) {
        this.busiType = busiType;
    }

    public List<CfgBusiSrvRel> getBusiSrvRels() {
        return busiSrvRels;
    }

    public void setBusiSrvRels(List<CfgBusiSrvRel> busiSrvRels) {
        this.busiSrvRels = busiSrvRels;
    }

    public Map<String, List<CfgBusiEventRel>> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, List<CfgBusiEventRel>> eventMap) {
        this.eventMap = eventMap;
    }

    public Map<String, List<CfgSrvParamMapping>> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, List<CfgSrvParamMapping>> paramMap) {
        this.paramMap = paramMap;
    }
}
