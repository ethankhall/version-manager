package io.ehdev.conrad.api.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class ApiUserWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        BaseUserModelResolver resolver = new BaseUserModelResolver();
        argumentResolvers.add(resolver);
    }
}
