package com.sandi.web.common.dync.entity;

import com.sandi.web.common.persistence.annotation.Column;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;
import java.util.List;

public class CfgDyncPageEntity extends BaseEntity {
	@Id
	private Long pageId;//页面编号

	private Integer pageType;//1配置模式2iframe3native

	private String pageName;//页面名称

	private Long rulesetId;//页面规则集

	private Long buttonsetId;//页面按钮集

	private String pageUrl;//页面地址

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getPageId() {return pageId;}

	public void setPageId(Long pageId) {this.pageId = pageId;}

	public Integer getPageType() {return pageType;}

	public void setPageType(Integer pageType) {this.pageType = pageType;}

	public String getPageName() {return pageName;}

	public void setPageName(String pageName) {this.pageName = pageName;}

	public Long getRulesetId() {return rulesetId;}

	public void setRulesetId(Long rulesetId) {this.rulesetId = rulesetId;}

	public Long getButtonsetId() {return buttonsetId;}

	public void setButtonsetId(Long buttonsetId) {this.buttonsetId = buttonsetId;}

	public String getPageUrl() {return pageUrl;}

	public void setPageUrl(String pageUrl) {this.pageUrl = pageUrl;}

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
	private List<CfgDyncRuleEntity> cfgDyncRuleEntities;//页面包含规则

	@Column(isColumn = false)
	private List<CfgDyncButtonEntity> cfgDyncButtonEntities;//页面包含按钮

	@Column(isColumn = false)
	private List<CfgDyncPageAreaEntity> cfgDyncPageAreaEntities;//页面包含的域


	public List<CfgDyncPageAreaEntity> getCfgDyncPageAreaEntities() {
		return cfgDyncPageAreaEntities;
	}

	public void setCfgDyncPageAreaEntities(List<CfgDyncPageAreaEntity> cfgDyncPageAreaEntities) {
		this.cfgDyncPageAreaEntities = cfgDyncPageAreaEntities;
	}



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



}