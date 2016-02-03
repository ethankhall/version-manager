package io.ehdev.conrad.authentication.jwt;

import io.ehdev.conrad.database.api.TokenManagementApi;
import io.ehdev.conrad.database.model.user.*;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
        this.key = environment.getRequiredProperty("jwt.signing.key").getBytes();
    }

    @Override
    public String createUserToken(ApiUser user) {
        return createToken(user, ApiTokenType.USER);
    }

    @Override
    public String createToken(ApiUser user, ApiTokenType type) {
        ApiGeneratedUserToken token = tokenManagementApi.createToken(user, type);
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
    public Optional<Pair<ApiUser, ApiToken>> parseToken(String token) {
        if(StringUtils.isBlank(token)) {
            return Optional.empty();
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
            Claims parsed = claimsJws.getBody();

            ApiProvidedToken conradToken = new ApiProvidedToken(UUID.fromString(parsed.getId()));
            ApiUser user = tokenManagementApi.findUser(conradToken);
            if(user == null) {
                return Optional.empty();
            }
            return Optional.of(new ImmutablePair<>(user, conradToken));
        } catch (JwtException | IllegalArgumentException exception) {
            logger.info("Token {} was invalid: {}", token, exception.getMessage());
            return Optional.empty();
        }
    }
}
