package io.ehdev.conrad.database.api.exception;


import io.ehdev.conrad.database.model.user.ApiToken;

import java.util.UUID;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(ApiToken token) {
        this(token.getUuid());
    }

    public TokenNotFoundException(UUID token) {
        super(String.format("Token %s is not valid", token.toString()));
    }
}
