package com.sandi.web.common.staticdata.dao;

import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.staticdata.entity.CfgStaticDataEntity;

/**
 * Created by dizl on 2015/6/12.
 */
@Dao(CfgStaticDataEntity.class)
public interface ICfgStaticDataDao extends CrudDao<CfgStaticDataEntity, String> {
}
