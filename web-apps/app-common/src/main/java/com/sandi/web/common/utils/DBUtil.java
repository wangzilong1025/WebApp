/**
 * $Id: DBUtil.java,v 1.0 2016/9/28 15:30 haomeng Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.utils;

import com.sandi.web.common.bp.entity.CfgDbConfigEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haomeng
 * @version $Id: DBUtil.java,v 1.1 2016/9/28 15:30 haomeng Exp $
 * Created on 2016/9/28 15:30
 */
public class DBUtil {
    public static String driverClassName = "oracle.jdbc.driver.OracleDriver";
    public static String url = "jdbc:oracle:thin:@10.10.149.108:1521:shrptsms";
    public static String user ="common";
    public static String password = "{RC2}Y6Unl66v2EUKYZ3naw==;";

    public final static String DEFAULT_DATASOURCE_NAME="base";

    public static List<Map> queryData(CfgDbConfigEntity entity, String sql){
        Connection conn = DBUtil.getConnection(entity);
        PreparedStatement pst = null ;
        ResultSet rs;
        List<Map> dataList = new ArrayList<Map>();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();//总共的列数
            while(rs.next()){
                Map<String,Object> map = new HashMap();
                for(int i=1;i<colCount;i++){
                    String colName = rsmd.getColumnLabel(i);
                    Object obj = rs.getObject(i);
                    map.put(colName,obj);
                }
                dataList.add(map);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据发生异常！"+e.getMessage());
        }finally{
            DBUtil.closePreparment(pst);
            DBUtil.closeConnection(conn);
            return dataList;
        }
    }

    public static Connection getConnection(CfgDbConfigEntity entity){
        try {
            Class.forName(driverClassName);
            if(entity!=null){
                url = "jdbc:oracle:thin:@"+entity.getHost()+":"+entity.getPort()+":"+entity.getSid();
                user = entity.getUserName();
                password = entity.getPassword();
            }
            Connection conn  = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接发生异常！"+e.getMessage());
        }

    }


    public static void closeConnection(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭数据库连接发生异常！"+e.getMessage());
            }
        }
    }

    public static void commit(Connection conn){
        if(conn != null){
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭数据库连接发生异常！"+e.getMessage());
            }
        }
    }

    public static void rollback(Connection conn){
        if(conn != null){
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭数据库连接发生异常！"+e.getMessage());
            }
        }
    }

    public static void closePreparment(PreparedStatement pst ){
        if(pst != null){
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭PreparedStatement发生异常！"+e.getMessage());
            }
        }
    }
}