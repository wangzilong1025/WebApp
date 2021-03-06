package com.sandi.web.common.dync.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class CfgDyncButtonsetButtonEntity extends BaseEntity {
	@Id
	private Long relatId;//主键

	private Long buttonsetId;//按钮组编号

	private Long buttonId;//按钮编号

	private Integer sortId;//排序

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getRelatId() {return relatId;}

	public void setRelatId(Long relatId) {this.relatId = relatId;}

	public Long getButtonsetId() {return buttonsetId;}

	public void setButtonsetId(Long buttonsetId) {this.buttonsetId = buttonsetId;}

	public Long getButtonId() {return buttonId;}

	public void setButtonId(Long buttonId) {this.buttonId = buttonId;}

	public Integer getSortId() {return sortId;}

	public void setSortId(Integer sortId) {this.sortId = sortId;}

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