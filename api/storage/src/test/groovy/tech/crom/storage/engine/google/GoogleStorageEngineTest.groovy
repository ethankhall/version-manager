package tech.crom.storage.engine.google

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification
import tech.crom.model.metadata.StorageData

class GoogleStorageEngineTest extends Specification {


    def env = new MockEnvironment().withProperty("storage.bucket.name", "test-storage-crom")
    def credentialsJson = GoogleStorageEngine.classLoader.getResourceAsStream("crom-dev-14dcad821985.json")
    def credential = GoogleCredential.fromStream(credentialsJson).createScoped(["https://www.googleapis.com/auth/cloud-platform"])
    def storageEngine = new GoogleStorageEngine(env, credential)

    def setup() {
        try {
            storageEngine.delete(toGoogleStorageUri("blob/text.txt"))
        } catch (Exception ignored) {
        }
    }

    def 'file is not in storage'() {
        expect:
        !storageEngine.exists(toGoogleStorageUri("blob/text.txt"))
    }

    def 'can store file in storage'() {
        when:
        def input = "this is a file".bytes
        def uri = storageEngine.upload("/blob/text.txt", new StorageData("", input, "text/plain"))

        then:
        assert storageEngine.exists(toGoogleStorageUri("blob/text.txt"))
        assert storageEngine.exists(uri)

        when:
        storageEngine.download(toGoogleStorageUri("blob/text.txt")) == input
        storageEngine.download(uri) == input

        storageEngine.delete(toGoogleStorageUri("blob/text.txt"))

        then:
        !storageEngine.exists(toGoogleStorageUri("blob/text.txt"))
        !storageEngine.exists(uri)
    }

    URI toGoogleStorageUri(String input) {
        return URI.create("gs://gcs/$input")
    }
}
