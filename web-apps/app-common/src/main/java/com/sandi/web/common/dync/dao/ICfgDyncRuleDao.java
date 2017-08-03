package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncRuleEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import java.util.List;

@Dao(CfgDyncRuleEntity.class)
public interface ICfgDyncRuleDao extends CrudDao<CfgDyncRuleEntity,Long> {
    //查询规则集包含的规则
    public List<CfgDyncRuleEntity> getRuleByRulesetId(long rulesetId);
}