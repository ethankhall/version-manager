package io.ehdev.conrad.model.commit;

import io.ehdev.conrad.model.update.matcher.CommitMessageMatcher;


public interface CommitDetails {
    boolean messageContains(CommitMessageMatcher search);
    String getParentCommitId();
    String getCommitId();
    String getScmRepositoryId();
}
