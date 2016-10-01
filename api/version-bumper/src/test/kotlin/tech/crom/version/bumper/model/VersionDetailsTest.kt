package tech.crom.version.bumper.model

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import tech.crom.model.commit.VersionDetails
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class VersionDetailsTest: Spek({
    on("versions") {
        it("will be able to parse normal version") {
            val normalVersion = VersionDetails("1.2.3")
            assertNull(normalVersion.postFix)
            assertEquals(normalVersion.versionString, "1.2.3")
            assertEquals(normalVersion.versionParts, listOf(1, 2, 3))
        }

        it("will be able to parse a single number version") {
            val normalVersion = VersionDetails("1")
            assertNull(normalVersion.postFix)
            assertEquals(normalVersion.versionString, "1")
            assertEquals(normalVersion.versionParts, listOf(1))
        }

        it("will be able to parse a long normal version") {
            val normalVersion = VersionDetails("1.2.3.4.5.6.7.8.9")
            assertNull(normalVersion.postFix)
            assertEquals(normalVersion.versionString, "1.2.3.4.5.6.7.8.9")
            assertEquals(normalVersion.versionParts, listOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
        }

        it("will be able to handle postfix's") {
            val normalVersion = VersionDetails("1.2.3-SNAPSHOT")
            assertEquals(normalVersion.postFix, "SNAPSHOT")
            assertEquals(normalVersion.versionString, "1.2.3-SNAPSHOT")
            assertEquals(normalVersion.versionParts, listOf(1, 2, 3))
        }

        it("will throw when only a snapshot is used") {
            assertFailsWith(RuntimeException::class, { VersionDetails("SNAPSHOT") })
        }

        it("can parse a spring version") {
            val normalVersion = VersionDetails("1.2.3.RELEASE")
            assertEquals(normalVersion.postFix, "RELEASE")
            assertEquals(normalVersion.versionString, "1.2.3.RELEASE")
            assertEquals(normalVersion.versionParts, listOf(1, 2, 3))
        }

        it("can parse a spring version with snapshot") {
            val normalVersion = VersionDetails("1.2.3.RELEASE-SNAPSHOT")
            assertEquals(normalVersion.postFix, "RELEASE-SNAPSHOT")
            assertEquals(normalVersion.versionString, "1.2.3.RELEASE-SNAPSHOT")
            assertEquals(normalVersion.versionParts, listOf(1, 2, 3))
        }
    }
})
