package com.sandi.web.common.bp.dao;

import com.sandi.web.common.bp.entity.CfgBpTemplateEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgBpTemplateEntity.class)
public interface ICfgBpTemplateDao extends CrudDao<CfgBpTemplateEntity, Long> {
}