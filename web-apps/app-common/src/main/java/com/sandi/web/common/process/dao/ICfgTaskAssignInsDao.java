package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgTaskAssignInsEntity;

@Dao(CfgTaskAssignInsEntity.class)
public interface ICfgTaskAssignInsDao extends CrudDao<CfgTaskAssignInsEntity,Long> {
}