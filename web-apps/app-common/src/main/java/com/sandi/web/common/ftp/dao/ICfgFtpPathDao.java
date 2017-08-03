package com.sandi.web.common.ftp.dao;

import com.sandi.web.common.ftp.entity.CfgFtpPathEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

/**
 * Created by lury on 2015/7/21.
 */
@Dao(CfgFtpPathEntity.class)
public interface ICfgFtpPathDao extends CrudDao<CfgFtpPathEntity, String> {
}
