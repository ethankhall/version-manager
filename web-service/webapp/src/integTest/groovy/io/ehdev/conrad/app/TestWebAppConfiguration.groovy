package io.ehdev.conrad.app

import org.springframework.beans.factory.config.CustomScopeConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.support.SimpleThreadScope

@Configuration
@Import(MainApplication)
class TestWebAppConfiguration {

    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        def configurer = new CustomScopeConfigurer()
        configurer.addScope("request", new SimpleThreadScope())
        return configurer
    }

}
