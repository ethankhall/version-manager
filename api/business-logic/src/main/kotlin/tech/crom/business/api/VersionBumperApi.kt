package tech.crom.business.api

import tech.crom.model.bumper.CromVersionBumper
import tech.crom.version.bumper.VersionBumper

interface VersionBumperApi {
    fun findVersionBumper(cromVersionBumper: CromVersionBumper): VersionBumper?
}
