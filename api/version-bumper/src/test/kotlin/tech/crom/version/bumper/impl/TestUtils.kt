package tech.crom.version.bumper.impl

import tech.crom.model.commit.VersionDetails

fun createVersionDetails(version: String = "1.2.3"): VersionDetails {
    return VersionDetails(version)
}
