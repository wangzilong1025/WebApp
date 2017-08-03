package com.sandi.web.common.cache;

import com.sandi.web.common.cache.entity.CfgCacheLoadLogEntity;
import com.sandi.web.common.cache.service.interfaces.ICfgCacheLoadLogSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.MacUtils;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.config.Global;
import org.apache.log4j.Logger;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by dizl on 2015/6/3.
 * 默认缓存类
 */
public abstract class DefaultCache {

    private static final Logger log = Logger.getLogger(DefaultCache.class);
    private static final String moduleName = Global.getConfig(CommConstants.Config.MODULE_NAME);
    private static final String serverIp = MacUtils.getLocalHostIp();
    private static final String serverName = MacUtils.getLocalHostName();

    /**
     * 加载缓存数据
     */
    public Map<String, Object> loadCache(String cacheName) throws Exception {
        String message = "加载成功";
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        int state = CommConstants.State.STATE_NORMAL;
        Map<String, Object> resultMap = null;
        try {
            log.info("缓存" + cacheName + ",模块" + moduleName + "开始加载....");
            resultMap = this.getData();
            log.info("缓存" + cacheName + ",模块" + moduleName + "加载结束....");

        } catch (Exception e) {
            message = "缓存名:" + cacheName + ",模块名:" + moduleName + "加载失败，失败原因：" + e.getMessage();
            state = CommConstants.State.STATE_ABNORMAL;
            log.error(message,e);
            e.printStackTrace();
            throw e;
        } finally {
            try {
                ICfgCacheLoadLogSV loadLogSV = SpringContextHolder.getBean(ICfgCacheLoadLogSV.class);
                CfgCacheLoadLogEntity logEntity = new CfgCacheLoadLogEntity();//缓存日志类
                logEntity.setStartTime(startTime);//设置开始时间
                logEntity.setCacheName(cacheName);
                logEntity.setModuleName(moduleName);
                logEntity.setState(state);
                logEntity.setNotes(message);
                logEntity.setServerIp(serverIp);
                logEntity.setServerName(serverName);
                logEntity.setEndTime(new Timestamp(System.currentTimeMillis()));
                loadLogSV.saveEntity(logEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    protected abstract Map getData() throws Exception;

}