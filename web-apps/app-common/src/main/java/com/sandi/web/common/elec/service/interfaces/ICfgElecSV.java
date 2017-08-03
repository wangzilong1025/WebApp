package com.sandi.web.common.elec.service.interfaces;


import com.sandi.web.common.elec.entity.CfgElecEntity;
import com.sandi.web.common.elec.entity.ElecInstEntity;
import java.util.List;

public interface ICfgElecSV {


    public List<CfgElecEntity> queryCfgElecEntity(CfgElecEntity cfgElecEntity) throws Exception;

    /**
     * 大小电子资料实例
     * @param elecInstEntity
     * @return
     * @throws Exception
     */
    public List<ElecInstEntity> queryElecInstEntity(ElecInstEntity elecInstEntity) throws Exception;


    /**
     * 获取新的fileInputId
     * @return
     * @throws Exception
     */
    public Long getNewFileInputId() throws Exception;


    /**
     * 保存电子资料实例
     * @throws Exception
     */
    public void saveElecInst(ElecInstEntity elecInstEntity) throws Exception;


    /**
     * 删除电子资料
     * @param elecInstEntity
     * @throws Exception
     */
    public void delElecInst(ElecInstEntity elecInstEntity) throws Exception;

}