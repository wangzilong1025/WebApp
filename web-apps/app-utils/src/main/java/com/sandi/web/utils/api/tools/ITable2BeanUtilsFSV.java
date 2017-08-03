package com.sandi.web.utils.api.tools;

/**
 * Created by liuqin on 2016/8/28
 */
public interface ITable2BeanUtilsFSV {

    //根据数据源和表名查询表的字段属性
    public String getTableInfo(String str) throws Exception;

    //静态表生成entity和dao
    public String createBean(String str) throws Exception;
}
