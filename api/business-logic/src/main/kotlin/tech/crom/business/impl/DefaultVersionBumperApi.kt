package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.VersionBumperApi
import tech.crom.database.api.RepoManager
import tech.crom.database.api.VersionBumperManager
import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.repository.CromRepo
import tech.crom.version.bumper.VersionBumper
import java.util.*

@Service
class DefaultVersionBumperApi @Autowired constructor(
    bumpers: Set<VersionBumper>,
    val versionBumperManager: VersionBumperManager,
    val repoManager: RepoManager
): VersionBumperApi {

    val classToBumper: MutableMap<String, VersionBumper> = HashMap()

    init {
        bumpers.forEach { classToBumper[it.javaClass.name] = it }
    }

    override fun findVersionBumper(cromRepo: CromRepo): VersionBumper {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findVersionBumper(cromVersionBumper: CromVersionBumper): VersionBumper {
        return classToBumper[cromVersionBumper.className] ?: throw VersionBumperNotFoundException(cromVersionBumper)
    }

    class VersionBumperNotFoundException(cvb: CromVersionBumper): RuntimeException("Unable to find bumper for ${cvb.bumperName}")
}
