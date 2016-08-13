package tech.crom.version.bumper.impl

import tech.crom.model.commit.VersionDetails

interface MessageRecognizer {
    fun produce(lastVersion: VersionDetails?, message: String): VersionCreator?
}
