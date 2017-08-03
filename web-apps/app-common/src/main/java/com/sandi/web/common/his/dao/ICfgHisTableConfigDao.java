package com.sandi.web.common.his.dao;

import com.sandi.web.common.his.entity.CfgHisTableConfigEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

/**
 * Created by dizl on 2015/7/17.
 */
@Dao(CfgHisTableConfigEntity.class)
public interface ICfgHisTableConfigDao extends CrudDao<CfgHisTableConfigEntity, String> {
}
