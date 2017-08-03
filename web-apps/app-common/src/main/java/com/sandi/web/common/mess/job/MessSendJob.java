package com.sandi.web.common.mess.job;

import com.sandi.web.common.mess.entity.CfgMsgInfoInstEntity;
import com.sandi.web.common.mess.entity.CfgMsgInfoInstErrorEntity;
import com.sandi.web.common.mess.entity.CfgMsgInfoInstHisEntity;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgInfoInstErrorSV;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgInfoInstHisSV;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgInfoInstSV;
import com.sandi.web.common.quartz.DefaultQuartzJob;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.common.utils.SendMsgUtil;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.response.Response;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dizl on 2017/2/13.
 * 消息发送，
 */
public class MessSendJob extends DefaultQuartzJob {
    private static final Logger logger = Logger.getLogger(MessSendJob.class);

    @Override
    protected void doJob(JobExecutionContext jobContext) throws JobExecutionException {
        //获取所有待发送数据
        try {
            ICfgMsgInfoInstSV cfgMsgInfoInstSV = SpringContextHolder.getBean(ICfgMsgInfoInstSV.class);
            ICfgMsgInfoInstHisSV cfgMsgInfoInstHisSV = SpringContextHolder.getBean(ICfgMsgInfoInstHisSV.class);
            ICfgMsgInfoInstErrorSV cfgMsgInfoInstErrorSV = SpringContextHolder.getBean(ICfgMsgInfoInstErrorSV.class);

            List<CfgMsgInfoInstEntity> msgInfoInstEntityList = cfgMsgInfoInstSV.getSendMsg();
            if(msgInfoInstEntityList!=null && msgInfoInstEntityList.size()>0) {
                Date currDate = DateUtils.getCurrentDate();
                List<CfgMsgInfoInstEntity> newInstInfoList = new ArrayList<CfgMsgInfoInstEntity>();
                List<CfgMsgInfoInstHisEntity> hisInstInfoList = new ArrayList<CfgMsgInfoInstHisEntity>();
                List<CfgMsgInfoInstErrorEntity> errorInstInfoList = new ArrayList<CfgMsgInfoInstErrorEntity>();
                for (CfgMsgInfoInstEntity msgInfoInstEntity : msgInfoInstEntityList) {
                    String errorMsg = null;
                    try {
                        //调用短信接口发送短信
                        String objVal = msgInfoInstEntity.getSendObjVal();
                        Response response = null;
                        if(StringUtils.equalsIgnoreCase(CommConstants.Mess.SMS,msgInfoInstEntity.getMsgType())){
                            response = SendMsgUtil.sendSms(objVal, msgInfoInstEntity.getMsgText(), DateUtils.formatDate(msgInfoInstEntity.getSendDate()));
                        }else if(StringUtils.equalsIgnoreCase(CommConstants.Mess.EMAIL,msgInfoInstEntity.getMsgType())){
                            response = SendMsgUtil.sendCommonMail(objVal, msgInfoInstEntity.getMsgTitle(),msgInfoInstEntity.getMsgText());
                        }
                        if (StringUtils.equals(Response.SUCCESS, response.getErrorInfo().getCode())) {//发送成功
                            //将该记录移动到历史表中
                            hisInstInfoList.add(SendMsgUtil.copy2His(msgInfoInstEntity));
                            if (msgInfoInstEntity.getSendType() == 3) {//周期性发送
                                boolean sendFlag = true;
                                if (msgInfoInstEntity.getSendMaxCount() != null && msgInfoInstEntity.getSendMaxCount() > 0 && msgInfoInstEntity.getSendMaxCount() <= msgInfoInstEntity.getSendCount()) {//已经达到发送次数
                                    sendFlag = false;
                                }
                                if (msgInfoInstEntity.getMsgExpireDate() != null && msgInfoInstEntity.getMsgExpireDate().compareTo(currDate) < 0) {
                                    sendFlag = false;
                                }
                                if (sendFlag) {
                                    CfgMsgInfoInstEntity newEntity = SendMsgUtil.copy(msgInfoInstEntity);
                                    newEntity.setMsgInfoId(newEntity.newId());
                                    newEntity.setSendCount(newEntity.getSendCount() + 1);
                                    newEntity.setSendDate(DateUtils.getNextValidTimeAfter(newEntity.getSendDate(), newEntity.getSendRate()));
                                    newEntity.setCreateDate(currDate);

                                    newInstInfoList.add(msgInfoInstEntity);
                                }
                            }
                        } else {//发送失败
                            errorMsg = response.getErrorInfo().getMessage();
                        }
                    } catch (Exception e) {
                        logger.error(e);
                        errorMsg = e.getMessage();
                    }

                    if(StringUtils.isNotEmpty(errorMsg)) {//如果发送失败
                        CfgMsgInfoInstErrorEntity errorEntity = SendMsgUtil.copy2Error(msgInfoInstEntity);
                        if (errorMsg.length() > 2000) {
                            errorMsg = errorMsg.substring(0, 2000);
                        }
                        errorEntity.setErrorMsg(errorMsg);
                        errorInstInfoList.add(errorEntity);
                        if (msgInfoInstEntity.getSendMaxCount() != null && msgInfoInstEntity.getSendMaxCount() > 0 && msgInfoInstEntity.getSendMaxCount() > msgInfoInstEntity.getSendCount()) {//没有到达发送次数
                            CfgMsgInfoInstEntity newEntity = SendMsgUtil.copy(msgInfoInstEntity);
                            newEntity.setSendCount(newEntity.getSendCount() + 1);
                            newEntity.setCreateDate(currDate);

                            newInstInfoList.add(msgInfoInstEntity);
                        }
                    }
                }
                //保存数据
                if(hisInstInfoList.size()>0){
                    cfgMsgInfoInstHisSV.saveEntity(hisInstInfoList);
                }
                if(errorInstInfoList.size()>0){
                    cfgMsgInfoInstErrorSV.saveEntity(errorInstInfoList);
                }
                if(newInstInfoList.size()>0){
                    cfgMsgInfoInstSV.saveEntity(newInstInfoList);
                }
                cfgMsgInfoInstSV.deleteEntity(msgInfoInstEntityList);//删除数据

            }
        }catch(Exception e1){
            logger.error(e1);
            throw new JobExecutionException(e1.getMessage());
        }
    }
}
