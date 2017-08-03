package com.sandi.web.common.bp.dao;

import com.sandi.web.common.bp.entity.CfgDbConfigEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgDbConfigEntity.class)
public interface ICfgDbConfigDao extends CrudDao<CfgDbConfigEntity, Long> {
}