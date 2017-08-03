/**
 * $Id: ISysBusiLogFSV.java,v 1.0 2016/8/31 14:52 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.api.syslog;

/**
 * @author dizl
 * @version $Id: ISysBusiLogFSV.java,v 1.1 2016/8/31 14:52 dizl Exp $
 * Created on 2016/8/31 14:52
 */
public interface ISysBusiLogFSV {
    /**
     * 服务调用成功日志
     * */
    public String srvLog(String requestJson);

    /**
     * 菜单访问日志
     * */
    public String menuLog(String requestJson);
}
