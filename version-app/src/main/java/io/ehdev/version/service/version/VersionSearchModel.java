package io.ehdev.version.service.version;

import java.util.List;

public class VersionSearchModel {
    List<String> commits;

    public VersionSearchModel(List<String> commits) {
        this.commits = commits;
    }

    public VersionSearchModel() {
    }

    public List<String> getCommits() {
        return commits;
    }

    public void setCommits(List<String> commits) {
        this.commits = commits;
    }
}
