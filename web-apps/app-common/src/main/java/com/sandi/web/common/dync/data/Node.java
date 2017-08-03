/**
 * $Id: Node.java,v 1.0 2016/8/30 16:35 haomeng Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.dync.data;

import java.util.List;

/**
 * @author haomeng
 * @version $Id: Node.java,v 1.1 2016/8/30 16:35 haomeng Exp $
 * Created on 2016/8/30 16:35
 */
public class Node {
    private String id;
    private String name;
    private String code;
    private String type;
    private List<Row> row;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }
}
