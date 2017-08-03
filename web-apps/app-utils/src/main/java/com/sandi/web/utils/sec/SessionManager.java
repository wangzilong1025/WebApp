package com.sandi.web.utils.sec;

import com.alibaba.dubbo.rpc.RpcContext;
import com.sandi.web.utils.common.JedisUtils;
import com.sandi.web.utils.config.Global;
import com.sandi.web.utils.sec.entity.UserInfoInterface;

/**
 * 已登录用户
 */
public class SessionManager {
    public final static String SESSION_KEY = "SESSION_ID";

    /**
     * SESSION 超时时间
     */
    public static int timeout = 30 * 60;

    static {
        String sessionTimeout = Global.getConfig("session.sessionTimeout");
        if (sessionTimeout != null && !sessionTimeout.equals("")) {
            timeout = Integer.valueOf(sessionTimeout);
        }
    }

    public static UserInfoInterface getUser() throws Exception {
        String sessionId = RpcContext.getContext().getAttachment(SessionManager.SESSION_KEY);
        return getUser(sessionId);
    }

    public static UserInfoInterface getUser(String sessionId) throws Exception {
        UserInfoInterface userInfoEntity = null;
        if (JedisUtils.exists(sessionId)) {
            userInfoEntity = (UserInfoInterface) JedisUtils.getObject(sessionId, timeout);
        }
        return userInfoEntity;
    }

    public static void checkLogin(String sessionId) throws Exception {
        UserInfoInterface userInfoInterface = SessionManager.getUser(sessionId);
        if (userInfoInterface == null) {
            throw new Exception("用户未登陆！");
        } else {
            RpcContext.getContext().setAttachment(SessionManager.SESSION_KEY, sessionId);
        }
    }

    public static void setUser(UserInfoInterface user) throws Exception {
        if (JedisUtils.existsObject(user.getSessionId())) {
            JedisUtils.delObject(user.getSessionId());
        }
        JedisUtils.setObject(user.getSessionId(), user, timeout);
    }
}
