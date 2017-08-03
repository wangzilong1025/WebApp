package com.sandi.web.common.cache.service.impl;

import com.sandi.web.common.cache.dao.ICfgCacheLoadDao;
import com.sandi.web.common.cache.entity.CfgCacheLoadEntity;
import com.sandi.web.common.cache.service.interfaces.ICfgCacheLoadSV;
import com.sandi.web.common.utils.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by dizl on 2015/6/5.
 */
@Service
public class CfgCacheLoadSVImpl implements ICfgCacheLoadSV {
    @Autowired
    private ICfgCacheLoadDao cfgCacheLoadDao;

    public CfgCacheLoadEntity getCfgCacheLoadEntity(String cacheName) throws Exception {
        CfgCacheLoadEntity entity = cfgCacheLoadDao.findById(cacheName);
        return entity;
    }

    public List<CfgCacheLoadEntity> getAllCfgCacheLoadEntity() throws Exception {
        CfgCacheLoadEntity entity = new CfgCacheLoadEntity();
        entity.setState(CommConstants.State.STATE_NORMAL);

        return cfgCacheLoadDao.findByEntity(entity);
    }

}
