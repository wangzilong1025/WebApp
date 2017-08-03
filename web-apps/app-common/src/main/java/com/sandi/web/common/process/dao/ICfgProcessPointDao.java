package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgProcessPointEntity;

@Dao(CfgProcessPointEntity.class)
public interface ICfgProcessPointDao extends CrudDao<CfgProcessPointEntity,Long> {
}