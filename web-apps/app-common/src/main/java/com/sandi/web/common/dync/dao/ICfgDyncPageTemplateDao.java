package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncPageTemplateEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgDyncPageTemplateEntity.class)
public interface ICfgDyncPageTemplateDao extends CrudDao<CfgDyncPageTemplateEntity,Long> {
}