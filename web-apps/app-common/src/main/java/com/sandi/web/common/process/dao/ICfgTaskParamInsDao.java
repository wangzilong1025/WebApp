package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgTaskParamInsEntity;

@Dao(CfgTaskParamInsEntity.class)
public interface ICfgTaskParamInsDao extends CrudDao<CfgTaskParamInsEntity,Long> {
}