/**
 * $Id: ServerUtil.java,v 1.0 2016/8/26 11:15 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.http;

import com.sandi.web.utils.api.osdi.IHttpServerFSV;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.response.Response;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: ServerUtil.java,v 1.1 2016/8/26 11:15 zhangrp Exp $
 *          Created on 2016/8/26 11:15
 */
public class ServerUtil {

    public static Response call(String busiCode, LinkedHashMap<String, Object> busiParam) throws Exception {
        //传递用户信息 不需要
        //RpcContext.getContext().setAttachment(SessionManager.SESSION_KEY, RpcContext.getContext().getAttachment(SessionManager.SESSION_KEY));
        IHttpServerFSV httpServerFSV = SpringContextHolder.getBean(IHttpServerFSV.class);
        return httpServerFSV.callServer(busiCode, busiParam);
    }
}
