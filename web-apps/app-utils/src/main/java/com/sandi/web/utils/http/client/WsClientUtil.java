package com.sandi.web.utils.http.client;

import com.sandi.web.utils.common.ConcurrentCapacity;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.config.Global;
import com.sandi.web.utils.http.client.request.HttpRequest;
import org.apache.axis.AxisProperties;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;

/**
 * Created by LIUQ on 2015/7/17.
 */
public class WsClientUtil {

    private static transient Logger log  = Logger.getLogger(WsClientUtil.class);;
    private static final int DEFAULT_TIMEOUT_SECONDS = 3;
    private static int CONCURRENT_CAPACITY;
    private static int CONCURRENT_ACQUIRE_TIMEOUT_SECONDS;
    private static ConcurrentCapacity SEM = null;

    static {
        //AXIS Properties Init
        AxisProperties.setProperty("axis.http.client.maximum.total.connections", "30");
        AxisProperties.setProperty("axis.http.client.maximum.connections.per.host", "30");
        AxisProperties.setProperty("axis.http.client.connection.pool.timeout", "30");
        AxisProperties.setProperty("axis.http.client.connection.default.so.timeout", "3000");
        AxisProperties.setProperty("axis.http.client.connection.default.connection.timeout", "3000");
        //WS CAPACITY Init
        String httpConcurrentCapacity = Global.getConfig("ws.concurrentCapacity");
        String httpTimeOutSeconds = Global.getConfig("ws.concurrentCapacityAcquireTimeoutSeconds");
        CONCURRENT_CAPACITY = StringUtils.isNotBlank(httpConcurrentCapacity) ? Integer.parseInt(httpConcurrentCapacity) : 15;
        CONCURRENT_ACQUIRE_TIMEOUT_SECONDS = StringUtils.isNotBlank(httpTimeOutSeconds) ? Integer.parseInt(httpTimeOutSeconds) : 3;
        SEM = new ConcurrentCapacity(CONCURRENT_CAPACITY, CONCURRENT_ACQUIRE_TIMEOUT_SECONDS);
        if (log.isInfoEnabled()) {
            log.info((new StringBuilder()).append("ws current capacity:").append(CONCURRENT_CAPACITY).toString());
            log.info((new StringBuilder()).append("ws current capacity timeout:").append(CONCURRENT_ACQUIRE_TIMEOUT_SECONDS).append(" seconds").toString());
        }
    }

    public static Object invoke(HttpRequest request) throws Exception {
        Object obj = null;
        boolean isAcquire = false;
        isAcquire = SEM.acquire();
        try {

            String methodParameter = request.getHeader().get("methodParameter");
            String methodReturnType = request.getHeader().get("methodReturnType");
            String operationStyle = request.getHeader().get("operationStyle");
            String operationUse = request.getHeader().get("operationUse");
            int timeoutSeconds = request.getTimeoutSeconds()>0?request.getTimeoutSeconds():3;
            String methodName = request.getHeader().get("methodName");
            String registerTypeMapping = request.getHeader().get("registerTypeMapping");
            //1 根据指定格式构建webservice请求调用入参
            Parameter inputParameters[] = parseAxisInputParameter(methodParameter);
            //2 根据指定格式构建webservice请求调用出参
            QName outputQName = parseAxisOutputQName(request.getBusiCode(),methodReturnType);

            obj = invoke(request.getBusiCode(),operationStyle,operationUse,timeoutSeconds,request.getUrl(),methodName,registerTypeMapping,inputParameters,request.getParameters().values().toArray(),outputQName);

        } catch (Exception e) {
            log.error("调用WS服务异常"+request.getBusiCode(),e);
            throw e;
        } finally {
            if (isAcquire) {
                SEM.release();
            }
        }
        return obj;
    }

    private static Object invoke(String busiCode,String operationStyle,String operationUse,int timeoutSeconds,String url,String methodName,String registerTypeMapping,Parameter[] inputParameters, Object inputParams[], QName returnType) throws Exception {
        Object rtn = null;
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            if (StringUtils.isNotBlank(operationStyle))
                call.setOperationStyle(operationStyle.trim());
            if (StringUtils.isNotBlank(operationUse))
                call.setOperationUse(operationUse.trim());
            //超时时间设置
            call.setTimeout(timeoutSeconds * 1000);
            //目标请求URL地址
            call.setTargetEndpointAddress(url);
            //设置调用客户端命名空间路径和方法
            if (org.apache.commons.lang.StringUtils.contains(methodName, ";")) {
                String tmp[] = StringUtils.split(methodName, ';');
                QName qn = new QName(tmp[0], tmp[1]);
                call.setOperationName(qn);
                if (tmp[0].trim().endsWith("/"))
                    call.setSOAPActionURI((new StringBuilder()).append(tmp[0]).append(tmp[1]).toString());
                else
                    call.setSOAPActionURI((new StringBuilder()).append(tmp[0]).append("/").append(tmp[1]).toString());
            } else {
                call.setOperationName(methodName);
                call.setSOAPActionURI("");
            }
            HashMap map = parseRegisterMapping(busiCode, call, registerTypeMapping);
            for (int i = 0; i < inputParameters.length; i++)
                call.addParameter(inputParameters[i].getName(), inputParameters[i].getType(), inputParameters[i].getMode());
            call.setUseSOAPAction(true);
            call.setReturnType(returnType);
            rtn = call.invoke(inputParams);
        } catch (Exception ex) {
            if ((ex instanceof SocketTimeoutException) || (ExceptionUtils.getRootCause(ex) instanceof SocketTimeoutException))
                log.error((new StringBuilder()).append(busiCode).append("调用url:").append(url).append(",方法:").append(methodName).append("超时").toString());
            throw ex;
        }
        return rtn;
    }

    /**
     * 解析请求入参对象
     *
     * @param str
     * @return
     * @throws Exception
     */
    private static Parameter[] parseAxisInputParameter(String str) throws Exception {
        List list = new ArrayList();
        String tmp1[] = StringUtils.getParamFromString(str, "[", "]");
        for (int i = 0; i < tmp1.length; i++) {
            String tmp2[] = StringUtils.getParamFromString(tmp1[i], "{", "}");
            for (int j = 0; j < tmp2.length; j++) {
                String tmp3[] = StringUtils.getParamFromString(tmp2[j], "'", "'");
                String tmp4[] = StringUtils.split(tmp3[1], ';');
                QName qname = new QName(tmp4[0], tmp4[1]);
                ParameterMode mode = null;
                if (tmp3[2].equalsIgnoreCase("IN"))
                    mode = ParameterMode.IN;
                else if (tmp3[2].equalsIgnoreCase("OUT"))
                    mode = ParameterMode.OUT;
                else if (tmp3[2].equalsIgnoreCase("INOUT"))
                    mode = ParameterMode.INOUT;
                else
                    throw new Exception((new StringBuilder()).append("无法识别的ParameterMode:").append(tmp3[2]).append(",目前只能识别IN,INOUT,OUT").toString());
                Parameter parameter = new Parameter(tmp3[0], qname, mode);
                list.add(parameter);
            }
        }
        return (Parameter[]) (Parameter[]) list.toArray(new Parameter[0]);
    }

    /**
     * 解析请求出参对象
     * @param methodReturnType
     * @return
     * @throws Exception
     */
    public static QName parseAxisOutputQName(String busiCode,String methodReturnType) throws Exception {
        String tmp[] = StringUtils.split(methodReturnType, ';');
        if (tmp == null || tmp.length != 2)
            throw new Exception("在cfg_ws_client_method中"+busiCode+"配置的返回类型[MethodReturnType]数据有误");
        QName qname = new QName(tmp[0], tmp[1]);
        return qname;
    }

    /**
     * 解析自定义出参对象
     *
     * @param call
     * @param str
     * @return
     * @throws Exception
     */
    private static HashMap parseRegisterMapping(String busiCode, Call call, String str) throws Exception {
        HashMap map = new HashMap();
        String tmp1[] = StringUtils.getParamFromString(str, "[", "]");
        for (int i = 0; i < tmp1.length; i++) {
            String tmp2[] = StringUtils.getParamFromString(tmp1[i], "{", "}");
            for (int j = 0; j < tmp2.length; j++) {
                String tmp3[] = StringUtils.getParamFromString(tmp2[j], "'", "'");
                if (tmp3 == null || tmp3.length != 2)
                    throw new Exception("在cfg_ws_client中" + busiCode + "配置方法为:的返回类型[RegisterTypeMapping]数据有误");
                String registerTypeUrn = tmp3[0];
                String selfObjectClass = tmp3[1];
                String tmp[] = StringUtils.split(registerTypeUrn, ';');
                QName qn = new QName(tmp[0], tmp[1]);
                if (tmp[1].startsWith("ArrayOf")) {
                    if (selfObjectClass.endsWith(";") && selfObjectClass.startsWith("[L")) {
                        Class clazz = Class.forName(str);
                        call.registerTypeMapping(clazz, qn, new ArraySerializerFactory(clazz, qn), new ArrayDeserializerFactory(qn));
                    } else {
                        throw new Exception((new StringBuilder()).append("定义的类型:").append(str).append(",必须是数组类型").toString());
                    }
                } else {
                    Class clazz = Class.forName((String) map.get(registerTypeUrn));
                    call.registerTypeMapping(clazz, qn, new BeanSerializerFactory(clazz, qn), new BeanDeserializerFactory(clazz, qn));
                }
            }
        }
        return map;
    }
}
