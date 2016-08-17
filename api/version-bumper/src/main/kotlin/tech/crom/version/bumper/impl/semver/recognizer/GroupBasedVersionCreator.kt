package tech.crom.version.bumper.impl.semver.recognizer

import tech.crom.version.bumper.impl.VersionCreator
import tech.crom.model.commit.VersionDetails

class GroupBasedVersionCreator(val lastVersion: VersionDetails?, val groupNumber: Int) : VersionCreator {
    override fun nextVersion(): String {
        lastVersion ?: return "0.0.1"
        val versionSplit = lastVersion.versionParts.toMutableList()
        if (versionSplit.size <= groupNumber) {
            for (i in versionSplit.size..groupNumber) {
                versionSplit.add(0)
            }
        }

        versionSplit[groupNumber] = (versionSplit[groupNumber].toInt() + 1)
        for(i in (groupNumber + 1)..(versionSplit.size - 1)) {
            versionSplit[i] = 0
        }
        return versionSplit.joinToString(".")
    }
}
