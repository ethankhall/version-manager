package io.ehdev.conrad.service.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class ApiServiceWebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    ApiParameterContainerResolver apiParameterContainerResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(apiParameterContainerResolver);
    }
}
