package com.sandi.web.common.tools.service.impl;

import com.sandi.web.common.support.CommonSqlSessionTemplate;
import com.sandi.web.common.tools.Table2BeanUtil;
import com.sandi.web.common.tools.dao.ITable2BeanDao;
import com.sandi.web.common.tools.service.interfaces.ITable2BeanSV;
import com.sandi.web.utils.common.SpringContextHolder;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuqin on 2016/8/28.
 */
@Service
public class Table2BeanSVImpl implements ITable2BeanSV {
    @Autowired
    ITable2BeanDao table2BeanDao;

    @Override
    public String createBean(List tableList, String path,String dataSource) throws Exception {
        String msg = "OK";
        for (int i = 0; i < tableList.size(); i++) {
            Map map = (Map) tableList.get(i);
            List tab_columns = (List) map.get("columns");
            String tableName = (String) map.get("tableName");
            List<String> constraintKey = new ArrayList<String>();
            String sql="  SELECT cu.COLUMN_NAME FROM user_cons_columns cu,user_constraints au WHERE cu.constraint_name = au.constraint_name AND au.constraint_type = 'P' AND au.table_name = '"+tableName+"'";
            CommonSqlSessionTemplate commonSqlSessionTemplate =  (CommonSqlSessionTemplate) SpringContextHolder.getBean("sqlSessionTemplate");
            SqlSessionFactory sqlSessionFactory = commonSqlSessionTemplate.getSqlSessionFactory(dataSource);
            SqlSession session = sqlSessionFactory.openSession();
            Connection conn = session.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                constraintKey.add(rs.getString("COLUMN_NAME"));
            }
            try {
                Table2BeanUtil.create(tab_columns, tableName, path, constraintKey);
            } catch (Exception e) {
                msg = e.getMessage();
            }
            session.close();
        }

        return msg;
    }

    @Override
    public List getTableInfo(String tableNames,String dataSource) throws Exception {
        List rtList = new ArrayList();
//        String[] tableName = tableNames.split(",");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        CommonSqlSessionTemplate commonSqlSessionTemplate =  (CommonSqlSessionTemplate)SpringContextHolder.getBean("sqlSessionTemplate");
        String sql = "SELECT T.COLUMN_NAME, T.DATA_TYPE, T.DATA_LENGTH, T.DATA_PRECISION, C.COMMENTS FROM ALL_TAB_COLUMNS T, USER_COL_COMMENTS C WHERE T.Table_Name = C.Table_Name AND T.COLUMN_NAME = C.COLUMN_NAME AND T.TABLE_NAME = '"+tableNames+"'";
        SqlSessionFactory sqlSessionFactory = commonSqlSessionTemplate.getSqlSessionFactory(dataSource);
        SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
            resultMap.put("DATA_TYPE",rs.getString("DATA_TYPE"));
            resultMap.put("DATA_LENGTH",rs.getString("DATA_LENGTH"));
            resultMap.put("DATA_PRECISION",rs.getString("DATA_PRECISION"));
            resultMap.put("COMMENTS",rs.getString("COMMENTS"));
            list.add(resultMap);
        }
            if (list.size()>0){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("tableName",tableNames);
                map.put("columns",list);
                rtList.add(map);
            }
        session.close();
        return rtList;
    }

}
