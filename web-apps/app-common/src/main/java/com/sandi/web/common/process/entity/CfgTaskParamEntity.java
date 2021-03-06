package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgTaskParamEntity extends BaseEntity {
	@Id
	private Long taskParamId;//null

	private Long processId;

	private Long taskId;//null

	private String paramCode;//null

	private String paramName;//null

	private Integer paramType;//null

	private String defaultValue;//null

	private Integer state;//null

	private Long creator;//null

	private Date createDate;//null

	private Long opId;//null

	private Date doneDate;//null

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Long getTaskParamId() {return taskParamId;}

	public void setTaskParamId(Long taskParamId) {this.taskParamId = taskParamId;}

	public Long getTaskId() {return taskId;}

	public void setTaskId(Long taskId) {this.taskId = taskId;}

	public String getParamCode() {return paramCode;}

	public void setParamCode(String paramCode) {this.paramCode = paramCode;}

	public String getParamName() {return paramName;}

	public void setParamName(String paramName) {this.paramName = paramName;}

	public Integer getParamType() {return paramType;}

	public void setParamType(Integer paramType) {this.paramType = paramType;}

	public String getDefaultValue() {return defaultValue;}

	public void setDefaultValue(String defaultValue) {this.defaultValue = defaultValue;}

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