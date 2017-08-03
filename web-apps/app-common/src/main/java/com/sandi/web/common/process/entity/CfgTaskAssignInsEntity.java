package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgTaskAssignInsEntity extends BaseEntity {
	@Id
	private Long assignInsId;//null

	private Long taskInsId;//null

	private Long taskId;//null

	private Long processInsId;//null

	private Long pointId;//null

	private String taskHandlerType;//null

	private String taskHandler;//null

	private Integer readOnlyFlag;//null

	private Integer state;//null

	private Long creator;//null

	private Date createDate;//null

	private Long opId;//null

	private Date doneDate;//null

	public Long getAssignInsId() {return assignInsId;}

	public void setAssignInsId(Long assignInsId) {this.assignInsId = assignInsId;}

	public Long getTaskInsId() {return taskInsId;}

	public void setTaskInsId(Long taskInsId) {this.taskInsId = taskInsId;}

	public Long getTaskId() {return taskId;}

	public void setTaskId(Long taskId) {this.taskId = taskId;}

	public Long getProcessInsId() {return processInsId;}

	public void setProcessInsId(Long processInsId) {this.processInsId = processInsId;}

	public Long getPointId() {return pointId;}

	public void setPointId(Long pointId) {this.pointId = pointId;}

	public String getTaskHandlerType() {
		return taskHandlerType;
	}

	public void setTaskHandlerType(String taskHandlerType) {
		this.taskHandlerType = taskHandlerType;
	}

	public String getTaskHandler() {
		return taskHandler;
	}

	public void setTaskHandler(String taskHandler) {
		this.taskHandler = taskHandler;
	}

	public Integer getReadOnlyFlag() {return readOnlyFlag;}

	public void setReadOnlyFlag(Integer readOnlyFlag) {this.readOnlyFlag = readOnlyFlag;}

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