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
        if(metaDataManager.getCurrentSizeOfProject(cromRepo) + storageData.bytes.size > maxStorageSize) {
            throw StorageApi.MaxStorageReachedException()
        }

        val uri = storageEngine.upload("/${cromRepo.repoUid}/${version.commitUid}/${storageData.fileName}", storageData)
        metaDataManager.insertFile(cromRepo, version, uri, storageData)
    }

    override fun getFile(version: PersistedCommit, fileName: String): StorageData? {
        val fileUri = metaDataManager.findFile(version, fileName)?: return null
        return storageEngine.download(fileUri)
    }

    override fun listFilesForVersion(version: PersistedCommit): List<String> {
        return metaDataManager.listFiles(version)
    }
}