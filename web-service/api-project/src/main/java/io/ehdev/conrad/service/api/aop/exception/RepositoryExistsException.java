package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;

public class RepositoryExistsException extends BaseHttpException {

    public RepositoryExistsException(ApiParameterContainer container) {
        super(HttpStatus.CONFLICT, "RP-001", "Repo (" + container.getMergedName() + ") exists, but it is required not to.");
    }
}
