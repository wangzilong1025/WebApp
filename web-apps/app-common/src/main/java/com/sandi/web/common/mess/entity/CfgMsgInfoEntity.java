package com.sandi.web.common.mess.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgMsgInfoEntity extends BaseEntity {
	@Id
	private Long busiMsgId;//null

	private Long msgId;//null

	private String busiName;//null

	private String dataCollectClass;//null

	private String dataCollectParam;//null

	private String pkNbr;//null

	private Integer sendType;//1、一次性发送、2、固定时间；3、周期发送

	private String sendRate;

	private Integer sendMaxCount;//针对周期性发送

	private String sendDuration;//消息发送时长

	private String sendLevel;//null

	private Date effectiveDate;//null

	private Date expireDate;//null

	private String remark;//null

	private Integer moduleId;//null

	private Long creator;//null

	private Date createDate;//null

	private Integer state;//null

	public Long getBusiMsgId() {return busiMsgId;}

	public void setBusiMsgId(Long busiMsgId) {this.busiMsgId = busiMsgId;}

	public Long getMsgId() {return msgId;}

	public void setMsgId(Long msgId) {this.msgId = msgId;}

	public String getBusiName() {return busiName;}

	public void setBusiName(String busiName) {this.busiName = busiName;}

	public String getDataCollectClass() {return dataCollectClass;}

	public void setDataCollectClass(String dataCollectClass) {this.dataCollectClass = dataCollectClass;}

	public String getDataCollectParam() {return dataCollectParam;}

	public void setDataCollectParam(String dataCollectParam) {this.dataCollectParam = dataCollectParam;}

	public String getPkNbr() {return pkNbr;}

	public void setPkNbr(String pkNbr) {this.pkNbr = pkNbr;}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public String getSendRate() {
		return sendRate;
	}

	public void setSendRate(String sendRate) {
		this.sendRate = sendRate;
	}

	public Integer getSendMaxCount() {return sendMaxCount;}

	public void setSendMaxCount(Integer sendMaxCount) {this.sendMaxCount = sendMaxCount;}

	public String getSendLevel() {return sendLevel;}

	public void setSendLevel(String sendLevel) {this.sendLevel = sendLevel;}

	public Date getEffectiveDate() {return effectiveDate;}

	public void setEffectiveDate(Date effectiveDate) {this.effectiveDate = effectiveDate;}

	public Date getExpireDate() {return expireDate;}

	public void setExpireDate(Date expireDate) {this.expireDate = expireDate;}

	public String getRemark() {return remark;}

	public void setRemark(String remark) {this.remark = remark;}

	public Integer getModuleId() {return moduleId;}

	public void setModuleId(Integer moduleId) {this.moduleId = moduleId;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public String getSendDuration() {
		return sendDuration;
	}

	public void setSendDuration(String sendDuration) {
		this.sendDuration = sendDuration;
	}
}