package io.ehdev.conrad.database.api.exception;

import io.ehdev.conrad.model.user.ConradToken;

public class UserTokenNotFound extends RuntimeException {
    public UserTokenNotFound(ConradToken token) {
        super(String.format("Token %s is not valid", token.getUuid().toString()));
    }
}
