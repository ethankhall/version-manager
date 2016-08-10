package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;


public class ProjectMissingException extends BaseHttpException {

    public ProjectMissingException(String projectName) {
        super(HttpStatus.NOT_FOUND, "PR-001", "Project (" + projectName + ") doesn't exist, but it is required.");
    }
}
