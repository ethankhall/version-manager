package io.ehdev.conrad.database.model.permission;

import java.util.UUID;

public class UserApiAuthentication implements ApiTokenAuthentication {

    private final UUID uuid;
    private final String userName;
    private final String name;
    private final String email;

    public UserApiAuthentication(UUID uuid, String userName, String name, String email) {
        this.email = email;
        this.name = name;
        this.userName = userName;
        this.uuid = uuid;
    }

    public UserApiAuthentication() {
        this(null, null, null, null);
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getNiceName() {
        return getUserName();
    }
}
