package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;


public class ProjectExistsException extends BaseHttpException {

    public ProjectExistsException(String projectName) {
        super(HttpStatus.CONFLICT, "PR-002", "Project (" + projectName + ") exists, but it should not.");
    }
}
