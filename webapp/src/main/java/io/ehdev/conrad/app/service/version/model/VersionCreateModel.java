package io.ehdev.conrad.app.service.version.model;

import io.ehdev.conrad.backend.commit.CommitDetails;
import io.ehdev.conrad.backend.commit.internal.DefaultCommitDetails;

import javax.validation.constraints.NotNull;
import java.util.List;

public class VersionCreateModel extends VersionSearchModel {
    @NotNull
    String message;

    @NotNull
    String commitId;

    public VersionCreateModel() {
    }

    public VersionCreateModel(List<String> commits, String message, String commitId) {
        super(commits);
        this.message = message;
        this.commitId = commitId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public CommitDetails toCommitDetails() {
        return new DefaultCommitDetails(commitId, message);
    }
}
