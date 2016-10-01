package tech.crom.storage.engine.disk

import org.jetbrains.spek.api.Spek
import org.springframework.mock.env.MockEnvironment
import tech.crom.model.metadata.StorageData
import java.io.File
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DiskStorageEngineTest : Spek({
    describe("can save files") {
        var tempDir: File? = null

        beforeEach {
            tempDir = createTempDir()
        }
        it("can do crud") {
            val mockEnvironment = MockEnvironment()
            mockEnvironment.setProperty("storage.disk.root", tempDir!!.absolutePath)
            val storageEngine = DiskStorageEngine(mockEnvironment)

            val input = "this is a file".toByteArray()
            val uri = storageEngine.upload("blob/text.txt", StorageData("", input, "text/plain"))

            assertTrue(storageEngine.exists(uri))

            storageEngine.download(uri) == input

            storageEngine.delete(uri)

            assertFalse(storageEngine.exists(uri))
        }


        afterEach {
            tempDir!!.deleteRecursively()
        }
    }
})
