package io.ehdev.conrad.database.api.exception;

public class CommitNotFoundException extends RuntimeException {
    public CommitNotFoundException(String commitId) {
        super(String.format("Commit `%s` not found", commitId));
    }
}
