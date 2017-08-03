package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgProcessDefineEntity extends BaseEntity {
	@Id
	private Long processId;//null

	private Long processDefineId;//null

	private String processName;//null

	private Integer processType;//null

	private String processKey;//null

	private String processDesc;//null

	private Integer state;//null

	private Date validDate;//null

	private Date expireDate;//null

	private Long creator;//null

	private Date createDate;//null

	private Long opId;//null

	private Date doneDate;//null

	private String processJson1;//null

	private String processJson2;//null

	private String processJson3;//null

	private String processJson4;//null

	private String processJson5;//null

	public Long getProcessId() {return processId;}

	public void setProcessId(Long processId) {this.processId = processId;}

	public Long getProcessDefineId() {return processDefineId;}

	public void setProcessDefineId(Long processDefineId) {this.processDefineId = processDefineId;}

	public String getProcessName() {return processName;}

	public void setProcessName(String processName) {this.processName = processName;}

	public Integer getProcessType() {return processType;}

	public void setProcessType(Integer processType) {this.processType = processType;}

	public String getProcessKey() {return processKey;}

	public void setProcessKey(String processKey) {this.processKey = processKey;}

	public String getProcessDesc() {return processDesc;}

	public void setProcessDesc(String processDesc) {this.processDesc = processDesc;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public Date getValidDate() {return validDate;}

	public void setValidDate(Date validDate) {this.validDate = validDate;}

	public Date getExpireDate() {return expireDate;}

	public void setExpireDate(Date expireDate) {this.expireDate = expireDate;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Long getOpId() {return opId;}

	public void setOpId(Long opId) {this.opId = opId;}

	public Date getDoneDate() {return doneDate;}

	public void setDoneDate(Date doneDate) {this.doneDate = doneDate;}

	public String getProcessJson1() {return processJson1;}

	public void setProcessJson1(String processJson1) {this.processJson1 = processJson1;}

	public String getProcessJson2() {return processJson2;}

	public void setProcessJson2(String processJson2) {this.processJson2 = processJson2;}

	public String getProcessJson3() {return processJson3;}

	public void setProcessJson3(String processJson3) {this.processJson3 = processJson3;}

	public String getProcessJson4() {return processJson4;}

	public void setProcessJson4(String processJson4) {this.processJson4 = processJson4;}

	public String getProcessJson5() {return processJson5;}

	public void setProcessJson5(String processJson5) {this.processJson5 = processJson5;}

}