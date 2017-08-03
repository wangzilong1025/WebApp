/**
 * $Id: CfgWsClient.java,v 1.0 2016/9/7 15:48 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.http.entity;

/**
 * @author dizl
 * @version $Id: CfgWsClient.java,v 1.1 2016/9/7 15:48 dizl Exp $
 * Created on 2016/9/7 15:48
 */
public class CfgWsClient  implements java.io.Serializable{
    private String cfgWsClientCode;
    private String urlAddress;
    private String methodName;
    private String methodParameter;
    private String methodReturnType;
    private String registerTypeMapping;
    private Integer timeoutSeconds;
    private String operationStyle;
    private String operationUse;
    private String handlerClass;
    private Integer state;
    private String remark;

    public String getCfgWsClientCode() {
        return cfgWsClientCode;
    }

    public void setCfgWsClientCode(String cfgWsClientCode) {
        this.cfgWsClientCode = cfgWsClientCode;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodParameter() {
        return methodParameter;
    }

    public void setMethodParameter(String methodParameter) {
        this.methodParameter = methodParameter;
    }

    public String getMethodReturnType() {
        return methodReturnType;
    }

    public void setMethodReturnType(String methodReturnType) {
        this.methodReturnType = methodReturnType;
    }

    public String getRegisterTypeMapping() {
        return registerTypeMapping;
    }

    public void setRegisterTypeMapping(String registerTypeMapping) {
        this.registerTypeMapping = registerTypeMapping;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getOperationStyle() {
        return operationStyle;
    }

    public void setOperationStyle(String operationStyle) {
        this.operationStyle = operationStyle;
    }

    public String getOperationUse() {
        return operationUse;
    }

    public void setOperationUse(String operationUse) {
        this.operationUse = operationUse;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(String handlerClass) {
        this.handlerClass = handlerClass;
    }
}
