package tech.crom.database.api

import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.metadata.StorageData
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import java.net.URI

interface MetaDataManager {

    fun getCurrentSizeOfProject(cromRepo: CromRepo): Long

    fun getCurrentSizeOfProject(cromProject: CromProject): Long

    fun insertFile(cromRepo: CromRepo, persistedCommit: PersistedCommit, uri: URI, storageData: StorageData)

    fun findFile(version: PersistedCommit, fileName: String): URI?

    fun listFiles(version: PersistedCommit): List<String>
}
