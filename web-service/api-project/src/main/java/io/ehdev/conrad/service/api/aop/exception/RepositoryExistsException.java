package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;

public class RepositoryExistsException extends BaseHttpException {

    public RepositoryExistsException(ResourceDetails container) {
        super(HttpStatus.CONFLICT, "RP-002", "Repo (" + container.getRepoId().getName() + ") exists, but it is required not to.");
    }
}
