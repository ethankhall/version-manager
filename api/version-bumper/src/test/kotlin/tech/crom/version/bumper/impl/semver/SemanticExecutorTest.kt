package tech.crom.version.bumper.impl.semver

import de.svenjacobs.loremipsum.LoremIpsum
import org.jetbrains.spek.api.Spek
import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.version.bumper.impl.createVersionDetails
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SemanticExecutorTest : Spek({
    val semanticVersionBumper = SemanticVersionBumper()
    val previousVersion = createVersionDetails()
    val commitModelCreator: (String) -> RequestedCommit = { message -> RequestedCommit("123", message) }

    on("force version") {
        val commitModel = commitModelCreator("[force version 1.2.3.4]")
        it("will handle no previous version found") {
            val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
            assertNotNull(versionModel)
            assertEquals("123", versionModel.commitId)
            assertEquals("1.2.3.4", versionModel.version.versionString)
        }

        it("will not care about previous version") {
            val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
            assertNotNull(versionModel)
            assertEquals(versionModel.commitId, "123")
            assertEquals(versionModel.version.versionString, "1.2.3.4")
        }
    }

    on("group based matcher") {
        given("major version bumper") {
            val commitModel = commitModelCreator("[bump major version]")
            it("will handle no previous version found") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("0.0.1", versionModel.version.versionString)
            }

            it("will use previous major version") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("2.0.0", versionModel.version.versionString)
            }
        }

        given("minor version bumper") {
            val commitModel = commitModelCreator("[bump minor version]")
            it("will handle no previous version found") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("0.0.1", versionModel.version.versionString)
            }

            it("will use previous minor version") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("1.3.0", versionModel.version.versionString)
            }
        }

        given("patch version bumper") {
            val commitModel = commitModelCreator("[bump patch version]")
            it("will handle no previous version found") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("0.0.1", versionModel.version.versionString)
            }

            it("will use previous patch version") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("1.2.4", versionModel.version.versionString)
            }
        }

        given("build version bumper") {
            val commitModel = commitModelCreator("[bump build version]")
            it("will handle no previous version found") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, null)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("0.0.1", versionModel.version.versionString)
            }

            it("will use previous build version") {
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("1.2.3.1", versionModel.version.versionString)
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
                assertEquals("0.0.1", versionModel.version.versionString)
            }
        }

        given("a previous version") {
            it("it will use next version") {
                val commitModel = commitModelCreator(LoremIpsum().getParagraphs(3))
                val versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)
                assertNotNull(versionModel)
                assertEquals("123", versionModel.commitId)
                assertEquals("1.2.4", versionModel.version.versionString)
            }
        }
    }
})
