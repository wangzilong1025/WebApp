package com.sandi.web.common.split.service.impl;

import com.sandi.web.common.split.dao.ICfgTableSplitDao;
import com.sandi.web.common.split.entity.CfgTableSplitEntity;
import com.sandi.web.common.split.entity.CfgTableSplitMappingEntity;
import com.sandi.web.common.split.service.interfaces.ICfgTableSplitSV;
import com.sandi.web.common.utils.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dizl on 2015/6/2.
 */
@Service
@Transactional(readOnly = true)
public class CfgTableSplitSVImpl implements ICfgTableSplitSV {

    @Autowired
    private ICfgTableSplitDao cfgTableSplitDao;

    public CfgTableSplitEntity getCfgTableSplitByTableName(String tableName) throws Exception {
        CfgTableSplitEntity entity = buildEntity();
        entity.setTableName(tableName);
        List<CfgTableSplitEntity> results = cfgTableSplitDao.findByEntity(entity);
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public List<CfgTableSplitEntity> getAllCfgTableSplits() throws Exception {
        CfgTableSplitEntity entity = buildEntity();
        return cfgTableSplitDao.findLike(entity);
    }

    private CfgTableSplitEntity buildEntity() {
        CfgTableSplitEntity entity = new CfgTableSplitEntity();
        entity.setState(CommConstants.State.STATE_NORMAL);

        CfgTableSplitMappingEntity mappingEntity = new CfgTableSplitMappingEntity();
        mappingEntity.setState(CommConstants.State.STATE_NORMAL);

        List<CfgTableSplitMappingEntity> lists = new ArrayList<CfgTableSplitMappingEntity>();
        lists.add(mappingEntity);
        entity.setCfgTableSplitMappingEntitys(lists);

        return entity;
    }
}
