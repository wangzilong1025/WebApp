package com.sandi.web.common.quartz.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.quartz.entity.CfgQuartzJobLogEntity;

/**
 * Created by dizl on 2015/6/15.
 */
@Dao(CfgQuartzJobLogEntity.class)
public interface IQuartzJobLogDao extends CrudDao<CfgQuartzJobLogEntity, String> {
}
