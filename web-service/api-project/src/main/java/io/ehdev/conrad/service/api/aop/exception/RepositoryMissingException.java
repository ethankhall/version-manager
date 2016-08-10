package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;

public class RepositoryMissingException extends BaseHttpException {

    public RepositoryMissingException(String projectName, String repoName) {
        super(HttpStatus.NOT_FOUND, "RP-001", "Repo (" + projectName + "/" + repoName + ") doesn't exist, but it is required.");
    }
}
