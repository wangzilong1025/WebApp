package com.sandi.web.common.elec.service.impl;

import com.sandi.web.common.elec.dao.ICfgElecDao;
import com.sandi.web.common.elec.dao.IElecInstDao;
import com.sandi.web.common.elec.entity.CfgElecEntity;
import com.sandi.web.common.elec.entity.ElecInstEntity;
import com.sandi.web.common.elec.service.interfaces.ICfgElecSV;
import com.sandi.web.common.id.IdGeneratorFactory;
import com.sandi.web.utils.sec.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class CfgElecSVImpl implements ICfgElecSV {
    @Autowired
    private ICfgElecDao cfgElecDao;

    @Autowired
    private IElecInstDao elecInstDao;


    @Override
    public List<CfgElecEntity> queryCfgElecEntity(CfgElecEntity cfgElecEntity) throws Exception {
        cfgElecEntity.setState(1);
        List<CfgElecEntity> cfgElecEntityList = cfgElecDao.findByEntity(cfgElecEntity);
        for (CfgElecEntity cfgElecEntityTemp : cfgElecEntityList) {
            CfgElecEntity cfgElecEntity1 = new CfgElecEntity();
            cfgElecEntity1.setParentFileTypeId(cfgElecEntityTemp.getFileTypeId());
            cfgElecEntity1.setState(1);
            if (cfgElecDao.findByEntity(cfgElecEntity1).size() > 0) {
                cfgElecEntityTemp.setHasChildren(true);
            } else {
                cfgElecEntityTemp.setHasChildren(false);
            }
        }
        return cfgElecEntityList;
    }


    /**
     * 大小电子资料实例
     *
     * @param elecInstEntity
     * @return
     * @throws Exception
     */
    @Override
    public List<ElecInstEntity> queryElecInstEntity(ElecInstEntity elecInstEntity) throws Exception {
        return elecInstDao.findByEntity(elecInstEntity);
    }


    /**
     * 获取新的fileInputId
     *
     * @return
     * @throws Exception
     */
    @Override
    public Long getNewFileInputId() throws Exception {
        return IdGeneratorFactory.newId("CFG_ELEC_FILE_INPUT_ID");
    }


    /**
     * 保存电子资料实例
     *
     * @throws Exception
     */
    @Override
    public void saveElecInst(ElecInstEntity elecInstEntity) throws Exception {
        elecInstEntity.setCreateDate(new Date());
        elecInstEntity.setElecInstId(elecInstEntity.newId());
        if(elecInstEntity.getEffectiveDate() == null){
            elecInstEntity.setEffectiveDate(new Date());
        }
        elecInstEntity.setCreator(SessionManager.getUser().getUserId());
        //getOrganizeId()传承getUserId
        elecInstEntity.setOrgId(SessionManager.getUser().getUserId());
        elecInstEntity.setState(1);
        elecInstDao.save(elecInstEntity);
    }


    /**
     * 删除电子资料
     *
     * @param elecInstEntity
     * @throws Exception
     */
    @Override
    public void delElecInst(ElecInstEntity elecInstEntity) throws Exception {
        elecInstDao.deleteByEntity(elecInstEntity);
    }
}