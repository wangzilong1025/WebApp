package com.sandi.web.common.log.service.impl;

import com.sandi.web.common.log.dao.ISysBusiLogDao;
import com.sandi.web.common.log.entity.SysBusiLogEntity;
import com.sandi.web.common.log.service.interfaces.ISysBusiLogSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xt on 2016/2/22.
 */
@Service
@Transactional(readOnly = true)
public class SysBusiLogSVImpl implements ISysBusiLogSV {
    @Autowired
    ISysBusiLogDao logDao;

    @Override
    public void saveLogs(List<SysBusiLogEntity> entityList) throws Exception {
        logDao.save(entityList);
    }
}
