package com.sandi.web.common.http.service.interfaces;

import com.sandi.web.common.http.entity.CfgHttpClientEntity;
import java.util.HashMap;

/**
 * Created by LIUQ on 2015/7/21.
 */
public interface IHttpSV {

    public HashMap getHttpMapping(String code) throws Exception;

    public CfgHttpClientEntity[] getAllCfgHttpClient() throws Exception;
}
