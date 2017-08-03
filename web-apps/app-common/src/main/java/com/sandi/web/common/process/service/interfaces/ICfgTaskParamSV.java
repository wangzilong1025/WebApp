package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgTaskParamEntity;

import java.util.List;

public interface ICfgTaskParamSV{
    /**
     * 根据任务编号获取任务参数配置
     * */
    public List<CfgTaskParamEntity> getParamEntityByTaskId(long taskId) throws Exception;

    /**
     * 保存任务参数数据
     * */
    public void saveEntity(List<CfgTaskParamEntity> taskParamEntityList) throws Exception;

    /**
     * 根据流程编号删除数据
     * */
    public void deleteByProcessId(long processId) throws Exception;
}