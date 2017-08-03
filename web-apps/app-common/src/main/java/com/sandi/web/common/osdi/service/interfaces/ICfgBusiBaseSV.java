package com.sandi.web.common.osdi.service.interfaces;


import com.sandi.web.utils.http.entity.CfgBusiBase;

public interface ICfgBusiBaseSV{
    CfgBusiBase getBusiBase(String busiId) throws Exception;
}