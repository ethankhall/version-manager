package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;


public class ProjectExistsException extends BaseHttpException {

    public ProjectExistsException(ResourceDetails container) {
        super(HttpStatus.CONFLICT, "PR-002", "Project (" + container.getProjectId().getName() + ") exists, but it should not.");
    }
}
