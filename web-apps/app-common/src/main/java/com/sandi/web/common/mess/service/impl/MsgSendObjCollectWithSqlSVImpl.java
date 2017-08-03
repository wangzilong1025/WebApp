package com.sandi.web.common.mess.service.impl;

import com.sandi.web.common.mess.service.interfaces.IMsgSendObjCollectSV;
import com.sandi.web.common.support.CommonSqlSessionTemplate;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.ExceptionUtil;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * Created by dizl on 2017/3/30.
 */
public class MsgSendObjCollectWithSqlSVImpl implements IMsgSendObjCollectSV {
    private static final Logger logger = Logger.getLogger(MsgSendObjCollectWithSqlSVImpl.class);

    /**
     * 提取接受消息对象数据
     *
     * @param inputParam
     */
    @Override
    public List<Map> doCollect(Map inputParam) throws Exception {
        String sql = "";
        String moduleId = "";
        String objVal = "";
        String objType = "";
        String pkVal = "";
        List<Map> retLists = new ArrayList<Map>();
        if(inputParam.containsKey("param") && inputParam.get("param")!=null){
            sql = String.valueOf(inputParam.get("param"));
        }
        if(inputParam.containsKey("objVal") && inputParam.get("objVal")!=null){
            objVal = String.valueOf(inputParam.get("objVal"));
        }
        if(inputParam.containsKey("objType") && inputParam.get("objType")!=null){
            objType = String.valueOf(inputParam.get("objType"));
        }
        if(inputParam.containsKey("moduleId") && inputParam.get("moduleId")!=null){
            moduleId = String.valueOf(inputParam.get("moduleId"));
        }
        if(inputParam.containsKey("pkVal") && inputParam.get("pkVal")!=null){
            pkVal = String.valueOf(inputParam.get("pkVal"));
        }
        if(StringUtils.isEmpty(moduleId)){
            ExceptionUtil.throwBusinessException("提取发送对象数据时，无法找到对应的模块编号");
        }
        if(StringUtils.isEmpty(sql)){
            ExceptionUtil.throwBusinessException("提取发送对象数据，无法找到对应的SQL语句");
        }

        if(sql.contains("{pkVal}")){//如果包含lastDate字段
            sql = StringUtils.replace(sql,"{pkVal}",pkVal);
        }

        CommonSqlSessionTemplate commonSqlSessionTemplate =  (CommonSqlSessionTemplate) SpringContextHolder.getBean("sqlSessionTemplate");
        SqlSessionFactory sqlSessionFactory = commonSqlSessionTemplate.getSqlSessionFactory(moduleId);
        SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        while (rs.next()){
            boolean canAdd = false;
            Map columnMap = new HashMap();
            if(StringUtils.isNotEmpty(objType)){
                long operatorId = -1;
                if(StringUtils.isNotEmpty(objVal)){
                    operatorId = rs.getLong(objVal);
                }else{
                    operatorId = rs.getLong(1);
                }
                if(operatorId>0){
                    columnMap.put(CommConstants.Mess.USER_ID,operatorId);
                }
            }else{
                try {
                    String billId = rs.getString(CommConstants.Mess.BILL_ID);
                    if(StringUtils.isNotEmpty(billId)){
                        columnMap.put(CommConstants.Mess.BILL_ID,billId);
                        canAdd = true;
                    }
                }catch (Exception e){
                    logger.error(e);
                }
                try {
                    String email = rs.getString(CommConstants.Mess.EMAIL);
                    if(StringUtils.isNotEmpty(email)){
                        columnMap.put(CommConstants.Mess.EMAIL,email);
                        canAdd = true;
                    }
                }catch (Exception e){
                    logger.error(e);
                }
                try {
                    String userId = rs.getString(CommConstants.Mess.USER_ID);
                    if(StringUtils.isNotEmpty(userId)){
                        columnMap.put(CommConstants.Mess.USER_ID,userId);
                        canAdd = true;
                    }
                }catch (Exception e){
                    logger.error(e);
                }
            }
            if(canAdd) {
                retLists.add(columnMap);
            }
        }
        session.close();

        return retLists;
    }
}
