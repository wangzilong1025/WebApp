package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgTaskDefineEntity;

import java.util.List;

public interface ICfgTaskDefineSV{
    /**
     * 查询数据
     * */
    public List<CfgTaskDefineEntity> findByEntity(CfgTaskDefineEntity taskDefineEntity) throws Exception;
    /**
     * 根据任务编号获取任务信息
     * */
    public CfgTaskDefineEntity getTaskDefineById(long taskDefineId) throws Exception;

    /**
     * 保存数据
     * */
    public void saveEntity(CfgTaskDefineEntity taskDefineEntity) throws Exception;

    /**
     * 根据流程编号删除数据
     * */
    public void deleteByProcessId(long processId) throws Exception;

}