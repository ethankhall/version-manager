package tech.crom.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(EhCacheConfig::class)
open class SharedMasterConfig
