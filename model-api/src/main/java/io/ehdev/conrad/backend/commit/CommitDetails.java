package io.ehdev.conrad.backend.commit;

import io.ehdev.conrad.backend.commit.matcher.CommitMessageMatcher;

public interface CommitDetails {

    boolean messageContains(CommitMessageMatcher search);

    String getCommitId();
}
