package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgProcessDefineEntity;

import java.util.List;
import java.util.Map;

public interface ICfgProcessDefineSV{
    /**
     * 根据流程编号获取流程对象
     * */
    public CfgProcessDefineEntity getEntityById(long processId) throws Exception;

    /**
     * 根据流程编号获取流程对象
     * */
    public List<CfgProcessDefineEntity> getEntityByProcessDefineId(long processDefineId) throws Exception;

    /**
     * 根据流程名称查询流程数据
     * */
    public List<CfgProcessDefineEntity> getEntityByName(String processName) throws Exception;

    /**
     * 查询满足条件的数据
     * */
    public List<CfgProcessDefineEntity> queryProcessInfo(Map params) throws Exception;

    /**
     * 查询有效的流程数据
     * */
    public List<CfgProcessDefineEntity> queryValidProcessInfo(Map params) throws Exception;

    /***
     * 根据流程编号查询有效的流程配置数据
     * */
    public CfgProcessDefineEntity getValidEntityByProcessDefineId(long processDefineId) throws Exception;
    /**
     * 创建新流程
     * */
    public long createProcess(String data) throws Exception;

    /**
     * 修改流程
     * */
    public long updateProcess(long processId, String data) throws Exception;

    /**
     * 删除流程
     * */
    public void deleteProcess(long processId) throws Exception;

    /**
     * 启用流程
     * */
    public void useProcess(long processId) throws Exception;
}