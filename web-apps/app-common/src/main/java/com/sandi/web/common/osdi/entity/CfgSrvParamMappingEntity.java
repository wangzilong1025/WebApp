package com.sandi.web.common.osdi.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

public class CfgSrvParamMappingEntity extends BaseEntity {
	@Id
	private String mappingId;//null

	private String busiId;//null

	private String srvId;//null

	private Integer paramType;//参数类型，1-入参，2-出参

	private String paramCode;//null

	private String paramName;//null

	private String srvParamCode;//null

	private String srvParamName;//null

	private String dataType;//null

	private Integer transitivity;//参数传递性,1-需要传递

	private Integer sort;//null

	private Integer state;//null

	public String getMappingId() {return mappingId;}

	public void setMappingId(String mappingId) {this.mappingId = mappingId;}

	public String getBusiId() {return busiId;}

	public void setBusiId(String busiId) {this.busiId = busiId;}

	public String getSrvId() {return srvId;}

	public void setSrvId(String srvId) {this.srvId = srvId;}

	public Integer getParamType() {return paramType;}

	public void setParamType(Integer paramType) {this.paramType = paramType;}

	public String getParamCode() {return paramCode;}

	public void setParamCode(String paramCode) {this.paramCode = paramCode;}

	public String getParamName() {return paramName;}

	public void setParamName(String paramName) {this.paramName = paramName;}

	public String getSrvParamCode() {return srvParamCode;}

	public void setSrvParamCode(String srvParamCode) {this.srvParamCode = srvParamCode;}

	public String getSrvParamName() {return srvParamName;}

	public void setSrvParamName(String srvParamName) {this.srvParamName = srvParamName;}

	public String getDataType() {return dataType;}

	public void setDataType(String dataType) {this.dataType = dataType;}

	public Integer getTransitivity() {return transitivity;}

	public void setTransitivity(Integer transitivity) {this.transitivity = transitivity;}

	public Integer getSort() {return sort;}

	public void setSort(Integer sort) {this.sort = sort;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

}