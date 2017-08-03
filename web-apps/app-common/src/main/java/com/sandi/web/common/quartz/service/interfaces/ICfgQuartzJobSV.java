package com.sandi.web.common.quartz.service.interfaces;


import com.sandi.web.common.quartz.entity.CfgQuartzJobEntity;

import java.util.List;

/**
 * Created by dizl on 2015/6/15.
 */
public interface ICfgQuartzJobSV {
    public CfgQuartzJobEntity getCfgQuartzJobById(long jobId) throws Exception;

    public List<CfgQuartzJobEntity> getAllCfgQuartzJob() throws Exception;

    public int removeCfgQuartzJob(long jobId) throws Exception;

    public String turnJob(String str) throws Exception;

    public String turnJobs(String what) throws Exception;

    public List getJobs() throws Exception;

    public String initJobs() throws Exception;

    public String isSchedulerStarted() throws Exception;

    public String shutdownJobs() throws Exception;
}
