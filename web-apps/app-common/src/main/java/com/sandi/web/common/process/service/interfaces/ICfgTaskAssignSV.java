package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgTaskAssignEntity;

import java.util.List;

public interface ICfgTaskAssignSV {
    /**
     * 保存数据
     * */
    public void saveEntity(List<CfgTaskAssignEntity> taskAssignEntityList) throws Exception;

    /**
     * 根据流程编号删除数据
     * */
    public void deleteByProcessId(long processId) throws Exception;

    /**
     * 根据任务编号获取任务处理人
     * */
    public List<CfgTaskAssignEntity> getTaskAssignByTaskId(long taskId) throws Exception;
}