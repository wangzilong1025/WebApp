/**
 * $Id: Row.java,v 1.0 2016/8/30 16:35 haomeng Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.dync.data;

import java.util.List;

/**
 * @author haomeng
 * @version $Id: Row.java,v 1.1 2016/8/30 16:35 haomeng Exp $
 * Created on 2016/8/30 16:35
 */
public class Row {
    private String status;
    private List<Col> col;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Col> getCol() {
        return col;
    }

    public void setCol(List<Col> col) {
        this.col = col;
    }
}
