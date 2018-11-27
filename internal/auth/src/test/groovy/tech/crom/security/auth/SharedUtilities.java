package tech.crom.security.auth;

import tech.crom.database.api.TokenManager;
import tech.crom.model.token.TokenType;
import tech.crom.model.user.CromUser;

import java.time.ZonedDateTime;
import java.util.UUID;

public class SharedUtilities {
    private SharedUtilities() {

    }

    public static TokenManager.TokenDetails createGeneratedToken(TokenType type) {
        ZonedDateTime now = ZonedDateTime.now();
        return new TokenManager.TokenDetails((long) (Math.random() * 1000),
            UUID.randomUUID().toString(),
            now,
            now.plusDays(2),
            true,
            type);
    }

    public static long randomNumber() {
        return (long) (Math.random() * 1000);
    }

    public static CromUser createUser() {
        return new CromUser(randomNumber(), "username", "displayName");
    }
}
