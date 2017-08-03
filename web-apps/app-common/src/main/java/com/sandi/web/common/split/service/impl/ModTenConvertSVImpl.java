/**
 * $Id: ModTenConvertSVImpl.java,v 1.0 2016/3/15 10:33 zhangrp Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.split.service.impl;


import com.sandi.web.common.split.service.interfaces.ISplitTableConvertSV;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangrp
 * @version $Id: ModTenConvertSVImpl.java,v 1.1 2016/3/15 10:33 zhangrp Exp $
 *          Created on 2016/3/15 10:33
 */
public class ModTenConvertSVImpl implements ISplitTableConvertSV {

    @Override
    public String convert(Object value) throws Exception {
        String rtn = null;
        if (value != null && (value instanceof Long || value instanceof Integer || value instanceof String)) {
            if (value instanceof Long) {
                rtn = "" + ((long) value) % 10;
            } else if (value instanceof Integer) {
                rtn = "" + ((int) value) % 10;
            } else if (value instanceof String) {
                //begin 字符串的最后一位分表 modify by RaoXb 2016/09/22
                int strLength = ((String) value).length();
                String lastCharater = ((String) value).substring(strLength-1,strLength);
                rtn = "" + (Integer.parseInt(lastCharater)) % 10;
                //end 字符串的最后一位分表 modify by RaoXb 2016/09/22
            }
        }
        return rtn;
    }
}
