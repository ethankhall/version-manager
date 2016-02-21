package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;

public class RepositoryMissingException extends BaseHttpException {

    public RepositoryMissingException(ApiParameterContainer container) {
        super(HttpStatus.NOT_FOUND, "RP-002", "Repo (" + container.getMergedName() + ") doesn't exist, but it is required.");
    }
}
