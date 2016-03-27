package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;


public class ProjectExistsException extends BaseHttpException {

    public ProjectExistsException(ApiParameterContainer container) {
        super(HttpStatus.CONFLICT, "PR-002", "Project (" + container.getProjectName() + ") exists, but it should not.");
    }
}
