package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgProcessInsEntity extends BaseEntity {
	@Id
	private Long processInsId;//null

	private Long processId;//null

	private Long processDefineId;//null

	private String processName;//null

	private String processKey;//null

	private Integer processType;

	private Long parentProcessInsId;//null

	private Long parentTaskInsId;//null

	private Integer processState;//null

	private Integer state;//null

	private Long creator;//null

	private Date createDate;//null

	private Long opId;//null

	private Date doneDate;//null

	public Long getProcessInsId() {return processInsId;}

	public void setProcessInsId(Long processInsId) {this.processInsId = processInsId;}

	public Integer getProcessType() {
		return processType;
	}

	public void setProcessType(Integer processType) {
		this.processType = processType;
	}

	public Long getProcessId() {return processId;}

	public void setProcessId(Long processId) {this.processId = processId;}

	public Long getProcessDefineId() {return processDefineId;}

	public void setProcessDefineId(Long processDefineId) {this.processDefineId = processDefineId;}

	public String getProcessName() {return processName;}

	public void setProcessName(String processName) {this.processName = processName;}

	public String getProcessKey() {return processKey;}

	public void setProcessKey(String processKey) {this.processKey = processKey;}

	public Long getParentProcessInsId() {return parentProcessInsId;}

	public void setParentProcessInsId(Long parentProcessInsId) {this.parentProcessInsId = parentProcessInsId;}

	public Long getParentTaskInsId() {
		return parentTaskInsId;
	}

	public void setParentTaskInsId(Long parentTaskInsId) {
		this.parentTaskInsId = parentTaskInsId;
	}

	public Integer getProcessState() {return processState;}

	public void setProcessState(Integer processState) {this.processState = processState;}

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