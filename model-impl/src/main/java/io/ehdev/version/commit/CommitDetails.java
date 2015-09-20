package io.ehdev.version.commit;

import io.ehdev.version.update.matcher.CommitMessageMatcher;


public interface CommitDetails {
    boolean messageContains(CommitMessageMatcher search);
    String getParentCommitId();
    String getCommitId();
    String getScmRepository();
}
