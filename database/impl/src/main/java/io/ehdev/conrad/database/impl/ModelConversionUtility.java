package io.ehdev.conrad.database.impl;

import io.ehdev.conrad.database.impl.token.TokenType;
import io.ehdev.conrad.database.impl.token.UserTokenModel;
import io.ehdev.conrad.database.impl.user.BaseUserModel;
import io.ehdev.conrad.model.user.ConradGeneratedToken;
import io.ehdev.conrad.model.user.ConradTokenType;
import io.ehdev.conrad.model.user.ConradUser;

public class ModelConversionUtility {

    public static ConradUser toApiModel(BaseUserModel user) {
        return new ConradUser(user.getId(), user.getName(), user.getEmailAddress());
    }

    public static TokenType toDatabaseModel(ConradTokenType type) {
        switch (type) {
            case USER:
                return TokenType.USER;
            case API:
                return TokenType.API;
            default:
                throw new IllegalArgumentException("Unknown type " + type.getType());
        }
    }

    public static ConradTokenType toDatabaseModel(TokenType type) {
        switch (type) {
            case USER:
                return ConradTokenType.USER;
            case API:
                return ConradTokenType.API;
            default:
                throw new IllegalArgumentException("Unknown type " + type.getName());
        }
    }

    public static ConradGeneratedToken toApiModel(UserTokenModel token) {
        return new ConradGeneratedToken(token.getId(), toDatabaseModel(token.getTokenType()), token.getCreatedAt(), token.getExpiresAt());
    }

}
