package com.sandi.web.common.mess.service.impl;

import com.sandi.web.common.mess.service.interfaces.IMsgDataCollectSV;
import com.sandi.web.common.support.CommonSqlSessionTemplate;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.common.utils.ExceptionUtil;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * Created by dizl on 2017/3/30.
 * 通过SQL语句获取数据
 */
public class MsgDataCollectionWithSqlSVImpl implements IMsgDataCollectSV {

    /**
     * 提取待消息发送的数据
     *
     * @param inputParam
     */
    @Override
    public List<Map> doCollect(Map inputParam) throws Exception {
        String sql = "";
        String moduleId = "";
        Date lastDate = DateUtils.getCurrentDate();
        List<Map> retLists = new ArrayList<Map>();
        if(inputParam.containsKey("param") && inputParam.get("param")!=null){
            sql = String.valueOf(inputParam.get("param"));
        }
        if(inputParam.containsKey("lastDate") && inputParam.get("lastDate")!=null){
            lastDate = (Date)inputParam.get("lastDate");
        }
        if(inputParam.containsKey("moduleId") && inputParam.get("moduleId")!=null){
            moduleId = String.valueOf(inputParam.get("moduleId"));
        }
        if(StringUtils.isEmpty(moduleId)){
            ExceptionUtil.throwBusinessException("提取消息数据时，无法找到对应的模块编号");
        }
        if(StringUtils.isEmpty(sql)){
            ExceptionUtil.throwBusinessException("提取消息数据时，无法找到对应的SQL语句");
        }

        if(sql.contains("{lastDate}")){//如果包含lastDate字段
            sql = StringUtils.replace(sql,"{lastDate}","to_date('"+DateUtils.formatDate(lastDate,"yyyy-MM-dd HH:mm:ss")+"','yyyy-mm-dd hh24:mi:ss')");
        }
        CommonSqlSessionTemplate commonSqlSessionTemplate =  (CommonSqlSessionTemplate) SpringContextHolder.getBean("sqlSessionTemplate");
        SqlSessionFactory sqlSessionFactory = commonSqlSessionTemplate.getSqlSessionFactory(moduleId);
        SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (rs.next()){
            Map columnMap = new HashMap();
            for(int i=0;i<count;i++){
                String columnName = resultSetMetaData.getColumnName(count);
                columnMap.put(columnName,rs.getObject(columnName));
            }
            retLists.add(columnMap);
        }
        session.close();

        return retLists;
    }
}
