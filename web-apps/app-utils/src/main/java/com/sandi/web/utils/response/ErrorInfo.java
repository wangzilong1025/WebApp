/*
 * $Id: ErrorInfo.java,v 1.0 2015年7月24日 上午10:13:53 zhangrp Exp $
 *
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.response;

import java.io.Serializable;

/**
 * @author zhangrp
 * @version $Id: ErrorInfo.java v 1.0 Exp $
 *          Created on 2015年7月24日 上午10:13:53
 */
public class ErrorInfo implements Serializable {
    private String message;
    private String code;

    public ErrorInfo() {

    }

    public ErrorInfo(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
