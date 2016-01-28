package io.ehdev.conrad.service.api.exception;

public class RepositoryNotFoundException extends RuntimeException {
    public RepositoryNotFoundException(String projectName, String repoName) {
        super(String.format("Unable to find %s/%s", projectName, repoName));
    }
}
