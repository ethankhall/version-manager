package tech.crom.version.bumper.impl.semver

import tech.crom.version.bumper.impl.MessageRecognizer
import tech.crom.version.bumper.model.ReservedVersionModel

class WildcardMessageRecognizer: MessageRecognizer {

    val wildcardSearchPattern: Regex

    init {
        wildcardSearchPattern = ".*?\\[\\s*bump group (\\d+)\\s*\\].*?".toRegex()
    }

    override fun produce(currentVersion: ReservedVersionModel, message: String): GroupBasedVersionCreator? {
        val result = wildcardSearchPattern.find(message) ?: return null
        val group = result.groups[1]?.value?.toInt() ?: return null
        return GroupBasedVersionCreator(currentVersion, group)
    }

}
