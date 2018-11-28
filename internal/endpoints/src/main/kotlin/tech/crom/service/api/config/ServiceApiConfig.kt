package tech.crom.service.api.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer

@EnableWebFlux
@Configuration
@ComponentScan("io.ehdev.conrad.service.api", "tech.crom.service.api")
class ServiceApiConfig : WebFluxConfigurer {

    @Autowired
    lateinit var resolver: RequestDetailsParameterResolver

    @Autowired
    lateinit var env: Environment

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(resolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        val corsDomain = env.getRequiredProperty("web-ui.cors-domain").split(",").toTypedArray()
        registry.addMapping("/api/**")
            .allowCredentials(true)
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowedOrigins(*corsDomain)
    }
}
