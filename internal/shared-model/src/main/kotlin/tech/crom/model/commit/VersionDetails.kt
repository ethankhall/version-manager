package tech.crom.model.commit

data class VersionDetails(val versionString: String) {

    private val versionParse: Pair<List<Int>, String?> by lazy { findVersionParts() }
    val versionParts: List<Int> by lazy { versionParse.first }
    val postFix: String? by lazy { versionParse.second }

    init {
        if (!versionString.contains(Regex("[0-9]"))) {
            throw RuntimeException("Must contain at least one number")
        }
    }

    private fun findVersionParts(): Pair<List<Int>, String?> {
        var splitPart: String = versionString
        val dashIndex = versionString.indexOf("-")
        var tempPostFix: String?
        if (dashIndex >= 0) {
            tempPostFix = versionString.substring(dashIndex + 1)
            splitPart = versionString.substring(0, dashIndex)
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
        return Pair(versionPartsBuilder.toList(), tempPostFix)
    }

    private fun findNumber(string: String): Int? {
        try {
            return string.toInt()
        } catch (nfe: NumberFormatException) {
            return null
        }
    }
}
