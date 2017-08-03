package com.sandi.web.common.dync.service.interfaces;

import com.sandi.web.common.dync.entity.*;
import com.sandi.web.common.persistence.entity.Page;
import java.util.List;
import java.util.Map;

public interface ICfgDyncCommonSV {

    /**
     * 根据条件获取请求的url路径
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    public String getRequestUrl(String requestJson) throws Exception;

    /**
     * 获取动态表单数据
     *
     * @param param
     * @return
     * @throws Exception
     */
    public String getBusiFrameEntity(String param) throws Exception;


    /**
     * 查询框架模板
     *
     * @param cfgDyncPageTemplateEntity
     * @return
     */
    public List<CfgDyncPageTemplateEntity> getCfgDyncPageTemplate(CfgDyncPageTemplateEntity cfgDyncPageTemplateEntity) throws Exception;

    /**
     * 查询规则集
     *
     * @param cfgDyncRulesetEntity
     * @return
     */
    public List<CfgDyncRulesetEntity> getCfgDyncRuleset(CfgDyncRulesetEntity cfgDyncRulesetEntity) throws Exception;

    /**
     * 查询按钮集
     *
     * @param cfgDyncButtonsetEntity
     * @return
     */
    public List<CfgDyncButtonsetEntity> getCfgDyncButtonset(CfgDyncButtonsetEntity cfgDyncButtonsetEntity) throws Exception;

    /**
     * 保存按钮集
     *
     * @param paramMap
     * @throws Exception
     */
    public void saveButtonset(Map<String, Object> paramMap) throws Exception;

    public void saveRuleset(Map<String, Object> paramMap) throws Exception;

    /**
     * 保存按钮
     *
     * @param cfgDyncButtonEntity
     * @throws Exception
     */
    public void saveButton(CfgDyncButtonEntity cfgDyncButtonEntity) throws Exception;


    public void saveRule(CfgDyncRuleEntity cfgDyncRuleEntity) throws Exception;

    /**
     * 查询按钮
     *
     * @param cfgDyncButtonEntity
     * @return
     */
    public List<CfgDyncButtonEntity> getCfgDyncButton(CfgDyncButtonEntity cfgDyncButtonEntity) throws Exception;


    /**
     * 查询规则
     *
     * @param cfgDyncRuleEntity
     * @return
     */
    public List<CfgDyncRuleEntity> getCfgDyncRule(CfgDyncRuleEntity cfgDyncRuleEntity) throws Exception;

    /**
     * 查询正则
     *
     * @param cfgDyncRuleExpEntity
     * @return
     */
    public List<CfgDyncRuleExpEntity> getCfgDyncRuleExp(CfgDyncRuleExpEntity cfgDyncRuleExpEntity) throws Exception;


    /**
     * 保存动态表单配置数据
     *
     * @param data
     * @throws Exception
     */
    public Long saveCfgDyncData(Map<String, Object> data) throws Exception;


    /**
     * 查询已配置的业务数据
     *
     * @param queryParam
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryCfgDyncBusiData(Map<String, Object> queryParam, Page page) throws Exception;


    /**
     * 启用业务
     *
     * @param busiFrameId
     * @throws Exception
     */
    public void startBusi(Long busiFrameId) throws Exception;

    /**
     * 锁定业务
     *
     * @param busiFrameId
     * @throws Exception
     */
    public void lockBusi(Long busiFrameId) throws Exception;


    /**
     * 获取业务配置数据
     *
     * @param data
     * @return
     * @throws Exception
     */
    public Map<String, Object> getCfgDyncData(Map<String, Object> data) throws Exception;


    /**
     * 获取页面和框架之间的复用关系
     *
     * @param param
     * @return
     */
    public Map<String, Object> queryPageFrameRelation(Map<String, Object> param) throws Exception;


    /**
     * 查询已配置的按钮集
     *
     * @param queryParam
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryCfgButtonsetData(Map<String, Object> queryParam, Page page) throws Exception;


    /**
     * 查询已配置的规则集
     *
     * @param queryParam
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryCfgRulesetData(Map<String, Object> queryParam, Page page) throws Exception;

    public void delCfgDyncButton(Long buttonId) throws Exception;

    public void delCfgDyncRule(Long ruleId) throws Exception;

    public void delButtonset(Long buttonsetId) throws Exception;

    public void delRuleset(Long rulesetId) throws Exception;
}