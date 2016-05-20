package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;


public class ProjectMissingException extends BaseHttpException {

    public ProjectMissingException(ResourceDetails container) {
        super(HttpStatus.NOT_FOUND, "PR-001", "Project (" + container.getProjectId().getName() + ") doesn't exist, but it is required.");
    }
}
