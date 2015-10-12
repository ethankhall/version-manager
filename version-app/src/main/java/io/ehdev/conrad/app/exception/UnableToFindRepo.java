package io.ehdev.conrad.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Unable to find repo by id")
public class UnableToFindRepo extends RuntimeException {
}
