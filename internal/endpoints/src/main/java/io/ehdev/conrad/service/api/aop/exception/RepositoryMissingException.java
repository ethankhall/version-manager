package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import io.ehdev.conrad.service.api.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class RepositoryMissingException extends BaseHttpException {

    public RepositoryMissingException(String projectName, String repoName) {
        super(HttpStatus.NOT_FOUND, ErrorCode.REPO_DOES_NOT_EXIST, "Repo (" + projectName + "/" + repoName + ") doesn't exist, but it is required.");
    }
}
