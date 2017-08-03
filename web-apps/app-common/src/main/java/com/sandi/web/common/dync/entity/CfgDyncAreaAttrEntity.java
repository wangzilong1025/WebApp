package com.sandi.web.common.dync.entity;

import com.sandi.web.common.persistence.annotation.Column;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;
import java.util.List;

public class CfgDyncAreaAttrEntity extends BaseEntity {
	@Id
	private Long relatId;//主键

	private Long areaId;//域编号

	private Long attrId;//属性编号

	private Integer rowSpan;//行

	private Integer colSpan;//列

	private Long rulesetId;//属性规则集编号

	private String defaultValue;//属性默认值

	private Integer isNullable;//是否能为空0不能1能

	private Integer isVisible;//是否可见0不能1能

	private Integer isEditable;//是否可编辑0不能1能

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	private Integer sortId;

	public Long getRelatId() {return relatId;}

	public void setRelatId(Long relatId) {this.relatId = relatId;}

	public Long getAreaId() {return areaId;}

	public void setAreaId(Long areaId) {this.areaId = areaId;}

	public Long getAttrId() {return attrId;}

	public void setAttrId(Long attrId) {this.attrId = attrId;}

	public Integer getRowSpan() {return rowSpan;}

	public void setRowSpan(Integer rowSpan) {this.rowSpan = rowSpan;}

	public Integer getColSpan() {return colSpan;}

	public void setColSpan(Integer colSpan) {this.colSpan = colSpan;}

	public Long getRulesetId() {return rulesetId;}

	public void setRulesetId(Long rulesetId) {this.rulesetId = rulesetId;}

	public String getDefaultValue() {return defaultValue;}

	public void setDefaultValue(String defaultValue) {this.defaultValue = defaultValue;}

	public Integer getIsNullable() {return isNullable;}

	public void setIsNullable(Integer isNullable) {this.isNullable = isNullable;}

	public Integer getIsVisible() {return isVisible;}

	public void setIsVisible(Integer isVisible) {this.isVisible = isVisible;}

	public Integer getIsEditable() {return isEditable;}

	public void setIsEditable(Integer isEditable) {this.isEditable = isEditable;}

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



	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}


	@Column(isColumn = false)
	private CfgDyncAttrEntity cfgDyncAttrEntity;//域包含的属性

	@Column(isColumn = false)
	private List<CfgDyncRuleEntity> cfgDyncRuleEntities;//域包含的规则

	public CfgDyncAttrEntity getCfgDyncAttrEntity() {
		return cfgDyncAttrEntity;
	}

	public void setCfgDyncAttrEntity(CfgDyncAttrEntity cfgDyncAttrEntity) {
		this.cfgDyncAttrEntity = cfgDyncAttrEntity;
	}

	public List<CfgDyncRuleEntity> getCfgDyncRuleEntities() {
		return cfgDyncRuleEntities;
	}

	public void setCfgDyncRuleEntities(List<CfgDyncRuleEntity> cfgDyncRuleEntities) {
		this.cfgDyncRuleEntities = cfgDyncRuleEntities;
	}
}