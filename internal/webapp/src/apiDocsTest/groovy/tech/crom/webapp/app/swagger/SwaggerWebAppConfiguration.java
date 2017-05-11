package tech.crom.webapp.app.swagger;

import com.google.common.collect.Ordering;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.SimpleThreadScope;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(tech.crom.webapp.app.MainApplication.class)
public class SwaggerWebAppConfiguration {
    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("request", new SimpleThreadScope());
        return configurer;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            //@formatter:off
            .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*"))
            .build()
            //@formatter:on
            .forCodeGeneration(true)
            .apiDescriptionOrdering(buildOrdering())
            .apiInfo(apiInfo());
    }

    private static Ordering<ApiDescription> buildOrdering() {
        return Ordering.from((first, second) -> {
            String[] firstSplit = first.getPath().split("/");
            String[] secondSplit = second.getPath().split("/");

            if (firstSplit.length >= 3 && secondSplit.length >= 3) {
                if (!firstSplit[3].equals(secondSplit[3])) {
                    if ("constant".equalsIgnoreCase(secondSplit[3])) {
                        return -100;
                    }

                    if ("user".equalsIgnoreCase(secondSplit[3])) {
                        return -10;
                    }

                    if ("project".equalsIgnoreCase(secondSplit[3])) {
                        return 10;
                    }
                }
            }

            if (firstSplit.length == secondSplit.length) {
                return first.getPath().compareTo(second.getPath());
            }

            return secondSplit.length - firstSplit.length;
        });
    }

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Crom")
            .description("Version Management Service")
            .version(System.getProperty("crom.version"))
            .license("BSD-2")
            .build();
    }

}
