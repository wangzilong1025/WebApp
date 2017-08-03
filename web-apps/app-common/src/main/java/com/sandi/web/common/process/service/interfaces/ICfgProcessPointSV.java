package com.sandi.web.common.process.service.interfaces;



import com.sandi.web.common.process.entity.CfgProcessPointEntity;

import java.util.List;

public interface ICfgProcessPointSV {
    /**
     * 查询数据
     * */
    public List<CfgProcessPointEntity> findByEntity(CfgProcessPointEntity processPointEntity) throws Exception;

    /**
     * 根据流程编号获取流程起始节点
     * */
    public CfgProcessPointEntity getStartPoint(Long processId) throws Exception;

    /**
     * 根据流程节点编号获取流程节点信息
     * */
    public CfgProcessPointEntity getPointRelEntityByPointId(Long pointId) throws Exception;

    /**
     * 根据流程编号获取流程节点信息
     * */
    public List<CfgProcessPointEntity> getPointRelEntityByProcessId(Long processId) throws Exception;

    /**
     * 保存数据
     * */
    public void saveEntity(CfgProcessPointEntity processPointEntity) throws Exception;

    /**
    * 保存数据
    * **/
    public void saveEntity(List<CfgProcessPointEntity> processPointEntityList) throws Exception;

    /**
     * 根据流程编号删除数据
     * */
    public void deleteByProcessId(long processId) throws Exception;
}