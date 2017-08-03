package com.sandi.web.common.mess.job;

import com.sandi.web.common.mess.entity.*;
import com.sandi.web.common.mess.service.interfaces.*;
import com.sandi.web.common.quartz.DefaultQuartzJob;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.sec.SecManage;
import com.sandi.web.utils.sec.entity.Operator;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.*;

/**
 * Created by dizl on 2017/2/13.
 * 消息数据提取
 */
public class MessDataExtractQueryJob extends DefaultQuartzJob {
    private static final Logger logger = Logger.getLogger(MessDataExtractQueryJob.class);

    @Override
    /**
     * 1、根据job中配置的消息提取等级获取消息配置数据
     * 2、调用配置的消息数据提取类，提取消息数据
     * **/
    protected void doJob(JobExecutionContext jobContext) throws JobExecutionException {
        try{
            ICfgMsgInfoSV cfgMsgInfoSV = SpringContextHolder.getBean(ICfgMsgInfoSV.class);

            JobDataMap jobDataMap = jobContext.getJobDetail().getJobDataMap();
            if(jobDataMap!=null){
                String configParam = jobDataMap.get("jobParam")==null?"":String.valueOf(jobDataMap.get("jobParam"));
                if(StringUtils.isBlank(configParam)){
                    throw new JobExecutionException("请配置消息提醒等级");
                }else{
                    List<CfgMsgInfoEntity> cfgMsgInfoEntityList = cfgMsgInfoSV.getCfgMsgInfoBySendLevel(configParam);
                    if(cfgMsgInfoEntityList!=null && cfgMsgInfoEntityList.size()>0){
                        for(CfgMsgInfoEntity entity : cfgMsgInfoEntityList){
                            List<Map> sendDataList = getSendData(entity);
                            if(sendDataList!=null && sendDataList.size()>0){
                               //提取可发送消息的数据
                                List<Map> canSendDataList = getCanSendData(entity,sendDataList);
                                if(canSendDataList!=null && canSendDataList.size()>0){
                                    ICfgMsgTemplateSV cfgMsgTemplateSV = SpringContextHolder.getBean(ICfgMsgTemplateSV.class);
                                    List<CfgMsgTemplateEntity> cfgMsgTemplateEntityList = cfgMsgTemplateSV.getEntityByMsgId(entity.getMsgId());
                                    for(Map sendDataMap : canSendDataList){
                                        sendData(entity,cfgMsgTemplateEntityList,sendDataMap);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error(e);
            throw new JobExecutionException(e.getMessage());
        }
    }

    /**
     * 获取待发送数据
     * */
    private List<Map> getSendData(CfgMsgInfoEntity entity) throws Exception{
        ICfgMsgCollectDateSV cfgMsgCollectDateSV = SpringContextHolder.getBean(ICfgMsgCollectDateSV.class);
        Date currDate = DateUtils.getCurrentDate();
        Date dbDate = DateUtils.getCurrentDateFromDB();
        //如果当前数据库日期比系统日期小,则当前时间设置为数据库时间
        if(dbDate!=null) {
            if (dbDate.getTime() - currDate.getTime() > 0) {
                currDate = dbDate;
            }
        }

        String dataCollectClazz = entity.getDataCollectClass();
        String dataCollectParam = entity.getDataCollectParam();
        Map inputMap = new HashMap();
        //查询该业务最后一次数据抽取时间
        CfgMsgCollectDateEntity collectDateEntity = cfgMsgCollectDateSV.getCfgMsgCollectDateEntity(entity.getBusiMsgId());
        boolean newCollectData = false;
        if(collectDateEntity==null){
            newCollectData = true;
            collectDateEntity = new CfgMsgCollectDateEntity();
            collectDateEntity.setBusiMsgId(entity.getBusiMsgId());
            collectDateEntity.setExtractDate(currDate);
        }
        inputMap.put("param",dataCollectParam);
        inputMap.put("lastDate",collectDateEntity.getExtractDate());
        inputMap.put("moduleId",entity.getModuleId());

        IMsgDataCollectSV collectSV = (IMsgDataCollectSV)Class.forName(dataCollectClazz).newInstance();
        List<Map> lists = collectSV.doCollect(inputMap);
        if(lists!=null && lists.size()>0){
            //保存最后一次数据抽取时间
            collectDateEntity.setExtractDate(currDate);
            if(newCollectData){
                cfgMsgCollectDateSV.saveEntity(collectDateEntity);
            }else{
                cfgMsgCollectDateSV.updateEntity(collectDateEntity);
            }
        }
        return lists;
    }

    /**
     * 过滤消息发送数据，剔除已经发送的数据
     * */
    private List<Map> getCanSendData(CfgMsgInfoEntity cfgMsgInfoEntity,List<Map> sendDataList) throws Exception{
        ICfgMsgCollectResultSV collectResultSV = SpringContextHolder.getBean(ICfgMsgCollectResultSV.class);
        List<CfgMsgCollectResultEntity> collectResultEntityList = collectResultSV.getCollectResultByBusiMsgId(cfgMsgInfoEntity.getBusiMsgId());
        List<Map> retList = new ArrayList<Map>();
        String pkNbr = cfgMsgInfoEntity.getPkNbr();//主键编号
        if(collectResultEntityList!=null && collectResultEntityList.size()>0){
            List<CfgMsgCollectResultEntity> notDealResultEntityList = new ArrayList<CfgMsgCollectResultEntity>();
            List<CfgMsgCollectResultEntity> newResultEntityList = new ArrayList<CfgMsgCollectResultEntity>();
            for(Map sendData : sendDataList){
                if(sendData.containsKey(pkNbr)) {
                    String pkValue = sendData.get(pkNbr).toString();
                    boolean canSend = true;
                    for(CfgMsgCollectResultEntity collectResultEntity : collectResultEntityList) {
                        if(StringUtils.equals(pkValue,collectResultEntity.getPkValue())){
                            notDealResultEntityList.add(collectResultEntity);
                            canSend = false;
                        }
                    }
                    if(canSend){
                        retList.add(sendData);

                        CfgMsgCollectResultEntity resultEntity = new CfgMsgCollectResultEntity();
                        resultEntity.setResultId(resultEntity.newId());
                        resultEntity.setBusiMsgId(cfgMsgInfoEntity.getBusiMsgId());
                        resultEntity.setPkNbr(cfgMsgInfoEntity.getPkNbr());
                        resultEntity.setPkValue(pkValue);
                        resultEntity.setState(CommConstants.State.STATE_NORMAL);
                        resultEntity.setCreateDate(DateUtils.getCurrentDate());
                        newResultEntityList.add(resultEntity);
                    }
                }
            }
            collectResultEntityList.removeAll(notDealResultEntityList);
            //删除不需要处理的数据
            if(collectResultEntityList!=null && collectResultEntityList.size()>0) {
                collectResultSV.deleteEntity(collectResultEntityList);
            }
            //插入新的数据
            if(newResultEntityList!=null && newResultEntityList.size()>0){
                collectResultSV.saveEntity(newResultEntityList);
            }
        }else{
            retList = sendDataList;
        }
        return retList;
    }

    /**
     * 消息待发送
     * */
    private void sendData(CfgMsgInfoEntity msgInfoEntity,List<CfgMsgTemplateEntity> msgTemplateEntityList,Map sendDataMap) throws Exception{
        if(msgTemplateEntityList!=null && msgTemplateEntityList.size()>0){
            List<CfgMsgInfoInstEntity> cfgMsgInfoInstEntityList = new ArrayList<CfgMsgInfoInstEntity>();
            Date currDate = DateUtils.getCurrentDate();
            //获取发送对象
            List<Map> sendObjList = getSendObj(msgInfoEntity,sendDataMap);
            if(sendObjList!=null && sendObjList.size()>0){
                String pkVal = "";
                Date sendDate = currDate;
                Date msgExpireDate = null;
                if(sendDataMap.containsKey(msgInfoEntity.getPkNbr())){
                    pkVal = String.valueOf(sendDataMap.get(msgInfoEntity.getPkNbr()));
                }
                if(msgInfoEntity.getSendType()==1){//立即发送
                    sendDate = currDate;
                }else{//固定时间发送 或 周期性发送
                    if(StringUtils.isNotEmpty(msgInfoEntity.getSendRate())) {
                        sendDate = DateUtils.getNextValidTimeAfter(currDate, msgInfoEntity.getSendRate());
                    }
                    if(StringUtils.isNotEmpty(msgInfoEntity.getSendDuration())){
                        msgExpireDate = DateUtils.getNextValidTimeAfter(currDate,msgInfoEntity.getSendDuration());
                    }
                }
                for(Map sendObj : sendObjList){
                    Long userId = null;
                    String billId = null;
                    String email = null;
                    if(sendObj.containsKey(CommConstants.Mess.USER_ID)){
                        userId = Long.parseLong(String.valueOf(sendObj.get(CommConstants.Mess.USER_ID)));
                    }
                    if(sendObj.containsKey(CommConstants.Mess.BILL_ID)){
                        billId = String.valueOf(sendObj.get(CommConstants.Mess.BILL_ID));
                    }
                    if(sendObj.containsKey(CommConstants.Mess.EMAIL)){
                        email = String.valueOf(sendObj.get(CommConstants.Mess.EMAIL));
                    }

                    for(CfgMsgTemplateEntity msgTemplateEntity : msgTemplateEntityList){
                        String objVal = null;
                        //如果为邮件
                        if(StringUtils.equalsIgnoreCase(CommConstants.Mess.EMAIL,msgTemplateEntity.getMsgType()) && StringUtils.isNotEmpty(email)){
                            objVal = email;
                        }else if(StringUtils.equalsIgnoreCase(CommConstants.Mess.SMS,msgTemplateEntity.getMsgType()) && StringUtils.isNotEmpty(billId)){
                            objVal = billId;
                        }else{
                            objVal = userId+"";
                        }
                        if(StringUtils.isNotEmpty(objVal)){
                            //转化消息内容
                            CfgMsgInfoInstEntity msgInfoInstEntity = new CfgMsgInfoInstEntity();
                            msgInfoInstEntity.setMsgInfoId(msgInfoInstEntity.newId());
                            msgInfoInstEntity.setBusiMsgId(msgInfoEntity.getBusiMsgId());
                            msgInfoInstEntity.setMsgId(msgInfoEntity.getMsgId());
                            msgInfoInstEntity.setMsgType(msgTemplateEntity.getMsgType());
                            msgInfoInstEntity.setMsgTitle(msgTemplateEntity.getMsgName());
                            msgInfoInstEntity.setMsgText(covertMsgText(msgTemplateEntity.getMsgText(),sendDataMap));
                            msgInfoInstEntity.setUserId(userId);
                            msgInfoInstEntity.setPkValue(pkVal);
                            msgInfoInstEntity.setSendObjVal(objVal);
                            msgInfoInstEntity.setSendRate(msgInfoEntity.getSendRate());
                            msgInfoInstEntity.setSendDate(sendDate);
                            msgInfoInstEntity.setSendCount(0);
                            msgInfoInstEntity.setSendMaxCount(msgInfoEntity.getSendMaxCount());
                            msgInfoInstEntity.setMsgExpireDate(msgExpireDate);
                            msgInfoInstEntity.setModuleId(msgInfoEntity.getModuleId());
                            msgInfoInstEntity.setState(CommConstants.State.STATE_NORMAL);
                            msgInfoInstEntity.setCreateDate(currDate);

                            cfgMsgInfoInstEntityList.add(msgInfoInstEntity);
                        }
                    }
                }
            }
            if(cfgMsgInfoInstEntityList!=null && cfgMsgInfoInstEntityList.size()>0){
                ICfgMsgInfoInstSV cfgMsgInfoInstSV = SpringContextHolder.getBean(ICfgMsgInfoInstSV.class);
                cfgMsgInfoInstSV.saveEntity(cfgMsgInfoInstEntityList);

            }
        }
    }

    /**
     * 获取发送对象
     * */
    private List<Map> getSendObj(CfgMsgInfoEntity cfgMsgInfoEntity,Map sendDataMap) throws Exception{
        ICfgMsgSendObjSV msgSendObjSV = SpringContextHolder.getBean(ICfgMsgSendObjSV.class);
        List<CfgMsgSendObjEntity> msgSendObjEntityList = msgSendObjSV.getSendObjEntityByBusiMsgId(cfgMsgInfoEntity.getBusiMsgId());
        List<Map> retList = new ArrayList<Map>();
        if(msgSendObjEntityList!=null && msgSendObjEntityList.size()>0){
            for(CfgMsgSendObjEntity msgSendObjEntity : msgSendObjEntityList){
                String objVal = msgSendObjEntity.getObjVal();
                String objType = msgSendObjEntity.getObjType();
                if(StringUtils.isNotEmpty(msgSendObjEntity.getSendObjDealClass())) {
                    IMsgSendObjCollectSV sendObjCollectSV = (IMsgSendObjCollectSV) Class.forName(msgSendObjEntity.getSendObjDealClass()).newInstance();
                    Map params = new HashMap();
                    params.put("param",msgSendObjEntity.getSendObjDealParam());
                    params.put("objType",objType);
                    params.put("objVal",objVal);
                    if(StringUtils.isNotEmpty(cfgMsgInfoEntity.getPkNbr())) {
                        params.put("pkVal", sendDataMap.get(cfgMsgInfoEntity.getPkNbr()));
                    }
                    params.put("moduleId",cfgMsgInfoEntity.getModuleId());
                    params.put("data",sendDataMap);
                    List<Map> lists = sendObjCollectSV.doCollect(params);
                    if(lists!=null && lists.size()>0){
                        for(Map map :lists){
                            if(map.get(CommConstants.Mess.USER_ID)!=null){
                                String userId = map.get(CommConstants.Mess.USER_ID).toString();
                                if (StringUtils.equalsIgnoreCase("operator", objType)) {//指定操作员
                                    Operator operator = SecManage.getOperatorByOperId(Long.parseLong(userId));
                                    if (operator != null) {
                                        addOperatorInfo(operator, retList);
                                    }
                                } else if (StringUtils.equalsIgnoreCase("station", objType)) {//指定岗位
                                    List<Operator> operatorList = SecManage.getOperatorByStation(Long.parseLong(userId));
                                    if (operatorList != null && operatorList.size() > 0) {
                                        for (Operator operator : operatorList) {
                                            addOperatorInfo(operator, retList);
                                        }
                                    }
                                } else if (StringUtils.equalsIgnoreCase("stationtype", objType)) {//指定岗位类型
                                    List<Operator> operatorList = SecManage.getOperatorByStationType(Long.parseLong(userId));
                                    if(operatorList!=null && operatorList.size()>0){
                                        for(Operator operator : operatorList){
                                            addOperatorInfo(operator,retList);
                                        }
                                    }
                                }
                            }else{
                                retList.add(map);
                            }
                        }
                    }
                }else{
                    if(objVal.contains(CommConstants.Mess.START_FLAG) && objVal.contains(CommConstants.Mess.END_FLAG)){
                        int startIdx = objVal.indexOf(CommConstants.Mess.START_FLAG, 0) + CommConstants.Mess.END_FLAG.length();
                        int endIdx = objVal.indexOf(CommConstants.Mess.END_FLAG, startIdx);
                        String replaceKey = objVal.substring(startIdx, endIdx);
                        if(sendDataMap.containsKey(replaceKey)){
                            objVal = String.valueOf(sendDataMap.get(replaceKey));
                        }
                        if(StringUtils.isNotBlank(objVal)) {
                            if (StringUtils.equalsIgnoreCase("operator", objType)) {//指定操作员
                                Operator operator = SecManage.getOperatorByOperId(Long.parseLong(objVal));
                                if (operator != null) {
                                    addOperatorInfo(operator, retList);
                                }
                            } else if (StringUtils.equalsIgnoreCase("station", objType)) {//指定岗位
                                List<Operator> operatorList = SecManage.getOperatorByStation(Long.parseLong(objVal));
                                if (operatorList != null && operatorList.size() > 0) {
                                    for (Operator operator : operatorList) {
                                        addOperatorInfo(operator, retList);
                                    }
                                }
                            } else if (StringUtils.equalsIgnoreCase("stationtype", objType)) {//指定岗位类型
                                List<Operator> operatorList = SecManage.getOperatorByStationType(Long.parseLong(objVal));
                                if(operatorList!=null && operatorList.size()>0){
                                    for(Operator operator : operatorList){
                                        addOperatorInfo(operator,retList);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return retList;
    }

    /**
     * 员工编号、手机号码、email
     * */
    private void addOperatorInfo(Operator operator,List<Map> mapList){
        for(Map map : mapList){
            String userId = map.get(CommConstants.Mess.USER_ID).toString();
            if(StringUtils.equalsIgnoreCase(userId,operator.getOperatorId()+"")){//如果已经存在
                return;
            }
        }
        Map newMap = new HashMap();
        newMap.put(CommConstants.Mess.USER_ID,operator.getOperatorId()+"");
        newMap.put(CommConstants.Mess.BILL_ID,operator.getBillId());
        newMap.put(CommConstants.Mess.EMAIL,operator.getEmail());

        mapList.add(newMap);
    }

    private String covertMsgText(String msgText,Map params){
        String resultText = msgText;
        for (; resultText.contains(CommConstants.Mess.START_FLAG) && resultText.contains(CommConstants.Mess.END_FLAG); ) {
            int startIdx = resultText.indexOf(CommConstants.Mess.START_FLAG, 0) + CommConstants.Mess.END_FLAG.length();
            int endIdx = resultText.indexOf(CommConstants.Mess.START_FLAG, startIdx);
            String replaceKey = resultText.substring(startIdx, endIdx);
            String replaceValue = "";
            if(params.containsKey(replaceKey)){
                replaceValue = String.valueOf(params.get(replaceKey));
            }
            resultText = StringUtils.replace(resultText, CommConstants.Mess.START_FLAG + replaceKey + CommConstants.Mess.END_FLAG, replaceValue);
        }
        return resultText;
    }
}
