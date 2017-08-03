package com.sandi.web.common.ftp.service.impl;

import com.sandi.web.common.ftp.dao.ICfgFtpPathDao;
import com.sandi.web.common.ftp.entity.CfgFtpPathEntity;
import com.sandi.web.common.ftp.service.interfaces.ICfgFtpPathSV;
import com.sandi.web.common.utils.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by lury on 2015/7/21.
 */
@Service
public class CfgFtpPathSVImpl implements ICfgFtpPathSV {
    @Autowired
    private ICfgFtpPathDao cfgFtpPathDao;

    public List<CfgFtpPathEntity> getAllCfgFtpPath() throws Exception {
        CfgFtpPathEntity entity = new CfgFtpPathEntity();
        entity.setState(CommConstants.State.STATE_NORMAL);
        return cfgFtpPathDao.findByEntity(entity);
    }
}
