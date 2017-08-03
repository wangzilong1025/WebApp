package com.sandi.web.common.cache.job;

import com.sandi.web.common.cache.CacheFactory;
import com.sandi.web.common.quartz.DefaultQuartzJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.StringUtils;

/**
 * Created by dizl on 2015/6/15.
 * 对加入缓存的数据进行处理
 */
public class CacheLoadJob extends DefaultQuartzJob {
    public static final String FLAG = "cache_job_";

    @Override
    protected void doJob(JobExecutionContext jobContext) throws JobExecutionException {

        String cacheName = StringUtils.replace(jobName, FLAG, "");//将flag标识进行替换，找到cacheName数据
        try {
            CacheFactory.reloadCache(cacheName);//重新加载缓存
        } catch (Exception e) {
            e.printStackTrace();
            throw new JobExecutionException(e.getCause());
        }
    }
}
