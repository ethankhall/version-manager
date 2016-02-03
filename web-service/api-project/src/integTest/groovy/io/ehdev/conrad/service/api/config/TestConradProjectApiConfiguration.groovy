package io.ehdev.conrad.service.api.config

import io.ehdev.conrad.database.config.ConradDatabaseConfig
import io.ehdev.conrad.version.config.ConradVersionConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import([ConradDatabaseConfig, ConradProjectApiConfiguration, ConradVersionConfiguration])
class TestConradProjectApiConfiguration {

}
