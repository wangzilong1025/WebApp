package com.sandi.web.common.mess.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgMsgSendObjEntity extends BaseEntity {
	@Id
	private Long busiMsgObjId;//null

	private Long busiMsgId;//null

	private String objType;//null

	private String objVal;//null

	private String sendObjDealClass;//null

	private String sendObjDealParam;//null

	private Integer state;//null

	private Long creator;//null

	private Date createDate;//null

	private String remark;//null

	public Long getBusiMsgObjId() {return busiMsgObjId;}

	public void setBusiMsgObjId(Long busiMsgObjId) {this.busiMsgObjId = busiMsgObjId;}

	public Long getBusiMsgId() {return busiMsgId;}

	public void setBusiMsgId(Long busiMsgId) {this.busiMsgId = busiMsgId;}

	public String getObjType() {return objType;}

	public void setObjType(String objType) {this.objType = objType;}

	public String getObjVal() {return objVal;}

	public void setObjVal(String objVal) {this.objVal = objVal;}

	public String getSendObjDealClass() {return sendObjDealClass;}

	public void setSendObjDealClass(String sendObjDealClass) {this.sendObjDealClass = sendObjDealClass;}

	public String getSendObjDealParam() {return sendObjDealParam;}

	public void setSendObjDealParam(String sendObjDealParam) {this.sendObjDealParam = sendObjDealParam;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public String getRemark() {return remark;}

	public void setRemark(String remark) {this.remark = remark;}

}