package io.ehdev.conrad.model.version

import org.jetbrains.spek.api.Spek
import kotlin.test.assertEquals

class BaseVersionResponseTest : Spek({
    class ExampleBaseVersion(commitId: String, version: String) : BaseVersionResponse(commitId, version)

    describe("create a simple object") {
        it("should return all the version parts") {
            val exampleBaseVersion = ExampleBaseVersion("abcdefg", "1.2.3")
            assertEquals(exampleBaseVersion.versionParts.size, 3)
        }

        it("should return all the version parts and postfix") {
            val exampleBaseVersion = ExampleBaseVersion("abcdefg", "1.2.3-NAME")
            assertEquals(exampleBaseVersion.versionParts.size, 3)
            assertEquals(exampleBaseVersion.postfix, "NAME")
        }
    }
})
