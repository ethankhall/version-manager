package io.ehdev.version.model.commit;

import io.ehdev.version.model.update.matcher.CommitMessageMatcher;


public interface CommitDetails {
    boolean messageContains(CommitMessageMatcher search);
    String getParentCommitId();
    String getCommitId();
    String getScmRepositoryId();
}
