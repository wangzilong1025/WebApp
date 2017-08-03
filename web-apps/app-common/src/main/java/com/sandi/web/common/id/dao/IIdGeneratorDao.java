package com.sandi.web.common.id.dao;

import com.sandi.web.common.id.entity.IdGeneratorEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by dizl on 2015/6/4.
 */
@Dao(IdGeneratorEntity.class)
public interface IIdGeneratorDao extends CrudDao<IdGeneratorEntity, String> {
    @Select("select ${value}.nextval from dual")
    public long getNewId(@Param("value") String sequenceName) throws Exception;
}
