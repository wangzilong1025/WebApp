package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgTaskInsEntity;

/**
 * Created by dizl on 2017/3/9.
 */
public interface IAutoTaskSV {
    /**
     * 任务创建前调用,如果返回值为false，则不会创建该任务
     * */
    public boolean beforeDeal(CfgTaskInsEntity taskInsEntity, String params) throws Exception;
    /**
     * 任务完成后调用
     * */
    public void afterDeal(CfgTaskInsEntity taskInsEntity, String params) throws Exception;
}
