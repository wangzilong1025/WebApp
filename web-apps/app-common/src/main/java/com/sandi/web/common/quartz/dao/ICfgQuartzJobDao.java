package com.sandi.web.common.quartz.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.quartz.entity.CfgQuartzJobEntity;

@Dao(CfgQuartzJobEntity.class)
public interface ICfgQuartzJobDao extends CrudDao<CfgQuartzJobEntity, Long> {
}