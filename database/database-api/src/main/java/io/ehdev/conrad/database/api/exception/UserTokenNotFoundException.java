package io.ehdev.conrad.database.api.exception;


import io.ehdev.conrad.database.model.user.ApiToken;

public class UserTokenNotFoundException extends RuntimeException {
    public UserTokenNotFoundException(ApiToken token) {
        super(String.format("Token %s is not valid", token.getUuid().toString()));
    }
}
