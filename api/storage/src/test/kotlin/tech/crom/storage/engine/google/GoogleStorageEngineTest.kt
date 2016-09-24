package tech.crom.storage.engine.google

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import org.jetbrains.spek.api.Spek
import org.springframework.mock.env.MockEnvironment
import tech.crom.model.metadata.StorageData
import java.net.URI
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GoogleStorageEngineTest: Spek({

    given("valid credentials") {
        val env = MockEnvironment()
        env.setProperty("storage.bucket.name", "test-storage-crom")
        val credentialsJson = GoogleStorageEngine::class.java.classLoader.getResourceAsStream("crom-dev-14dcad821985.json")
        val credential = GoogleCredential.fromStream(credentialsJson).createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
        val storageEngine = GoogleStorageEngine(env, credential)

        it("can store a file in storage") {
            assertFalse(storageEngine.exists("blob/text.txt".toGoogleStorageUri()))
        }

        it("can store file in storage") {
            val input = "this is a file".toByteArray()
            storageEngine.upload("/blob/text.txt", StorageData("", input, "text/plain"))

            assertTrue(storageEngine.exists("blob/text.txt".toGoogleStorageUri()))

            storageEngine.download("blob/text.txt".toGoogleStorageUri()).bytes == input

            storageEngine.delete("blob/text.txt".toGoogleStorageUri())

            assertFalse(storageEngine.exists("blob/text.txt".toGoogleStorageUri()))
        }
    }

})

fun String.toGoogleStorageUri(): URI = URI.create("gs://gcs/$this")
