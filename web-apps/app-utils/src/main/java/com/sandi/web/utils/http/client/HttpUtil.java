package com.sandi.web.utils.http.client;

import com.sandi.web.utils.common.ConcurrentCapacity;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.config.Global;
import com.sandi.web.utils.http.client.request.HttpRequest;
import com.sandi.web.utils.http.client.response.HttpResponse;
import com.sandi.web.utils.http.client.response.HttpResponseByte;
import com.sandi.web.utils.http.client.response.HttpResponseString;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class HttpUtil {

    protected static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    private static ConcurrentCapacity SEM = null;
    private static int CONCURRENT_CAPACITY;
    private static int CONCURRENT_ACQUIRE_TIMEOUT_SECONDS;

    static {
        String httpConcurrentCapacity = Global.getConfig("http.concurrentCapacity");
        String httpTimeOutSeconds = Global.getConfig("http.concurrentCapacityAcquireTimeoutSeconds");
        CONCURRENT_CAPACITY = StringUtils.isNotBlank(httpConcurrentCapacity) ? Integer.parseInt(httpConcurrentCapacity) : 15;
        CONCURRENT_ACQUIRE_TIMEOUT_SECONDS = StringUtils.isNotBlank(httpTimeOutSeconds) ? Integer.parseInt(httpTimeOutSeconds) : 3;
        SEM = new ConcurrentCapacity(CONCURRENT_CAPACITY, CONCURRENT_ACQUIRE_TIMEOUT_SECONDS);
        logger.error((new StringBuilder()).append("http current capacity:").append(CONCURRENT_CAPACITY).toString());
        logger.error((new StringBuilder()).append("http current capacity timeout:").append(CONCURRENT_ACQUIRE_TIMEOUT_SECONDS).append(" seconds").toString());
    }


    public static Object doGet(HttpRequest httpRequest) throws Exception {
        boolean isAcquire = false;
        HttpResponse httpresponse;
        isAcquire = SEM.acquire();
        try {
            HttpResponse rtn = null;
            long start = 0L;
            String url = httpRequest.getUrl();
            int timeoutSeconds = httpRequest.getTimeoutSeconds();
            String responseType = httpRequest.getResponseType();
            if (responseType.equalsIgnoreCase("byte")) {
                rtn = new HttpResponseByte();
            } else if (responseType.equalsIgnoreCase("string")) {
                rtn = new HttpResponseString();
            } else {
                throw new Exception((new StringBuilder()).append("无法识别的返回类型:").append(responseType).toString());
            }
            Map<String, String> requestHeaderMap = httpRequest.getHeader();
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(timeoutSeconds * 1000);
            client.getHttpConnectionManager().getParams().setSoTimeout(timeoutSeconds * 1000);
            GetMethod method = new GetMethod(url);
            if (requestHeaderMap != null && !requestHeaderMap.isEmpty()) {
                Set keys = requestHeaderMap.keySet();
                String item;
                for (Iterator iter = keys.iterator(); iter.hasNext(); method.addRequestHeader(item, (String) requestHeaderMap.get(item)))
                    item = (String) iter.next();

            }
            Map<String, Object> parameters = httpRequest.getParameters();
            if (parameters != null && !parameters.isEmpty()) {
                List list = new ArrayList();
                Set<String> keys = parameters.keySet();
                String item;
                for (Iterator<String> iter = keys.iterator(); iter.hasNext(); list.add(new NameValuePair(item, (String) parameters.get(item)))) {
                    item = (String) iter.next();
                }
                method.setQueryString((NameValuePair[]) list.toArray(new NameValuePair[0]));
            }
            DefaultHttpMethodRetryHandler retry = new DefaultHttpMethodRetryHandler(0, false);
            method.getParams().setParameter("http.method.retry-handler", retry);
            if (logger.isDebugEnabled()) {
                logger.debug((new StringBuilder()).append("http请求的url:").append(url).toString());
                List list = new ArrayList();
                Header header[] = method.getRequestHeaders();
                if (header != null) {
                    for (int i = 0; i < header.length; i++)
                        list.add(header[i].toString());

                }
                logger.debug((new StringBuilder()).append("http请求的头:").append(StringUtils.join(list.iterator(), " , ")).toString());
                logger.debug((new StringBuilder()).append("http请求的参数:").append(method.getQueryString()).toString());
            }
            try {
                int statusCode = client.executeMethod(method);
                rtn.setStatusCode(statusCode);
                if (statusCode != 200) {
                    if (logger.isDebugEnabled())
                        logger.debug((new StringBuilder()).append("请求url:").append(url).append("失败,返回代码:").append(statusCode).toString());
                } else if (rtn instanceof HttpResponseByte)
                    ((HttpResponseByte) rtn).setResponseBody(method.getResponseBody());
                else if (rtn instanceof HttpResponseString)
                    ((HttpResponseString) rtn).setResponseBody(method.getResponseBodyAsString());
                else
                    throw new Exception((new StringBuilder()).append("无法识别的返回对象:").append(rtn).toString());
            } catch (Exception ex) {
                logger.error("执行http的get失败", ex);
                if (method != null)
                    try {
                        method.releaseConnection();
                    } catch (Exception ex2) {
                        logger.error("关闭http连接出现失败", ex2);
                    }
                throw new Exception(ex);
            }
            httpresponse = rtn;
        } catch (Exception eee) {
            throw eee;
        } finally {
            if (isAcquire)
                SEM.release();
        }
        return httpresponse;
    }


    public static HttpResponse doPost(HttpRequest httpRequest) throws Exception {
        boolean isAcquire = false;
        HttpResponse httpresponse;
        isAcquire = SEM.acquire();

        //修改资源未释放bug
        try {
            HttpResponse rtn = null;
            String url = httpRequest.getUrl();
            int timeoutSeconds = httpRequest.getTimeoutSeconds();
            String responseType = httpRequest.getResponseType();

            if (responseType.equalsIgnoreCase("byte")) {
                rtn = new HttpResponseByte();
            } else if (responseType.equalsIgnoreCase("string")) {
                rtn = new HttpResponseString();
            } else {
                throw new Exception((new StringBuilder()).append("无法识别的返回类型:").append(responseType).toString());
            }
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(timeoutSeconds * 1000);
            client.getHttpConnectionManager().getParams().setSoTimeout(timeoutSeconds * 1000);
            PostMethod method = new PostMethod(url);
            if (httpRequest.getHeader() != null && !httpRequest.getHeader().isEmpty()) {
                Set<String> keys = httpRequest.getHeader().keySet();
                Iterator<String> iter = keys.iterator();
                while (iter.hasNext()) {
                    String item = iter.next();
                    method.addRequestHeader(item, httpRequest.getHeader().get(item));
                }
            }
            Map<String, Object> parameters = httpRequest.getParameters();
            if (parameters != null && !parameters.isEmpty()) {
                List list = new ArrayList();
                Set keys = parameters.keySet();
                String item;
                for (Iterator iter = keys.iterator(); iter.hasNext(); list.add(new NameValuePair(item, (String) parameters.get(item)))) {
                    item = (String) iter.next();
                }
                method.setQueryString((NameValuePair[]) list.toArray(new NameValuePair[0]));
            }

            if (httpRequest.getBody() != null && httpRequest.getBody().length > 0) {
                org.apache.commons.httpclient.methods.RequestEntity objRequestEntity = new ByteArrayRequestEntity(httpRequest.getBody());
                method.setRequestEntity(objRequestEntity);
            }

            DefaultHttpMethodRetryHandler retry = new DefaultHttpMethodRetryHandler(0, false);
            method.getParams().setParameter("http.method.retry-handler", retry);
            if (logger.isDebugEnabled()) {
                logger.debug((new StringBuilder()).append("http请求的url:").append(url).toString());
                List list = new ArrayList();
                Header header[] = method.getRequestHeaders();
                if (header != null) {
                    for (int i = 0; i < header.length; i++) {
                        list.add(header[i].toString());
                    }
                }
                logger.debug((new StringBuilder()).append("http请求的头:").append(StringUtils.join(list.iterator(), " , ")).toString());
                logger.debug((new StringBuilder()).append("http请求的参数:").append(method.getQueryString()).toString());
                if (httpRequest.getBody() != null) {
                    logger.debug((new StringBuilder()).append("http请求输入流的字节数:").append(httpRequest.getBody()).toString());
                }
            }
            try {
                int statusCode = client.executeMethod(method);
                rtn.setStatusCode(statusCode);
                if (statusCode != 200) {
                    logger.debug((new StringBuilder()).append("请求url:").append(url).append("失败,返回代码:").append(statusCode).toString());
                } else if (rtn instanceof HttpResponseByte) {
                    ((HttpResponseByte) rtn).setResponseBody(method.getResponseBody());
                } else if (rtn instanceof HttpResponseString) {
                    ((HttpResponseString) rtn).setResponseBody(method.getResponseBodyAsString());
                } else {
                    throw new Exception((new StringBuilder()).append("无法识别的返回对象:").append(rtn).toString());
                }
            } catch (Exception ex) {
                logger.error("执行http的post失败", ex);
                if (method != null)
                    try {
                        method.releaseConnection();
                    } catch (Exception ex2) {
                        logger.error("关闭http连接出现失败", ex2);
                    }
                throw new Exception(ex);
            }
            httpresponse = rtn;
        } catch (Exception eee) {
            throw eee;
        } finally {
            if (isAcquire) {
                SEM.release();
            }
        }
        return httpresponse;
    }

    /**
     * Post请求，返回结果为一个byte数组
     *
     * @param url      url地址
     * @param paramMap 请求参数
     */
    public static byte[] doHttpPost(String url, Map<String, String> paramMap) throws Exception {
        HttpClient client = null;
        PostMethod httpPost = null;
        byte[] bytes = new byte[0];
        try {
            client = new HttpClient();
            httpPost = new PostMethod(url);
            if (paramMap != null && paramMap.size() > 0) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                Iterator iterator = paramMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry) iterator.next();
                    list.add(new NameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setRequestBody(list.toArray(new NameValuePair[0]));
            }
            client.getHttpConnectionManager().getParams().setConnectionTimeout(10 * 1000);
            client.getHttpConnectionManager().getParams().setSoTimeout(10 * 1000);

            int status = client.executeMethod(httpPost);
            if (status == 200) {
                bytes = httpPost.getResponseBody();
            } else {
                throw new Exception("调用http请求失败" + status);
            }
        } catch (Exception e) {
            logger.error("调用http请求失败", e);
            throw e;
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        return bytes;
    }
}