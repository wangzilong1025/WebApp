package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgTaskDefineEntity extends BaseEntity {
	@Id
	private Long taskId;//null

	private Long processId;

	private String taskName;//null

	private String taskKey;//null

	private String taskDesc;//null

	private String planTime;//null

	private Integer autoDealFlag;//超时自动处理标记

	private Integer taskType;//0-人工任务	 1-自动任务

	private String taskDealClass;//null

	private String taskDealParam;//null

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

	public Long getTaskId() {return taskId;}

	public void setTaskId(Long taskId) {this.taskId = taskId;}

	public String getTaskName() {return taskName;}

	public void setTaskName(String taskName) {this.taskName = taskName;}

	public String getTaskKey() {return taskKey;}

	public void setTaskKey(String taskKey) {this.taskKey = taskKey;}

	public String getTaskDesc() {return taskDesc;}

	public void setTaskDesc(String taskDesc) {this.taskDesc = taskDesc;}

	public String getPlanTime() {return planTime;}

	public void setPlanTime(String planTime) {this.planTime = planTime;}

	public Integer getTaskType() {return taskType;}

	public void setTaskType(Integer taskType) {this.taskType = taskType;}

	public String getTaskDealClass() {return taskDealClass;}

	public void setTaskDealClass(String taskDealClass) {this.taskDealClass = taskDealClass;}

	public String getTaskDealParam() {return taskDealParam;}

	public void setTaskDealParam(String taskDealParam) {this.taskDealParam = taskDealParam;}

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

	public Integer getAutoDealFlag() {
		return autoDealFlag;
	}

	public void setAutoDealFlag(Integer autoDealFlag) {
		this.autoDealFlag = autoDealFlag;
	}
}