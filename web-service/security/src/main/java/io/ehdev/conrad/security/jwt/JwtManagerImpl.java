package io.ehdev.conrad.security.jwt;

import io.ehdev.conrad.security.database.model.TokenType;
import io.ehdev.conrad.security.database.model.SecurityUserModel;
import io.ehdev.conrad.security.database.model.SecurityUserTokenModel;
import io.ehdev.conrad.security.database.repositories.SecurityUserTokenModelRepository;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtManagerImpl implements JwtManager {

    private static final Logger logger = LoggerFactory.getLogger(JwtManagerImpl.class);
    private static final String TOKEN_TYPE = "type";

    private final SecurityUserTokenModelRepository tokenRepository;
    private final byte[] key;

    @Autowired
    public JwtManagerImpl(Environment environment, SecurityUserTokenModelRepository tokenRepository) {
        this.key = environment.getProperty("jwt.signing.key").getBytes();
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String createUserToken(SecurityUserModel user, LocalDateTime expiration) {
        return createToken(user, TokenType.USER, expiration);
    }

    @Override
    public String createToken(SecurityUserModel user, TokenType type, LocalDateTime expiration) {
        SecurityUserTokenModel userTokenModel = new SecurityUserTokenModel(user, type, expiration);
        userTokenModel = tokenRepository.save(userTokenModel);

        Claims claims = Jwts.claims()
            .setSubject(user.getId().toString())
            .setExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)))
            .setId(userTokenModel.getId().toString())
            .setNotBefore(Date.from(userTokenModel.getCreatedAt().toInstant(ZoneOffset.UTC)));

        return Jwts.builder()
            .setClaims(claims)
            .setHeaderParam(TOKEN_TYPE, type.getName())
            .signWith(SignatureAlgorithm.HS256, new SecretKeySpec(key, "HmacSHA256"))
            .compact();
    }

    @Override
    public UserToken parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
            Claims parsed = claimsJws.getBody();
            JwsHeader header = claimsJws.getHeader();

            TokenType tokenType = TokenType.parse((String) header.get(TOKEN_TYPE));
            SecurityUserTokenModel tokenModel = tokenRepository.findOne(UUID.fromString(claimsJws.getBody().getId()));

            if(isTokenInvalid(tokenType, tokenModel)) {
                return null;
            }
            return new UserToken(parsed.getSubject(), parsed.getId(), tokenType);
        } catch (JwtException | IllegalArgumentException exception) {
            logger.info("Token {} was invalid: {}", token, exception.getMessage());
            return null;
        }
    }

    private boolean isTokenInvalid(TokenType tokenType, SecurityUserTokenModel tokenModel) {
        return tokenModel == null || !tokenModel.isValid() || tokenModel.getTokenType() != tokenType;
    }
}
