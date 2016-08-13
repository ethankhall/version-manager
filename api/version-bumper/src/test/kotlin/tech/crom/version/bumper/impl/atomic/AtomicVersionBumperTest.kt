package tech.crom.version.bumper.impl.atomic

import org.jetbrains.spek.api.Spek
import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.version.bumper.impl.createVersionDetails
import kotlin.test.assertEquals

class AtomicVersionBumperTest : Spek({
    on("an atomic bumper") {
        val atomicBumper = AtomicVersionBumper()
        it("will produce a version from none") {
            val nextVersion = atomicBumper.calculateNextVersion(RequestedCommit("abc", "asdfasf"), null)
            assertEquals(nextVersion.commitId, "abc")
            assertEquals(nextVersion.version.versionString, "1")
        }

        it("will produce the next version from existing") {
            val currentVersion = createVersionDetails("13")
            val nextVersion = atomicBumper.calculateNextVersion(RequestedCommit("abc", "asdfasf"), currentVersion)
            assertEquals(nextVersion.commitId, "abc")
            assertEquals(nextVersion.version.versionString, "14")
        }

        it("will produce the next version from existing, from list") {
            val currentVersion = createVersionDetails("1.3.4")
            val nextVersion = atomicBumper.calculateNextVersion(RequestedCommit("abc", "asdfasf"), currentVersion)
            assertEquals(nextVersion.commitId, "abc")
            assertEquals(nextVersion.version.versionString, "2")
        }
    }
})
