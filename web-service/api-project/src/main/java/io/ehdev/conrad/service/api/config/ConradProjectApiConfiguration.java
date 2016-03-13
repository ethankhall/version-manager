package io.ehdev.conrad.service.api.config;

import com.fasterxml.jackson.module.kotlin.KotlinModule;
import io.ehdev.conrad.authentication.jwt.config.ConradJwtConfig;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
@EnableAspectJAutoProxy
@Import({ApiServiceWebMvcConfigurer.class, ConradJwtConfig.class})
@ComponentScan("io.ehdev.conrad.service.api")
public class ConradProjectApiConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder().modulesToInstall(new KotlinModule());
    }
}
