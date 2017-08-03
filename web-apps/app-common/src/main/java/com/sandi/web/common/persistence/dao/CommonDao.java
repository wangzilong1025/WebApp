package com.sandi.web.common.persistence.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoKe on 2016/1/19.
 */
public interface CommonDao extends BaseDao {
    public Date getSysDate() throws Exception;

    public List<Map> findBySql(@Param("sql") String sql, @Param("param") Map params) throws Exception;
}
