package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgProcessPointRuleEntity;

import java.util.List;

public interface ICfgProcessPointRuleSV {
    /**
     * 根据节点编号获取节点判断值
     * */
    public List<CfgProcessPointRuleEntity> getPointRuleEntity(long pointId) throws Exception;

    /**
     * 保存数据
     * */
    public void saveEntity(CfgProcessPointRuleEntity pointRuleEntity) throws Exception;

    /**
     * 保存数据
     * */
    public void saveEntity(List<CfgProcessPointRuleEntity> pointRuleEntityList) throws Exception;

    /**
     * 根据流程编号删除数据
     * */
    public void deleteByProcessId(long processId) throws Exception;
}