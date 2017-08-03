package com.sandi.web.common.mess.dao;


import com.sandi.web.common.mess.entity.CfgMsgSendObjEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgMsgSendObjEntity.class)
public interface ICfgMsgSendObjDao extends CrudDao<CfgMsgSendObjEntity,Long> {
}