package io.ehdev.conrad.app.service.version.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.annotation.Nonnull;
import java.util.List;

public class RepoVersionModel {

    private final VersionCommitModel latest;
    private final List<VersionCommitModel> commits;

    public RepoVersionModel(@Nonnull List<VersionCommitModel> commits) {
        this.commits = commits;
        VersionCommitModel latest = null;
        if(!commits.isEmpty()) {
            latest = commits.get(commits.size() - 1);
        }
        this.latest = latest;
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    public VersionCommitModel getLatest() {
        return latest;
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    public List<VersionCommitModel> getCommits() {
        return commits;
    }
}
