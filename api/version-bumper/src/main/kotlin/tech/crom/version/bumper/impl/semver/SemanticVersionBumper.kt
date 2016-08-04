package tech.crom.version.bumper.impl.semver

import tech.crom.version.bumper.VersionBumper
import tech.crom.version.bumper.impl.MessageRecognizer
import tech.crom.version.bumper.impl.VersionCreator
import tech.crom.version.bumper.impl.semver.recognizer.ForceVersionMessageRecognizer
import tech.crom.version.bumper.impl.semver.recognizer.SquareBracketMessageRecognizer
import tech.crom.version.bumper.impl.semver.recognizer.WildcardMessageRecognizer
import tech.crom.version.bumper.model.CommitModel
import tech.crom.version.bumper.model.ReservedVersionModel
import tech.crom.version.bumper.model.VersionDetails
import java.time.LocalDateTime

class SemanticVersionBumper : VersionBumper {

    val recognizers: List<MessageRecognizer>

    init {
        recognizers = listOf(
            ForceVersionMessageRecognizer(),
            SquareBracketMessageRecognizer("major", 0),
            SquareBracketMessageRecognizer("minor", 1),
            SquareBracketMessageRecognizer("patch", 2),
            SquareBracketMessageRecognizer("build", 3),
            WildcardMessageRecognizer()
        )
    }

    override fun calculateNextVersion(commitModel: CommitModel, lastCommit: ReservedVersionModel?): ReservedVersionModel {
        var versionCreator = findVersionCreator(commitModel, lastCommit)
        val nextVersion = if(versionCreator != null) versionCreator.nextVersion() else bumpLowestPart(lastCommit)
        return ReservedVersionModel(commitModel.commitId, VersionDetails(nextVersion), LocalDateTime.now())
    }

    internal fun findVersionCreator(commitModel: CommitModel, lastCommit: ReservedVersionModel?): VersionCreator? {
        recognizers.forEach {
            val versionCreator =it.produce(lastCommit, commitModel.message)
            if(versionCreator != null) {
                return versionCreator
            }
        }
        return null
    }

    internal fun bumpLowestPart(lastCommit: ReservedVersionModel?): String {
        if(lastCommit == null) {
            return "0.0.1"
        }

        val list = lastCommit.version.versionParts.toMutableList()
        list[list.size - 1] = list[list.size - 1] + 1

        return list.joinToString(".")
    }
}
