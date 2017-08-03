package com.sandi.web.common.bp.dao;

import com.sandi.web.common.bp.entity.EsBpDataEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.persistence.entity.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Dao(EsBpDataEntity.class)
public interface IEsBpDataDao extends CrudDao<EsBpDataEntity, Long> {
    public List<Map<String, Object>> queryEsBpData(@Param("param") Map<String, Object> param, @Param("page") Page page);
}

