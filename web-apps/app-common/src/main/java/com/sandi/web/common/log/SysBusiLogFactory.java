package com.sandi.web.common.log;

import com.sandi.web.common.log.entity.SysBusiLogEntity;
import com.sandi.web.common.log.service.interfaces.ISysBusiLogSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.config.Global;
import com.sandi.web.utils.sec.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xt on 2016/2/24.
 */
public class SysBusiLogFactory {
    private static Logger log = LoggerFactory.getLogger(SysBusiLogFactory.class);
    private static final String moduleName = Global.getConfig(CommConstants.Config.MODULE_NAME);
    private static List<SysBusiLogEntity> entityList = new ArrayList<SysBusiLogEntity>();

    static {
        new SaveLogThread().start();
    }

    public static void success(String logType,String businessId,Date startDate,Date endDate,String content,String remark){
        saveLog(logType,businessId,startDate,endDate,content,"success",remark,-1,-1);
    }

    public static void error(String logType,String businessId,Date startDate,Date endDate,String content,String remark){
        saveLog(logType,businessId,startDate,endDate,content,"error",remark,-1,-1);
    }

    public static void saveLog(String logType,String businessId,Date startDate,Date endDate,String content,String logLevel,String remark,long opId,long orgId){
        try {
            SysBusiLogEntity entity = new SysBusiLogEntity();
            entity.setLogId(entity.newId());
            entity.setLogType(logType);
            entity.setBusinessId(businessId);
            entity.setStartDate(startDate);
            entity.setEndDate(endDate);
            if(content.length()>250){
                entity.setContent(content.substring(0,250));
            }else{
                entity.setContent(content);
            }
            entity.setLogLevel(logLevel);
            entity.setRemark(remark);
            if(opId<0){
                if(SessionManager.getUser()!=null && SessionManager.getUser().getUserId() != null) {
                    opId = SessionManager.getUser().getUserId();
                }
            }
            if(orgId<0){
                if(SessionManager.getUser()!=null && SessionManager.getUser().getUserId() != null){
                    orgId = SessionManager.getUser().getUserId();
                }
            }
            if(SessionManager.getUser()!=null){
                //entity.setModuleId(SessionManager.getUser().getModuleId());
            }
            entity.setOpId(opId);
            entity.setOrgId(orgId);
            entity.setRemark(remark);
            entity.setModule(moduleName);
            entity.setState(CommConstants.State.STATE_NORMAL);

            entityList.add(entity);
        }catch (Exception e){
            log.error("saveLog error ",e);
        }
    }
    static class SaveLogThread extends Thread {
        public void run() {
            log.info("启动操作日志线程");
            try {
                while (true) {
                    try {
                        synchronized (this) {
                            if (entityList.size() > 0) {
                                log.info("写入操作日志");
                                ISysBusiLogSV logSV = SpringContextHolder.getBean(ISysBusiLogSV.class);
                                logSV.saveLogs(entityList);
                                entityList.clear();
                            }
                        }
                        Thread.sleep(1000);//设置成1秒保存一次
                    } catch (Throwable e) {
                        log.error(e.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
