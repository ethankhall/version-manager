package tech.crom.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(MetricsConfiguration::class, EhCacheConfig::class)
open class SharedMasterConfig
