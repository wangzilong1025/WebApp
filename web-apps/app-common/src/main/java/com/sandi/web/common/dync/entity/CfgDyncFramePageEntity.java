package com.sandi.web.common.dync.entity;


import com.sandi.web.common.persistence.annotation.Column;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;
import java.util.List;

public class CfgDyncFramePageEntity extends BaseEntity {
	@Id
	private Long relatId;//主键

	private Long frameId;//框架编号

	private Long pageId;//页面编号

	private String pageTitle;//页面标题

	private String pageCode;//页面编码

	private Integer isDisplay;//页面是否展示1展示0不展示

	private Long rulesetId;//页面规则集

	private Integer sortId;//排序

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getRelatId() {return relatId;}

	public void setRelatId(Long relatId) {this.relatId = relatId;}

	public Long getFrameId() {return frameId;}

	public void setFrameId(Long frameId) {this.frameId = frameId;}

	public Long getPageId() {return pageId;}

	public void setPageId(Long pageId) {this.pageId = pageId;}

	public String getPageTitle() {return pageTitle;}

	public void setPageTitle(String pageTitle) {this.pageTitle = pageTitle;}

	public String getPageCode() {return pageCode;}

	public void setPageCode(String pageCode) {this.pageCode = pageCode;}

	public Integer getIsDisplay() {return isDisplay;}

	public void setIsDisplay(Integer isDisplay) {this.isDisplay = isDisplay;}

	public Long getRulesetId() {return rulesetId;}

	public void setRulesetId(Long rulesetId) {this.rulesetId = rulesetId;}

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

	@Column(isColumn = false)
	private List<CfgDyncRuleEntity> cfgDyncRuleEntities;//规则集规则

	@Column(isColumn = false)
	private CfgDyncPageEntity cfgDyncPageEntity;//页面


	public CfgDyncPageEntity getCfgDyncPageEntity() {
		return cfgDyncPageEntity;
	}

	public void setCfgDyncPageEntity(CfgDyncPageEntity cfgDyncPageEntity) {
		this.cfgDyncPageEntity = cfgDyncPageEntity;
	}

	public List<CfgDyncRuleEntity> getCfgDyncRuleEntities() {
		return cfgDyncRuleEntities;
	}

	public void setCfgDyncRuleEntities(List<CfgDyncRuleEntity> cfgDyncRuleEntities) {
		this.cfgDyncRuleEntities = cfgDyncRuleEntities;
	}



}