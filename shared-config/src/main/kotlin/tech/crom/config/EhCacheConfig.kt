package tech.crom.config

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.ehcache.InstrumentedEhcache
import net.sf.ehcache.Cache
import net.sf.ehcache.config.CacheConfiguration
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

        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("versionsForRepo", 10)))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("aclCache")))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("tokensById", 10)))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("tokensByResource", 10)))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("userById", 30)))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("repoDetailsByUid", 30)))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("repoByProjectAndName", 30)))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("projectByName", 30)))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("projectById", 30)))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("allCommitsByRepo", 30)))
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, createCache("commitById", 30)))

        return cacheManager
    }

    private fun createCache(name: String, ttl: Long? = null, maxEntries: Long = 5000, policy: String = "LRU"): Cache {
        val cacheConfiguration = CacheConfiguration()
        if(ttl != null) {
            cacheConfiguration.timeToLiveSeconds = ttl
        }
        cacheConfiguration.name = name
        cacheConfiguration.maxEntriesLocalHeap = maxEntries
        cacheConfiguration.maxEntriesLocalDisk = 0
        cacheConfiguration.setMemoryStoreEvictionPolicy(policy)
        return Cache(cacheConfiguration)
    }

    @Bean
    open fun cacheManager(ehCacheManager: net.sf.ehcache.CacheManager): EhCacheCacheManager {
        return EhCacheCacheManager(ehCacheManager)
    }
}
