package com.sandi.web.common.staticdata.service.impl;

import com.sandi.web.common.persistence.entity.Rank;
import com.sandi.web.common.staticdata.dao.ICfgStaticDataDao;
import com.sandi.web.common.staticdata.entity.CfgStaticDataEntity;
import com.sandi.web.common.staticdata.service.interfaces.ICfgStaticDataSV;
import com.sandi.web.common.utils.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dizl on 2015/6/12.
 */
@Service
public class CfgStaticDataSVImpl implements ICfgStaticDataSV {
    @Autowired
    private ICfgStaticDataDao cfgStaticDataDao;

    public List<CfgStaticDataEntity> getAllCfgStaticData() throws Exception {
        CfgStaticDataEntity entity = new CfgStaticDataEntity();
        entity.setState(CommConstants.State.STATE_NORMAL);
        Rank[] ranks = new Rank[2];
        ranks[0] = new Rank("codeType");
        ranks[1] = new Rank("sortId");
        entity.setRanks(ranks);
        return cfgStaticDataDao.findByEntity(entity);
    }

    public List<CfgStaticDataEntity> getCfgStaticDataByCodeType(String codeType) throws Exception {
        CfgStaticDataEntity entity = new CfgStaticDataEntity();
        entity.setCodeType(codeType);
        entity.setState(CommConstants.State.STATE_NORMAL);
        Rank[] ranks = new Rank[1];
        ranks[0] = new Rank("sortId");
        entity.setRanks(ranks);
        return cfgStaticDataDao.findByEntity(entity);
    }

    @Override
    public CfgStaticDataEntity getCfgStaticDataByCon(String codeType, String codeValue) throws Exception {
        CfgStaticDataEntity entity = new CfgStaticDataEntity();
        entity.setCodeType(codeType);
        entity.setCodeValue(codeValue);
        entity.setState(CommConstants.State.STATE_NORMAL);
        Rank[] ranks = new Rank[1];
        ranks[0] = new Rank("sortId");
        entity.setRanks(ranks);
        List<CfgStaticDataEntity> list = cfgStaticDataDao.findByEntity(entity);
        if (list.size() > 0) {
            entity = list.get(0);
        }
        return entity;
    }
}
