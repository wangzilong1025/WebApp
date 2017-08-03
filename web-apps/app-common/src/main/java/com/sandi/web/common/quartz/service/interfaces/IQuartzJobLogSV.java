package com.sandi.web.common.quartz.service.interfaces;


import com.sandi.web.common.quartz.entity.CfgQuartzJobLogEntity;

/**
 * Created by dizl on 2015/6/15.
 */
public interface IQuartzJobLogSV {
    public void saveQuartzJobLog(CfgQuartzJobLogEntity entity) throws Exception;
}
