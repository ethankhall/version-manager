package io.ehdev.conrad.model.commit.internal;

import io.ehdev.conrad.model.commit.CommitDetails;
import io.ehdev.conrad.model.update.matcher.CommitMessageMatcher;

public class DefaultCommitDetails implements CommitDetails {

    private final String message;
    private final String commitSha;
    private final String parentCommit;
    private final String scmRepoId;

    public DefaultCommitDetails(String message, String commitSha, String parentCommit, String scmRepoId) {
        this.message = message;
        this.commitSha = commitSha;
        this.parentCommit = parentCommit;
        this.scmRepoId = scmRepoId;
    }

    @Override
    public boolean messageContains(CommitMessageMatcher search) {
        return search.matches(message);
    }

    @Override
    public String getParentCommitId() {
        return parentCommit;
    }

    public String getCommitId() {
        return commitSha;
    }

    @Override
    public String getScmRepositoryId() {
        return scmRepoId;
    }
}
