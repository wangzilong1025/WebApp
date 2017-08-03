package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgTaskAssignEntity extends BaseEntity {
	@Id
	private Long taskAssignId;//null

	private Long processId;

	private Long taskId;//null

	private String taskHandlerType;//null

	private String taskHandler;//null

	private Integer readOnlyFlag;//null

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

	public Long getTaskAssignId() {
		return taskAssignId;
	}

	public void setTaskAssignId(Long taskAssignId) {
		this.taskAssignId = taskAssignId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

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

	public Integer getReadOnlyFlag() {
		return readOnlyFlag;
	}

	public void setReadOnlyFlag(Integer readOnlyFlag) {
		this.readOnlyFlag = readOnlyFlag;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}
}