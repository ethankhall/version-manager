package io.ehdev.version.service.model;

import io.ehdev.version.commit.internal.DefaultCommitDetails;

public class VersionCreation {
    String parentCommitId;
    String commitId;
    String repoId;
    String commitMessage;

    public String getParentCommitId() {
        return parentCommitId;
    }

    public void setParentCommitId(String parentCommitId) {
        this.parentCommitId = parentCommitId;
    }

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

    public DefaultCommitDetails toCommitDetails() {
        return new DefaultCommitDetails(commitMessage, commitId, parentCommitId, repoId);
    }
}
