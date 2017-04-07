package tech.crom.security.authorization.impl;

public class AuthUtils {

    private AuthUtils() {
    }

    public static Long randomLongGenerator() {
        return (long) (Math.random() * Long.MAX_VALUE);
    }
}
