package com.sandi.web.utils.http.client.response;

/**
 * Created by LIUQ on 2015/7/21.
 */
public abstract class HttpResponse {

    private int statusCode;

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}