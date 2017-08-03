/**
 * $Id: SysBusiLogFSVImpl.java,v 1.0 2016/8/31 14:51 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.api.syslog;

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.log.SysBusiLogFactory;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.utils.api.syslog.ISysBusiLogFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.response.Response;
import org.apache.log4j.Logger;
import java.util.Date;
import java.util.Map;

/**
 * @author dizl
 * @version $Id: SysBusiLogFSVImpl.java,v 1.1 2016/8/31 14:51 dizl Exp $
 * Created on 2016/8/31 14:51
 */
@Service
public class SysBusiLogFSVImpl implements ISysBusiLogFSV {
    private static final Logger logger = Logger.getLogger(SysBusiLogFSVImpl.class);
    /**
     * 保存系统服务日志
     * @param requestJson
     */
    @Override
    public String srvLog(String requestJson) {
        Response response = new Response();
        try {
            Map params = JsonUtil.json2Map(requestJson);
            String logType = "";
            String businessId = "";
            Date startDate = null;
            Date endDate = null;
            String content = "";
            String remark = "";
            String isSuccess = "0";//0-成功  1-失败

            if(params.containsKey("LOG_TYPE")){
                logType = params.get("LOG_TYPE")+"";
            }
            if(params.containsKey("BUSINESS_ID")){
                businessId = params.get("BUSINESS_ID")+"";
            }
            if(params.containsKey("START_DATE")){
                startDate = DateUtils.parseDate(params.get("START_DATE"));
            }
            if(params.containsKey("END_DATE")){
                endDate = DateUtils.parseDate(params.get("END_DATE"));
            }
            if(params.containsKey("CONTENT")){
                content = params.get("CONTENT")+"";
            }
            if(params.containsKey("REMARK")){
                remark = params.get("REMARK")+"";
            }
            if(params.containsKey("IS_SUCCESS")){
                isSuccess = params.get("IS_SUCCESS")+"";
            }
            if(StringUtils.equals(Response.SUCCESS,isSuccess)) {
                SysBusiLogFactory.success(logType, businessId, startDate, endDate, content, remark);
            }else{
                SysBusiLogFactory.error(logType, businessId, startDate, endDate, content, remark);
            }
            response.setCode(Response.SUCCESS);
        }catch(Exception e){
            logger.error("服务日志记录异常",e);
            response.setCode(Response.ERROR);
        }
        return response.toString();
    }

    /**
     * 菜单访问日志
     * @param requestJson
     */
    @Override
    public String menuLog(String requestJson) {
        Response response = new Response();
        try{
            Map params = JsonUtil.json2Map(requestJson);
            if(params!=null){
                String funcId = "";
                String url = "";
                String funcName = "";
                if(params.containsKey("funcId")){//菜单编号
                    funcId = params.get("funcId").toString();
                }
                if(params.containsKey("url")){//菜单路径
                    url = params.get("url").toString();
                }
                if(params.containsKey("funcName")){//菜单名称
                    funcName = params.get("funcName").toString();
                }

                SysBusiLogFactory.success("MENU",funcId,new Date(),new Date(),url,funcName);
            }
        }catch (Exception e){
            logger.error("菜单日志记录异常",e);
            response.setCode(Response.ERROR);
        }
        return response.toString();
    }
}
