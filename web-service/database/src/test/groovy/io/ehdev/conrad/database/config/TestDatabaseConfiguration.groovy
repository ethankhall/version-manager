package io.ehdev.conrad.database.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableAutoConfiguration
@Import(DatabaseConfiguration.class)
class TestDatabaseConfiguration {
}
