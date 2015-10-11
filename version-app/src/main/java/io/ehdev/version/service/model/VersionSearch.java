package io.ehdev.version.service.model;

import java.util.List;

public class VersionSearch {

    String repoId;
    List<String> commitIds;

    public VersionSearch() {
    }

    public VersionSearch(String repoId, List<String> commitIds) {
        this.repoId = repoId;
        this.commitIds = commitIds;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public List<String> getCommitIds() {
        return commitIds;
    }

    public void setCommitIds(List<String> commitIds) {
        this.commitIds = commitIds;
    }
}
