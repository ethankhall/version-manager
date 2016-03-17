package io.ehdev.conrad.version.commit.details;

public class CommitDetailsFactory {

    public static CommitDetails createCommitDetails(String commitId, String message) {
        return new DefaultCommitDetails(commitId, message);
    }
}
