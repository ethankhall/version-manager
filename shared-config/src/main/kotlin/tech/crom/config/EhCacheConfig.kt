package tech.crom.config

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.ehcache.InstrumentedEhcache
import net.sf.ehcache.Cache
import net.sf.ehcache.config.CacheConfiguration
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@EnableCaching
@Configuration
@Import(MetricsConfiguration::class)
open class EhCacheConfig() {

    @Bean(destroyMethod = "shutdown")
    open fun ehCacheManager(metricRegistry: MetricRegistry): net.sf.ehcache.CacheManager {
        val cacheManager = net.sf.ehcache.CacheManager.create()

        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, versionForRepoCacheConfig()))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, aclCacheConfig()))

        return cacheManager
    }

    private fun versionForRepoCacheConfig(): Cache {
        val cacheConfiguration = CacheConfiguration()
        cacheConfiguration.setTimeToLiveSeconds(10)
        cacheConfiguration.setName("versionsForRepo")
        cacheConfiguration.setMaxEntriesLocalHeap(5000)
        cacheConfiguration.setMaxEntriesLocalDisk(0)
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU")
        return Cache(cacheConfiguration)
    }

    private fun aclCacheConfig(): Cache {
        val cacheConfiguration = CacheConfiguration()
        cacheConfiguration.setName("aclCache")
        cacheConfiguration.setMaxEntriesLocalHeap(5000)
        cacheConfiguration.setMaxEntriesLocalDisk(0)
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU")
        return Cache(cacheConfiguration)
    }

    @Bean
    open fun cacheManager(ehCacheManager: net.sf.ehcache.CacheManager): EhCacheCacheManager {
        return EhCacheCacheManager(ehCacheManager)
    }
}
