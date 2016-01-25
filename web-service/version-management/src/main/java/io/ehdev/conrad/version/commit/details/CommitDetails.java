package io.ehdev.conrad.version.commit.details;

import io.ehdev.conrad.version.matcher.CommitMessageMatcher;

public interface CommitDetails {

    boolean messageContains(CommitMessageMatcher search);

    String getCommitId();
}
