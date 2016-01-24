package io.ehdev.conrad.database.api.exception;

import io.ehdev.conrad.model.user.ConradToken;

public class UserTokenNotFoundException extends RuntimeException {
    public UserTokenNotFoundException(ConradToken token) {
        super(String.format("Token %s is not valid", token.getUuid().toString()));
    }
}
