package com.sandi.web.common.quartz.service.impl;

import com.sandi.web.common.quartz.QuartzJobFactory;
import com.sandi.web.common.quartz.dao.ICfgQuartzJobDao;
import com.sandi.web.common.quartz.entity.CfgQuartzJobEntity;
import com.sandi.web.common.quartz.service.interfaces.ICfgQuartzJobSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.utils.common.JsonUtil;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/6/15.
 */
@Service
public class CfgQuartzJobSVImpl implements ICfgQuartzJobSV {
    @Autowired
    private ICfgQuartzJobDao cfgQuartzJobDao;

    @Override
    public List<CfgQuartzJobEntity> getAllCfgQuartzJob() throws Exception {
        CfgQuartzJobEntity entity = new CfgQuartzJobEntity();
        entity.setState(CommConstants.State.STATE_ABNORMAL);
        return cfgQuartzJobDao.findNotThisEntity(entity);
    }

    @Override
    public CfgQuartzJobEntity getCfgQuartzJobById(long jobId) throws Exception {
        return cfgQuartzJobDao.findById(jobId);
    }

    @Override
    public int removeCfgQuartzJob(long jobId) throws Exception {
        return cfgQuartzJobDao.deleteById(jobId);
    }

    @Override
    public String turnJob(String str) {
        String msg = "OK";
        try {
            boolean isStarted = QuartzJobFactory.isSchedulerStarted();
            if (!isStarted) {
                return "NOT START";
            }
            Map map = JsonUtil.json2Map(str);
            String what = (String) map.get("what");
            String triggerName = (String) map.get("triggerName");

            if (what.equals("start")) {
                int state = QuartzJobFactory.getTriggerState(triggerName, Scheduler.DEFAULT_GROUP);
                if (state == -1) {
                    CfgQuartzJobEntity jobEntity = cfgQuartzJobDao.findById(Long.parseLong((String) map.get("jobId")));
                    QuartzJobFactory.addJob(jobEntity);
                }
                QuartzJobFactory.resumeTrigger(triggerName, Scheduler.DEFAULT_GROUP);
            } else if (what.equals("stop")) {
                QuartzJobFactory.pauseTrigger(triggerName, Scheduler.DEFAULT_GROUP);
            } else {
                msg = "INPUT ERROR";
            }
        } catch (Exception e) {
            msg = e.getMessage();
        }

        return msg;
    }

    @Override
    public String turnJobs(String what) {
        String msg = "OK";
        try {
            boolean isStarted = QuartzJobFactory.isSchedulerStarted();
            if (!isStarted) {
                return "NOT START";
            }
            if (what.equals("start")) {
                QuartzJobFactory.resumeTriggers();
            } else if (what.equals("stop")) {
                QuartzJobFactory.pauseTriggers();
            } else {
                msg = "INPUT ERROR";
            }
        } catch (Exception e) {
            msg = e.getMessage();
        }
        return msg;
    }

    @Override
    public List getJobs() throws Exception {
        List<CfgQuartzJobEntity> list = getAllCfgQuartzJob();
        List rtList = new ArrayList();
        for (CfgQuartzJobEntity entity : list) {
            String triggerName = entity.getTriggerName();
            int state = QuartzJobFactory.getTriggerState(triggerName, Scheduler.DEFAULT_GROUP);
            Map map = new HashMap();
            map.put("job", entity);
            map.put("state", state);
            rtList.add(map);
        }
        return rtList;
    }

    @Override
    public String initJobs() {
        String msg = "OK";
        try {
            boolean isSchedulerStarted = QuartzJobFactory.isSchedulerStarted();
            if (!isSchedulerStarted) {
                QuartzJobFactory.initJobs();
            } else {
                msg = "IS STARTED";
            }
        } catch (Exception e) {
            msg = e.getMessage();
        }
        return msg;
    }

    @Override
    public String isSchedulerStarted() {
        boolean isSchedulerStarted = false;
        try {
            isSchedulerStarted = QuartzJobFactory.isSchedulerStarted();
        } catch (Exception e) {
            return e.getMessage();
        }
        if (isSchedulerStarted == true) {
            return "Started";
        } else {
            return "Not Started";
        }
    }

    @Override
    public String shutdownJobs() {
        String msg = "OK";
        try {
            boolean isSchedulerStarted = QuartzJobFactory.isSchedulerStarted();
            boolean isSchedulerShutdown = QuartzJobFactory.isSchedulerShutdown();
            if (isSchedulerStarted && !isSchedulerShutdown) {
                QuartzJobFactory.shutdown();
            } else {
                msg = "Not Started";
            }
        } catch (Exception e) {
            msg = e.getMessage();
        }
        return msg;
    }
}
