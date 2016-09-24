package tech.crom.database.api

import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.metadata.StorageData
import tech.crom.model.repository.CromRepo
import java.net.URI

interface MetaDataManager {

    fun getCurrentSizeOfProject(cromRepo: CromRepo): Long

    fun addNewFile(cromRepo: CromRepo, persistedCommit: PersistedCommit, uri: URI, storageData: StorageData)

    fun findFileUri(cromRepo: CromRepo, version: PersistedCommit, fileName: String): URI?
}
