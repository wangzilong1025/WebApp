package com.sandi.web.utils.http.client;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

/**
 * Created by LIUQ on 2015/7/17.
 */
public class Parameter {

    private String name;
    private QName type;
    private ParameterMode mode;

    public Parameter(String name, QName type) {
        this.name = name;
        this.type = type;
        mode = ParameterMode.IN;
    }

    public Parameter(String name, QName type, ParameterMode mode) {
        this.name = name;
        this.type = type;
        this.mode = mode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(QName type) {
        this.type = type;
    }

    public void setMode(ParameterMode mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public QName getType() {
        return type;
    }

    public ParameterMode getMode() {
        return mode;
    }
}
