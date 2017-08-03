package com.sandi.web.common.ftp.service.interfaces;

import com.sandi.web.common.ftp.entity.CfgFtpEntity;
import java.util.List;

/**
 * Created by lury on 2015/7/21.
 */
public interface ICfgFtpSV {
    public List<CfgFtpEntity> getAllCfgFtp() throws Exception;
}
