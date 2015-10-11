package io.ehdev.version.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE, reason="Repo name must be at least 5 characters. It's best to be the repo name.")
public class RepoNameMustBeLongerThanFive extends RuntimeException {
}
