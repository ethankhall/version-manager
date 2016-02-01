package io.ehdev.conrad.app

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

import javax.sql.DataSource

@Configuration
@Import(MainApplication)
class TestWebAppConfiguration {
    @Bean(destroyMethod = "close")
    DataSource dataSource() {
        def config = new HikariConfig()
        config.setJdbcUrl("jdbc:postgresql://172.0.1.100/version_manager_test")
        config.setUsername("version_manager_test")
        config.setPassword("password")
        return new HikariDataSource(config)
    }
}
