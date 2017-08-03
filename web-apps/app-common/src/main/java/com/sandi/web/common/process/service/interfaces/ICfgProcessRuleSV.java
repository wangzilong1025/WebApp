package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgProcessRuleEntity;

import java.util.List;
import java.util.Map;

public interface ICfgProcessRuleSV{

    /**
     * 根据规则编号获取规则配置数据
     * */
    public CfgProcessRuleEntity getRuleEntityByRuleId(long ruleId) throws Exception;

    /**
     * 获取有效的规则数据
     * */
    public List<CfgProcessRuleEntity> getRuleEntityList(Map params) throws Exception;
}