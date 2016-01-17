package io.ehdev.conrad.security.jwt;

import io.ehdev.conrad.security.database.model.TokenType;
import io.ehdev.conrad.security.database.model.UserModel;

import java.time.LocalDateTime;

public interface JwtManager {

    String createUserToken(UserModel user, LocalDateTime expiration);

    String createToken(UserModel user, TokenType type, LocalDateTime expiration);

    UserToken parseToken(String cookieValue);
}
