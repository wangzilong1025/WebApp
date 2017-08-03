package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgTaskInsEntity extends BaseEntity {
	@Id
	private Long taskInsId;//null

	private Long processInsId;//null

	private Long processId;//null

	private Long taskId;//null

	private Long pointId;//null

	private Long prevTaskInsId;//null

	private String taskName;//null

	private String taskKey;//null

	private Integer taskState;//null

	private Integer taskType;//null

	private Long dealOpId;//null

	private String dealOpName;

	private Date planStartTime;//null

	private Date planEndTime;//null

	private Integer overTimeFlag;//null

	private String remarks;

	private Integer state;//null

	private Date createDate;//null

	private Long creator;//null

	private Long opId;//null

	private Date doneDate;//null

	public Long getTaskInsId() {return taskInsId;}

	public void setTaskInsId(Long taskInsId) {this.taskInsId = taskInsId;}

	public Long getProcessInsId() {return processInsId;}

	public void setProcessInsId(Long processInsId) {this.processInsId = processInsId;}

	public Long getProcessId() {return processId;}

	public void setProcessId(Long processId) {this.processId = processId;}

	public Long getTaskId() {return taskId;}

	public void setTaskId(Long taskId) {this.taskId = taskId;}

	public Long getPointId() {return pointId;}

	public void setPointId(Long pointId) {this.pointId = pointId;}

	public Long getPrevTaskInsId() {return prevTaskInsId;}

	public void setPrevTaskInsId(Long prevTaskInsId) {this.prevTaskInsId = prevTaskInsId;}

	public String getTaskName() {return taskName;}

	public void setTaskName(String taskName) {this.taskName = taskName;}

	public String getTaskKey() {return taskKey;}

	public void setTaskKey(String taskKey) {this.taskKey = taskKey;}

	public Integer getTaskState() {return taskState;}

	public void setTaskState(Integer taskState) {this.taskState = taskState;}

	public Integer getTaskType() {return taskType;}

	public void setTaskType(Integer taskType) {this.taskType = taskType;}

	public Long getDealOpId() {return dealOpId;}

	public String getDealOpName() {
		return dealOpName;
	}

	public void setDealOpName(String dealOpName) {
		this.dealOpName = dealOpName;
	}

	public void setDealOpId(Long dealOpId) {this.dealOpId = dealOpId;}

	public Date getPlanStartTime() {return planStartTime;}

	public void setPlanStartTime(Date planStartTime) {this.planStartTime = planStartTime;}

	public Date getPlanEndTime() {return planEndTime;}

	public void setPlanEndTime(Date planEndTime) {this.planEndTime = planEndTime;}

	public Integer getOverTimeFlag() {return overTimeFlag;}

	public void setOverTimeFlag(Integer overTimeFlag) {this.overTimeFlag = overTimeFlag;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

	public Long getOpId() {return opId;}

	public void setOpId(Long opId) {this.opId = opId;}

	public Date getDoneDate() {return doneDate;}

	public void setDoneDate(Date doneDate) {this.doneDate = doneDate;}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}