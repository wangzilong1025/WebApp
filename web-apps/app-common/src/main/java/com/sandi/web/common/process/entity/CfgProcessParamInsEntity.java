package com.sandi.web.common.process.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgProcessParamInsEntity extends BaseEntity {
	@Id
	private Long paramInsId;//null

	private Long processId;//null

	private Long processInsId;//null

	private Long paramId;//null

	private String paramCode;//null

	private String paramName;//null

	private Integer paramType;//null

	private String paramValue;//null

	private Integer state;//null

	private Long creator;//null

	private Date createDate;//null

	private Long opId;//null

	private Date doneDate;//null

	public Long getParamInsId() {return paramInsId;}

	public void setParamInsId(Long paramInsId) {this.paramInsId = paramInsId;}

	public Long getProcessId() {return processId;}

	public void setProcessId(Long processId) {this.processId = processId;}

	public Long getProcessInsId() {return processInsId;}

	public void setProcessInsId(Long processInsId) {this.processInsId = processInsId;}

	public Long getParamId() {return paramId;}

	public void setParamId(Long paramId) {this.paramId = paramId;}

	public String getParamCode() {return paramCode;}

	public void setParamCode(String paramCode) {this.paramCode = paramCode;}

	public String getParamName() {return paramName;}

	public void setParamName(String paramName) {this.paramName = paramName;}

	public Integer getParamType() {return paramType;}

	public void setParamType(Integer paramType) {this.paramType = paramType;}

	public String getParamValue() {return paramValue;}

	public void setParamValue(String paramValue) {this.paramValue = paramValue;}

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