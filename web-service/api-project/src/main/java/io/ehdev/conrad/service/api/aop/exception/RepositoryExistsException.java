package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;

public class RepositoryExistsException extends BaseHttpException {

    public RepositoryExistsException(String projectName, String repoName) {
        super(HttpStatus.CONFLICT, "RP-002", "Repo (" + projectName + "/" + repoName + ") exists, but it is required not to.");
    }
}
