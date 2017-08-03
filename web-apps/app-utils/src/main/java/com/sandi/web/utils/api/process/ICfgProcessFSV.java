package com.sandi.web.utils.api.process;

/**
 * Created by dizl on 2017/2/21.
 */
public interface ICfgProcessFSV {
    /**
     * 获取满足条件的流程配置信息
     * */
    public String queryProcessInfo(String params);

    /**
     * 查询有效的流程数据
     * */
    public String queryValidProcessInfo(String params);

    /**
     * 新增或变更流程配置信息
     * */
    public String editProcess(String params);

    /**
     * 查询流程规则
     * */
    public String queryProcessRule(String params);
}
