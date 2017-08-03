package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgProcessPointRuleEntity extends BaseEntity {

	@Id
	private Long pointRuleId;//null

	private Long processId;

	private String ruleParam;//null

	private String ruleValue;//null

	private Long nextPointId;//null

	private Integer state;//null

	private Long creator;//null

	private Date createDate;//null

	private Long opId;//null

	private Date doneDate;//null

	private Long pointId;//null

	private Long ruleId;//null

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public String getRuleParam() {return ruleParam;}

	public void setRuleParam(String ruleParam) {this.ruleParam = ruleParam;}

	public String getRuleValue() {return ruleValue;}

	public void setRuleValue(String ruleValue) {this.ruleValue = ruleValue;}

	public Long getNextPointId() {return nextPointId;}

	public void setNextPointId(Long nextPointId) {this.nextPointId = nextPointId;}

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

	public Long getPointRuleId() {return pointRuleId;}

	public void setPointRuleId(Long pointRuleId) {this.pointRuleId = pointRuleId;}

	public Long getPointId() {return pointId;}

	public void setPointId(Long pointId) {this.pointId = pointId;}

	public Long getRuleId() {return ruleId;}

	public void setRuleId(Long ruleId) {this.ruleId = ruleId;}

}