package com.sandi.web.utils.http.handler.sh;

//import org.codehaus.jackson.annotate.JsonIgnore;
//import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Class: ReqParamBean
 * @Description:请求报文
 * @version: v1.0.0
 * @author: LiuQ
 * @date: 2013-11-26 下午2:25:06
 * <p/>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2013-11-26     LiuQ           v1.0.0
 */
class ReqParamBean {
	/*公共入参*/
	@JsonProperty("PubInfo")
	private PubInfoBean PubInfo;
	/*业务入参*/
	@JsonProperty("Request")
	private RequestBean Request;

	@JsonIgnore
	public RequestBean getRequest() {
		return Request;
	}

	@JsonIgnore
	public void setRequest(RequestBean request) {
		Request = request;
	}

	@JsonIgnore
	public PubInfoBean getPubInfo() {
		return PubInfo;
	}

	@JsonIgnore
	public void setPubInfo(PubInfoBean pubInfo) {
		PubInfo = pubInfo;
	}
}
