package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncRuleExpEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgDyncRuleExpEntity.class)
public interface ICfgDyncRuleExpDao extends CrudDao<CfgDyncRuleExpEntity,Long> {
}