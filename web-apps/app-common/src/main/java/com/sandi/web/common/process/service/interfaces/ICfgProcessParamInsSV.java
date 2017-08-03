package com.sandi.web.common.process.service.interfaces;

import com.sandi.web.common.process.entity.CfgProcessInsEntity;
import com.sandi.web.common.process.entity.CfgProcessParamInsEntity;

import java.util.List;
import java.util.Map;

public interface ICfgProcessParamInsSV{
    /**
     * 处理流程参数
     * */
    public void startProcess(CfgProcessInsEntity processInsEntity, Map params) throws Exception;

    /**
     * 根据流程编号获取流程参数
     * */
    public List<CfgProcessParamInsEntity> getParamEntityByProcessInsId(long processInsId) throws Exception;

    /**
     * 完成流程
     * */
    public void cancelProcess(long processInsId) throws Exception;
}