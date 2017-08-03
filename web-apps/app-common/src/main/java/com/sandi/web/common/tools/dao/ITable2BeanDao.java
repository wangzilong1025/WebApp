package com.sandi.web.common.tools.dao;

import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuqin on 2016/8/28(PS:改用JDBC代替Mybatis,即该接口可不用)
 */
@Dao(ITable2BeanDao.class)
@Repository("table2BeanDao")

public interface ITable2BeanDao extends BaseDao {
    public List<Map<String, Object>> getTableInfo(String tableName);

    public String[] getConstraintKey(String tableName);
}