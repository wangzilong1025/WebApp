package com.sandi.web.common.split.service.impl;


import com.sandi.web.common.split.service.interfaces.ISplitTableConvertSV;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dizl on 2015/6/3.
 * 按照年月进行分表，例如：tableName_201506
 */
public class YYYYMMConvertSVImpl implements ISplitTableConvertSV {

    @Override
    public String convert(Object value) throws Exception {
        String rtn = null;
        if (value != null && value instanceof Date) {
            DateFormat objDateFormat = new SimpleDateFormat("yyyyMM");
            rtn = objDateFormat.format(value);
        }
        return rtn;
    }
}
