package com.sandi.web.common.dync.entity;

import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class CfgDyncButtonEntity extends BaseEntity {
	@Id
	private Long buttonId;//按钮编号

	private String buttonText;//按钮显示名称

	private Integer fileType;//文件类型：1文件路径，2：文件内容

	private String fileName;//点击事件方法所在文件名

	private String fileContent;//文件内容

	private String clickFunc;//点击事件方法

	private String buttonClass;//按钮样式

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getButtonId() {return buttonId;}

	public void setButtonId(Long buttonId) {this.buttonId = buttonId;}

	public String getButtonText() {return buttonText;}

	public void setButtonText(String buttonText) {this.buttonText = buttonText;}

	public String getFileName() {return fileName;}

	public void setFileName(String fileName) {this.fileName = fileName;}

	public String getClickFunc() {return clickFunc;}

	public void setClickFunc(String clickFunc) {this.clickFunc = clickFunc;}

	public String getButtonClass() {return buttonClass;}

	public void setButtonClass(String buttonClass) {this.buttonClass = buttonClass;}

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

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
}