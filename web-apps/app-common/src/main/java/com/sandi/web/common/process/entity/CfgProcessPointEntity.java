package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgProcessPointEntity extends BaseEntity {
	@Id
	private Long pointId;//null

	private String pointName;//null

	private Long processId;//null

	private Integer pointType;//null

	private Long pointObjId;//null

	private String statesName;//null

	private Integer state;//null

	private Long creator;//null

	private Date createDate;//null

	private Long opId;//null

	private Date doneDate;//null

	public Long getPointId() {return pointId;}

	public void setPointId(Long pointId) {this.pointId = pointId;}

	public String getPointName() {return pointName;}

	public void setPointName(String pointName) {this.pointName = pointName;}

	public Long getProcessId() {return processId;}

	public void setProcessId(Long processId) {this.processId = processId;}

	public Integer getPointType() {return pointType;}

	public void setPointType(Integer pointType) {this.pointType = pointType;}

	public Long getPointObjId() {return pointObjId;}

	public void setPointObjId(Long pointObjId) {this.pointObjId = pointObjId;}

	public String getStatesName() {return statesName;}

	public void setStatesName(String statesName) {this.statesName = statesName;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Long getOpId() {return opId;}

	public void setOpId(Long opId) {this.opId = opId;}

	public Date getDoneDate() {return doneDate;}

	public void setDoneDate(Date doneDate) {this.doneDate = doneDate;}

}