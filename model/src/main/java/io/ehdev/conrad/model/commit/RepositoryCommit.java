package io.ehdev.conrad.model.commit;

import io.ehdev.version.model.CommitVersion;

public interface RepositoryCommit extends Comparable<RepositoryCommit> {
    CommitVersion getVersion();

    RepositoryCommit getNextCommit();

    RepositoryCommit getBugfixCommit();

    String getCommitId();
}
