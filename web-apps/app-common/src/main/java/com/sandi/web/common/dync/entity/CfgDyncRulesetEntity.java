package com.sandi.web.common.dync.entity;

import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class CfgDyncRulesetEntity extends BaseEntity {
	@Id
	private Long rulesetId;//规则集编号

	private String rulesetName;//规则集名称

	private Integer rulesetType;//规则集类型	1-frame规则	2-page规则	3-area规则	4-attr规则	5-button规则

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getRulesetId() {return rulesetId;}

	public void setRulesetId(Long rulesetId) {this.rulesetId = rulesetId;}

	public String getRulesetName() {return rulesetName;}

	public void setRulesetName(String rulesetName) {this.rulesetName = rulesetName;}

	public Integer getRulesetType() {return rulesetType;}

	public void setRulesetType(Integer rulesetType) {this.rulesetType = rulesetType;}

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