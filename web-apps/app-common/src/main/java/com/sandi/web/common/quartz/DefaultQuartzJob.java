package com.sandi.web.common.quartz;

import com.sandi.web.common.quartz.entity.CfgQuartzJobLogEntity;
import com.sandi.web.common.quartz.service.interfaces.ICfgQuartzJobSV;
import com.sandi.web.common.quartz.service.interfaces.IQuartzJobLogSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.MacUtils;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.config.Global;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.sql.Timestamp;

/**
 * Created by dizl on 2015/6/4.
 * 有状态定时任务
 */
public abstract class DefaultQuartzJob implements StatefulJob {
    private static final Log log = LogFactory.getLog(DefaultQuartzJob.class);
    private static final String moduleName = Global.getConfig(CommConstants.Config.MODULE_NAME);
    private static final String serverIp = MacUtils.getLocalHostIp();
    private static final String serverName = MacUtils.getLocalHostName();
    protected String jobName;
    protected String jobGroup;
    protected String triggerName;
    protected String triggerGroup;
    protected long jobId;
    protected String jobState;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        initParam(jobExecutionContext);

        int state = CommConstants.State.STATE_NORMAL;
        String message = "处理成功";
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        try {
            log.info("开始处理" + jobName + "任务... ...");
            doJob(jobExecutionContext);
            log.info("任务" + jobName + "处理结束.");

        } catch (JobExecutionException e) {
            message = "任务" + jobName + "处理失败，失败原因为:" + e.getMessage();
            state = CommConstants.State.STATE_ABNORMAL;
            log.error(message);
            e.printStackTrace();
            throw e;
        } finally {
            try {
                IQuartzJobLogSV quartzJobLogSV = SpringContextHolder.getBean(IQuartzJobLogSV.class);
                //如果为一次性执行，则执行后将数据移动到历史表中
                if (StringUtils.equalsIgnoreCase(CommConstants.State.I, jobState)) {
                    QuartzJobFactory.removeJob(jobName, jobGroup);
                    ICfgQuartzJobSV cfgQuartzJobSV = SpringContextHolder.getBean(ICfgQuartzJobSV.class);
                    cfgQuartzJobSV.removeCfgQuartzJob(jobId);
                }
                //记录日志信息
                CfgQuartzJobLogEntity jobLogEntity = new CfgQuartzJobLogEntity();
                jobLogEntity.setJobId(jobId);
                jobLogEntity.setStartTime(startTime);
                jobLogEntity.setJobName(jobName);
                jobLogEntity.setEndTime(new Timestamp(System.currentTimeMillis()));
                jobLogEntity.setModuleName(moduleName);
                jobLogEntity.setServerIp(serverIp);
                jobLogEntity.setServerName(serverName);
                jobLogEntity.setState(state);
                if (message.length() > 4000) {
                    message = message.substring(0, 4000);
                }
                jobLogEntity.setNotes(message);

                quartzJobLogSV.saveQuartzJobLog(jobLogEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initParam(JobExecutionContext jobExecutionContext) {
        jobName = jobExecutionContext.getJobDetail().getKey().getName();
        jobGroup = jobExecutionContext.getJobDetail().getKey().getGroup();
        triggerName = jobExecutionContext.getTrigger().getKey().getName();
        triggerGroup = jobExecutionContext.getTrigger().getKey().getGroup();
        if (jobExecutionContext.getJobDetail().getJobDataMap().containsKey("jobId")) {
            jobId = Long.parseLong(jobExecutionContext.getJobDetail().getJobDataMap().get("jobId").toString());
        }
        if (jobExecutionContext.getJobDetail().getJobDataMap().containsKey("jobState")) {
            jobState = String.valueOf(jobExecutionContext.getJobDetail().getJobDataMap().get("jobState"));
        }
    }

    protected abstract void doJob(JobExecutionContext jobContext) throws JobExecutionException;

}
