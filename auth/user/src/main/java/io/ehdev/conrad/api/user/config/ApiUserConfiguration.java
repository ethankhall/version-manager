package io.ehdev.conrad.api.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("io.ehdev.conrad.api.user")
@Import({ApiUserWebSecurityConfigurerAdapter.class, ApiUserWebMvcConfigurerAdapter.class})
public class ApiUserConfiguration {


}
