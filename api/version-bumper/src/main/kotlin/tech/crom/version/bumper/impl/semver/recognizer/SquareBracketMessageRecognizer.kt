package tech.crom.version.bumper.impl.semver.recognizer

import tech.crom.version.bumper.impl.MessageRecognizer
import tech.crom.version.bumper.impl.VersionCreator
import tech.crom.version.bumper.model.ReservedVersionModel

class SquareBracketMessageRecognizer(keyword: String, groupNumber: Int) : MessageRecognizer {

    val searchPattern: Regex
    val versionCreator: (ReservedVersionModel?) -> VersionCreator

    init {
        searchPattern = ".*?\\[\\s*bump $keyword( version)?\\s*\\].*?".toRegex(setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))
        versionCreator = { currentVersion: ReservedVersionModel? -> GroupBasedVersionCreator(currentVersion, groupNumber) }
    }

    override fun produce(currentVersion: ReservedVersionModel?, message: String): VersionCreator? {
        searchPattern.find(message) ?: return null
        return versionCreator(currentVersion)
    }
}
