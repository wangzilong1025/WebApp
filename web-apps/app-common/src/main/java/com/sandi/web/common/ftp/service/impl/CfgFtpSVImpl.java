package com.sandi.web.common.ftp.service.impl;

import com.sandi.web.common.ftp.dao.ICfgFtpDao;
import com.sandi.web.common.ftp.entity.CfgFtpEntity;
import com.sandi.web.common.ftp.service.interfaces.ICfgFtpSV;
import com.sandi.web.common.utils.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by lury on 2015/7/21.
 */
@Service
public class CfgFtpSVImpl implements ICfgFtpSV {
    @Autowired
    private ICfgFtpDao cfgFtpDao;

    public List<CfgFtpEntity> getAllCfgFtp() throws Exception {
        CfgFtpEntity entity = new CfgFtpEntity();
        entity.setState(CommConstants.State.STATE_NORMAL);
        return cfgFtpDao.findByEntity(entity);
    }
}
