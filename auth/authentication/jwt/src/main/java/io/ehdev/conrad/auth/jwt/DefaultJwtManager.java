package io.ehdev.conrad.auth.jwt;

import io.ehdev.conrad.database.api.TokenManagementApi;
import io.ehdev.conrad.model.user.ConradGeneratedToken;
import io.ehdev.conrad.model.user.ConradTokenType;
import io.ehdev.conrad.model.user.ConradUser;
import io.ehdev.conrad.model.user.DefaultConradToken;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultJwtManager implements JwtManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultJwtManager.class);
    private static final String TOKEN_TYPE = "type";

    private final TokenManagementApi tokenManagementApi;
    private final byte[] key;

    @Autowired
    public DefaultJwtManager(Environment environment,
                             TokenManagementApi tokenManagementApi) {
        this.tokenManagementApi = tokenManagementApi;
        this.key = environment.getProperty("jwt.signing.key").getBytes();
    }

    @Override
    public String createUserToken(ConradUser user) {
        return createToken(user, ConradTokenType.USER);
    }

    @Override
    public String createToken(ConradUser user, ConradTokenType type) {
        ConradGeneratedToken token = tokenManagementApi.createToken(user, type);
        Claims claims = Jwts.claims()
            .setSubject(user.getUuid().toString())
            .setExpiration(Date.from(token.getExpiresAt().toInstant()))
            .setId(token.getUuid().toString())
            .setNotBefore(Date.from(token.getCreatedAt().toInstant()));

        return Jwts.builder()
            .setClaims(claims)
            .setHeaderParam(TOKEN_TYPE, type.getType())
            .signWith(SignatureAlgorithm.HS256, new SecretKeySpec(key, "HmacSHA256"))
            .compact();
    }

    @Override
    public Optional<ConradUser> findUser(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
            Claims parsed = claimsJws.getBody();

            return Optional.ofNullable(tokenManagementApi.findUser(new DefaultConradToken(UUID.fromString(parsed.getId()))));
        } catch (JwtException | IllegalArgumentException exception) {
            logger.info("Token {} was invalid: {}", token, exception.getMessage());
            return Optional.empty();
        }
    }
}
