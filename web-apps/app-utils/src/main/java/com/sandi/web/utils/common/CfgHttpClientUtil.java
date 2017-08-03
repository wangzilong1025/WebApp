/**
 * $Id: CfgHttpClientUtil.java,v 1.0 2016/8/26 8:39 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.common;

import com.sandi.web.utils.http.entity.CfgHttpClient;
import com.sandi.web.utils.http.entity.CfgWsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: CfgHttpClientUtil.java,v 1.1 2016/8/26 8:39 zhangrp Exp $
 *          Created on 2016/8/26 8:39
 */
public class CfgHttpClientUtil {

    protected static final Logger logger = LoggerFactory.getLogger(CfgHttpClientUtil.class);
    private static final String httpClientCode = "HttpClientCache";
    private static final String wsClientCode = "WsClientCache";

    public static CfgHttpClient getCfgHttpClientByBusiCode(String busiCode) {
        try {
            CfgHttpClient rtn = null;
            if(JedisUtils.exists(httpClientCode)) {
                Object obj = JedisUtils.getObject(httpClientCode);
                if (obj != null) {
                    Map tempMap = (Map<String, Object>) obj;
                    if (tempMap.containsKey(busiCode)) {
                        rtn = (CfgHttpClient) tempMap.get(busiCode);
                    }
                }
            }
            if (rtn == null) {
                logger.error((new StringBuilder()).append("根据cfg_http_client编码:").append(busiCode).append(",无法获得对应的数据").toString());
            } else {
                return rtn;
            }
        } catch (Exception ex) {
            logger.error((new StringBuilder()).append("CFG_HTTP_CLIENT表根据CFG_HTTP_CLIENT_CODE编码:").append(busiCode).append(",无法获得对应的数据").toString(), ex);
        }
        return null;
    }

    public static CfgWsClient getCfgWsClientByBusiCode(String busiCode){
        try {
            CfgWsClient rtn = null;
            if(JedisUtils.exists(wsClientCode)) {
                Object obj = JedisUtils.getObject(wsClientCode);
                if (obj != null) {
                    Map tempMap = (Map<String, Object>) obj;
                    if (tempMap.containsKey(busiCode)) {
                        rtn = (CfgWsClient) tempMap.get(busiCode);
                    }
                }
            }
            if (rtn == null) {
                logger.error((new StringBuilder()).append("根据cfg_ws_client编码:").append(busiCode).append(",无法获得对应的数据").toString());
            } else {
                return rtn;
            }
        } catch (Exception ex) {
            logger.error((new StringBuilder()).append("cfg_ws_client表根据cfg_ws_client_code编码:").append(busiCode).append(",无法获得对应的数据").toString(), ex);
        }
        return null;
    }


}
