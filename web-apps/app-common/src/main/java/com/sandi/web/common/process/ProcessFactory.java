package com.sandi.web.common.process;

import com.sandi.web.common.process.entity.CfgProcessInsEntity;
import com.sandi.web.common.process.service.interfaces.ICfgProcessInsSV;
import com.sandi.web.common.process.service.interfaces.ICfgTaskInsSV;
import com.sandi.web.utils.common.SpringContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2017/2/20.
 */
public class ProcessFactory {
    /**
     * 启动流程
     * @param processDefineId 流程编号
     * @param params 流程启动时的参数
     * @return 流程实例编号
     * */
    public static long startProcess(long processDefineId,Map params) throws Exception{
        ICfgProcessInsSV processInsSV = SpringContextHolder.getBean(ICfgProcessInsSV.class);
        CfgProcessInsEntity processInsEntity = processInsSV.startProcess(processDefineId,params);
        return processInsEntity.getProcessInsId();
    }

    /**
     * 终止流程
     * @param processInsId 流程实例编号
     * @return 是否成功
     * */
    public static void stopProcess(long processInsId) throws Exception{
        ICfgProcessInsSV processInsSV = SpringContextHolder.getBean(ICfgProcessInsSV.class);
        processInsSV.stopProcess(processInsId);
    }

    /**
     * 完成任务
     * @param taskInsId 任务实例编号
     * @param params 任务处理参数
     * @return  下一任务编号，如果为0，则标识该流程已结束
     * */
    public static void completeTask(long taskInsId,Map params) throws Exception{
        ICfgTaskInsSV taskInsSV = SpringContextHolder.getBean(ICfgTaskInsSV.class);
        taskInsSV.completeTask(taskInsId,params);
    }

    /***
     * 完成任务
     * @param processInsId 流程实例编号
     * @param taskId 任务编号
     * @param params 任务参数
     * @return 下一任务编号，如果为0，则标识该流程已结束
     * */
    public static void completeTask(long processInsId,long taskId,Map params) throws Exception{
        ICfgTaskInsSV taskInsSV = SpringContextHolder.getBean(ICfgTaskInsSV.class);
        taskInsSV.completeTask(processInsId,taskId,params);
    }

    /***
     * 根据当前用户待处理 和 已处理任务
     * */
    public List<Map> queryUserTask(Map params) throws Exception{
        ICfgTaskInsSV taskInsSV = SpringContextHolder.getBean(ICfgTaskInsSV.class);
        return taskInsSV.queryUserTask(params);
    }
}
