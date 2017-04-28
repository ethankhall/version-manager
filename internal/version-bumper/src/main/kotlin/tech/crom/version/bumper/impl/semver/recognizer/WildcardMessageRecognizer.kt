package tech.crom.version.bumper.impl.semver.recognizer

import tech.crom.model.commit.VersionDetails
import tech.crom.version.bumper.impl.MessageRecognizer

class WildcardMessageRecognizer : MessageRecognizer {

    val wildcardSearchPattern: Regex = ".*?\\[\\s*bump group (\\d+)\\s*\\].*?".toRegex()

    override fun produce(lastVersion: VersionDetails?, message: String): GroupBasedVersionCreator? {
        val result = wildcardSearchPattern.find(message) ?: return null
        val group = result.groups[1]?.value?.toInt() ?: return null
        return GroupBasedVersionCreator(lastVersion, group)
    }

}
