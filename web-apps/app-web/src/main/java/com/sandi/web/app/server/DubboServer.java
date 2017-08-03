/**
 * $Id: DubboServer.java,v 1.0 2015/7/16 15:39 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.server;

import com.sandi.web.app.dubbo.DubboManage;
import com.sandi.web.app.util.CfgSrvBaseUtil;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.http.entity.CfgSrvBase;
import com.sandi.web.utils.http.entity.SerParameter;
import com.sandi.web.utils.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhangrp
 * @version $Id: DubboServer.java,v 1.1 2015/7/16 15:39 zhangrp Exp $
 *          Created on 2015/7/16 15:39
 */
public class DubboServer {

    protected static final Logger logger = LoggerFactory.getLogger(DubboServer.class);

    public void callServer(SerParameter serParameter) {
        if (serParameter.isEnd() == true) {
            return;
        }
        String serverId = serParameter.getBusiCode();
        CfgSrvBase cfgSrvBase = null;
        try {
            cfgSrvBase = CfgSrvBaseUtil.getCfgSrvBase(serverId);
            if (cfgSrvBase != null) {
                Object resultData = null;
                if (serParameter.getRequest().getRequestInfo().isForTest() == false) {

                    Object object = DubboManage.getServer(cfgSrvBase);
                    Class<?> classType = object.getClass();
                    Method method = classType.getMethod(cfgSrvBase.getSrvMethod(), String.class);
                    resultData = method.invoke(object, JsonUtil.object2Json(serParameter.getRequest().getRequestInfo().getBusiParams()));
                } else {
                    resultData = cfgSrvBase.getParOut();
                }
                Response response = (Response) JsonUtil.json2Object((String) resultData, Response.class);
                serParameter.setResponse(response);
            } else {
                serParameter.setEnd(true);
                serParameter.getResponse().getErrorInfo().setCode(Response.ERROR);
                serParameter.getResponse().getErrorInfo().setMessage("未找到相关服务");
            }
        } catch (InvocationTargetException e) {
            logger.error("", e);
            String mess = e.getTargetException().getMessage();
            if (mess == null) {
                mess = e.getMessage();
            }
            serParameter.setEnd(true);
            serParameter.getResponse().getErrorInfo().setCode(Response.SYS_ERROR);

            int index = mess.indexOf("\n");
            if (index != -1) {
                mess = mess.substring(0, index);
            } else if (mess.length() > 100) {
                mess = mess.substring(0, 100);
            }
            String serName = cfgSrvBase == null ? "" : cfgSrvBase.getSrvId();
            serParameter.getResponse().getErrorInfo().setMessage("调用服务[" + serName + "]异常:" + mess);
        } catch (Exception e) {
            logger.error("", e);
            serParameter.setEnd(true);
            serParameter.getResponse().getErrorInfo().setCode(Response.SYS_ERROR);
            String errorMsg = e.getMessage() == null ? e.toString() : e.getMessage();//e.getCause().toString();
            String msg = errorMsg.length() < 100 ? errorMsg : errorMsg.substring(0, 100);
            serParameter.getResponse().getErrorInfo().setMessage("调用服务异常" + msg);
        }
    }




}
