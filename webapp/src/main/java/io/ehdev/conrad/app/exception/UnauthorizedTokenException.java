package io.ehdev.conrad.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="Token is invalid. Try again.")
public class UnauthorizedTokenException extends RuntimeException {
}
