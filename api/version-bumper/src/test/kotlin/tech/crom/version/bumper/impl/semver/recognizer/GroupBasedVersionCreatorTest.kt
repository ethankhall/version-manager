package tech.crom.version.bumper.impl.semver.recognizer

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import tech.crom.version.bumper.impl.createVersionDetails
import kotlin.test.assertEquals


class GroupBasedVersionCreatorTest : Spek({
    on("expanding versions") {
        val currentVersion = createVersionDetails()

        it("will update major version") {
            val versionCreator = GroupBasedVersionCreator(currentVersion, 0)
            assertEquals("2.0.0", versionCreator.nextVersion())
        }

        it("will update minor version") {
            val versionCreator = GroupBasedVersionCreator(currentVersion, 1)
            assertEquals("1.3.0", versionCreator.nextVersion())
        }

        it("will update patch version") {
            val versionCreator = GroupBasedVersionCreator(currentVersion, 2)
            assertEquals("1.2.4", versionCreator.nextVersion())
        }

        it("will will create build group") {
            val versionCreator = GroupBasedVersionCreator(currentVersion, 3)
            assertEquals("1.2.3.1", versionCreator.nextVersion())
        }

        it("will will create the 10th group") {
            val versionCreator = GroupBasedVersionCreator(currentVersion, 9)
            assertEquals("1.2.3.0.0.0.0.0.0.1", versionCreator.nextVersion())
        }
    }
})
