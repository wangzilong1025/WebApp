package com.sandi.web.app.interceptor;

import com.sandi.web.utils.common.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志拦截器
 *
 * @author
 * @version 2014-8-19
 */
public class LogInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger("ESBLogFile");

    private static final ThreadLocal<Map<String, Object>> threadLocalData = new NamedThreadLocal<Map<String, Object>>("ThreadLocal data") {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };

    /**
     * 放数据
     *
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        threadLocalData.get().put(key, value);
    }

    private Object get(String key) {
        return threadLocalData.get().get(key);
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long beginTime = System.currentTimeMillis();
        this.put("beginTime", beginTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        long beginTime = (long) this.get("beginTime");
        long endTime = System.currentTimeMillis();

        logger.info("channel=(${}$)," +
                        "busiCode=(${}$)," +  //接口编号
                        "startTime=(${}$)," + //调用开始时间
                        "costTime=(${}$)," +  //调用耗时
                        "result=(${}$)," +    //调用是否成功
                        "message=(${}$)," +   //调用失败原因
                        "clientIp=(${}$)," +  //客户端IP

                        "opId=(${}$)," +      //操作员ID
                        "orgId=(${}$)," +     //操作员OPID
                        "opCode=(${}$)" +    //操作员名称
                        "",

                "NEWESOP", this.get("busiCode"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(beginTime),
                endTime - beginTime,
                this.get("result"),
                this.get("message"),
                request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP"),
                this.get("opId"),
                this.get("orgId"),
                this.get("opCode"));

//        logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
//                new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtils.formatDateTime(endTime - beginTime),
//                request.getRequestURI(), Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
//                (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);

        if (this.get("busiCode") != null
                && !this.get("busiCode").equals("ICOMMUTILSFSV_SAVEMENULOG")
                && !this.get("busiCode").equals("ICOMMUTILSFSV_SAVEFILELOG")) {

            String busiCode = (String) this.get("busiCode");
            String message = (String) this.get("message");
            String ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP");
            String opId = String.valueOf((long) this.get("opId"));
            String orgId = String.valueOf((long) this.get("orgId"));
            String result = (String) this.get("result");

            Map logMap = new HashMap();
            logMap.put("LOG_TYPE", "2");
            logMap.put("BUSINESS_ID", busiCode);
            logMap.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(beginTime));
            logMap.put("END_DATE", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTime));
            logMap.put("LOG_LEVEL", result);
            logMap.put("OP_ID", opId);
            logMap.put("ORG_ID", orgId);
            logMap.put("CONTENT", "\"Message\":\"" + message + "\",\"IP\":\"" + ip + "\"");
            logMap.put("REMARKS", "FSV服务调用日志");
            String log = JsonUtil.object2Json(logMap);

            logger.info(log);

//            CfgSrvBase cfgSrvBase = new CfgSrvBase();
//            cfgSrvBase.setSrvId("ICOMMUTILSFSV_SAVEMENULOG");
//            cfgSrvBase.setSrvPackage("com.sandi.web.core.api.interfaces.ICommUtilsFSV");
//            ICommUtilsFSV commUtilsFSV = (ICommUtilsFSV) DubboManage.getServer(cfgSrvBase);
//
//            commUtilsFSV.saveFsvLog(log);
        }
    }
}