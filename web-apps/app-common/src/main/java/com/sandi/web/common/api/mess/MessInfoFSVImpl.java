package com.sandi.web.common.api.mess;

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.mess.entity.CfgMsgInfoInstEntity;
import com.sandi.web.common.mess.entity.CfgMsgTemplateEntity;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgInfoInstSV;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgTemplateSV;
import com.sandi.web.utils.api.mess.IMessInfoFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.response.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2017/2/15.
 */
@Service
public class MessInfoFSVImpl implements IMessInfoFSV {
    private static final Logger logger = Logger.getLogger(MessInfoFSVImpl.class);
    @Autowired
    private ICfgMsgInfoInstSV msgInfoInstSV;
    @Autowired
    private ICfgMsgTemplateSV msgTemplateSV;

    @Override
    public String getMessInfoCount(String params){
        Response response = new Response();
        try{
            Map param = JsonUtil.json2Map(params);
            String type = "";
            if(param!=null && param.containsKey("type")){
                type = String.valueOf(param.get("type"));
            }
            int count = 0;
            if(StringUtils.equalsIgnoreCase("WEB",type)){//获取web消息提醒数量
                count = msgInfoInstSV.getWebInfoCount();
            }else if(StringUtils.equalsIgnoreCase("APP",type)){//获取app消息提醒数量
                count = msgInfoInstSV.getAppInfoCount();
            }

            Map retMap = new HashMap();
            retMap.put("count",count);
            response.setCode(Response.SUCCESS);
            response.setData(retMap);
        }catch (Exception e){
            logger.error(e);
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }

    @Override
    /**
     * 获取web消息
     * */
    public String getMessInfo(String params) {
        Response response = new Response();
        try{
            Map param = JsonUtil.json2Map(params);
            String type = "";
            if(param!=null && param.containsKey("type")){
                type = String.valueOf(param.get("type"));
            }
            List<CfgMsgInfoInstEntity> msgInfoInstEntityList = null;
            List<Map> retList = new ArrayList<Map>();
            if(StringUtils.equalsIgnoreCase("WEB",type)){
                msgInfoInstEntityList = msgInfoInstSV.getWebInfoList();
            }else if(StringUtils.equalsIgnoreCase("APP",type)){
                msgInfoInstEntityList = msgInfoInstSV.getAppInfoList();
            }
            if(msgInfoInstEntityList!=null && msgInfoInstEntityList.size()>0){
                for(CfgMsgInfoInstEntity msgInfoInstEntity : msgInfoInstEntityList){
                    Map retMap = null;
                    for(Map map : retList){
                        if(StringUtils.equals(map.get("msgId").toString(),msgInfoInstEntity.getMsgId()+"")){
                            retMap = map;
                            break;
                        }
                    }
                    if(retMap==null){
                        retMap = new HashMap();
                        CfgMsgTemplateEntity templateEntity = msgTemplateSV.getEntityByMsgIdAndMsgType(msgInfoInstEntity.getMsgId(),msgInfoInstEntity.getMsgType());
                        if(templateEntity!=null){
                            retMap.put("msgId",templateEntity.getMsgId());
                            retMap.put("msgName",templateEntity.getMsgName());
                            retMap.put("msgIcon",templateEntity.getMsgIcon());
                        }else{
                            retMap.put("msgId",msgInfoInstEntity.getMsgId());
                            retMap.put("msgName",msgInfoInstEntity.getMsgTitle());
                            retMap.put("msgIcon","");
                        }
                        retMap.put("msgInfo",new ArrayList());
                        retList.add(retMap);
                    }
                    List msgInfoList = (List)retMap.get("msgInfo");
                    msgInfoList.add(msgInfoInstEntity);
                    retMap.put("msgCount",msgInfoList.size());
                }
            }

            response.setCode(Response.SUCCESS);
            response.setData(retList);
        }catch(Exception e){
            logger.error(e);
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }

    /**
     * 读取消息内容
     * */
    public String readMess(String params){
        Response response = new Response();
        try{
            Map param = JsonUtil.json2Map(params);
            if(param!=null && param.containsKey("msgInfoId")){
                long msgInfoId = Long.parseLong(param.get("msgInfoId").toString());
                msgInfoInstSV.readMess(msgInfoId);
            }
            response.setCode(Response.SUCCESS);
        }catch (Exception e){
            logger.error(e);
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }
}
