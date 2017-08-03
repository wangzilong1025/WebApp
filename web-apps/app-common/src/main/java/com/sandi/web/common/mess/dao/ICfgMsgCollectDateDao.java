package com.sandi.web.common.mess.dao;


import com.sandi.web.common.mess.entity.CfgMsgCollectDateEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgMsgCollectDateEntity.class)
public interface ICfgMsgCollectDateDao extends CrudDao<CfgMsgCollectDateEntity,Long> {
}