package com.sandi.web.common.staticdata.service.interfaces;

import com.sandi.web.common.staticdata.entity.CfgStaticDataEntity;

import java.util.List;

/**
 * Created by dizl on 2015/6/12.
 */
public interface ICfgStaticDataSV {
    public List<CfgStaticDataEntity> getAllCfgStaticData() throws Exception;

    public List<CfgStaticDataEntity> getCfgStaticDataByCodeType(String codeType) throws Exception;

    public CfgStaticDataEntity getCfgStaticDataByCon(String codeType, String codeValue) throws Exception;
}
