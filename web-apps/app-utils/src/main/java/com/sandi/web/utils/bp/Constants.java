/**
 * $Id: Constants.java,v 1.0 17/2/23 下午2:25 zhangruiping Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.bp;

/**
 * @author zhangruiping
 * @version $Id: Constants.java,v 1.1 17/2/23 下午2:25 zhangruiping Exp $
 *          Created on 17/2/23 下午2:25
 */
public class Constants {

    public class FileType {
        public static final int TXT = 0;
        public static final int CVS = 1;
        public static final int EXCEL = 2;
        public static final int EXCEL_XLS = 3;
        public static final int EXCEL_XLSX = 4;
    }

    public class RowType {
        public static final int HEADER = 0;//文件头
        public static final int CONTENT = 1;//文件内容
    }
}