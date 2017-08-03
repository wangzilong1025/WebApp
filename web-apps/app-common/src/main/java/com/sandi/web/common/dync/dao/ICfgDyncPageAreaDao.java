package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncPageAreaEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import org.apache.ibatis.annotations.Param;

@Dao(CfgDyncPageAreaEntity.class)
public interface ICfgDyncPageAreaDao extends CrudDao<CfgDyncPageAreaEntity, Long> {
    public int deleteByPageId(@Param("pageId") Long pageId);
}