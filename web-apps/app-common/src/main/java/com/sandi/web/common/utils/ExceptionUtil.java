package com.sandi.web.common.utils;

import com.sandi.web.common.support.BusinessException;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by dizl on 2015/6/3.
 * 业务异常工具类
 */
public final class ExceptionUtil {
    public static void throwBusinessException(String key, Object[] args) throws BusinessException {
        throw new BusinessException(key, args);
    }

    public static void throwBusinessException(String key) throws BusinessException {
        throw new BusinessException(key);
    }

    public static void throwBusinessException(String key, String val1) throws BusinessException {
        throw new BusinessException(key, new String[]{val1});
    }

    public static void throwBusinessException(String key, String val1, String val2) throws BusinessException {
        throw new BusinessException(key, new String[]{val1, val2});
    }

    public static void throwBusinessException(String key, String val1, String val2, String val3) throws BusinessException {
        throw new BusinessException(key, new String[]{val1, val2, val3});
    }

    /**
     * 将CheckedException转换为UncheckedException.
     */
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }

    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     */
    public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * 在request中获取异常类
     *
     * @param request
     * @return
     */
    public static Throwable getThrowable(HttpServletRequest request) {
        Throwable ex = null;
        if (request.getAttribute("exception") != null) {
            ex = (Throwable) request.getAttribute("exception");
        } else if (request.getAttribute("javax.servlet.error.exception") != null) {
            ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
        }
        return ex;
    }
}
