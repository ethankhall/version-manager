package tech.crom.storage.config

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("tech.crom.storage")
open class StorageConfig {

    @Bean
    @ConditionalOnProperty(name = arrayOf("storage.default.credentials"), havingValue = "google-cloud")
    open fun googleCredentials(): GoogleCredential = GoogleCredential.getApplicationDefault()
}
