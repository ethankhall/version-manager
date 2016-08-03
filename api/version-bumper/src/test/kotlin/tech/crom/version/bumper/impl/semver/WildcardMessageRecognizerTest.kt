package tech.crom.version.bumper.impl.semver

import de.svenjacobs.loremipsum.LoremIpsum
import org.jetbrains.spek.api.Spek
import tech.crom.version.bumper.model.ReservedVersionModel
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class WildcardMessageRecognizerTest : Spek({
    fun generateMessage(input: Int): String {
        val loremIpsum = LoremIpsum()
        return loremIpsum.getParagraphs(3) + "[bump group $input]" + loremIpsum.getParagraphs(2)
    }

    val currentVersion = ReservedVersionModel("", "1.2.3", LocalDateTime.now())

    on("a new version will be used") {
        val recognizer = WildcardMessageRecognizer()
        it("will produce a major version bump") {
            val versionCreator = recognizer.produce(currentVersion, generateMessage(0))
            assertNotNull(versionCreator)
            assertEquals(versionCreator!!.groupNumber, 0)
        }

        it("will produce a minor version bump") {
            val versionCreator = recognizer.produce(currentVersion, generateMessage(1))
            assertNotNull(versionCreator)
            assertEquals(versionCreator!!.groupNumber, 1)
        }

        it("will produce a patch version bump") {
            val versionCreator = recognizer.produce(currentVersion, generateMessage(2))
            assertNotNull(versionCreator)
            assertEquals(versionCreator!!.groupNumber, 2)
        }

        it("will produce a build version bump") {
            val versionCreator = recognizer.produce(currentVersion, generateMessage(4))
            assertNotNull(versionCreator)
            assertEquals(versionCreator!!.groupNumber, 4)
        }

        it("will produce a 99th version bump") {
            val versionCreator = recognizer.produce(currentVersion, generateMessage(99))
            assertNotNull(versionCreator)
            assertEquals(versionCreator!!.groupNumber, 99)
        }
    }
})
