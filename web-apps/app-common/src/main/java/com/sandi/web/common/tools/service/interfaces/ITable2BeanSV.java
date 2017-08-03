package com.sandi.web.common.tools.service.interfaces;

import java.util.List;

/**
 * Created by liuqin on 2016/8/28
 */
public interface ITable2BeanSV {
    public String createBean(List tableList, String path, String dataSource) throws Exception;

    public List getTableInfo(String tableNames, String dataSource) throws Exception;

}
