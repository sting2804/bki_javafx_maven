package com.privat.bki.desktopapp.utils;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;


public class MyResponseErrorHandler implements ResponseErrorHandler {
    private static final Logger log = Logger.getLogger(MyResponseErrorHandler.class);

    private ResponseErrorHandler myErrorHandler = new DefaultResponseErrorHandler();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return myErrorHandler.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.error("Response error: "+ response.getStatusCode()+", "+ response.getStatusText());
        String body = response.getBody().toString();
        throw new MyCustomException(response.getStatusCode(), body, body);
    }

    /*@Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return RestUtil.isError(response.getStatusCode());
    }*/
}