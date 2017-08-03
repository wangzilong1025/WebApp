/**
 * $Id: Response.java,v 1.0 2015/7/17 17:26 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.response;

import com.sandi.web.utils.common.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author zhangrp
 * @version $Id: Response.java,v 1.1 2015/7/17 17:26 zhangrp Exp $
 *          Created on 2015/7/17 17:26
 */
public class Response implements Serializable {

    protected static final Logger logger = LoggerFactory.getLogger(Response.class);
    public static final String SUCCESS = "0000";//成功
    public static final String ERROR = "0001";//业务异常
    public static final String NO_FOUND_USER = "0002";//用户未登陆
    public static final String SYS_ERROR = "0003";//系统异常

    private ErrorInfo errorInfo;
    private Object data;

    public Response() {
        errorInfo = new ErrorInfo();
        errorInfo.setCode(Response.SUCCESS);
        errorInfo.setMessage("");
        data = null;
    }

    public String toString() {
        try {
            return JsonUtil.object2Json(this);
        } catch (IOException e) {
            logger.error("", e);
        }
        return "";
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public void setErrorInfo(String code, String message){
        this.errorInfo = new ErrorInfo();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        if (data != null && data instanceof String) {
            try {
                data = JsonUtil.json2Map((String) data);
            } catch (Exception e) {
                try {
                    data = JsonUtil.json2List((String) data);
                } catch (Exception e1) {
                    logger.warn("返回值非JSONObject和JSONArray格式！不转换！[" + data + "]");
                }
            }
        }
        this.data = data;
    }

    public void setMessage(String message) {
        this.errorInfo.setMessage(message);
    }

    public void setCode(String code) {
        this.errorInfo.setCode(code);
    }


}
