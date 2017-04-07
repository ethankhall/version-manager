package tech.crom.storage.engine.disk

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification
import tech.crom.model.metadata.StorageData

class DiskStorageEngineTest extends Specification {

    @Rule
    TemporaryFolder temporaryFolder

    def 'can do crud'() {
        when:
        def mockEnvironment = new MockEnvironment()
        mockEnvironment.setProperty("storage.disk.root", temporaryFolder.root.absolutePath)
        def storageEngine = new DiskStorageEngine(mockEnvironment)

        def input = "this is a file".bytes
        def uri = storageEngine.upload("blob/text.txt", new StorageData("", input, "text/plain"))

        then:
        assert storageEngine.exists(uri)

        when:
        storageEngine.download(uri) == input

        storageEngine.delete(uri)

        then:
        !storageEngine.exists(uri)
    }
}
