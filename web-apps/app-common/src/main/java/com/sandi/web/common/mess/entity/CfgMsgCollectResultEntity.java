package com.sandi.web.common.mess.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgMsgCollectResultEntity extends BaseEntity {
	@Id
	private Long resultId;//null

	private Long busiMsgId;//null

	private String pkNbr;//null

	private String pkValue;//null

	private Integer state;//null

	private Date createDate;//null

	private Long creator;//null

	public Long getResultId() {return resultId;}

	public void setResultId(Long resultId) {this.resultId = resultId;}

	public Long getBusiMsgId() {return busiMsgId;}

	public void setBusiMsgId(Long busiMsgId) {this.busiMsgId = busiMsgId;}

	public String getPkNbr() {return pkNbr;}

	public void setPkNbr(String pkNbr) {this.pkNbr = pkNbr;}

	public String getPkValue() {return pkValue;}

	public void setPkValue(String pkValue) {this.pkValue = pkValue;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}


}