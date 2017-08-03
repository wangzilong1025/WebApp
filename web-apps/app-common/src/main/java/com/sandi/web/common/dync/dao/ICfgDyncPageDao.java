package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncPageEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import org.apache.ibatis.annotations.Param;

@Dao(CfgDyncPageEntity.class)
public interface ICfgDyncPageDao extends CrudDao<CfgDyncPageEntity, Long> {
    public int deleteByPageId(@Param("pageId") Long pageId);
}