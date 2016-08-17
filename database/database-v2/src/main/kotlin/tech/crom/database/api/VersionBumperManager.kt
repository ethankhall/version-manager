package tech.crom.database.api

import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.repository.CromRepo

interface VersionBumperManager {
    fun findBumper(name: String): CromVersionBumper?
    fun getBumper(cromRepo: CromRepo): CromVersionBumper
}
