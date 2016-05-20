package io.ehdev.conrad.database.api.exception;

import io.ehdev.conrad.database.model.repo.details.ResourceDetails;

public class RepoAlreadyExistsException extends RuntimeException {
    private final ResourceDetails resourceDetails;

    public RepoAlreadyExistsException(ResourceDetails resourceDetails) {
        this.resourceDetails = resourceDetails;
    }
}
