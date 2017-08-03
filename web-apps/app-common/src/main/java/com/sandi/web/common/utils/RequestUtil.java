/**
 * $Id: RequestUtil.java,v 1.0 2016/6/2 10:15 zhangrp Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.utils;


import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangrp
 * @version $Id: RequestUtil.java,v 1.1 2016/6/2 10:15 zhangrp Exp $
 *          Created on 2016/6/2 10:15
 */
public class RequestUtil {

    public static String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
