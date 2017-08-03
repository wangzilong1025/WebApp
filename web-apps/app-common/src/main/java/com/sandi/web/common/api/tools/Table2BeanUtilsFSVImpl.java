package com.sandi.web.common.api.tools;

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.tools.service.interfaces.ITable2BeanSV;
import com.sandi.web.utils.api.tools.ITable2BeanUtilsFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

/**
 * Created by liuqin on 2016/8/28.
 */
@Service
public class Table2BeanUtilsFSVImpl implements ITable2BeanUtilsFSV {
    @Autowired
    private ITable2BeanSV table2BeanSV;

    private static final Logger log = LoggerFactory.getLogger(Table2BeanUtilsFSVImpl.class);

    @Override
    public String getTableInfo(String str)  {
        Response response = new Response();

        try{
            Map map = JsonUtil.json2Map(str);
            String tableNames = (String) map.get("tableNames");
            String dataSource = (String) map.get("dataSource");
            List list = table2BeanSV.getTableInfo(tableNames,dataSource);
            response.setData(list);
            response.setCode(Response.SUCCESS);
            response.setMessage("调用成功！");
        }
        catch(Exception e){
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            log.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String createBean(String str)  {
        Response response = new Response();
        try {
            Map paramMap = JsonUtil.json2Map(str);
            List tableList = (List) paramMap.get("tableList");
            String path = (String) paramMap.get("path");
            String dataSource = (String) paramMap.get("dataSource");
            response.setData(table2BeanSV.createBean(tableList, path,dataSource));
            response.setCode(Response.SUCCESS);
        }catch (Exception e){
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            log.error("调用失败！",e);
        }

        return response.toString();
    }
}
