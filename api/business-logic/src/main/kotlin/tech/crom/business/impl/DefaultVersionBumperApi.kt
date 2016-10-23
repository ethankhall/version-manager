package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.VersionBumperApi
import tech.crom.database.api.VersionBumperManager
import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.repository.CromRepo

@Service
class DefaultVersionBumperApi @Autowired constructor(
    val versionBumperManager: VersionBumperManager
): VersionBumperApi {

    override fun findVersionBumper(cromRepo: CromRepo): CromVersionBumper {
        return versionBumperManager.getBumper(cromRepo)
    }

    override fun findVersionBumper(name: String): CromVersionBumper {
        return versionBumperManager.findBumper(name) ?: throw VersionBumperNotFoundException(name)
    }

    override fun findAll(): List<CromVersionBumper> {
        return versionBumperManager.findAll()
    }

    class VersionBumperNotFoundException(name: String): RuntimeException("Unable to find bumper for $name")
}
