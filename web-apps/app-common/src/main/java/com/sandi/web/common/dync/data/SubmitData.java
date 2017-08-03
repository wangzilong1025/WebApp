/**
 * $Id: SubmitData.java,v 1.0 2016/8/30 16:35 haomeng Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.dync.data;

import java.util.List;
import java.util.Map;

/**
 * @author haomeng
 * @version $Id: SubmitData.java,v 1.1 2016/8/30 16:35 haomeng Exp $
 * Created on 2016/8/30 16:35
 */
public class SubmitData {
    private String name;
    private String id;
    private Map paramMap;
    private List<NodeInfo> nodeinfo;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
    }

    public List<NodeInfo> getNodeinfo() {
        return nodeinfo;
    }

    public void setNodeinfo(List<NodeInfo> nodeinfo) {
        this.nodeinfo = nodeinfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
