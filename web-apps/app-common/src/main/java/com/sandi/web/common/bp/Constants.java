/**
 * $Id: Constants.java,v 1.0 2016/9/29 14:28 haomeng Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.bp;

/**
 * @author haomeng
 * @version $Id: Constants.java,v 1.1 2016/9/29 14:28 haomeng Exp $
 *          Created on 2016/9/29 14:28
 */
public class Constants {
    public class FileState {
        public static final int WAIT_ANALYSE = 1;       //等待文件解析入库
        public static final int IN_ANALYSE = 2;         //文件解析入库中
        public static final int WAIT_BUSINESS = 3;      //文件解析入库成功等待业务处理
        public static final int ANALYSE_ERROR = 4;      //文件解析入库失败
        public static final int IN_BUSINESS = 5;           //业务处理中
        public static final int BUSINESS_SUCCESS = 6;   //业务处理成功
        public static final int BUSINESS_ERROR = 7;     //业务处理失败
    }

    public class RowState {
        public static final int WAIT = 1;       //待处理
        public static final int SUCCESS = 2;    //处理成功
        public static final int ERROR = 3;      //处理失败
    }
}