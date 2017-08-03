/**
 * $Id: ICfgStaticDataFSV.java,v 1.0 2016/9/6 10:36 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.api.staticdata;

/**
 * @author zhangrp
 * @version $Id: ICfgStaticDataFSV.java,v 1.1 2016/9/6 10:36 zhangrp Exp $
 *          Created on 2016/9/6 10:36
 */
public interface ICfgStaticDataFSV {
    /**
     * 提供接口根据codeTyepe调获取静态值列表
     * 传入的为具体的codeType
     * @param requestJson
     * @return
     * @throws Exception
     */
    public String getCfgStaticDataByCodeType(String requestJson) throws Exception;

}
