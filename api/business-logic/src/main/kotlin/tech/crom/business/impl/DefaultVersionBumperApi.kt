package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.VersionBumperApi
import tech.crom.model.bumper.CromVersionBumper
import tech.crom.version.bumper.VersionBumper
import java.util.*

@Service
class DefaultVersionBumperApi @Autowired constructor(
    bumpers: Set<VersionBumper>
): VersionBumperApi {

    val classToBumper: MutableMap<String, VersionBumper> = HashMap()

    init {
        bumpers.forEach { classToBumper[it.javaClass.name] = it }
    }

    override fun findVersionBumper(cromVersionBumper: CromVersionBumper): VersionBumper {
        return classToBumper[cromVersionBumper.className] ?: throw VersionBumperNotFoundException(cromVersionBumper)
    }

    class VersionBumperNotFoundException(cvb: CromVersionBumper): RuntimeException("Unable to find bumper for ${cvb.bumperName}")
}
