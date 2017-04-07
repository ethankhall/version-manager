package tech.crom.storage.engine.memory

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import tech.crom.model.metadata.StorageData
import tech.crom.storage.engine.StorageEngine
import java.net.URI

@Service
@ConditionalOnProperty(name = arrayOf("storage.engine"), havingValue = "memory")
class InMemoryStorageEngine : StorageEngine {
    val map: MutableMap<String, ByteArray> = mutableMapOf()

    override fun upload(path: String, storageData: StorageData): URI {
        map[path] = storageData.bytes

        return URI.create("mem://localhost/$path")
    }

    override fun download(uri: URI): ByteArray? = map[uri.getFileName()]

    override fun delete(uri: URI) {
        map.remove(uri.getFileName())
    }

    override fun exists(uri: URI): Boolean = map.containsKey(uri.getFileName())

    fun URI.getFileName(): String = this.path.split("/").last()
}
