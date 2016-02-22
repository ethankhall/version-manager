package io.ehdev.conrad.database.model.user;

import java.util.Objects;

public enum ApiTokenType {
    USER("USER"),
    REPOSITORY("REPOSITORY"),
    PROJECT("PROJECT");

    private final String type;

    ApiTokenType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ApiTokenType parse(String tokenType) {
        if (Objects.equals(USER.type, tokenType)) {
            return USER;
        }

        if (Objects.equals(REPOSITORY.type, tokenType)) {
            return REPOSITORY;
        }

        if (Objects.equals(PROJECT.type, tokenType)) {
            return PROJECT;
        }

        throw new RuntimeException("Unknown type " + tokenType);
    }
}
