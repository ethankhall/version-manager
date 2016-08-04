package tech.crom.version.bumper.impl.atomic

import org.jetbrains.spek.api.Spek
import tech.crom.version.bumper.impl.createReservedVersionModel
import tech.crom.version.bumper.model.CommitModel
import kotlin.test.assertEquals

class AtomicVersionBumperTest : Spek({
    on("an atomic bumper") {
        val atomicBumper = AtomicVersionBumper()
        it("will produce a version from none") {
            val nextVersion = atomicBumper.calculateNextVersion(CommitModel("abc", "asdfasf"), null)
            assertEquals(nextVersion.commitId, "abc")
            assertEquals(nextVersion.version.version, "1")
        }

        it("will produce the next version from existing") {
            val currentVersion = createReservedVersionModel("13")
            val nextVersion = atomicBumper.calculateNextVersion(CommitModel("abc", "asdfasf"), currentVersion)
            assertEquals(nextVersion.commitId, "abc")
            assertEquals(nextVersion.version.version, "14")
        }

        it("will produce the next version from existing, from list") {
            val currentVersion = createReservedVersionModel("1.3.4")
            val nextVersion = atomicBumper.calculateNextVersion(CommitModel("abc", "asdfasf"), currentVersion)
            assertEquals(nextVersion.commitId, "abc")
            assertEquals(nextVersion.version.version, "2")
        }
    }
})
