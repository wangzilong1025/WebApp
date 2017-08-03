/*
 * $Id: ParaCheckServer.java,v 1.0 2015年7月24日 上午9:52:08 zhangrp Exp $
 *
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.server;

import com.sandi.web.utils.http.entity.SerParameter;
import com.sandi.web.utils.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangrp
 * @version $Id: ParaCheckServer.java v 1.0 Exp $
 *          Created on 2015年7月24日 上午9:52:08
 */
public class ParaCheckServer {

    private static final Logger logger = LoggerFactory.getLogger(ParaCheckServer.class);

    /* (non-Javadoc)
     * @see com.sandi.web.app.service.interfaces.IServer#callServer(com.sandi.web.app.common.SerParameter)
     */
    public void callServer(SerParameter serParameter) {
        if (serParameter.isEnd() == true) {
            return;
        }
        String jsonData = serParameter.getHttpServletRequest().getParameter("data");
        try {
            serParameter.setRequest(Request.getRequest(jsonData));
            serParameter.setBusiCode(serParameter.getRequest().getRequestInfo().getBusiCode());

        } catch (Exception e) {
            logger.error("参数不符合规范", e);
            serParameter.setEnd(true);
            serParameter.getResponse().getErrorInfo().setCode("0001");
            serParameter.getResponse().getErrorInfo().setMessage("参数不符合规范！");
        }
    }

}
