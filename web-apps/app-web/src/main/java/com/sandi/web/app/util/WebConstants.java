/**
 * $Id: WebConstants.java,v 1.0 2016/9/7 15:24 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.util;

/**
 * @author dizl
 * @version $Id: WebConstants.java,v 1.1 2016/9/7 15:24 dizl Exp $
 * Created on 2016/9/7 15:24
 */
public class WebConstants {
    public class RequestType{
        public static final String HTTP = "HTTP";
        public static final String WS = "WS";
        public static final String FSV = "FSV";
    }

    public class EventType{
        public static final int BEFORE_EVENT = 1;
        public static final int RETURN_EVENT = 2;
        public static final int TIMEOUT_EVENT = 3;
    }

    public class EventKind{
        public static final int JS_FILE = 1;
        public static final int JS_CONTENT = 2;
        public static final int JAVA_CLASS = 3;
    }
}
