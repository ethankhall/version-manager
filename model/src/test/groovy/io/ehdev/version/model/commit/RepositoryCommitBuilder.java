package io.ehdev.version.model.commit;

import io.ehdev.version.model.commit.model.RepositoryCommitModel;
import org.apache.commons.lang3.StringUtils;

public class RepositoryCommitBuilder {
    final String repoName;
    final String commitId;
    CommitVersion version;
    RepositoryCommitModel nextCommit;
    RepositoryCommitModel bugfixCommit;

    public RepositoryCommitBuilder(String commitId, String repoName) {
        this.commitId = commitId;
        this.repoName = repoName;
    }

    public RepositoryCommitBuilder withVersion(CommitVersion commitVersion) {
        this.version = commitVersion;
        return this;
    }

    public RepositoryCommitBuilder withNextCommit(RepositoryCommitModel repositoryCommit) {
        this.nextCommit = repositoryCommit;
        return this;
    }

    public RepositoryCommitBuilder withBugfixCommit(RepositoryCommitModel repositoryCommit) {
        this.bugfixCommit = repositoryCommit;
        return this;
    }

    public RepositoryCommitModel build() {
        if (StringUtils.isBlank(repoName) || StringUtils.isBlank(commitId) || null == version) {
            throw new RuntimeException("Unable to build repository version without name, commit, and version");
        }
        return new RepositoryCommitModel(commitId, version, nextCommit, bugfixCommit);
    }
}
