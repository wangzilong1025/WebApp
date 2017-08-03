package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgTaskAssignEntity;

@Dao(CfgTaskAssignEntity.class)
public interface ICfgTaskAssignDao extends CrudDao<CfgTaskAssignEntity,Long> {
}