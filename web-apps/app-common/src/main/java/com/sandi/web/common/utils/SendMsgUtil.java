/**
 * Copyright &copy; 2012-2014 <a href="https://github.com//jeesite">JeeSite</a> All rights reserved.
 */
package com.sandi.web.common.utils;

import com.sandi.web.common.http.ServerUtil;
import com.sandi.web.common.mess.entity.CfgMsgInfoInstEntity;
import com.sandi.web.common.mess.entity.CfgMsgInfoInstErrorEntity;
import com.sandi.web.common.mess.entity.CfgMsgInfoInstHisEntity;
import com.sandi.web.utils.response.Response;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发送电子邮件
 */
public class SendMsgUtil {

    // private static final String smtphost = "192.168.1.70";
    private static final String from = "@163.com";
    private static final String fromName = "测试公司";
    private static final String charSet = "utf-8";
    private static final String username = "@163.com";
    private static final String password = "123456";

    private static Map<String, String> hostMap = new HashMap<String, String>();

    static {
        // 126
        hostMap.put("smtp.126", "smtp.126.com");
        // qq
        hostMap.put("smtp.qq", "smtp.qq.com");
        // 163
        hostMap.put("smtp.163", "smtp.163.com");
        // sina
        hostMap.put("smtp.sina", "smtp.sina.com.cn");
        // tom
        hostMap.put("smtp.tom", "smtp.tom.com");
        // 263
        hostMap.put("smtp.263", "smtp.263.net");
        // yahoo
        hostMap.put("smtp.yahoo", "smtp.mail.yahoo.com");
        // hotmail
        hostMap.put("smtp.hotmail", "smtp.live.com");
        // gmail
        hostMap.put("smtp.gmail", "smtp.gmail.com");
        hostMap.put("smtp.port.gmail", "465");
    }

    public static String getHost(String email) throws Exception {
        Pattern pattern = Pattern.compile("\\w+@(\\w+)(\\.\\w+){1,2}");
        Matcher matcher = pattern.matcher(email);
        String key = "unSupportEmail";
        if (matcher.find()) {
            key = "smtp." + matcher.group(1);
        }
        if (hostMap.containsKey(key)) {
            return hostMap.get(key);
        } else {
            throw new Exception("unSupportEmail");
        }
    }

    public static int getSmtpPort(String email) throws Exception {
        Pattern pattern = Pattern.compile("\\w+@(\\w+)(\\.\\w+){1,2}");
        Matcher matcher = pattern.matcher(email);
        String key = "unSupportEmail";
        if (matcher.find()) {
            key = "smtp.port." + matcher.group(1);
        }
        if (hostMap.containsKey(key)) {
            return Integer.parseInt(hostMap.get(key));
        } else {
            return 25;
        }
    }

    /**
     * 发送普通邮件
     *
     * @param toMailAddr 收信人地址
     * @param subject    email主题
     * @param message    发送email信息
     */
    public static Response sendCommonMail(String toMailAddr, String subject, String message) {
        Response response = new Response();
        HtmlEmail hemail = new HtmlEmail();
        try {
            hemail.setHostName(getHost(from));
            hemail.setSmtpPort(getSmtpPort(from));
            hemail.setCharset(charSet);
            hemail.addTo(toMailAddr);
            hemail.setFrom(from, fromName);
            hemail.setAuthentication(username, password);
            hemail.setSubject(subject);
            hemail.setMsg(message);
            hemail.send();
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 调用短信接口发送短信
     * TODO
     * */
    public static Response sendSms(String billId,String msg,String sendDate) throws Exception{
        boolean flag = false;
        String cfgHttpClientCode =  CommConstants.CrmInterfaces.ESOP_SEND_MESSAGE_CRM;
        LinkedHashMap<String, Object> busiInfoMap = new LinkedHashMap<String, Object>();
        busiInfoMap.put("Bill_id", billId);
        busiInfoMap.put("msg", msg);
        busiInfoMap.put("Send_date",sendDate);
        Response response = ServerUtil.call(cfgHttpClientCode, busiInfoMap);

        return response;
    }

    /**
     * 拷贝数据到his对象中
     * */
    public static CfgMsgInfoInstEntity copy(CfgMsgInfoInstEntity entity) throws Exception{
        CfgMsgInfoInstEntity newEntity = new CfgMsgInfoInstEntity();
        newEntity.setMsgInfoId(entity.getMsgInfoId());
        newEntity.setBusiMsgId(entity.getBusiMsgId());
        newEntity.setMsgId(entity.getMsgId());
        newEntity.setMsgType(entity.getMsgType());
        newEntity.setMsgText(entity.getMsgText());
        newEntity.setMsgTitle(entity.getMsgTitle());
        newEntity.setUserId(entity.getUserId());
        newEntity.setPkValue(entity.getPkValue());
        newEntity.setSendObjVal(entity.getSendObjVal());
        newEntity.setSendType(entity.getSendType());
        newEntity.setSendRate(entity.getSendRate());
        newEntity.setSendDate(entity.getSendDate());
        newEntity.setSendCount(entity.getSendCount());
        newEntity.setSendMaxCount(entity.getSendMaxCount());
        newEntity.setMsgExpireDate(entity.getMsgExpireDate());
        newEntity.setModuleId(entity.getModuleId());
        newEntity.setState(entity.getState());
        newEntity.setCreator(entity.getCreator());
        newEntity.setCreateDate(entity.getCreateDate());

        return newEntity;
    }

    /**
     * 拷贝数据到his对象中
     * */
    public static CfgMsgInfoInstHisEntity copy2His(CfgMsgInfoInstEntity entity) throws Exception{
        CfgMsgInfoInstHisEntity hisEntity = new CfgMsgInfoInstHisEntity();
        hisEntity.setMsgInfoId(entity.getMsgInfoId());
        hisEntity.setBusiMsgId(entity.getBusiMsgId());
        hisEntity.setMsgId(entity.getMsgId());
        hisEntity.setMsgType(entity.getMsgType());
        hisEntity.setMsgText(entity.getMsgText());
        hisEntity.setMsgTitle(entity.getMsgTitle());
        hisEntity.setUserId(entity.getUserId());
        hisEntity.setPkValue(entity.getPkValue());
        hisEntity.setSendObjVal(entity.getSendObjVal());
        hisEntity.setSendType(entity.getSendType());
        hisEntity.setSendRate(entity.getSendRate());
        hisEntity.setSendDate(entity.getSendDate());
        hisEntity.setSendCount(entity.getSendCount());
        hisEntity.setSendMaxCount(entity.getSendMaxCount());
        hisEntity.setMsgExpireDate(entity.getMsgExpireDate());
        hisEntity.setModuleId(entity.getModuleId());
        hisEntity.setState(entity.getState());
        hisEntity.setCreator(entity.getCreator());
        hisEntity.setCreateDate(entity.getCreateDate());
        hisEntity.setDoneDate(DateUtils.getCurrentDate());
        return hisEntity;
    }

    /**
     * 拷贝数据到his对象中
     * */
    public static CfgMsgInfoInstErrorEntity copy2Error(CfgMsgInfoInstEntity entity) throws Exception{
        CfgMsgInfoInstErrorEntity errorEntity = new CfgMsgInfoInstErrorEntity();
        errorEntity.setMsgInfoId(entity.getMsgInfoId());
        errorEntity.setBusiMsgId(entity.getBusiMsgId());
        errorEntity.setMsgId(entity.getMsgId());
        errorEntity.setMsgType(entity.getMsgType());
        errorEntity.setMsgText(entity.getMsgText());
        errorEntity.setMsgTitle(entity.getMsgTitle());
        errorEntity.setUserId(entity.getUserId());
        errorEntity.setPkValue(entity.getPkValue());
        errorEntity.setSendObjVal(entity.getSendObjVal());
        errorEntity.setSendType(entity.getSendType());
        errorEntity.setSendRate(entity.getSendRate());
        errorEntity.setSendDate(entity.getSendDate());
        errorEntity.setSendCount(entity.getSendCount());
        errorEntity.setSendMaxCount(entity.getSendMaxCount());
        errorEntity.setMsgExpireDate(entity.getMsgExpireDate());
        errorEntity.setModuleId(entity.getModuleId());
        errorEntity.setState(entity.getState());
        errorEntity.setCreator(entity.getCreator());
        errorEntity.setCreateDate(entity.getCreateDate());
        errorEntity.setDoneDate(DateUtils.getCurrentDate());


        return errorEntity;
    }
}