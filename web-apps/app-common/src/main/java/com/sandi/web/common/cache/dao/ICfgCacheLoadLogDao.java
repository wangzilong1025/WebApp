package com.sandi.web.common.cache.dao;

import com.sandi.web.common.cache.entity.CfgCacheLoadLogEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

/**
 * Created by dizl on 2015/6/5.
 */
@Dao(CfgCacheLoadLogEntity.class)
public interface ICfgCacheLoadLogDao extends CrudDao<CfgCacheLoadLogEntity, Long> {
}
