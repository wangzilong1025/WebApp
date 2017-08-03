/**
 * $Id: ICfgDyncBusiFrameRelFSV.java,v 1.1 2016/8/30 14:41 haomeng Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.api.dync;

/**
 * @author haomeng
 * @version $Id: ICfgDyncBusiFrameFSV.java,v 1.1 2016/8/30 14:41 haomeng Exp $
 *          Created on 2016/8/30 14:41
 */
public interface ICfgDyncCommonFSV {

    /**
     * 获取根据条件获取请求的动态页面路径
     * @param param
     * @return
     * @throws Exception
     */
    public String getRequestUrl(String param);

    /**
     * 获取动态表单busiframe数据
     * @param param
     * @return
     * @throws Exception
     */
    public String getBusiFrameEntity(String param);


    /**
     * 查询框架模板
     * @param param
     * @return
     */
    public String getCfgDyncPageTemplate(String param);


    /**
     * 保存动态表单配置数据
     * @param param
     * @return
     */
    public String saveCfgDyncData(String param);


    /**
     * 查询规则集
     * @param param
     * @return
     */
    public String getCfgDyncRuleset(String param);

    /**
     * 查询规则
     * @param param
     * @return
     */
    public String getCfgDyncRule(String param);

    /**
     * 保存规则
     * @param param
     * @return
     */
    public String saveCfgDyncRule(String param);

    /**
     * 保存规则集
     * @param param
     * @return
     */
    public String saveRuleset(String param);

    /**
     * 删除规则集
     * @param param
     * @return
     */
    public String delRuleset(String param);

    /**
     * 获取新的规则ID
     * @param param
     * @return
     */
    public String getNewRuleId(String param);

    /**
     * 删除规则
     * @param param
     * @return
     */
    public String delCfgDyncRule(String param);

    /**
     * 查询规则集
     *
     * @param param
     * @return
     */
    public String queryCfgRulesetData(String param);


    /**
     * 查询按钮集
     * @param param
     * @return
     */
    public String getCfgDyncButtonset(String param);

    /**
     * 查询按钮
     * @param param
     * @return
     */
    public String getCfgDyncButton(String param);


    /**
     * 查保存按钮
     * @param param
     * @return
     */
    public String saveCfgDyncButton(String param);


    /**
     * 查询按钮集
     *
     * @param param
     * @return
     */
    public String queryCfgButtonsetData(String param);

    /**
     * 保存按钮集
     * @param param
     * @return
     */
    public String saveButtonset(String param);

    /**
     * 删除按钮集
     * @param param
     * @return
     */
    public String delButtonset(String param);

    /**
     * 获取新的按钮ID
     * @param param
     * @return
     */
    public String getNewButtonId(String param);

    /**
     * 删除按钮
     * @param param
     * @return
     */
    public String delCfgDyncButton(String param);


    /**
     * 查询正则
     * @param param
     * @return
     */
    public String getCfgDyncRuleExp(String param);


    /**
     * 查询已配置的业务数据
     * @param param
     * @return
     */
    public String queryCfgDyncBusiData(String param);

    /**
     * 启用业务
     * @param param
     * @return
     */
    public String startBusi(String param);


    /**
     * 锁定业务
     * @param param
     * @return
     */
    public String lockBusi(String param);


    /**
     * 获取业务配置数据
     * @param param
     * @return
     */
    public String getCfgDyncData(String param);

    /**
     * 获取页面和框架之间的复用关系
     *
     * @param param
     * @return
     */
    public String queryPageFrameRelation(String param);


}
