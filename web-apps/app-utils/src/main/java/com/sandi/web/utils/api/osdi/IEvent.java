/**
 * $Id: IEvent.java,v 1.0 2017/1/19 12:01 lijie Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.api.osdi;

import com.sandi.web.utils.http.entity.CfgBusiBase;
import com.sandi.web.utils.http.entity.SerParameter;

/**
 * @author lijie
 * @version $Id: IEvent.java,v 1.1 2017/1/19 12:01 lijie Exp $
 * Created on 2017/1/19 12:01
 */
public interface IEvent {

    public void init() throws Exception;

    public SerParameter doEvent(SerParameter serParameter, CfgBusiBase cfgBusiBase);

}

