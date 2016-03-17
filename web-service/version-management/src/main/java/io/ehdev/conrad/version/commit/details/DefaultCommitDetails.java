package io.ehdev.conrad.version.commit.details;

import io.ehdev.conrad.version.matcher.CommitMessageMatcher;

public class DefaultCommitDetails implements CommitDetails {

    final String message;
    final String commitId;

    public DefaultCommitDetails(String commitId, String message) {
        this.commitId = commitId;
        this.message = message;
    }

    @Override
    public boolean messageContains(CommitMessageMatcher search) {
        return search.matches(message);
    }

    @Override
    public String getCommitId() {
        return commitId;
    }
}
