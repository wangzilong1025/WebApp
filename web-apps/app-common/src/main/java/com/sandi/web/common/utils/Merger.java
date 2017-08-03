/**
 * $Id: Merger.java,v 1.0 2016/3/3 10:59 zhangrp Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.utils;

/**
 * @author zhangrp
 * @version $Id: Merger.java,v 1.1 2016/3/3 10:59 zhangrp Exp $
 *          Created on 2016/3/3 10:59
 */
public class Merger {

    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;

    public Merger(int firstRow, int firstCol, int lastRow, int lastCol) {
        this.firstCol = firstCol;
        this.firstRow = firstRow;
        this.lastCol = lastCol;
        this.lastRow = lastRow;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getLastRow() {
        return lastRow;
    }

    public int getFirstCol() {
        return firstCol;
    }

    public int getLastCol() {
        return lastCol;
    }
}

