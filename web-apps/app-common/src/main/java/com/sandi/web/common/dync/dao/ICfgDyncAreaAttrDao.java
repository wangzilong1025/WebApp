package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncAreaAttrEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import org.apache.ibatis.annotations.Param;

@Dao(CfgDyncAreaAttrEntity.class)
public interface ICfgDyncAreaAttrDao extends CrudDao<CfgDyncAreaAttrEntity, Long> {
    public int deleteByPageId(@Param("pageId") Long pageId);
}