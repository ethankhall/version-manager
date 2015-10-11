package io.ehdev.conrad.backend.commit;

import io.ehdev.conrad.backend.commit.internal.DefaultCommitDetails;

public class CommitDetailsFactory {

    public static CommitDetails createCommitDetails(String commitId, String message) {
        return new DefaultCommitDetails(commitId, message);
    }
}
