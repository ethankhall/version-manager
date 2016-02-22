package io.ehdev.conrad.service.api.service.model.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.UUID;

public class GetTokenListEntry {
    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("createdAt")
    private final ZonedDateTime createdAt;

    @JsonProperty("expiresAt")
    private final ZonedDateTime expiresAt;

    public GetTokenListEntry(UUID id, ZonedDateTime createdAt, ZonedDateTime expiresAt) {
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
