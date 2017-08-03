package com.sandi.web.utils.http.client.response;

/**
 * Created by LIUQ on 2015/7/21.
 */
public class HttpResponseByte extends HttpResponse {

    private byte responseBody[];

    public void setResponseBody(byte responseBody[]) {
        this.responseBody = responseBody;
    }

    public byte[] getResponseBody() {
        return responseBody;
    }
}