package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgTaskParamEntity;

@Dao(CfgTaskParamEntity.class)
public interface ICfgTaskParamDao extends CrudDao<CfgTaskParamEntity,Long> {
}