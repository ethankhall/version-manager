package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;

import java.util.Comparator;

class ReverseApiCommitComparator implements Comparator<ApiCommitModel> {

    @Override
    public int compare(ApiCommitModel o1, ApiCommitModel o2) {
        if (o1 == o2) {
            return 0;
        }

        String[] split1 = o1.getVersion().split("\\.");
        String[] split2 = o2.getVersion().split("\\.");
        int sharedParts = Math.min(split1.length, split2.length);
        for (int i = 0; i < sharedParts; i++) {
            if (split1[i].compareTo(split2[i]) != 0) {
                return -1 * split1[i].compareTo(split2[i]);
            }
        }

        if (split1.length > split2.length) {
            return -1;
        } else if (split1.length < split2.length) {
            return 1;
        } else {
            return 0;
        }
    }
}
