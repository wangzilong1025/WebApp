/**
 * $Id: KettleJob.java,v 1.0 2015/11/23 13:32 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.kettle;

import com.sandi.web.common.quartz.DefaultQuartzJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangrp
 * @version $Id: KettleJob.java,v 1.1 2015/11/23 13:32 zhangrp Exp $
 *          Created on 2015/11/23 13:32
 */
public class KettleJob extends DefaultQuartzJob {

    private static final Logger logger = LoggerFactory.getLogger(KettleJob.class);

    @Override
    protected void doJob(JobExecutionContext jobContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobContext.getJobDetail().getJobDataMap();
        String fileName = jobDataMap.get("jobParam") == null ? "" : jobDataMap.get("jobParam").toString().trim();
        if (fileName.equals("")) {
            throw new JobExecutionException("请配置kettle执行的文件！");
        }
        try {
            KettleInvoker.invoker(fileName, null);
        } catch (Exception e) {
            logger.error("执行报错！");
            throw new JobExecutionException(e.getMessage());
        }
    }
}
