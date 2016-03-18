package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;


public class ProjectMissingException extends BaseHttpException {

    public ProjectMissingException(ApiParameterContainer container) {
        super(HttpStatus.NOT_FOUND, "PR-001", "Project (" + container.getProjectName() + ") doesn't exist, but it is required.");
    }
}
