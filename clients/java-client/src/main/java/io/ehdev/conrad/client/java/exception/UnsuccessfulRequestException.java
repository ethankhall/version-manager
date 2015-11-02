package io.ehdev.conrad.client.java.exception;

public class UnsuccessfulRequestException extends Exception {
    public UnsuccessfulRequestException(int statusCode, String content) {
        super(String.format("Server response was not 200 (was %d): message %s", statusCode, content));
    }
}
