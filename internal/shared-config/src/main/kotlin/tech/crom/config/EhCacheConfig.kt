package tech.crom.config

import net.sf.ehcache.Cache
import net.sf.ehcache.config.CacheConfiguration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@EnableCaching
@Configuration
class EhCacheConfig {

    @Bean(destroyMethod = "shutdown")
    fun ehCacheManager(): net.sf.ehcache.CacheManager {
        val cacheManager = net.sf.ehcache.CacheManager.create()

        cacheManager.addCache(createCache("aclCache"))
        cacheManager.addCache(createCache("tokensById", 10))
        cacheManager.addCache(createCache("tokensByRepo", 10))
        cacheManager.addCache(createCache("tokensByUser", 10))
        cacheManager.addCache(createCache("userById", 30))
        cacheManager.addCache(createCache("repoById", 30))
        cacheManager.addCache(createCache("repoDetailsById", 30))
        cacheManager.addCache(createCache("repoByProjectAndName", 30))
        cacheManager.addCache(createCache("projectByName", 30))
        cacheManager.addCache(createCache("projectById", 30))
        cacheManager.addCache(createCache("allCommitsByRepo", 30))
        cacheManager.addCache(createCache("commitById", 30))
        cacheManager.addCache(createCache("versionBumperByName"))
        cacheManager.addCache(createCache("versionBumperByRepo"))

        return cacheManager
    }

    private fun createCache(name: String, ttl: Long? = null, maxEntries: Long = 5000, policy: String = "LRU"): Cache {
        val cacheConfiguration = CacheConfiguration()
        if (ttl != null) {
            cacheConfiguration.timeToLiveSeconds = ttl
        }
        cacheConfiguration.name = name
        cacheConfiguration.maxEntriesLocalHeap = maxEntries
        cacheConfiguration.maxEntriesLocalDisk = 0
        cacheConfiguration.setMemoryStoreEvictionPolicy(policy)
        return Cache(cacheConfiguration)
    }

    @Bean
    fun cacheManager(ehCacheManager: net.sf.ehcache.CacheManager): EhCacheCacheManager {
        return EhCacheCacheManager(ehCacheManager)
    }
}
