package tech.crom.version.bumper.impl.semver

import tech.crom.version.bumper.impl.MessageRecognizer
import tech.crom.version.bumper.model.ReservedVersionModel

class ForceVersionMessageRecognizer() : MessageRecognizer {

    companion object {
        val searchString = ".*?\\[\\s*(?:force|set) version\\s*(.*?)\\s*\\].*?".toRegex(
            setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))
    }

    override fun produce(currentVersion: ReservedVersionModel, message: String): VersionCreator? {
        val result = searchString.find(message) ?: return null
        val versionString = result.groups[1]?.value ?: return null
        return VersionCreator(versionString)
    }

    class VersionCreator(internal val value: String) : MessageRecognizer.VersionCreator {
        override fun nextVersion(): String {
            return value
        }

    }

}
