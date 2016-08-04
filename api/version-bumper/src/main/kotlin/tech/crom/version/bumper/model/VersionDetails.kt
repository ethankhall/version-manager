package tech.crom.version.bumper.model

data class VersionDetails(val version: String) {
    val versionParts: List<Int>
    val postFix: String?

    init {
        var splitPart: String = version
        val dashIndex = version.indexOf("-")
        var tempPostFix: String?
        if (dashIndex >= 0) {
            tempPostFix = version.substring(dashIndex + 1)
            splitPart = version.substring(0, dashIndex)
        } else {
            tempPostFix = null
        }

        val split = splitPart.split(".")
        val versionPartsBuilder = mutableListOf<Int>()
        split.forEachIndexed { index, it ->
            val parsed = findNumber(it)
            if (parsed != null) {
                versionPartsBuilder.add(parsed)
            } else {
                val foundPostFix = split.subList(index, split.size).joinToString(".")
                if (tempPostFix != null) {
                    tempPostFix = foundPostFix + "-" + tempPostFix
                } else {
                    tempPostFix = foundPostFix
                }
            }
        }
        versionParts = versionPartsBuilder.toList()
        postFix = tempPostFix
        if (versionParts.isEmpty()) {
            throw RuntimeException("$version is not a valid version")
        }
    }

    fun findNumber(string: String): Int? {
        try {
            return string.toInt()
        } catch (nfe: NumberFormatException) {
            return null
        }
    }
}
