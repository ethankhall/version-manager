package io.ehdev.conrad.security.jwt;

import io.ehdev.conrad.security.database.model.TokenType;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserToken {

    private final String userId;
    private final String uniqueId;
    private final TokenType type;

    public UserToken(String userId, String uniqueId, TokenType type) {
        this.userId = userId;
        this.uniqueId = uniqueId;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("userId", userId)
            .append("uniqueId", uniqueId)
            .append("type", type)
            .toString();
    }
}
