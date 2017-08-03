package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.entity.CfgProcessParamInsEntity;
import com.sandi.web.common.process.entity.CfgTaskInsEntity;
import com.sandi.web.common.process.entity.CfgTaskParamInsEntity;
import com.sandi.web.common.process.service.interfaces.ICfgProcessParamInsSV;
import com.sandi.web.common.process.service.interfaces.ICfgTaskParamInsSV;
import com.sandi.web.common.process.service.interfaces.IProcessRuleSV;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dizl on 2017/3/8.
 * 正则表达式规则
 */
public class ExpreRuleSVImpl implements IProcessRuleSV {
    @Override
    public boolean validate(CfgTaskInsEntity taskInsEntity, String ruleParam, String ruleValue) throws Exception {
        ICfgTaskParamInsSV taskParamInsSV = SpringContextHolder.getBean(ICfgTaskParamInsSV.class);
        ICfgProcessParamInsSV processParamInsSV = SpringContextHolder.getBean(ICfgProcessParamInsSV.class);

        //查询当前任务参数
        List<CfgTaskParamInsEntity> taskParamInsEntityList = taskParamInsSV.getParamEntityByTaskInsId(taskInsEntity.getTaskInsId());
        //查询当前流程参数
        List<CfgProcessParamInsEntity> processParamInsEntityList = processParamInsSV.getParamEntityByProcessInsId(taskInsEntity.getProcessInsId());
        while(ruleValue.contains("[") && ruleValue.contains("]")){
            int startIdx = ruleValue.indexOf("[", 0) + 1;
            int endIdx = ruleValue.indexOf("]",startIdx);
            String key = ruleValue.substring(startIdx,endIdx);
            String value = "";
            if(taskParamInsEntityList!=null && taskParamInsEntityList.size()>0){
                for(CfgTaskParamInsEntity taskParamInsEntity : taskParamInsEntityList){
                    if(taskParamInsEntity.getParamCode().equalsIgnoreCase(key)){
                        value = taskParamInsEntity.getParamValue();
                        break;
                    }
                }
            }
            if(StringUtils.isEmpty(value)){
                for(CfgProcessParamInsEntity processParamInsEntity : processParamInsEntityList){
                    if(processParamInsEntity.getParamCode().equalsIgnoreCase(key)){
                        value = processParamInsEntity.getParamValue();
                        break;
                    }
                }
            }
            ruleValue = StringUtils.replace(ruleValue,"["+key+"]",value);
        }
        //查询任务参数
        Pattern p = Pattern.compile(ruleParam);
        Matcher m = p.matcher(ruleValue);
        return m.matches();
    }
}
