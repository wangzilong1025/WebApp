package com.sandi.web.common.http.dao;

import com.sandi.web.common.http.entity.CfgHttpMappingEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

/**
 * Created by LIUQ on 2015/7/21.
 */
@Dao(CfgHttpMappingEntity.class)
public interface ICfgHttpMappingDao extends CrudDao<CfgHttpMappingEntity, String> {
}
