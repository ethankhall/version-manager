package io.ehdev.conrad.security.jwt;

import io.ehdev.conrad.api.user.database.BaseUserModel;
import io.ehdev.conrad.security.database.model.TokenType;

import java.time.LocalDateTime;

public interface JwtManager {

    String createUserToken(BaseUserModel user, LocalDateTime expiration);

    String createToken(BaseUserModel user, TokenType type, LocalDateTime expiration);

    UserToken parseToken(String cookieValue);
}
