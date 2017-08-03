package com.sandi.web.common.mess.dao;


import com.sandi.web.common.mess.entity.CfgMsgInfoInstErrorEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgMsgInfoInstErrorEntity.class)
public interface ICfgMsgInfoInstErrorDao extends CrudDao<CfgMsgInfoInstErrorEntity,Long> {
}