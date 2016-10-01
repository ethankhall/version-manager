package tech.crom.version.bumper.impl.semver.recognizer

import de.svenjacobs.loremipsum.LoremIpsum
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import tech.crom.version.bumper.impl.createVersionDetails
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ForceVersionMessageRecognizerTest: Spek({
    val ipsum = LoremIpsum()
    on("versions that should be forced") {
        val currentVersion = createVersionDetails()

        it("should work with short message") {
            val recognizer = ForceVersionMessageRecognizer().produce(currentVersion, "[force version 1.2.4]")
            assertNotNull(recognizer)
            assertEquals(recognizer!!.nextVersion(), "1.2.4")
        }

        it("should search with full message") {
            val message = ipsum.getParagraphs(2) + "[force version 1.2.4]" + ipsum.getParagraphs(3)
            val recognizer = ForceVersionMessageRecognizer().produce(currentVersion, message)
            assertNotNull(recognizer)
            assertEquals(recognizer!!.nextVersion(), "1.2.4")
        }

        it("should parse a long version string") {
            val message = "[force version 1.2.3.4.5.6.7.8.9-BETA2]"
            val recognizer = ForceVersionMessageRecognizer().produce(currentVersion, message)
            assertNotNull(recognizer)
            assertEquals(recognizer!!.nextVersion(), "1.2.3.4.5.6.7.8.9-BETA2")
        }
    }
})
