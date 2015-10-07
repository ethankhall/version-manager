package io.ehdev.version.model.commit;

public interface RepositoryCommit {
    CommitVersion getVersion();

    RepositoryCommit getNextCommit();

    RepositoryCommit getBugfixCommit();

    String getCommitId();
}
