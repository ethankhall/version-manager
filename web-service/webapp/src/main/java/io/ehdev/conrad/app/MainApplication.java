package io.ehdev.conrad.app;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ehcache.InstrumentedEhcache;
import io.ehdev.conrad.authentication.config.ConradSecurityConfig;
import io.ehdev.conrad.database.config.ConradDatabaseConfig;
import io.ehdev.conrad.service.api.config.ConradProjectApiConfiguration;
import io.ehdev.conrad.version.config.ConradVersionConfiguration;
import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableCaching
@Configuration
@Import({
    ConradDatabaseConfig.class, ConradVersionConfiguration.class,
    ConradSecurityConfig.class, ConradProjectApiConfiguration.class})
@ComponentScan("io.ehdev.conrad.app")
@EnableAutoConfiguration
public class MainApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean(destroyMethod="shutdown")
    public net.sf.ehcache.CacheManager ehCacheManager(MetricRegistry metricRegistry) {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setTimeToLiveSeconds(10);
        cacheConfiguration.setName("versionsForRepo");
        cacheConfiguration.setMaxEntriesLocalHeap(5000);
        cacheConfiguration.setMaxEntriesLocalDisk(0);
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");

        Cache cache = new Cache(cacheConfiguration);
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.create();
        cacheManager.addCache(InstrumentedEhcache.instrument(metricRegistry, cache));

        return cacheManager;
    }

    @Bean
    public CacheManager cacheManager(net.sf.ehcache.CacheManager ehCacheManager) {
        return new EhCacheCacheManager(ehCacheManager);
    }
}
