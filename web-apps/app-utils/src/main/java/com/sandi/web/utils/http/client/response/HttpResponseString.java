package com.sandi.web.utils.http.client.response;

/**
 * Created by LIUQ on 2015/7/21.
 */
public class HttpResponseString extends HttpResponse {

    private String responseBody;

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getResponseBody() {
        return responseBody;
    }
}

