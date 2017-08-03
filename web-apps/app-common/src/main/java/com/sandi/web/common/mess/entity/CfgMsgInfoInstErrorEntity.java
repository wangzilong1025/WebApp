package com.sandi.web.common.mess.entity;



import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgMsgInfoInstErrorEntity extends BaseEntity {
	private String pkValue;//null

	private String sendObjVal;//null

	private Integer sendType;//1、一次性发送、2、固定时间；3、周期发送

	private String sendRate;//

	private Date sendDate;//null

	private Integer sendMaxCount;//null

	private Integer sendCount;//三次发送机会

	private Date msgExpireDate;//null

	private String errorMsg;//null

	private Date doneDate;//null

	private Integer moduleId;//null

	private Integer state;//null

	private Long creator;//null

	private Date createDate;//null

	private Long msgInfoId;//null

	private Long busiMsgId;//null

	private Long msgId;//null

	private String msgType;//1、短信；2、邮件；3、网页消息

	private String msgTitle;//null

	private String msgText;//null

	private Long userId;//null

	public String getPkValue() {return pkValue;}

	public void setPkValue(String pkValue) {this.pkValue = pkValue;}

	public String getSendObjVal() {return sendObjVal;}

	public void setSendObjVal(String sendObjVal) {this.sendObjVal = sendObjVal;}

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

	public Date getSendDate() {return sendDate;}

	public void setSendDate(Date sendDate) {this.sendDate = sendDate;}

	public Integer getSendMaxCount() {return sendMaxCount;}

	public void setSendMaxCount(Integer sendMaxCount) {this.sendMaxCount = sendMaxCount;}

	public Integer getSendCount() {return sendCount;}

	public void setSendCount(Integer sendCount) {this.sendCount = sendCount;}

	public Date getMsgExpireDate() {return msgExpireDate;}

	public void setMsgExpireDate(Date msgExpireDate) {this.msgExpireDate = msgExpireDate;}

	public String getErrorMsg() {return errorMsg;}

	public void setErrorMsg(String errorMsg) {this.errorMsg = errorMsg;}

	public Date getDoneDate() {return doneDate;}

	public void setDoneDate(Date doneDate) {this.doneDate = doneDate;}

	public Integer getModuleId() {return moduleId;}

	public void setModuleId(Integer moduleId) {this.moduleId = moduleId;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Long getMsgInfoId() {return msgInfoId;}

	public void setMsgInfoId(Long msgInfoId) {this.msgInfoId = msgInfoId;}

	public Long getBusiMsgId() {return busiMsgId;}

	public void setBusiMsgId(Long busiMsgId) {this.busiMsgId = busiMsgId;}

	public Long getMsgId() {return msgId;}

	public void setMsgId(Long msgId) {this.msgId = msgId;}

	public String getMsgType() {return msgType;}

	public void setMsgType(String msgType) {this.msgType = msgType;}

	public String getMsgTitle() {return msgTitle;}

	public void setMsgTitle(String msgTitle) {this.msgTitle = msgTitle;}

	public String getMsgText() {return msgText;}

	public void setMsgText(String msgText) {this.msgText = msgText;}

	public Long getUserId() {return userId;}

	public void setUserId(Long userId) {this.userId = userId;}

}