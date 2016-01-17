package io.ehdev.conrad.api.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(101)
@Configuration
@ComponentScan("io.ehdev.conrad.api.user")
public class ApiUserConfiguration extends WebSecurityConfigurerAdapter {
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/v1/user/**").authenticated();
    }
}
