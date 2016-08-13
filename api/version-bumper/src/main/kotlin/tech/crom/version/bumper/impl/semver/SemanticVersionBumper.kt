package tech.crom.version.bumper.impl.semver

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.VersionDetails
import tech.crom.version.bumper.VersionBumper
import tech.crom.version.bumper.impl.MessageRecognizer
import tech.crom.version.bumper.impl.VersionCreator
import tech.crom.version.bumper.impl.semver.recognizer.ForceVersionMessageRecognizer
import tech.crom.version.bumper.impl.semver.recognizer.SquareBracketMessageRecognizer
import tech.crom.version.bumper.impl.semver.recognizer.WildcardMessageRecognizer

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

    override fun calculateNextVersion(commitModel: CommitDetails.RequestedCommit, lastVersion: VersionDetails?): CommitDetails.RealizedCommit {
        val versionCreator = findVersionCreator(commitModel, lastVersion)
        val nextVersion = if(versionCreator != null) versionCreator.nextVersion() else bumpLowestPart(lastVersion)
        return CommitDetails.RealizedCommit(commitModel.commitId, VersionDetails(nextVersion))
    }

    internal fun findVersionCreator(commitModel: CommitDetails.RequestedCommit, lastVersion: VersionDetails?): VersionCreator? {
        recognizers.forEach {
            val versionCreator = it.produce(lastVersion, commitModel.message)
            if(versionCreator != null) {
                return versionCreator
            }
        }
        return null
    }

    internal fun bumpLowestPart(lastCommit: VersionDetails?): String {
        if(lastCommit == null) {
            return "0.0.1"
        }

        val list = lastCommit.versionParts.toMutableList()
        list[list.size - 1] = list[list.size - 1] + 1

        return list.joinToString(".")
    }
}
