package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgProcessParamInsEntity;

@Dao(CfgProcessParamInsEntity.class)
public interface ICfgProcessParamInsDao extends CrudDao<CfgProcessParamInsEntity,Long> {
}