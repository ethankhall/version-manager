package tech.crom.version.bumper.impl.semver.recognizer

import de.svenjacobs.loremipsum.LoremIpsum
import org.jetbrains.spek.api.Spek
import tech.crom.version.bumper.impl.createVersionDetails
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SquareBracketMessageRecognizerTest : Spek({
    fun generateMessage(message: String): String {
        val loremIpsum = LoremIpsum()
        return loremIpsum.getParagraphs(3) + message + loremIpsum.getParagraphs(2)
    }

    val currentVersion = createVersionDetails()
    on("simple square bracket search") {

        it("will be able to search for any string") {
            val recognizer = SquareBracketMessageRecognizer("major", 0)
            assertNotNull(recognizer.produce(currentVersion, generateMessage("[bump major version]")))
            assertNotNull(recognizer.produce(currentVersion, generateMessage("[bump major version ]")))
            assertNotNull(recognizer.produce(currentVersion, generateMessage("[bump major]")))
            assertNotNull(recognizer.produce(currentVersion, generateMessage("[bump major ]")))
        }
    }

    on("it will update full details") {
        it("should bump the major component") {
            val recognizer = SquareBracketMessageRecognizer("major", 0)
            val producer = recognizer.produce(currentVersion, generateMessage("[bump major version]"))
            assertNotNull(producer)
            assertEquals(producer!!.nextVersion(), "2.0.0")
        }
    }
})
