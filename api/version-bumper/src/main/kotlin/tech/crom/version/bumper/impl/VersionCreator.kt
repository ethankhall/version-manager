package tech.crom.version.bumper.impl

interface VersionCreator {
    fun nextVersion(): String
}
