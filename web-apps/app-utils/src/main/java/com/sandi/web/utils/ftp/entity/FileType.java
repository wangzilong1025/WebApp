package com.sandi.web.utils.ftp.entity;

/**
 * Created by 15049 on 2017-07-01.
 */
/**
 * @author zhangrp
 * @version $Id: FileType.java,v 1.1 2016/9/8 15:37 zhangrp Exp $
 *          Created on 2016/9/8 15:37
 */
public enum FileType {
    BIN(2), ASC(1);

    private int value;

    FileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}