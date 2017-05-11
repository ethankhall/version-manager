package tech.crom.business.impl

import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import tech.crom.business.api.StorageApi
import tech.crom.database.api.MetaDataManager
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.metadata.StorageData
import tech.crom.model.repository.CromRepo
import tech.crom.storage.engine.StorageEngine
import javax.transaction.Transactional

@Service
@Transactional
open class DefaultStorageApi(
    val storageEngine: StorageEngine,
    val metaDataManager: MetaDataManager,
    env: Environment
) : StorageApi {

    val maxStorageSize: Long

    init {
        maxStorageSize = env.getProperty("storage.size.max", Long::class.java, 5 * 1024 * 1024)
    }

    @Throws(StorageApi.MaxStorageReachedException::class)
    override fun insertFile(cromRepo: CromRepo, version: PersistedCommit, storageData: StorageData) {
        try {
            if (metaDataManager.getCurrentSizeOfProject(cromRepo) + storageData.bytes.size > maxStorageSize) {
                throw StorageApi.MaxStorageReachedException()
            }

            val uri = storageEngine.upload("/${cromRepo.repoId}/${version.id}/${storageData.fileName}", storageData)
            metaDataManager.insertFile(cromRepo, version, uri, storageData)
        } catch (e: Exception) {
            throw StorageApi.BackedException(e)
        }
    }

    override fun getFile(version: PersistedCommit, fileName: String): StorageData? {
        val fileMetaData = metaDataManager.findFile(version, fileName) ?: return null
        val bytes = storageEngine.download(fileMetaData.uri) ?: return null
        return StorageData(fileMetaData.fileName, bytes, fileMetaData.contentType)
    }

    override fun listFilesForVersion(version: PersistedCommit): List<String> {
        return metaDataManager.listFiles(version)
    }
}
