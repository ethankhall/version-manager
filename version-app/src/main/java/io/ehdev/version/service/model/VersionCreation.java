package io.ehdev.version.service.model;

import io.ehdev.conrad.model.commit.internal.DefaultCommitDetails;

public class VersionCreation extends VersionSearch {

    String commitId;
    String commitMessage;

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public DefaultCommitDetails toCommitDetails(String parentCommitId) {
        return new DefaultCommitDetails(commitMessage, commitId, parentCommitId, repoId);
    }
}
