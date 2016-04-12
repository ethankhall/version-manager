package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonProperty
import io.ehdev.conrad.model.DefaultResourceSupport
import java.util.*

open class BaseVersionResponse(@JsonProperty("commitId") val commitId: String,
                               @JsonProperty("version") val version: String?) : DefaultResourceSupport() {

    @JsonProperty("versionParts")
    val versionParts: MutableList<String>

    @JsonProperty("postfix")
    val postfix: String?

    init {
        var subVersion = version
        if (null == version || !version.contains("-")) {
            if (null == version) {
                subVersion = ""
            }
            postfix = null
        } else {
            val indexOfDash = version.indexOf('-')
            subVersion = version.substring(0, indexOfDash)
            postfix = version.substring(indexOfDash + 1)
        }

        versionParts = ArrayList<String>()
        val splitVersion = subVersion!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in 0..splitVersion.size - 1) {
            versionParts.add(splitVersion[i])
        }
    }
}
