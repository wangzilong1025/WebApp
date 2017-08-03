package com.sandi.web.common.ws.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.ws.entity.CfgWsClientEntity;

/**
 * Created by LIUQ on 2015/7/17.
 */
@Dao(CfgWsClientEntity.class)
public interface IWsClientDAO extends CrudDao<CfgWsClientEntity, String> {
}
