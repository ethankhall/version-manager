package io.ehdev.conrad.app

import org.springframework.beans.factory.config.CustomScopeConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.support.SimpleThreadScope
import tech.crom.storage.engine.StorageEngine
import tech.crom.storage.engine.disk.DiskStorageEngine

@Configuration
@Import(MainApplication)
class TestWebAppConfiguration {

    @Bean
    public StorageEngine storageEngine() {
        def dir = File.createTempDir()
        dir.deleteOnExit()
        return new DiskStorageEngine(dir)
    }

    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        def configurer = new CustomScopeConfigurer()
        configurer.addScope("request", new SimpleThreadScope())
        return configurer
    }

}
