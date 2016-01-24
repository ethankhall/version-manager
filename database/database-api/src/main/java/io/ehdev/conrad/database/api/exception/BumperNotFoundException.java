package io.ehdev.conrad.database.api.exception;

public class BumperNotFoundException extends RuntimeException {
    public BumperNotFoundException(String bumperName) {
        super(String.format("Bumper `%s` not found", bumperName));
    }
}
