package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncButtonsetButtonEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgDyncButtonsetButtonEntity.class)
public interface ICfgDyncButtonsetButtonDao extends CrudDao<CfgDyncButtonsetButtonEntity,Long> {
}