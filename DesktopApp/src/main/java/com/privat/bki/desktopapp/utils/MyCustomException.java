package com.privat.bki.desktopapp.utils;

import org.springframework.http.HttpStatus;

/**
 * Created by sting on 6/7/15.
 */
public class MyCustomException extends RuntimeException {
    private HttpStatus statusCode;

    private String body;

    public MyCustomException(String msg) {
        super(msg);
    }

    public MyCustomException(HttpStatus statusCode, String body, String msg) {
        super(msg);
        this.statusCode = statusCode;
        this.body = body;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
