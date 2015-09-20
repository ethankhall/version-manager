package io.ehdev.version.commit;

public interface RepositoryCommit {
    CommitVersion getVersion();

    RepositoryCommit getNextCommit();

    RepositoryCommit getBugfixCommit();

    String getCommitId();
}
