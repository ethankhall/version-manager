package tech.crom.business.api

import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.metadata.StorageData
import tech.crom.model.repository.CromRepo

interface StorageApi {

    fun insertFile(cromRepo: CromRepo, version: PersistedCommit, storageData: StorageData)

    fun getFile(version: PersistedCommit, fileName: String): StorageData?

    fun listFilesForVersion(version: PersistedCommit): List<String>

    class MaxStorageReachedException : RuntimeException("Your project has reached it's max size.")
    class BackedException(e: Throwable) : RuntimeException("An unknown error with the storage backend occurred.", e)
}
