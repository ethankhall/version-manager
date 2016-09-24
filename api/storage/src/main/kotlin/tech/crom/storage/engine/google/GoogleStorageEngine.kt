package tech.crom.storage.engine.google

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.InputStreamContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.IOUtils
import com.google.api.services.storage.Storage
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import tech.crom.model.metadata.StorageData
import tech.crom.storage.engine.StorageEngine
import java.io.ByteArrayOutputStream
import java.net.URI

@Service
@ConditionalOnProperty("'\${storage.engine}' == 'google'")
class GoogleStorageEngine(env: Environment, val credential: GoogleCredential): StorageEngine {

    val bucketName: String
    val storage = getStorage()

    init {
        bucketName = env.getRequiredProperty("storage.bucket.name")
    }

    override fun upload(path: String, storageData: StorageData): URI {
        val content = InputStreamContent(storageData.contentType, storageData.bytes.inputStream())
        val insertObject = storage.objects().insert(bucketName, null, content).setName(path)

        insertObject.mediaHttpUploader.disableGZipContent = true

        insertObject.execute()

        return URI.create("gs://" + path)
    }

    override fun download(uri: URI): StorageData {
        val get = storage.objects().get(bucketName, uri.toPath())
        get.mediaHttpDownloader.isDirectDownloadEnabled = true
        val media = get.executeMedia()

        val outputStream = ByteArrayOutputStream()
        IOUtils.copy(media.content, outputStream)

        return StorageData(uri.getFileName(), outputStream.toByteArray(), media.contentType)
    }

    override fun delete(uri: URI) {
        storage.objects().delete(bucketName, uri.toPath()).execute()
    }

    override fun exists(uri: URI): Boolean {
        try {
            println(uri.toPath())
            return storage.objects().get(bucketName, uri.toPath()).executeUsingHead().isSuccessStatusCode
        } catch (gjre: GoogleJsonResponseException) {
            return false
        }
    }

    fun URI.toPath(): String = this.path
    fun URI.getFileName(): String = this.path.split("/").last()

    internal fun getStorage(): Storage {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()
        val storage = Storage.Builder(httpTransport, jsonFactory, credential).setApplicationName("Crom/1.0").build()
        return storage
    }

}
