package com.sandi.web.common.log.service.interfaces;


import com.sandi.web.common.log.entity.SysBusiLogEntity;
import java.util.List;
import java.util.Map;

/**
 * Created by xt on 2016/2/22.
 */
public interface ISysBusiLogSV {

    public void saveLogs(List<SysBusiLogEntity> entityList) throws Exception;
}
