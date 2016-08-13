package tech.crom.service.api

import tech.crom.model.commit.CromCommitDetails
import java.util.*

class ReverseApiCommitComparator : Comparator<CromCommitDetails> {

    override fun compare(o1: CromCommitDetails, o2: CromCommitDetails): Int {
        if (o1 == o2) {
            return 0
        }

        val split1 = o1.version.split("\\.".toRegex()).toTypedArray()
        val split2 = o2.version.split("\\.".toRegex()).toTypedArray()
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
