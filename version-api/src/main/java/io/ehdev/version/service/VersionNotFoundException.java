package io.ehdev.version.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such parent commit")
public class VersionNotFoundException extends RuntimeException {
}
