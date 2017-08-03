package com.sandi.web.common.osdi.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

public class CfgEventRelEntity extends BaseEntity {
	@Id
	private String eventRelId;//null

	private String busiId;//null

	private String srvId;//业务事件，则此编码为空

	private Integer busiSrvType;//类型，1-业务，2-服务

	private String eventId;//null

	private Integer eventType;//事件类型，1-beforeInovkeEvent-调用前事件，2-returnEvent-返回处理事件，3-invokeTimeout-调用超时事件

	private Integer sort;//null

	private Integer state;//null

	public String getEventRelId() {return eventRelId;}

	public void setEventRelId(String eventRelId) {this.eventRelId = eventRelId;}

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

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

}