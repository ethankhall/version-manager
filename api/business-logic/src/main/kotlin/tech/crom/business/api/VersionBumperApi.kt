package tech.crom.business.api

import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.repository.CromRepo

interface VersionBumperApi {
    fun findVersionBumper(name: String): CromVersionBumper
    fun findVersionBumper(cromRepo: CromRepo): CromVersionBumper
    fun findAll(): List<CromVersionBumper>
}
