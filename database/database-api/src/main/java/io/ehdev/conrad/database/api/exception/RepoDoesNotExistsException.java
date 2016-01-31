package io.ehdev.conrad.database.api.exception;

import io.ehdev.conrad.database.model.project.ApiRepoModel;

public class RepoDoesNotExistsException extends RuntimeException {
    private final ApiRepoModel qualifiedRepo;

    public RepoDoesNotExistsException(ApiRepoModel qualifiedRepo) {
        this.qualifiedRepo = qualifiedRepo;
    }

    public ApiRepoModel getQualifiedRepo() {
        return qualifiedRepo;
    }
}
