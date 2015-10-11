package io.ehdev.version.service.version;

import java.util.List;

public class VersionCreateModel {
    List<String> commits;

    public VersionCreateModel(List<String> commits) {
        this.commits = commits;
    }

    public VersionCreateModel() {
    }

    public List<String> getCommits() {
        return commits;
    }

    public void setCommits(List<String> commits) {
        this.commits = commits;
    }
}
