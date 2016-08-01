package tech.crom.database.api

import tech.crom.model.bumper.CromVersionBumper

interface VersionBumperManager {
    fun findBumper(name: String): CromVersionBumper?
}
