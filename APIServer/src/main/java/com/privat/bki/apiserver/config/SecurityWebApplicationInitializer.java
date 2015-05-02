package com.privat.bki.apiserver.config;

import org.springframework.security.web.context.*;

/**
 * for loading the springSecurityFilterChain automatically
 * empty class is equivalent to
 <filter>
 <filter-name>springSecurityFilterChain</filter-name>
 <filter-class>org.springframework.web.filter.DelegatingFilterProxy
 </filter-class>
 </filter>

 <filter-mapping>
 <filter-name>springSecurityFilterChain</filter-name>
 <url-pattern>/*</url-pattern>
 </filter-mapping>
 */
public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityConfig.class);
    }
}