package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgProcessParamEntity;

@Dao(CfgProcessParamEntity.class)
public interface ICfgProcessParamDao extends CrudDao<CfgProcessParamEntity,Long> {
}