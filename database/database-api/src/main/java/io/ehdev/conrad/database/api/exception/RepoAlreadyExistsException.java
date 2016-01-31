package io.ehdev.conrad.database.api.exception;

import io.ehdev.conrad.database.model.project.ApiRepoModel;

public class RepoAlreadyExistsException extends RuntimeException {
    private final ApiRepoModel qualifiedRepo;

    public RepoAlreadyExistsException(ApiRepoModel qualifiedRepo) {
        this.qualifiedRepo = qualifiedRepo;
    }

    public ApiRepoModel getQualifiedRepo() {
        return qualifiedRepo;
    }
}
