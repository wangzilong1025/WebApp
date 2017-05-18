package com.sandi.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 15049 on 2017-05-14.
 */
public class UtilStatic {

    /**
     * 静态常量0
     */
    public static final int STATIC_ZERO = 0;
    /**
     * 静态常量1
     */
    public static final int STATIC_ONE = 1;
    /**
     * 静态常量2
     */
    public static final int STATIC_TWO = 2;
    /**
     * 静态常量3
     */
    public static final int STATIC_THREE = 3;
    /**
     * 静态常量4
     */
    public static final int STATIC_FORE = 4;
    /**
     * 静态常量时间样式yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     *只有日期类型的时间格式
     */
    public static final SimpleDateFormat shortTime = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 默认页数
     */
    public static final int STATIC_PAGE = 1;
    /**
     * 默认每页数量
     */
    public static final int STATIC_PAGESIZE = 16;
    /**
     * 创建新的时间
     */
    public static final Date NEW_DATE = new Date();
}
