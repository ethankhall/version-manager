package tech.crom.version.bumper.impl.semver

import de.svenjacobs.loremipsum.LoremIpsum
import org.jetbrains.spek.api.Spek
import tech.crom.version.bumper.impl.createReservedVersionModel
import tech.crom.version.bumper.model.CommitModel
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SemanticVersionBumperTest : Spek({
    val semanticVersionBumper = SemanticVersionBumper()
    val previousVersion = createReservedVersionModel()
    val commitModelCreator: (String) -> CommitModel = { message -> CommitModel("123", message, LocalDateTime.now()) }

    on("force version") {
        val commitModel = commitModelCreator("[force version 1.2.3.4]")
        it("will handle no previous version found") {
            val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
            assertNotNull(versionModel)
            assertEquals("123", versionModel.commitId)
            assertEquals("1.2.3.4", versionModel.version.version)
        }

        it("will not care about previous version") {
            val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
            assertNotNull(versionModel)
            assertEquals(versionModel.commitId, "123")
            assertEquals(versionModel.version.version, "1.2.3.4")
        }
    }

    on("group based matcher") {
        given("major version bumper") {
            val commitModel = commitModelCreator("[bump major version]")
            it("will handle no previous version found") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("0.0.1", versionModel.version.version)
            }

            it("will use previous major version") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("2.0.0", versionModel.version.version)
            }
        }

        given("minor version bumper") {
            val commitModel = commitModelCreator("[bump minor version]")
            it("will handle no previous version found") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("0.0.1", versionModel.version.version)
            }

            it("will use previous minor version") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("1.3.0", versionModel.version.version)
            }
        }

        given("patch version bumper") {
            val commitModel = commitModelCreator("[bump patch version]")
            it("will handle no previous version found") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("0.0.1", versionModel.version.version)
            }

            it("will use previous patch version") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("1.2.4", versionModel.version.version)
            }
        }

        given("build version bumper") {
            val commitModel = commitModelCreator("[bump build version]")
            it("will handle no previous version found") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("0.0.1", versionModel.version.version)
            }

            it("will use previous build version") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("1.2.3.1", versionModel.version.version)
            }
        }
    }

    on("no version bump specified") {
        given("no previous version") {
            it("it will use next version") {
                val commitModel = commitModelCreator(LoremIpsum().getParagraphs(3))
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("0.0.1", versionModel.version.version)
            }
        }

        given("a previous version") {
            it("it will use next version") {
                val commitModel = commitModelCreator(LoremIpsum().getParagraphs(3))
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("1.2.4", versionModel.version.version)
            }
        }
    }
})
