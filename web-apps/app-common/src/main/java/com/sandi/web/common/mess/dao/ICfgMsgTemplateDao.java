package com.sandi.web.common.mess.dao;


import com.sandi.web.common.mess.entity.CfgMsgTemplateEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgMsgTemplateEntity.class)
public interface ICfgMsgTemplateDao extends CrudDao<CfgMsgTemplateEntity,Long> {
}