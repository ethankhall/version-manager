package tech.crom.database.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.stereotype.Service
import tech.crom.database.api.CacheManager
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.repository.CromRepo

@Service
class DefaultCacheManager @Autowired constructor(
    val cacheManager: EhCacheCacheManager) : CacheManager {

    override fun purgeCacheFor(cromRepo: CromRepo) {
        cacheManager.cacheManager.getEhcache("commitById").removeAll()
        cacheManager.cacheManager.getEhcache("allCommitsByRepo").remove(cromRepo.repoId.toString())
    }

    override fun purgeCacheFor(cromRepo: CromRepo, commit: PersistedCommit) {
        purgeCacheFor(cromRepo)
    }
}
