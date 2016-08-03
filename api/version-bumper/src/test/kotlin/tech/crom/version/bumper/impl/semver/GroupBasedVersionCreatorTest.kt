package tech.crom.version.bumper.impl.semver

import org.jetbrains.spek.api.Spek
import tech.crom.version.bumper.model.ReservedVersionModel
import java.time.LocalDateTime
import kotlin.test.assertEquals


class GroupBasedVersionCreatorTest : Spek({
    on("expanding versions") {
        val currentVersion = ReservedVersionModel("", "1.2.3", LocalDateTime.now())

        it("will update major version") {
            val versionCreator = GroupBasedVersionCreator(currentVersion, 0)
            assertEquals(versionCreator.nextVersion(), "2.0.0")
        }

        it("will update minor version") {
            val versionCreator = GroupBasedVersionCreator(currentVersion, 1)
            assertEquals(versionCreator.nextVersion(), "1.3.0")
        }

        it("will update patch version") {
            val versionCreator = GroupBasedVersionCreator(currentVersion, 2)
            assertEquals(versionCreator.nextVersion(), "1.2.4")
        }

        it("will will create build group") {
            val versionCreator = GroupBasedVersionCreator(currentVersion, 3)
            assertEquals(versionCreator.nextVersion(), "1.2.3.1")
        }

        it("will will create the 10th group") {
            val versionCreator = GroupBasedVersionCreator(currentVersion, 9)
            assertEquals(versionCreator.nextVersion(), "1.2.3.0.0.0.0.0.0.1")
        }
    }
})
