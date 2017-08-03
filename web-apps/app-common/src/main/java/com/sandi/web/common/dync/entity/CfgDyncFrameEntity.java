package com.sandi.web.common.dync.entity;


import com.sandi.web.common.persistence.annotation.Column;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;
import java.util.List;

public class CfgDyncFrameEntity extends BaseEntity {
	private Long versionId;//内部使用版本号

	private String version;//外部使用版本

	private Long buttonsetId;//框架按钮集

	private Long rulesetId;//框架规则集

	private Integer state;//状态:1在用0停止使用

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	@Id
	private Long frameId;//框架编号

	private Long busiFrameId;//业务框架编号


	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getButtonsetId() {return buttonsetId;}

	public void setButtonsetId(Long buttonsetId) {this.buttonsetId = buttonsetId;}

	public Long getRulesetId() {return rulesetId;}

	public void setRulesetId(Long rulesetId) {this.rulesetId = rulesetId;}

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

	public Long getFrameId() {return frameId;}

	public void setFrameId(Long frameId) {this.frameId = frameId;}

	public Long getBusiFrameId() {return busiFrameId;}

	public void setBusiFrameId(Long busiFrameId) {this.busiFrameId = busiFrameId;}

	public List<CfgDyncButtonEntity> getCfgDyncButtonEntities() {
		return cfgDyncButtonEntities;
	}

	public void setCfgDyncButtonEntities(List<CfgDyncButtonEntity> cfgDyncButtonEntities) {
		this.cfgDyncButtonEntities = cfgDyncButtonEntities;
	}

	public List<CfgDyncRuleEntity> getCfgDyncRuleEntities() {
		return cfgDyncRuleEntities;
	}

	public void setCfgDyncRuleEntities(List<CfgDyncRuleEntity> cfgDyncRuleEntities) {
		this.cfgDyncRuleEntities = cfgDyncRuleEntities;
	}

	@Column(isColumn = false)
	private List<CfgDyncButtonEntity> cfgDyncButtonEntities;//框架按钮组按钮

	@Column(isColumn = false)
	private List<CfgDyncRuleEntity> cfgDyncRuleEntities;//框架规则集规则

	public List<CfgDyncFramePageEntity> getCfgDyncFramePageEntities() {
		return cfgDyncFramePageEntities;
	}

	public void setCfgDyncFramePageEntities(List<CfgDyncFramePageEntity> cfgDyncFramePageEntities) {
		this.cfgDyncFramePageEntities = cfgDyncFramePageEntities;
	}

	@Column(isColumn = false)
	private List<CfgDyncFramePageEntity> cfgDyncFramePageEntities;//框架包含的页面

}