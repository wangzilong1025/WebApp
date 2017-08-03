package com.sandi.web.common.quartz;

import com.sandi.web.common.quartz.entity.CfgQuartzJobEntity;
import com.sandi.web.common.quartz.service.interfaces.ICfgQuartzJobSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.MacUtils;
import com.sandi.web.utils.common.JedisUtils;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.config.Global;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/6/4.
 * 定时任务
 */
public class QuartzJobFactory {
    private static final Logger log = Logger.getLogger(QuartzJobFactory.class);
    private static Scheduler scheduler = null;
    private static final String MODULE_NAME_SPLIT_FLAG = ",";//缓存表中配置的模块分隔符
    private static final String ALL_MODULE = "ALL";//所有模块
    private static final String CONFIG_NAME = "quartz.properties";
    private static final String LOCK_QUARTZ_MODULE_FLAG = "_LOCK_QUARTZ_MODULE_FLAG_";//定时任务正在处理的模块
    private static final String LOCK_QUARTZ_FLAG = "_LOCK_QUARTZ_FLAG_";
    private static final String hostIp = MacUtils.getLocalHostIp();//获取当前服务器主机编号
    private static final String hostName = MacUtils.getLocalHostName();//获取服务器主机名称
    private static final String sysModuleName = Global.getConfig(CommConstants.Config.MODULE_NAME);//当前模块名称

    static {
        try {
            initJobs();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加入一个定时任务
     *
     * @param jobName        定时任务名称
     * @param triggerName    触发器名称
     * @param cronExpression 触发器表达式
     * @param jobState       状态 I-执行一次  U-一直执行
     * @param jobClazz       job处理类
     */
    public static void addJob(long jobId, String jobName, String triggerName, String cronExpression, int jobState, Class jobClazz) throws Exception {
        if (scheduler == null) {
            StdSchedulerFactory factory = new StdSchedulerFactory(CONFIG_NAME);
            scheduler = factory.getScheduler();
        }

        JobDetail jobDetail = addJobDetail(jobId, jobName, Scheduler.DEFAULT_GROUP, jobState, jobClazz);
        Trigger trigger = addTrigger(triggerName, Scheduler.DEFAULT_GROUP, cronExpression, jobName);
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 添加定时任务
     * add by zhangrp
     *
     * @param jobEntity
     */
    public static void addJob(CfgQuartzJobEntity jobEntity) throws Exception {
        if (scheduler == null) {
            StdSchedulerFactory factory = new StdSchedulerFactory(CONFIG_NAME);
            scheduler = factory.getScheduler();
        }
        JobDetail jobDetail = addJobDetail(jobEntity);
        Trigger trigger = addTrigger(jobEntity.getTriggerName(), Scheduler.DEFAULT_GROUP, jobEntity.getCronExpression(), jobEntity.getJobName());
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void removeJob(String jobName, String groupName) throws Exception {
        if (scheduler == null) {
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();
        }
        scheduler.deleteJob(jobName, groupName);
    }

    public static boolean isSchedulerStarted() throws Exception {
        if (scheduler != null) {
            return scheduler.isStarted();
        }
        return false;
    }

    public static void startScheduler() throws Exception {
        if (scheduler != null) {
            scheduler.start();
        }
    }

    public static boolean isSchedulerShutdown() throws Exception {
        if (scheduler != null) {
            return scheduler.isShutdown();
        }
        return true;
    }

    public static void shutdown() throws Exception {
        if (scheduler == null) {
            StdSchedulerFactory factory = new StdSchedulerFactory(CONFIG_NAME);
            scheduler = factory.getScheduler();
        }
        scheduler.shutdown(false);
        destroy();
    }

    //暂停触发器
    public static void pauseTrigger(String triggerName, String group) throws Exception {
        if (scheduler == null) {
            StdSchedulerFactory factory = new StdSchedulerFactory(CONFIG_NAME);
            scheduler = factory.getScheduler();
        }
        scheduler.pauseTrigger(triggerName, group);
    }

    //暂停所有触发器
    public static void pauseTriggers() throws Exception {
        if (scheduler == null) {
            StdSchedulerFactory factory = new StdSchedulerFactory(CONFIG_NAME);
            scheduler = factory.getScheduler();
        }
        scheduler.pauseAll();
    }

    //恢复触发器
    public static void resumeTrigger(String triggerName, String group) throws Exception {
        if (scheduler == null) {
            StdSchedulerFactory factory = new StdSchedulerFactory(CONFIG_NAME);
            scheduler = factory.getScheduler();
        }
        scheduler.resumeTrigger(triggerName, group);
    }

    //恢复所有触发器
    public static void resumeTriggers() throws Exception {
        if (scheduler == null) {
            StdSchedulerFactory factory = new StdSchedulerFactory(CONFIG_NAME);
            scheduler = factory.getScheduler();
        }
        scheduler.resumeAll();
    }

    /*查看触发器状态
    *   STATE_BLOCKED 	4
        STATE_COMPLETE 	2
        STATE_ERROR 	3
        STATE_NONE 	-1
        STATE_NORMAL 	0
        STATE_PAUSED 	1
    * */
    public static int getTriggerState(String triggerName, String group) throws Exception {
        if (scheduler == null || isSchedulerShutdown()) {
            StdSchedulerFactory factory = new StdSchedulerFactory(CONFIG_NAME);
            scheduler = factory.getScheduler();
        }
        return scheduler.getTriggerState(triggerName, group);
    }

    /**
     * 查询数据库，获取所有定时任务，加载缓存定时任务
     * 在定时任务中，根据模块名称进行加载
     */
    public static void initJobs() throws Exception {
        log.info("开始加载待处理任务");
        ICfgQuartzJobSV jobSV = SpringContextHolder.getBean(ICfgQuartzJobSV.class);
        List<CfgQuartzJobEntity> quartzJobEntityList = jobSV.getAllCfgQuartzJob();

        if (quartzJobEntityList != null && quartzJobEntityList.size() > 0) {
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();
            boolean canStartScheduler = false;
            List<Map> redisQuartzModules = getRedisQuartzModule();

            for (CfgQuartzJobEntity jobEntity : quartzJobEntityList) {
                if (canDealJob(jobEntity, redisQuartzModules)) {//如果由该模块处理
                    canStartScheduler = true;
                    log.info(jobEntity.getJobName() + "可以由该模块进行处理.....");
                    String triggerName = jobEntity.getTriggerName();
                    int state = QuartzJobFactory.getTriggerState(triggerName, Scheduler.DEFAULT_GROUP);
                    if (state == -1) {
                        addJob(jobEntity);
                    }
                }
            }
            if (canStartScheduler) {
                scheduler.start();
            }
        } else {
            log.info("从cfg_quartz_job中加载数量为0");
        }
        log.info("待处理任务加载完成");
    }

    /**
     * 判断该JOB是否可以由改模块进行处理
     */
    private static boolean canDealJob(CfgQuartzJobEntity jobEntity, List<Map> redisQuartzLists) {
        //判断是否为开发环境,如果为开发环境则开发人员只启动自己测试的进程
        if (StringUtils.isNotEmpty(jobEntity.getExt1())) {
            String runEnv = System.getProperty("run-env");
            if (StringUtils.isNotEmpty(runEnv) && StringUtils.equalsIgnoreCase("dev", runEnv)) {
                String devName = System.getProperty("dev-name");
                if (!StringUtils.equalsIgnoreCase(jobEntity.getExt1(), devName)) {
                    return false;
                }
            }
        }

        boolean canDealJob = false;
        if (jobEntity.getModuleName() != null) {
            String[] moduleNames = jobEntity.getModuleName().split(MODULE_NAME_SPLIT_FLAG);
            String signleFlag = jobEntity.getSingleFlag();
            if (moduleNames != null && moduleNames.length > 0) {
                for (String tempModuleName : moduleNames) {
                    //如果为所有模块都可加载
                    if (StringUtils.equalsIgnoreCase(ALL_MODULE, tempModuleName)) {
                        //如果该quartz为所用应用只启动一个
                        if (StringUtils.equalsIgnoreCase(CommConstants.State.Y, signleFlag)) {
                            for (Map map : redisQuartzLists) {
                                String redisModuleName = (String) map.get("moduleName");
                                String redisServerIp = (String) map.get("serverIp");
                                String redisServerName = (String) map.get("serverName");
                                String allQuartzFlag = (String) map.get("allQuartz");
                                //模块相同，则判断IP是否相同，是否可处理全局进程
                                if (StringUtils.equalsIgnoreCase(redisModuleName, sysModuleName)) {
                                    if (StringUtils.isNotEmpty(redisServerIp) && StringUtils.equals(redisServerIp, hostIp) && StringUtils.equals(CommConstants.State.Y, allQuartzFlag)) {
                                        canDealJob = true;
                                        break;
                                    } else if (StringUtils.isNotEmpty(redisServerName) && StringUtils.equals(redisServerName, hostName) && StringUtils.equals(CommConstants.State.Y, allQuartzFlag)) {
                                        canDealJob = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            canDealJob = true;
                            break;
                        }
                        //如果存在该模块中
                    } else if (StringUtils.equalsIgnoreCase(sysModuleName, tempModuleName)) {
                        if (StringUtils.equalsIgnoreCase(CommConstants.State.Y, signleFlag)) {
                            for (Map map : redisQuartzLists) {
                                String redisModuleName = (String) map.get("moduleName");
                                String redisServerIp = (String) map.get("serverIp");
                                String redisServerName = (String) map.get("serverName");
                                String moduleQuartzFlag = (String) map.get("moduleQuartz");
                                //模块相同，则判断IP是否相同，是否可处理模块进程
                                if (StringUtils.equalsIgnoreCase(redisModuleName, tempModuleName)) {
                                    if (StringUtils.isNotEmpty(redisServerIp) && StringUtils.equals(redisServerIp, hostIp) && StringUtils.equals(CommConstants.State.Y, moduleQuartzFlag)) {
                                        canDealJob = true;
                                        break;
                                    } else if (StringUtils.isNotEmpty(redisServerName) && StringUtils.equals(redisServerName, hostName) && StringUtils.equals(CommConstants.State.Y, moduleQuartzFlag)) {
                                        canDealJob = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            canDealJob = true;
                            break;
                        }
                    }
                }
            }
        } else {
            log.error(jobEntity.getJobName() + "未配置模块值");
        }
        return canDealJob;
    }

    /**
     * 从调度器中根据jobName 和 jobGroup 获取 jobDetail
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    private static JobDetail getJobDetail(String jobName, String jobGroup) throws SchedulerException {
        return scheduler.getJobDetail(jobName, jobGroup);
    }

    /**
     * 向调度器中加入job
     *
     * @param jobName
     * @param jobGroup
     * @param aClass
     * @return
     * @throws SchedulerException
     */

    private static JobDetail addJobDetail(long jobId, String jobName, String jobGroup, int jobState, Class aClass) throws SchedulerException {
        JobDetail aJobDetail = getJobDetail(jobName, jobGroup);
        if (aJobDetail == null) {
            aJobDetail = new JobDetail();
            aJobDetail.setName(jobName);
            aJobDetail.setGroup(jobGroup);
            aJobDetail.setJobClass(aClass);
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("jobId", jobId);
            jobDataMap.put("jobState", jobState);
            aJobDetail.setJobDataMap(jobDataMap);

            log.info("job[" + jobName + "]" + "成功加入到调度器,对应处理类为[" + aClass.getName() + "]\n");
        } else {
            log.warn("job[" + jobName + "]" + "已经存在于调度器中,对应处理类为[" + aClass.getName() + "],不再对其进行处理..\n");
        }
        return aJobDetail;
    }

    /**
     * addJobDetail by zhangrp
     *
     * @param jobEntity
     * @return
     * @throws Exception
     */
    private static JobDetail addJobDetail(CfgQuartzJobEntity jobEntity) throws Exception {
        JobDetail aJobDetail = getJobDetail(jobEntity.getJobName(), Scheduler.DEFAULT_GROUP);
        Class jobClass = Class.forName(jobEntity.getJobImplClass());
        if (aJobDetail == null) {
            aJobDetail = new JobDetail();
            aJobDetail.setName(jobEntity.getJobName());
            aJobDetail.setGroup(Scheduler.DEFAULT_GROUP);
            aJobDetail.setJobClass(jobClass);
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("jobId", jobEntity.getJobId());
            jobDataMap.put("jobState", jobEntity.getState());
            jobDataMap.put("jobParam", jobEntity.getJobParam());
            aJobDetail.setJobDataMap(jobDataMap);
            log.info("job[" + jobEntity.getJobName() + "]" + "成功加入到调度器,对应处理类为[" + jobClass.getName() + "]\n");
        } else {
            log.warn("job[" + jobEntity.getJobName() + "]" + "已经存在于调度器中,对应处理类为[" + jobClass.getName() + "],不再对其进行处理..\n");
        }
        return aJobDetail;
    }

    /**
     * 根据 triggerName和所属group获取调度器
     *
     * @param aTriggerName
     * @param aTriggerGroup
     * @return
     * @throws SchedulerException
     */
    private static Trigger getTrigger(String aTriggerName, String aTriggerGroup) throws SchedulerException {
        return scheduler.getTrigger(aTriggerName, aTriggerGroup);
    }

    /**
     * 向调度器中加入触发器
     *
     * @param aTriggerName
     * @param aTriggerGroup
     * @param cronExpression
     * @param taskDesc
     * @return
     * @throws Exception
     */
    private static Trigger addTrigger(String aTriggerName, String aTriggerGroup, String cronExpression, String taskDesc) throws Exception {
        CronTrigger aTrigger = (CronTrigger) getTrigger(aTriggerName, aTriggerGroup);
        if (aTrigger == null) {
            aTrigger = new CronTrigger();
            aTrigger.setName(aTriggerName);
            aTrigger.setGroup(aTriggerGroup);
            aTrigger.setCronExpression(cronExpression);
            aTrigger.setDescription(taskDesc);
            log.info("Trigger[" + aTriggerName + "]" + "成功加入到调度器中.....,对应CRON_EXPRESSION为:[" + cronExpression + "]\n");
        } else {
            log.warn("Trigger[" + aTriggerName + "]" + "已经存在于调度器中,不再对其进行处理..\n");
        }

        return aTrigger;
    }

    /**
     * 获取redis当前已经启动定时任务的模块
     */
    private static List<Map> getRedisQuartzModule() throws Exception {
        List redisLockModules = null;
        boolean isNewRedisQuartzModule = true;
        int idx = 0;
        //判断是否已处于加载状态中
        while (JedisUtils.exists(LOCK_QUARTZ_FLAG) && idx < 500) {
            log.info("从缓存中获取加载quartz的模块... ...");
            idx++;
            Thread.sleep(50);
        }

        JedisUtils.set(LOCK_QUARTZ_FLAG, "Y", 0);//加锁
        if (JedisUtils.exists(LOCK_QUARTZ_MODULE_FLAG)) {
            redisLockModules = JedisUtils.getObjectList(LOCK_QUARTZ_MODULE_FLAG);
            if (redisLockModules != null && redisLockModules.size() > 0) {
                boolean hasModule = false;
                for (Object obj : redisLockModules) {
                    Map lockModule = (Map) obj;
                    //如果该模块未启动进程
                    String tempModuleName = lockModule.get("moduleName").toString();
                    if (StringUtils.equalsIgnoreCase(tempModuleName, sysModuleName)) {//如果有相同的模块已经加载
                        hasModule = true;
                        break;
                    }
                }
                Map newMap = new HashMap();
                newMap.put("moduleName", sysModuleName);//模块名称
                newMap.put("serverIp", hostIp);//主机IP
                newMap.put("serverName", hostName);//主机名称
                newMap.put("allQuartz", "N");//是否可加载标记为all的quartz
                if (!hasModule) {
                    newMap.put("moduleQuartz", "Y");//是否可加载标记为单例的本quartz模块
                } else {
                    newMap.put("moduleQuartz", "N");
                }
                redisLockModules.add(newMap);
            }
        }
        if (redisLockModules == null || redisLockModules.size() <= 0) {
            redisLockModules = new ArrayList<Map>();
            Map newMap = new HashMap();
            newMap.put("moduleName", sysModuleName);
            newMap.put("serverIp", hostIp);
            newMap.put("serverName", hostName);
            newMap.put("allQuartz", "Y");
            newMap.put("moduleQuartz", "Y");
            redisLockModules.add(newMap);
        }
        JedisUtils.setObjectList(LOCK_QUARTZ_MODULE_FLAG, redisLockModules, 0);
        JedisUtils.del(LOCK_QUARTZ_FLAG);//解锁

        return redisLockModules;
    }

    public static void destroy() throws Exception {
        List redisLockModules = null;
        //判断是否已处于加载状态中
        while (JedisUtils.exists(LOCK_QUARTZ_FLAG)) {
            log.info("从缓存中获取加载quartz的模块... ...");
            Thread.sleep(50);
        }

        JedisUtils.set(LOCK_QUARTZ_FLAG, "Y", 0);//加锁
        if (JedisUtils.existsObject(LOCK_QUARTZ_MODULE_FLAG)) {
            redisLockModules = (List) JedisUtils.getObjectList(LOCK_QUARTZ_MODULE_FLAG);
            if (redisLockModules != null && redisLockModules.size() > 0) {
                boolean hasModule = false;
                for (int i = 0; i < redisLockModules.size(); i++) {
                    Map lockModule = (Map) redisLockModules.get(i);
                    String tempModuleName = String.valueOf(lockModule.get("moduleName"));
                    String redisServerIp = String.valueOf(lockModule.get("serverIp"));
                    String redisServerName = String.valueOf(lockModule.get("serverName"));
                    if (StringUtils.equalsIgnoreCase(tempModuleName, sysModuleName) && (StringUtils.equals(redisServerIp, hostIp) || StringUtils.equals(redisServerName, hostName))) {//如果有相同的模块已经加载
                        redisLockModules.remove(i);
                        i--;
                    }
                }
            }
            if (redisLockModules.size() == 0) {
                JedisUtils.del(LOCK_QUARTZ_MODULE_FLAG);
            } else {
                JedisUtils.setObjectList(LOCK_QUARTZ_MODULE_FLAG, redisLockModules, 0);
            }
        }
        JedisUtils.del(LOCK_QUARTZ_FLAG);//解锁
    }
}
