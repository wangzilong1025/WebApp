package com.sandi.web.common.cache.service.interfaces;

import com.sandi.web.common.cache.entity.CfgCacheLoadEntity;
import java.util.List;

/**
 * Created by dizl on 2015/6/5.
 */
public interface ICfgCacheLoadSV {
    public CfgCacheLoadEntity getCfgCacheLoadEntity(String cacheName) throws Exception;

    public List<CfgCacheLoadEntity> getAllCfgCacheLoadEntity() throws Exception;
}
