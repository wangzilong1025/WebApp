package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgProcessRuleEntity extends BaseEntity {
	@Id
	private Long ruleId;//null

	private String ruleName;//null

	private String dealClass;//null

	private Integer state;//null

	private Date createDate;//null

	private Long creator;//null

	private Date doneDate;//null

	private Long opId;//null

	public Long getRuleId() {return ruleId;}

	public void setRuleId(Long ruleId) {this.ruleId = ruleId;}

	public String getRuleName() {return ruleName;}

	public void setRuleName(String ruleName) {this.ruleName = ruleName;}

	public String getDealClass() {return dealClass;}

	public void setDealClass(String dealClass) {this.dealClass = dealClass;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

	public Date getDoneDate() {return doneDate;}

	public void setDoneDate(Date doneDate) {this.doneDate = doneDate;}

	public Long getOpId() {return opId;}

	public void setOpId(Long opId) {this.opId = opId;}

}