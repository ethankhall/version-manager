package io.ehdev.conrad.service.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
@Import({ApiServiceWebMvcConfigurer.class})
@ComponentScan("io.ehdev.conrad.service.api")
public class ConradProjectApiConfiguration {

}
