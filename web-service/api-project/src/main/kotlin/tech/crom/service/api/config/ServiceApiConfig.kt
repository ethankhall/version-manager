package tech.crom.service.api.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@EnableWebMvc
@Configuration
@ComponentScan("io.ehdev.conrad.service.api", "tech.crom.service.api")
open class ServiceApiConfig : WebMvcConfigurerAdapter() {

    @Autowired
    lateinit var resolver: RequestDetailsParameterResolver

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(resolver)
    }
}
