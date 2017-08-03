package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgTaskInsEntity;
import com.sandi.web.common.process.entity.CfgTaskParamInsEntity;

import java.util.List;
import java.util.Map;

public interface ICfgTaskParamInsSV{
    /**
     * 完成任务
     * */
    public void completeTask(CfgTaskInsEntity taskInsEntity, Map params) throws Exception;

    /**
     * 根据任务编号查询任务参数
     * */
    public List<CfgTaskParamInsEntity> getParamEntityByTaskInsId(Long taskInsId) throws Exception;

    /**
     * 流程完成
     * */
    public void cancelProcess(long taskInsId) throws Exception;
}