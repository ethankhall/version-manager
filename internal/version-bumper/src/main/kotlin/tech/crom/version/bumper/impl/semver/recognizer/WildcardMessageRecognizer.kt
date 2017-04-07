package tech.crom.version.bumper.impl.semver.recognizer

import tech.crom.version.bumper.impl.MessageRecognizer
import tech.crom.model.commit.VersionDetails

class WildcardMessageRecognizer: MessageRecognizer {

    val wildcardSearchPattern: Regex

    init {
        wildcardSearchPattern = ".*?\\[\\s*bump group (\\d+)\\s*\\].*?".toRegex()
    }

    override fun produce(lastVersion: VersionDetails?, message: String): GroupBasedVersionCreator? {
        val result = wildcardSearchPattern.find(message) ?: return null
        val group = result.groups[1]?.value?.toInt() ?: return null
        return GroupBasedVersionCreator(lastVersion, group)
    }

}
