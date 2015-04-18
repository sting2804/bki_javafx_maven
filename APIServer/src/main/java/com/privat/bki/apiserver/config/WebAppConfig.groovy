package com.privat.bki.apiserver.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.JstlView
import org.springframework.web.servlet.view.UrlBasedViewResolver

/**
 * Created by sting on 4/18/15.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.privat.bki.apiserver.spring")
class WebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/")
    }

    @Bean
    UrlBasedViewResolver setupViewResolver() {

        UrlBasedViewResolver resolver = new UrlBasedViewResolver()
        resolver.setPrefix("/pages/")
        resolver.setSuffix(".jsp")
        resolver.setViewClass(JstlView.class)

        return resolver
    }
    @Bean
    void setupRestTemplate(){

    }
}
