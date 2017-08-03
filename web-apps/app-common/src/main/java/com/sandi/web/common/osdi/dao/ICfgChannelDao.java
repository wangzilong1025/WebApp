package com.sandi.web.common.osdi.dao;


import com.sandi.web.common.osdi.entity.CfgChannelEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgChannelEntity.class)
public interface ICfgChannelDao extends CrudDao<CfgChannelEntity, Long> {
}