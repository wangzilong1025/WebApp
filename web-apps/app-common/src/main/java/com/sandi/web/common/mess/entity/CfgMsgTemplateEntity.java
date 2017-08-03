package com.sandi.web.common.mess.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class CfgMsgTemplateEntity extends BaseEntity {
	@Id
	private Long tempId;//主键

	private Long msgId;//null

	private String msgName;//null

	private String msgText;//null

	private String msgType;//模板类型：SMS、短信；EMAIL、e-mail;WEB、网页消息；APP、APP消息、WEB-APP、web和APP消息

	private String msgIcon;//消息图标

	private Integer state;//null

	private Date createDate;//null

	private Long creator;//null

	public Long getTempId() {
		return tempId;
	}

	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}

	public Long getMsgId() {return msgId;}

	public void setMsgId(Long msgId) {this.msgId = msgId;}

	public String getMsgName() {return msgName;}

	public void setMsgName(String msgName) {this.msgName = msgName;}

	public String getMsgText() {return msgText;}

	public void setMsgText(String msgText) {this.msgText = msgText;}

	public String getMsgType() {return msgType;}

	public void setMsgType(String msgType) {this.msgType = msgType;}

	public String getMsgIcon() {
		return msgIcon;
	}

	public void setMsgIcon(String msgIcon) {
		this.msgIcon = msgIcon;
	}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

}