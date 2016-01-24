package io.ehdev.conrad.database.impl.token;

import org.apache.commons.lang3.StringUtils;

public enum TokenType {
    USER("user"),
    API("api");

    private final String name;

    TokenType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TokenType parse(String token) {
        for (TokenType tokenType : TokenType.values()) {
            if (StringUtils.equalsIgnoreCase(tokenType.getName(), token)) {
                return tokenType;
            }
        }
        throw new IllegalArgumentException(token + " is not valid");
    }
}
