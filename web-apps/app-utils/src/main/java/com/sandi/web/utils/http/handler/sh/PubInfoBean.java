package com.sandi.web.utils.http.handler.sh;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * @Class: PubInfoBean
 * @Description: 公共业务测试Bean
 * @version: v1.0.0
 * @author: LiuQ
 * @date: 2013-11-26 下午2:34:29
 * <p/>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2013-11-26     LiuQ           v1.0.0
 */
class PubInfoBean {
	@JsonProperty("ClientIP")
	private String ClientIP;

	@JsonProperty("TransactionId")
	private String TransactionId;

	@JsonProperty("InterfaceId")
	private String InterfaceId;

	@JsonProperty("InterfaceType")
	private String InterfaceType;

	@JsonProperty("OpId")
	private String OpId;

	@JsonProperty("CountyCode")
	private String CountyCode;

	@JsonProperty("OrgId")
	private String OrgId;

	@JsonProperty("TransactionTime")
	private String TransactionTime;

	@JsonProperty("RegionCode")
	private String RegionCode;

	@JsonProperty("EsopBusiCode")
	private String EsopBusiCode;

	@JsonIgnore
	public String getEsopBusiCode() {
		return EsopBusiCode;
	}

	@JsonIgnore
	public void setEsopBusiCode(String esopBusiCode) {EsopBusiCode = esopBusiCode; }

	@JsonIgnore
	public String getClientIP() {
		return ClientIP;
	}
	@JsonIgnore
	public void setClientIP(String clientIP) {
		ClientIP = clientIP;
	}

	@JsonIgnore
	public String getTransactionId() {
		return TransactionId;
	}

	@JsonIgnore
	public void setTransactionId(String transactionId) {
		TransactionId = transactionId;
	}

	@JsonIgnore
	public String getInterfaceId() {
		return InterfaceId;
	}

	@JsonIgnore
	public void setInterfaceId(String interfaceId) {
		InterfaceId = interfaceId;
	}

	@JsonIgnore
	public String getInterfaceType() {
		return InterfaceType;
	}

	@JsonIgnore
	public void setInterfaceType(String interfaceType) {
		InterfaceType = interfaceType;
	}

	@JsonIgnore
	public String getOpId() {
		return OpId;
	}

	@JsonIgnore
	public void setOpId(String opId) {
		OpId = opId;
	}

	@JsonIgnore

	public String getCountyCode() {
		return CountyCode;
	}

	@JsonIgnore
	public void setCountyCode(String countyCode) {
		CountyCode = countyCode;
	}

	@JsonIgnore
	public String getOrgId() {
		return OrgId;
	}

	@JsonIgnore
	public void setOrgId(String orgId) {
		OrgId = orgId;
	}

	@JsonIgnore
	public String getTransactionTime() {
		return TransactionTime;
	}

	@JsonIgnore
	public void setTransactionTime(String transactionTime) {
		TransactionTime = transactionTime;
	}

	@JsonIgnore
	public String getRegionCode() {
		return RegionCode;
	}

	@JsonIgnore
	public void setRegionCode(String regionCode) {
		RegionCode = regionCode;
	}
}
