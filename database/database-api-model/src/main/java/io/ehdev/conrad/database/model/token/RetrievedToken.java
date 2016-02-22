package io.ehdev.conrad.database.model.token;

import java.time.ZonedDateTime;
import java.util.UUID;

public class RetrievedToken {

    private final UUID id;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime expiresAt;

    public RetrievedToken(UUID id, ZonedDateTime createdAt, ZonedDateTime expiresAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public UUID getId() {
        return id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }
}
