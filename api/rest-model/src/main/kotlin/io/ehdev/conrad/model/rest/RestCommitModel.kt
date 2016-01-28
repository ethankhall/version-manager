package io.ehdev.conrad.model.rest


import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.collections.toArrayList
import kotlin.collections.toIntArray
import kotlin.text.*

class RestCommitModel(@JsonProperty("commitId") val commitId: String, @JsonProperty("version") val version: String) {
    @JsonProperty("versionParts")
    public val versionParts: IntArray = findVersionParts()

    @JsonProperty("postfix")
    public val postfix: String? = findPostFix()

    private fun findVersionParts(): IntArray {
        var subVersion = version
        if (version.contains("-")) {
            val indexOfDash = version.indexOf('-')
            subVersion = version.substring(0, indexOfDash)
        }

        val parts = emptyArray<Int>().toArrayList()
        val splitVersion = subVersion.split("\\.".toRegex())
        for (i in 0..splitVersion.size - 1) {
            parts.add(Integer.parseInt(splitVersion[i]))
        }
        return parts.toIntArray()
    }

    private fun findPostFix(): String? {
        if(version.contains('-')) {
            val indexOfDash = version.indexOf('-')
            return version.substring(indexOfDash + 1)
        } else {
            return null;
        }
    }

    override fun toString(): String {
        return version
    }
}
