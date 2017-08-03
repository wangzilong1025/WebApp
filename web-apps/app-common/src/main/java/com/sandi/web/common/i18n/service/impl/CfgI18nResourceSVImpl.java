package com.sandi.web.common.i18n.service.impl;

import com.sandi.web.common.i18n.dao.ICfgI18nResourceDao;
import com.sandi.web.common.i18n.entity.CfgI18nResourceEntity;
import com.sandi.web.common.i18n.service.interfaces.ICfgI18nResourceSV;
import com.sandi.web.common.utils.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dizl on 2015/6/10.
 */
@Service
public class CfgI18nResourceSVImpl implements ICfgI18nResourceSV {

    @Autowired
    private ICfgI18nResourceDao cfgI18nResourceDao;

    @Override
    public CfgI18nResourceEntity getCfgI18nResouce(String resKey) throws Exception {
        return cfgI18nResourceDao.findById(resKey);
    }

    @Override
    public List<CfgI18nResourceEntity> getAllCfgI18nResouce() throws Exception {
        CfgI18nResourceEntity entity = new CfgI18nResourceEntity();
        entity.setState(CommConstants.State.STATE_NORMAL);
        return cfgI18nResourceDao.findByEntity(entity);
    }
}
