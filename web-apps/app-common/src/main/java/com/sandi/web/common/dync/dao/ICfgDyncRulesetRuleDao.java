package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncRulesetRuleEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgDyncRulesetRuleEntity.class)
public interface ICfgDyncRulesetRuleDao extends CrudDao<CfgDyncRulesetRuleEntity,Long> {
}