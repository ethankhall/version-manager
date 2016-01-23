package io.ehdev.conrad.api.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ApiUserWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/v1/user/**").authenticated();
    }
}
