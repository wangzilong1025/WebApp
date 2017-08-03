package com.sandi.web.common.split.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.split.entity.CfgTableSplitEntity;

/**
 * Created by dizl on 2015/6/2.
 */
@Dao(CfgTableSplitEntity.class)
public interface ICfgTableSplitDao extends CrudDao<CfgTableSplitEntity, String> {

}
