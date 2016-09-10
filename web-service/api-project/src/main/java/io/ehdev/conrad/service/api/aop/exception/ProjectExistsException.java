package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import io.ehdev.conrad.service.api.exception.ErrorCode;
import org.springframework.http.HttpStatus;


public class ProjectExistsException extends BaseHttpException {

    public ProjectExistsException(String projectName) {
        super(HttpStatus.CONFLICT, ErrorCode.PROJECT_EXISTS_SHOULD_NOT, "Project (" + projectName + ") exists, but it should not.");
    }
}
