package com.sandi.web.common.dync.entity;


import com.sandi.web.common.persistence.annotation.Column;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;
import java.util.List;

public class CfgDyncPageAreaEntity extends BaseEntity {
	@Id
	private Long relatId;//主键

	private Long pageId;//页面编号

	private Long areaId;//域编号

	private Integer sortId;//排序

	private Integer isShowTitle;//是否显示标题

	private Integer isEditable;//是否可编辑0不能1能

	private Integer isDisplay;//是否展示0不能1能

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getRelatId() {return relatId;}

	public void setRelatId(Long relatId) {this.relatId = relatId;}

	public Long getPageId() {return pageId;}

	public void setPageId(Long pageId) {this.pageId = pageId;}

	public Long getAreaId() {return areaId;}

	public void setAreaId(Long areaId) {this.areaId = areaId;}

	public Integer getSortId() {return sortId;}

	public void setSortId(Integer sortId) {this.sortId = sortId;}

	public Integer getIsShowTitle() {return isShowTitle;}

	public void setIsShowTitle(Integer isShowTitle) {this.isShowTitle = isShowTitle;}

	public Integer getIsEditable() {return isEditable;}

	public void setIsEditable(Integer isEditable) {this.isEditable = isEditable;}

	public Integer getIsDisplay() {return isDisplay;}

	public void setIsDisplay(Integer isDisplay) {this.isDisplay = isDisplay;}

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



	@Column(isColumn = false)
	private CfgDyncAreaEntity cfgDyncAreaEntity;//域


	public CfgDyncAreaEntity getCfgDyncAreaEntity() {
		return cfgDyncAreaEntity;
	}
	public void setCfgDyncAreaEntity(CfgDyncAreaEntity cfgDyncAreaEntity) {
		this.cfgDyncAreaEntity = cfgDyncAreaEntity;
	}
}