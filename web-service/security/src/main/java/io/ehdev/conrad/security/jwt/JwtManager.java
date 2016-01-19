package io.ehdev.conrad.security.jwt;

import io.ehdev.conrad.security.database.model.TokenType;
import io.ehdev.conrad.security.database.model.SecurityUserModel;

import java.time.LocalDateTime;

public interface JwtManager {

    String createUserToken(SecurityUserModel user, LocalDateTime expiration);

    String createToken(SecurityUserModel user, TokenType type, LocalDateTime expiration);

    UserToken parseToken(String cookieValue);
}
