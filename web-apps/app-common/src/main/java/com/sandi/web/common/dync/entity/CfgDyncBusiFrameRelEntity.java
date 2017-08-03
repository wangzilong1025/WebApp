package com.sandi.web.common.dync.entity;

import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class CfgDyncBusiFrameRelEntity extends BaseEntity {
	@Id
	private Long busiFrameId;//关联的业务框架编号

	private String busiId;//业务编号，具体指某一类业务

	private String operateId;//业务下面的具体的某一类操作编号

	private Long pageTemplateId;//业务框架使用的模板

	private Integer moduleId;//数据操作来源	1-PC	2-APP

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getBusiFrameId() {return busiFrameId;}

	public void setBusiFrameId(Long busiFrameId) {this.busiFrameId = busiFrameId;}

	public String getBusiId() {return busiId;}

	public void setBusiId(String busiId) {this.busiId = busiId;}

	public String getOperateId() {return operateId;}

	public void setOperateId(String operateId) {this.operateId = operateId;}

	public Long getPageTemplateId() {return pageTemplateId;}

	public void setPageTemplateId(Long pageTemplateId) {this.pageTemplateId = pageTemplateId;}

	public Integer getModuleId() {return moduleId;}

	public void setModuleId(Integer moduleId) {this.moduleId = moduleId;}

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