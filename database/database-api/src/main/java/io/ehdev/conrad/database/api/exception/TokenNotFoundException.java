package io.ehdev.conrad.database.api.exception;


import io.ehdev.conrad.database.model.user.ApiToken;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(ApiToken token) {
        super(String.format("Token %s is not valid", token.getUuid().toString()));
    }
}
