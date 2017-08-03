/**
 * $Id: BpProcess.java,v 1.0 2016/9/29 13:55 haomeng Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.bp;

import com.sandi.web.common.bp.entity.EsBpDataEntity;
import com.sandi.web.common.bp.entity.EsBpDataRowEntity;

/**
 * @author haomeng
 * @version $Id: BpProcess.java,v 1.1 2016/9/29 13:55 haomeng Exp $
 *          Created on 2016/9/29 13:55
 *          bp业务处理类必须继实现该类
 */
public interface BpProcess {
    public void processData(EsBpDataEntity esBpDataEntity, EsBpDataRowEntity esBpDataRowEntity) throws Exception;
}