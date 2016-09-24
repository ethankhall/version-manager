package tech.crom.storage.engine

import tech.crom.model.metadata.StorageData
import java.net.URI

interface StorageEngine {
    fun upload(path: String, storageData: StorageData): URI
    fun download(uri: URI): StorageData
    fun delete(uri: URI)
    fun exists(uri: URI): Boolean
}
