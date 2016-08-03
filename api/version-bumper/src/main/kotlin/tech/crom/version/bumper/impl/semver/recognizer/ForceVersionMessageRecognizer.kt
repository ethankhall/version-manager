package tech.crom.version.bumper.impl.semver.recognizer

import tech.crom.version.bumper.impl.MessageRecognizer
import tech.crom.version.bumper.impl.VersionCreator
import tech.crom.version.bumper.model.ReservedVersionModel

class ForceVersionMessageRecognizer() : MessageRecognizer {

    companion object {
        val searchString = ".*?\\[\\s*(?:force|set) version\\s*(.*?)\\s*\\].*?".toRegex(
            setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))
    }

    override fun produce(currentVersion: ReservedVersionModel, message: String): ForceVersionCreator? {
        val result = searchString.find(message) ?: return null
        val versionString = result.groups[1]?.value ?: return null
        return ForceVersionCreator(versionString)
    }

    class ForceVersionCreator(internal val value: String) : VersionCreator {
        override fun nextVersion(): String {
            return value
        }

    }

}
