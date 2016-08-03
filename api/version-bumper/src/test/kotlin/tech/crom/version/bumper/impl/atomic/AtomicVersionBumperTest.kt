package tech.crom.version.bumper.impl.atomic

import org.jetbrains.spek.api.Spek
import tech.crom.version.bumper.model.CommitModel
import tech.crom.version.bumper.model.ReservedVersionModel
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AtomicVersionBumperTest : Spek({
    on("an atomic bumper") {
        val atomicBumper = AtomicVersionBumper()
        it("will produce a version from none") {
            val nextVersion = atomicBumper.calculateNextVersion(CommitModel("abc", "asdfasf"), null)
            assertEquals(nextVersion.commitId, "abc")
            assertEquals(nextVersion.version, "1")
        }

        it("will produce the next version from existing") {
            val currentVersion = ReservedVersionModel("", "13", LocalDateTime.now())
            val nextVersion = atomicBumper.calculateNextVersion(CommitModel("abc", "asdfasf"), currentVersion)
            assertEquals(nextVersion.commitId, "abc")
            assertEquals(nextVersion.version, "14")
        }

        it("will produce the next version from existing, from list") {
            val currentVersion = ReservedVersionModel("", "1.3.4", LocalDateTime.now())
            val nextVersion = atomicBumper.calculateNextVersion(CommitModel("abc", "asdfasf"), currentVersion)
            assertEquals(nextVersion.commitId, "abc")
            assertEquals(nextVersion.version, "2")
        }

        it("will throw when version is crap") {
            val currentVersion = ReservedVersionModel("", "z.y.z", LocalDateTime.now())
            assertFailsWith(NumberFormatException::class, { atomicBumper.calculateNextVersion(CommitModel("abc", "asdfasf"), currentVersion) })
        }
    }
})
