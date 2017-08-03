/**
 * $Id: CfgSrvBaseCache.java,v 1.0 2016/8/18 16:16 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.osdi.cache;

import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.common.osdi.dao.ICfgSrvBaseDao;
import com.sandi.web.common.osdi.entity.CfgSrvBaseEntity;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: CfgSrvBaseCache.java,v 1.1 2016/8/18 16:16 zhangrp Exp $
 *          Created on 2016/8/18 16:16
 */
public class CfgSrvBaseCache extends DefaultCache {

    protected static final Logger logger = LoggerFactory.getLogger(CfgSrvBaseCache.class);


    @Override
    protected Map getData() throws Exception {
        logger.info("load CfgSrvBaseCache ...");


        HashMap<String, String> retMap = new HashMap<String, String>();
        ICfgSrvBaseDao cfgFunSrvDao = SpringContextHolder.getBean(ICfgSrvBaseDao.class);

        List<CfgSrvBaseEntity> cfgSrvBaseEntityList = cfgFunSrvDao.findByEntity(new CfgSrvBaseEntity());
        for (CfgSrvBaseEntity cfgSrvBaseEntity : cfgSrvBaseEntityList) {
            if (cfgSrvBaseEntity.getSrvId() != null) {
                if (retMap.get(cfgSrvBaseEntity.getSrvId()) == null) {
                    retMap.put(cfgSrvBaseEntity.getSrvId(), JsonUtil.object2Json(cfgSrvBaseEntity));
                }
            }
        }
        return retMap;
    }
}

