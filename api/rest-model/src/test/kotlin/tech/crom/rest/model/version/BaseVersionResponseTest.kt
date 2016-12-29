package tech.crom.rest.model.version

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import java.time.ZonedDateTime
import kotlin.test.assertEquals

class BaseVersionResponseTest : Spek({
    class ExampleBaseVersion(commitId: String, version: String, createdAt: ZonedDateTime) : BaseVersionResponse(commitId, version, createdAt)

    describe("create a simple object") {
        it("should return all the version parts") {
            val exampleBaseVersion = ExampleBaseVersion("abcdefg", "1.2.3", ZonedDateTime.now())
            assertEquals(exampleBaseVersion.version, "1.2.3")
        }

        it("should return all the version parts and postfix") {
            val exampleBaseVersion = ExampleBaseVersion("abcdefg", "1.2.3-NAME", ZonedDateTime.now())
            assertEquals(exampleBaseVersion.version, "1.2.3-NAME")
        }
    }
})
