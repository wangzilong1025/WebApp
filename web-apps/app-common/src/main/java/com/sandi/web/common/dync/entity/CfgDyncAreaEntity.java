package com.sandi.web.common.dync.entity;

import com.sandi.web.common.persistence.annotation.Column;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;
import java.util.List;

public class CfgDyncAreaEntity extends BaseEntity {
	@Id
	private Long areaId;//页面域编号

	private String areaName;//域名称

	private String areaType;//域类型	form,table,template

	private Long rulesetId;//规则集编号

	private Long buttonsetId;//按钮组编号

	private String areaCode;//域编码

	private String initSrvId;//初始化服务

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getAreaId() {return areaId;}

	public void setAreaId(Long areaId) {this.areaId = areaId;}

	public String getAreaName() {return areaName;}

	public void setAreaName(String areaName) {this.areaName = areaName;}

	public String getAreaType() {return areaType;}

	public void setAreaType(String areaType) {this.areaType = areaType;}

	public Long getRulesetId() {return rulesetId;}

	public void setRulesetId(Long rulesetId) {this.rulesetId = rulesetId;}

	public Long getButtonsetId() {return buttonsetId;}

	public void setButtonsetId(Long buttonsetId) {this.buttonsetId = buttonsetId;}

	public String getAreaCode() {return areaCode;}

	public void setAreaCode(String areaCode) {this.areaCode = areaCode;}

	public String getInitSrvId() {return initSrvId;}

	public void setInitSrvId(String initSrvId) {this.initSrvId = initSrvId;}

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
	private List<CfgDyncRuleEntity> cfgDyncRuleEntities;//规则

	@Column(isColumn = false)
	private List<CfgDyncButtonEntity> cfgDyncButtonEntities;//按钮

	@Column(isColumn = false)
	private List<CfgDyncAreaAttrEntity> cfgDyncAreaAttrEntities;//域包含的属性



	public List<CfgDyncRuleEntity> getCfgDyncRuleEntities() {
		return cfgDyncRuleEntities;
	}

	public void setCfgDyncRuleEntities(List<CfgDyncRuleEntity> cfgDyncRuleEntities) {
		this.cfgDyncRuleEntities = cfgDyncRuleEntities;
	}

	public List<CfgDyncButtonEntity> getCfgDyncButtonEntities() {
		return cfgDyncButtonEntities;
	}

	public void setCfgDyncButtonEntities(List<CfgDyncButtonEntity> cfgDyncButtonEntities) {
		this.cfgDyncButtonEntities = cfgDyncButtonEntities;
	}

	public List<CfgDyncAreaAttrEntity> getCfgDyncAreaAttrEntities() {
		return cfgDyncAreaAttrEntities;
	}

	public void setCfgDyncAreaAttrEntities(List<CfgDyncAreaAttrEntity> cfgDyncAreaAttrEntities) {
		this.cfgDyncAreaAttrEntities = cfgDyncAreaAttrEntities;
	}



}