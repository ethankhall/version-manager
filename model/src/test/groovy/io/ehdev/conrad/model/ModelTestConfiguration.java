package io.ehdev.conrad.model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan("io.ehdev.version.model")
@Import(ModelConfiguration.class)
public class ModelTestConfiguration {

}
