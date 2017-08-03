package com.sandi.web.common.cache.service.impl;

import com.sandi.web.common.cache.dao.ICfgCacheLoadLogDao;
import com.sandi.web.common.cache.entity.CfgCacheLoadLogEntity;
import com.sandi.web.common.cache.service.interfaces.ICfgCacheLoadLogSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dizl on 2015/6/5.
 */
@Service
public class CfgCacheLoadLogSVImpl implements ICfgCacheLoadLogSV {

    @Autowired
    private ICfgCacheLoadLogDao cacheLoadLogDao;

    public void saveEntity(CfgCacheLoadLogEntity entity) throws Exception {
        cacheLoadLogDao.save(entity);
    }
}
