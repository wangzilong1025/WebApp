package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgProcessInsEntity;
import com.sandi.web.common.process.entity.CfgTaskInsEntity;

public interface ICfgTaskAssignInsSV{
    /**
     * 创建下一任务
     * */
    public void createNextTask(CfgTaskInsEntity taskInsEntity, CfgTaskInsEntity preTaskInsEntity, CfgProcessInsEntity processInsEntity) throws Exception;

    /**
     * 完成流程
     */
    public void cancelProcess(long taskInsId) throws Exception;
}