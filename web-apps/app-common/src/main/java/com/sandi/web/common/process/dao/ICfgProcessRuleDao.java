package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgProcessRuleEntity;

@Dao(CfgProcessRuleEntity.class)
public interface ICfgProcessRuleDao extends CrudDao<CfgProcessRuleEntity,Long> {
}