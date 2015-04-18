package com.privat.bki.apiserver.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


/**
 * Expose the Spring Security Configuration
 * 
 * @author malalanayake
 * 
 */
@Configuration
@ImportResource({ "/WEB-INF/spring/application-security.xml" })
@ComponentScan("com.privat.bki.apiserver.spring.security")
public class SpringSecurityConfig {

	public SpringSecurityConfig() {
		super();
	}

}
