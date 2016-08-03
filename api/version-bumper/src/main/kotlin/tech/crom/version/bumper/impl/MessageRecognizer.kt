package tech.crom.version.bumper.impl

import tech.crom.version.bumper.model.ReservedVersionModel

interface MessageRecognizer {
    fun produce(currentVersion: ReservedVersionModel, message: String): VersionCreator?

}
