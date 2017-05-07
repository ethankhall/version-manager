package tech.crom.config

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.Slf4jReporter
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
open class MetricsConfiguration {

    @Bean
    open fun metricRegistry(): MetricRegistry {
        return MetricRegistry()
    }

    @Bean
    @ConditionalOnMissingBean(Slf4jReporter::class)
    open fun consoleReporter(metricRegistry: MetricRegistry): Slf4jReporter {
        val reporter = Slf4jReporter
            .forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .outputTo(LoggerFactory.getLogger(MetricsConfiguration::class.java))
            .withLoggingLevel(Slf4jReporter.LoggingLevel.INFO)
            .build()

        reporter.start(1, TimeUnit.MINUTES)
        return reporter
    }
}
