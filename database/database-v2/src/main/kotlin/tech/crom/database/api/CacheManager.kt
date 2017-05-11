package tech.crom.database.api

import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.repository.CromRepo

interface CacheManager {
    fun purgeCacheFor(cromRepo: CromRepo)
    fun purgeCacheFor(cromRepo: CromRepo, commit: PersistedCommit)
}
