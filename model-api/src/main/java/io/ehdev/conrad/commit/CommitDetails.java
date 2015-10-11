package io.ehdev.conrad.commit;

import io.ehdev.conrad.commit.matcher.CommitMessageMatcher;

public interface CommitDetails {

    boolean messageContains(CommitMessageMatcher search);

    String getCommitId();
}
