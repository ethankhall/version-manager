package io.ehdev.conrad.database.model.comparator

import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import java.util.*
import kotlin.collections.dropLastWhile
import kotlin.collections.toTypedArray
import kotlin.text.isEmpty
import kotlin.text.split
import kotlin.text.toRegex

class ReverseApiCommitComparator : Comparator<ApiCommitModel> {

    override fun compare(o1: ApiCommitModel, o2: ApiCommitModel): Int {
        if (o1 == o2) {
            return 0
        }

        val split1 = o1.version!!.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        val split2 = o2.version!!.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        val sharedParts = Math.min(split1.size, split2.size)
        for (i in 0..sharedParts - 1) {
            if (split1[i].compareTo(split2[i]) != 0) {
                return -1 * split1[i].compareTo(split2[i])
            }
        }

        if (split1.size > split2.size) {
            return -1
        } else if (split1.size < split2.size) {
            return 1
        } else {
            return 0
        }
    }
}
