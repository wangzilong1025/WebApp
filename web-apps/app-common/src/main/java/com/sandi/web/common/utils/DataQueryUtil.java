/**
 * $Id: DataQueryUtil.java,v 1.0 2016/9/22 16:44 Administrator Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.utils;

import com.sandi.web.common.persistence.dao.CommonDao;
import com.sandi.web.utils.common.SpringContextHolder;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liuqin
 * @version $Id: DataQueryUtil.java,v 1.1 2016/9/22 16:44 liuqin Exp $
 * Created on 2016/9/22 16:44
 */
public class DataQueryUtil {
    public static List<Map> getDataBySql(String sql,Map params) throws Exception{

        CommonDao commonDao = SpringContextHolder.getBean(CommonDao.class);
        return commonDao.findBySql(sql,params);
    }
    public static Date getSysDate() throws Exception{

        CommonDao commonDao = SpringContextHolder.getBean(CommonDao.class);
        return commonDao.getSysDate();
    }
}

