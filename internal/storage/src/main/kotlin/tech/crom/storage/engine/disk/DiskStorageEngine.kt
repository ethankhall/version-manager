package tech.crom.storage.engine.disk

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import tech.crom.model.metadata.StorageData
import tech.crom.storage.engine.StorageEngine
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths

@Service
@ConditionalOnProperty(name = arrayOf("storage.engine"), havingValue = "disk")
open class DiskStorageEngine(val rootDir: File) : StorageEngine {

    @Autowired constructor(env: Environment): this(File(env.getRequiredProperty("storage.disk.root")))

    init {
        if (!rootDir.exists()) {
            throw RuntimeException("Unable to start because ${rootDir.absolutePath} dir is not available")
        }
    }

    override fun upload(path: String, storageData: StorageData): URI {
        val storedFile = File(rootDir, path).toPath()
        Files.createDirectories(storedFile.parent)
        Files.copy(ByteArrayInputStream(storageData.bytes), storedFile)

        return storedFile.toUri()
    }

    override fun download(uri: URI): ByteArray {
        val outputStream = ByteArrayOutputStream()
        Files.copy(Paths.get(uri), outputStream)
        return outputStream.toByteArray()
    }

    override fun delete(uri: URI) = Files.delete(Paths.get(uri))

    override fun exists(uri: URI): Boolean = Files.exists(Paths.get(uri))
}
