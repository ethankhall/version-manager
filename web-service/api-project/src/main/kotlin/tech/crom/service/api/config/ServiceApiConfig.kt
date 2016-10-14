package tech.crom.service.api.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.resource.GzipResourceResolver
import org.springframework.web.servlet.resource.PathResourceResolver
import java.util.concurrent.TimeUnit

@EnableWebMvc
@Configuration
@EnableAutoConfiguration
@ComponentScan("io.ehdev.conrad.service.api", "tech.crom.service.api")
open class ServiceApiConfig : WebMvcConfigurerAdapter() {

    @Autowired
    lateinit var resolver: RequestDetailsParameterResolver

    @Autowired
    lateinit var env: Environment

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(resolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        val domains = env.getRequiredProperty("web-ui.cors-domain").split(",")
        registry.addMapping("/api/**").allowedOrigins(*domains.toTypedArray())
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val builder = Jackson2ObjectMapperBuilder()
        builder
            .serializationInclusion(JsonInclude.Include.ALWAYS)
            .indentOutput(true)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modulesToInstall(KotlinModule(), JavaTimeModule())
            .defaultViewInclusion(true)
        converters.add(MappingJackson2HttpMessageConverter(builder.build()))
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
            .setCachePeriod(TimeUnit.MINUTES.toSeconds(1).toInt())
            .resourceChain(true)
            .addResolver(GzipResourceResolver())
            .addResolver(PathResourceResolver())

        registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/")
            .resourceChain(true)
            .addResolver(GzipResourceResolver())
            .addResolver(PathResourceResolver())
    }
}
