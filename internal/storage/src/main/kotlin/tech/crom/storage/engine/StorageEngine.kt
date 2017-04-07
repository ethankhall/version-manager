package tech.crom.storage.engine

import tech.crom.model.metadata.StorageData
import java.net.URI

interface StorageEngine {
    fun upload(path: String, storageData: StorageData): URI
    fun download(uri: URI): ByteArray?
    fun delete(uri: URI)
    fun exists(uri: URI): Boolean
}
