package com.sandi.web.common.ftp.dao;


import com.sandi.web.common.ftp.entity.CfgFtpEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

/**
 * Created by lury on 2015/7/21.
 */
@Dao(CfgFtpEntity.class)
public interface ICfgFtpDao extends CrudDao<CfgFtpEntity, String> {
}
