package io.ehdev.conrad.version.commit.details;

import io.ehdev.conrad.version.commit.internal.DefaultCommitDetails;

public class CommitDetailsFactory {

    public static CommitDetails createCommitDetails(String commitId, String message) {
        return new DefaultCommitDetails(commitId, message);
    }
}
