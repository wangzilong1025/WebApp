/*
 * $Id: RequestInfo.java,v 1.0 2015年7月24日 上午10:13:11 zhangrp Exp $
 *
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.request;

import org.apache.commons.lang3.StringEscapeUtils;
import java.io.Serializable;
import java.util.*;

/**
 * @author zhangrp
 * @version $Id: RequestInfo.java v 1.0 Exp $
 *          Created on 2015年7月24日 上午10:13:11
 */
public class RequestInfo implements Serializable {
    private LinkedHashMap busiParams;
    private String busiCode;
    private String moduleName;

    private boolean forTest = false;

    public LinkedHashMap getBusiParams() {
        return busiParams;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Map getEscapeBusiParams() {
        return escapeMap(busiParams);
    }

    public void setBusiParams(LinkedHashMap busiParams) {
        this.busiParams = busiParams;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public boolean isForTest() {
        return forTest;
    }

    public void setForTest(boolean forTest) {
        this.forTest = forTest;
    }


    /**
     * 用户特殊字符过滤
     *
     * @param source
     * @return
     */
    public Map escapeMap(Map source) {
        Map map = new HashMap();
        Set keySet = source.keySet();
        Iterator keyIterator = keySet.iterator();
        while (keyIterator.hasNext()) {
            Object key = keyIterator.next();
            if (source.get(key) instanceof Map) {
                map.put(key, escapeMap((Map) source.get(key)));
            } else if (source.get(key) instanceof List) {
                List list = new ArrayList();
                for (Object temp : (List) source.get(key)) {
                    if (temp instanceof Map) {
                        list.add(escapeMap((Map) temp));
                    } else if (temp != null) {
                        list.add(escapeHtml4(temp.toString()));
                    }
                }
                map.put(key, list);
            } else if (source.get(key) != null) {
                map.put(key, escapeHtml4(source.get(key).toString()));
            }
        }
        return map;
    }

    private String escapeHtml4(String str) {
        if (str == null) {
            return null;
        }
        return StringEscapeUtils.escapeHtml4(str);
    }
}
