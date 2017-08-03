package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgProcessParamEntity;

import java.util.List;

public interface ICfgProcessParamSV{
    /**
     * 根据流程编号获取流程参数
     * */
    public List<CfgProcessParamEntity> getParamEntityByProcessId(long processId) throws Exception;

    /**
     * 保存流程参数
     * */
    public void saveEntity(List<CfgProcessParamEntity> paramEntityList) throws Exception;

    /**
     * 根据流程编号删除数据
     * */
    public void deleteByProcessId(long processId) throws Exception;
}