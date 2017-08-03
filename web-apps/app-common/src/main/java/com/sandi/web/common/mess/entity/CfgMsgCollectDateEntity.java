package com.sandi.web.common.mess.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

public class CfgMsgCollectDateEntity extends BaseEntity {
	@Id
	private Long busiMsgId;//null

	private Date extractDate;//null

	private String ext1;//null

	private Long ext2;//null

	public Long getBusiMsgId() {return busiMsgId;}

	public void setBusiMsgId(Long busiMsgId) {this.busiMsgId = busiMsgId;}

	public Date getExtractDate() {return extractDate;}

	public void setExtractDate(Date extractDate) {this.extractDate = extractDate;}

	public String getExt1() {return ext1;}

	public void setExt1(String ext1) {this.ext1 = ext1;}

	public Long getExt2() {return ext2;}

	public void setExt2(Long ext2) {this.ext2 = ext2;}

}