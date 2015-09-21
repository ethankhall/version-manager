package io.ehdev.version.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Unable to find version bumper")
public class UnknownBumperException extends RuntimeException {
}
