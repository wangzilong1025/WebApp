package com.sandi.web.common.i18n.dao;

import com.sandi.web.common.i18n.entity.CfgI18nResourceEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

/**
 * Created by dizl on 2015/6/10.
 */
@Dao(CfgI18nResourceEntity.class)
public interface ICfgI18nResourceDao extends CrudDao<CfgI18nResourceEntity, String> {
}
