package io.ehdev.conrad.service.api.config

import io.ehdev.conrad.database.config.ConradDatabaseConfig
import io.ehdev.conrad.version.config.ConradVersionConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType

@Configuration
@EnableAutoConfiguration
@Import([ConradProjectApiConfiguration, ConradDatabaseConfig, ConradVersionConfiguration])
class TestConradProjectApiConfiguration {

    @Bean(destroyMethod = "shutdown")
    EmbeddedDatabase embeddedDatabase() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build()
    }

}
