package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgProcessInsEntity;

import java.util.List;
import java.util.Map;

public interface ICfgProcessInsSV{
    /**
     * 启动新流程
     * */
    public CfgProcessInsEntity startProcess(long processDefineId, Map params) throws Exception;

    /**
     * 启动子流程
     * */
    public CfgProcessInsEntity startChildProcess(long processId, long parentProcessInsId, long parentTaskInsId, Map params) throws Exception;

    /**
     * 修改流程数据
     * */
    public void updateProcess(CfgProcessInsEntity cfgProcessInsEntity) throws Exception;

    /**
     * 终止流程
     * */
    public void stopProcess(long processInsId) throws Exception;

    /**
     * 完成流程
     * */
    public void cancelProcess(long processInsId) throws Exception;

    /***
     * 获取待处理流程
     * */
    public List<CfgProcessInsEntity> getWaitProcess() throws Exception;

    /**
     *根据主键获取流程数据
     * */
    public CfgProcessInsEntity getProcessById(long processInsId) throws Exception;
}