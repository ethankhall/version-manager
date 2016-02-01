package io.ehdev.conrad.version.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ehdev.conrad.database.config.ConradDatabaseConfig
import io.ehdev.conrad.service.api.config.ConradProjectApiConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

import javax.sql.DataSource

@Configuration
@Import([ConradDatabaseConfig, ConradProjectApiConfiguration, ConradVersionConfiguration])
class TestConradVersionConfiguration {

    @Bean(destroyMethod = "close")
    DataSource dataSource() {
        def config = new HikariConfig()
        config.setJdbcUrl("jdbc:postgresql://172.0.1.100/version_manager_test")
        config.setUsername("version_manager_test")
        config.setPassword("password")
        return new HikariDataSource(config)
    }
}
