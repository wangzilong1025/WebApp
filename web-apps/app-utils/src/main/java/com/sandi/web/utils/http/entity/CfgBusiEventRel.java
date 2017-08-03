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
public class CfgBusiEventRel implements java.io.Serializable{

    private String busiId;

    private String srvId;//业务事件，则此编码为空

    private Integer busiSrvType;//类型，1-业务，2-服务

    private String eventId;

    private Integer eventType;//事件类型，1-beforeInovkeEvent-调用前事件，2-returnEvent-返回处理事件，3-invokeTimeout-调用超时事件

    private Integer sort;

    private Integer eventKind;//事件种类，1-js文件，2-js内容，3-JAVA类

    private String eventDesc;

    private String eventFile;//js文件路径

    private String eventFunc;//js文件中的方法

    private String eventContent;

    private String eventClazz;//事件处理类

    public String getBusiId() {return busiId;}

    public void setBusiId(String busiId) {this.busiId = busiId;}

    public String getSrvId() {return srvId;}

    public void setSrvId(String srvId) {this.srvId = srvId;}

    public Integer getBusiSrvType() {return busiSrvType;}

    public void setBusiSrvType(Integer busiSrvType) {this.busiSrvType = busiSrvType;}

    public String getEventId() {return eventId;}

    public void setEventId(String eventId) {this.eventId = eventId;}

    public Integer getEventType() {return eventType;}

    public void setEventType(Integer eventType) {this.eventType = eventType;}

    public Integer getSort() {return sort;}

    public void setSort(Integer sort) {this.sort = sort;}

    public Integer getEventKind() {return eventKind;}

    public void setEventKind(Integer eventKind) {this.eventKind = eventKind;}

    public String getEventDesc() {return eventDesc;}

    public void setEventDesc(String eventDesc) {this.eventDesc = eventDesc;}

    public String getEventFile() {return eventFile;}

    public void setEventFile(String eventFile) {this.eventFile = eventFile;}

    public String getEventFunc() {return eventFunc;}

    public void setEventFunc(String eventFunc) {this.eventFunc = eventFunc;}

    public String getEventContent() {return eventContent;}

    public void setEventContent(String eventContent) {this.eventContent = eventContent;}

    public String getEventClazz() {return eventClazz;}

    public void setEventClazz(String eventClazz) {this.eventClazz = eventClazz;}
}
