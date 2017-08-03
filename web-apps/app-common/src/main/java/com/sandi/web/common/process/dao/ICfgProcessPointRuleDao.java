package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgProcessPointRuleEntity;

@Dao(CfgProcessPointRuleEntity.class)
public interface ICfgProcessPointRuleDao extends CrudDao<CfgProcessPointRuleEntity,Long> {
}