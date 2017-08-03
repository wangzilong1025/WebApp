package com.sandi.web.common.cache.dao;

import com.sandi.web.common.cache.entity.CfgCacheLoadEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

/**
 * Created by dizl on 2015/6/5.
 */
@Dao(CfgCacheLoadEntity.class)
public interface ICfgCacheLoadDao extends CrudDao<CfgCacheLoadEntity, String> {
}
