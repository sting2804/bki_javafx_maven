package com.privat.bki.desktopapp.utils;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

public class RestAuthClient {
    private String baseUrl = "http://localhost:8181/loans";
    
    public String authenticateGetCookie(String user, String password){
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();

        HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();

        List<HttpMessageConverter> messageConverters = new LinkedList<>();

        messageConverters.add(formHttpMessageConverter);
        messageConverters.add(stringHttpMessageConverternew);
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("j_username", user);
        map.add("j_password", password);

        String authURL = baseUrl+"/j_spring_security_check";
        RestTemplate restTemplate = new RestTemplate();

//        restTemplate.setMessageConverters(messageConverters);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap> entity = new HttpEntity<>(map,requestHeaders);

        ResponseEntity result = restTemplate.exchange(authURL, HttpMethod.POST, entity, String.class);
        HttpHeaders respHeaders = result.getHeaders();
        System.out.println(respHeaders.toString());

        System.out.println(result.getStatusCode());

        String cookies = respHeaders.getFirst("Set-Cookie");
        return cookies;
    }
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}