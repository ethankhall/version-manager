package io.ehdev.conrad.service.api.project.config

import io.ehdev.conrad.database.config.ConradDatabaseConfig
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.service.api.config.ConradProjectApiConfiguration
import io.ehdev.conrad.version.config.ConradVersionConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableWebMvc
@Configuration
@EnableSwagger2
@Import([ConradDatabaseConfig, ConradProjectApiConfiguration, ConradVersionConfiguration])
class SwaggerApiConfig {
    @Bean
    public Docket customImplementation() {
        def info = new ApiInfo(
            "Conrad Version Manager",
            "An API that lets you manage your versioning",
            "1.0.0",
            null,
            "Ethan Hall",
            "Custom",
            null
        )
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(info)
            .forCodeGeneration(true)
            .ignoredParameterTypes(ApiParameterContainer)
    }
}
