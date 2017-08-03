package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgTaskDefineEntity;

@Dao(CfgTaskDefineEntity.class)
public interface ICfgTaskDefineDao extends CrudDao<CfgTaskDefineEntity,Long> {
}