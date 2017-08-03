package com.sandi.web.common.support;

/**
 * Created by dizl on 2015/6/3.
 * 业务异常类
 */
public class BusinessException extends Exception {
    private String key = null;
    private Object[] args = null;

    public BusinessException(String key, Object[] args) {
        super(wrapperMessage(key, args));
        this.key = key;
        this.args = args;
    }

    public BusinessException(String key) {
        super(wrapperMessage(key, null));
        this.key = key;
    }

    public BusinessException(String key, Object[] args, Throwable cause) {
        super(wrapperMessage(key, args), cause);
        this.key = key;
        this.args = args;
    }

    public BusinessException(String key, Throwable cause) {
        super(wrapperMessage(key, null), cause);
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public Object[] getArgs() {
        return this.args;
    }

    private static String wrapperMessage(String key, Object[] args) {
        StringBuilder sb;
//        try
//        {
//            if (args == null) {
//                return PlatLocaleFactory.getResource(key);
//            }
//
//            return PlatLocaleFactory.getResource(key, args);
//        }
//        catch (Throwable ex)
//        {
        sb = new StringBuilder();
        sb.append("{");
        if (args != null) {
            for (int i = 0; i < args.length; ++i) {
                sb.append(args[i]);
                if (i != args.length - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append("}");
//        }
        return "key=" + key + ",args=" + sb.toString();
    }
}
