/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopclient.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author user
 */
public class RestTemplateRequester {

    private String baseUrl = "http://localhost:8181/";
    private final RestTemplate rt;
    private static final Logger log = Logger.getLogger(RestTemplateRequester.class);
    private final ObjectMapper om = new ObjectMapper();

    public RestTemplateRequester() {
        //HttpContext hc;
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(2000);
        factory.setConnectTimeout(2000);
        rt = new RestTemplate(factory);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public <T> T doRequest(String cmd, Class<T> returnType, Param... p) {
        try {
            Map<String, Object> map = new HashMap<>();
            for (Param p1 : p) {
                map.put(p1.getName(), p1.getValue());
            }
            return rt.getForObject(baseUrl + cmd, returnType, map);
        } catch (HttpServerErrorException ex) {
            handleHttpError(ex);
        }
        return null;
    }

    public <T> T doPostRequest(String cmd, T obj) {
        try {
            return (T) rt.postForObject(baseUrl + cmd, obj, obj.getClass());
        } catch (HttpServerErrorException ex) {
            handleHttpError(ex);
        }
        return null;
    }

    private void handleHttpError(HttpServerErrorException ex){
        logger.error(ex.getResponseBodyAsString(), ex);
        System.out.println("body: "+ex.getResponseBodyAsString());
        try {
            ExceptionWrapper ew = om.readValue(ex.getResponseBodyAsString(), ExceptionWrapper.class);
            if (ex.getStatusCode() == HttpStatus.CONFLICT) {
                throw new LogicException(ew.getMessage());
            } else {
                throw new RuntimeException(ew.getMessage());
            }
        } catch (IOException ex1) {
            throw new RuntimeException(ex.getResponseBodyAsString());
        }
    }

    public static void main(String[] args) throws IOException {
//        ExceptionWrapper ew = new ObjectMapper().readValue("{\"message\":\"Пользователь с логином test5 уже существует\",\"exceptionType\":\"class lection.diplom.example.exceptions.LogicException\",\"stackTrace\":\"lection.diplom.example.exceptions.LogicException: Пользователь с логином test5 уже существует\\n\\tat lection.diplom.example.spring.business.usermmanagement.UserComponent.add(UserComponent.java:46)\\n\\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\\n\\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\\n\\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\\n\\tat java.lang.reflect.Method.invoke(Method.java:483)\\n\\tat org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:317)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157)\\n\\tat org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:98)\\n\\tat org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:266)\\n\\tat org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:95)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)\\n\\tat org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:207)\\n\\tat com.sun.proxy.$Proxy139.add(Unknown Source)\\n\\tat lection.diplom.example.spring.controllers.UserController.add(UserController.java:47)\\n\\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\\n\\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\\n\\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\\n\\tat java.lang.reflect.Method.invoke(Method.java:483)\\n\\tat org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:215)\\n\\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\\n\\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)\\n\\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:781)\\n\\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:721)\\n\\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:83)\\n\\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:943)\\n\\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:877)\\n\\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:961)\\n\\tat org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:863)\\n\\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:644)\\n\\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:837)\\n\\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:725)\\n\\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:291)\\n\\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\\n\\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)\\n\\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:239)\\n\\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\\n\\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\\n\\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\\n\\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:239)\\n\\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\\n\\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:219)\\n\\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:106)\\n\\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:503)\\n\\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:136)\\n\\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:79)\\n\\tat org.apache.catalina.valves.AbstractAccessLogValve.invoke(AbstractAccessLogValve.java:610)\\n\\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:88)\\n\\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:526)\\n\\tat org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1078)\\n\\tat org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:655)\\n\\tat org.apache.coyote.http11.Http11NioProtocol$Http11ConnectionHandler.process(Http11NioProtocol.java:222)\\n\\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1566)\\n\\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.run(NioEndpoint.java:1523)\\n\\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)\\n\\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)\\n\\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\\n\\tat java.lang.Thread.run(Thread.java:745)\\n\"}", ExceptionWrapper.class);
//        System.out.println("ew="+ew);
        RestTemplateRequester rtr = new RestTemplateRequester();
        UserCollectionWrapper w = rtr.doRequest("user/list.xml", UserCollectionWrapper.class);
        System.out.println("" + w.getList());

        User u = new User();
        //u.setId(Integer.SIZE);
        u.setBirthDate(new Date());
        u.setLogin("test5");
        u.setName("name");
        u = rtr.doPostRequest("user/add.json", u);
        System.out.println("" + u);
//        try{
//            UserCollectionWrapper w = rt.getForObject("http://localhost:8080/WebSample/api/admin/user/list.xml", 
//                    UserCollectionWrapper.class);
//            System.out.println(""+w.getList());
//            Map<String, String> map = new HashMap<>();
//            map.put("id", "0");
//            User u = rt.getForObject("http://localhost:8080/WebSample/api/admin/user/info.xml?id={id}", 
//                    User.class, map);
//            System.out.println(""+u);
//        } catch(HttpClientErrorException ex){
//            System.out.println(""+ex.getResponseBodyAsString());
//        }
    }

}
