package com.sandi.web.common.dync.entity;

import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class CfgDyncPageTemplateEntity extends BaseEntity {
	@Id
	private Long templateId;//前台模板编号

	private String templateName;//前台模板名称

	private Integer templateType;//前台模板类型:1向导式2单页面式3tab式

	private String templateUrl;//前台模板访问地址

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getTemplateId() {return templateId;}

	public void setTemplateId(Long templateId) {this.templateId = templateId;}

	public String getTemplateName() {return templateName;}

	public void setTemplateName(String templateName) {this.templateName = templateName;}

	public Integer getTemplateType() {return templateType;}

	public void setTemplateType(Integer templateType) {this.templateType = templateType;}

	public String getTemplateUrl() {return templateUrl;}

	public void setTemplateUrl(String templateUrl) {this.templateUrl = templateUrl;}

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