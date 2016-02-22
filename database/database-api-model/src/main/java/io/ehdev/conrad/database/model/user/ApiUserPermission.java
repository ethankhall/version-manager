package io.ehdev.conrad.database.model.user;

public enum ApiUserPermission {
    NONE(0),
    READ(1 << (0)),
    WRITE(1 << (1)),
    ADMIN(1 << (2));

    int securityId;

    ApiUserPermission(int id) {
        this.securityId = id;
    }

    public int getSecurityId() {
        return securityId;
    }

    public static ApiUserPermission findById(int id) {
        for (ApiUserPermission value : values()) {
            if (value.securityId == id) {
                return value;
            }
        }
        return null;
    }
}
