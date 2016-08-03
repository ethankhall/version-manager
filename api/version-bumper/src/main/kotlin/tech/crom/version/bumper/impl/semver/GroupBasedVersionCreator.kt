package tech.crom.version.bumper.impl.semver

import tech.crom.version.bumper.impl.MessageRecognizer
import tech.crom.version.bumper.model.ReservedVersionModel

class GroupBasedVersionCreator(val currentVersion: ReservedVersionModel, val groupNumber: Int) : MessageRecognizer.VersionCreator {
    override fun nextVersion(): String {
        val versionSplit = currentVersion.version.split(".").toMutableList()
        if (versionSplit.size <= groupNumber) {
            for (i in versionSplit.size..groupNumber) {
                versionSplit.add("0")
            }
        }

        versionSplit[groupNumber] = (versionSplit[groupNumber].toInt() + 1).toString()
        for(i in (groupNumber + 1)..(versionSplit.size - 1)) {
            versionSplit[i] = "0"
        }
        return versionSplit.joinToString(".")
    }
}
