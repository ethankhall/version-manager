package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;

public class RepositoryMissingException extends BaseHttpException {

    public RepositoryMissingException(ResourceDetails container) {
        super(HttpStatus.NOT_FOUND, "RP-001", "Repo (" + container.getRepoId().getName() + ") doesn't exist, but it is required.");
    }
}
