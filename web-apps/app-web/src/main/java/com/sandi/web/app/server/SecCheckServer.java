/**
 * $Id: SecCheckServer.java,v 1.0 2016/8/26 13:11 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.server;

import com.alibaba.dubbo.rpc.RpcContext;
import com.sandi.web.utils.http.entity.SerParameter;
import com.sandi.web.utils.response.Response;
import com.sandi.web.utils.sec.SessionManager;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangrp
 * @version $Id: SecCheckServer.java,v 1.1 2016/8/26 13:11 zhangrp Exp $
 *          Created on 2016/8/26 13:11
 */
public class SecCheckServer {

    private static final Logger logger = LoggerFactory.getLogger(ParaCheckServer.class);

    public void callServer(SerParameter serParameter) {
        if (serParameter.isEnd() == true) {
            return;
        }
        try {
            String sessionId = serParameter.getHttpServletRequest().getRequestedSessionId();
            UserInfoInterface userInfoInterface = SessionManager.getUser(sessionId);
            if (userInfoInterface == null) {
                throw new Exception("用户未登陆！");
            } else {
                RpcContext.getContext().setAttachment(SessionManager.SESSION_KEY, sessionId);
            }
        } catch (Exception e) {
            logger.error("用户未登陆", e);
            serParameter.setEnd(true);
            serParameter.getResponse().getErrorInfo().setCode(Response.NO_FOUND_USER);
            serParameter.getResponse().getErrorInfo().setMessage("用户未登陆，请登录！");
        }

    }
}
