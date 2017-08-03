/**
 * $Id: Order.java,v 1.0 2016/8/30 16:35 haomeng Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.dync.data;

import java.util.List;

/**
 * @author haomeng
 * @version $Id: Order.java,v 1.1 2016/8/30 16:35 haomeng Exp $
 * Created on 2016/8/30 16:35
 */
public class Order {
    private String orderId;
    private List<SubmitData> submitData;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<SubmitData> getSubmitData() {
        return submitData;
    }

    public void setSubmitData(List<SubmitData> submitData) {
        this.submitData = submitData;
    }
}
