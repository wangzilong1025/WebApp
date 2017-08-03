package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgProcessInsEntity;
import com.sandi.web.common.process.entity.CfgTaskInsEntity;

import java.util.List;
import java.util.Map;

public interface ICfgTaskInsSV{

    public List<CfgTaskInsEntity> findByEntity(CfgTaskInsEntity taskInsEntity) throws Exception;

    /**
     * 完成当前任务
     * */
    public void completeTask(long taskInsId, Map taskParams) throws Exception;

    /**
     * 根据流程编号和任务编号，完成当前任务
     * */
    public void completeTask(long processInsId, long taskId, Map taskParams) throws Exception;

    /**
     * 创建下一任务节点
     * */
    public void createNextTask(CfgProcessInsEntity processInsEntity, CfgTaskInsEntity taskInsEntity) throws Exception;

    /**
     * 流程完成
     * */
    public void cancelProcess(long processInsId) throws Exception;

    /**
     * 处理已完成的任务
     * */
    public void dealCompleteTask() throws Exception;

    /**
     * 处理已过期的任务
     * */
    public void dealExpireTask() throws Exception;

    /**
     * 查询待处理商机信息
     * */
    public List<Map> queryUserTask(Map params) throws Exception;

}