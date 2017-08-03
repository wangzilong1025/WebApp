package com.sandi.web.common.osdi.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

public class CfgBusiSrvRelEntity extends BaseEntity {
	@Id
	private String busiSrvRelId;//null

	private String busiId;//null

	private String srvId;//null

	private Integer srvType;//服务类型，1-dubbo,2-http,3-ws

	private Integer sort;//null

	private Integer state;//null

	public String getBusiSrvRelId() {return busiSrvRelId;}

	public void setBusiSrvRelId(String busiSrvRelId) {this.busiSrvRelId = busiSrvRelId;}

	public String getBusiId() {return busiId;}

	public void setBusiId(String busiId) {this.busiId = busiId;}

	public String getSrvId() {return srvId;}

	public void setSrvId(String srvId) {this.srvId = srvId;}

	public Integer getSrvType() {return srvType;}

	public void setSrvType(Integer srvType) {this.srvType = srvType;}

	public Integer getSort() {return sort;}

	public void setSort(Integer sort) {this.sort = sort;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

}