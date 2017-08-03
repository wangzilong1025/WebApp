package com.sandi.web.common.dync.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class CfgDyncRuleExpEntity extends BaseEntity {
	@Id
	private Long expId;//表达式编号

	private String expText;//表达式内容

	private String expName;//表达式名称

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getExpId() {return expId;}

	public void setExpId(Long expId) {this.expId = expId;}

	public String getExpText() {return expText;}

	public void setExpText(String expText) {this.expText = expText;}

	public String getExpName() {return expName;}

	public void setExpName(String expName) {this.expName = expName;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public String getRemark() {return remark;}

	public void setRemark(String remark) {this.remark = remark;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Date getDoneDate() {return doneDate;}

	public void setDoneDate(Date doneDate) {this.doneDate = doneDate;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

	public Long getOpId() {return opId;}

	public void setOpId(Long opId) {this.opId = opId;}

}