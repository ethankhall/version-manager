package io.ehdev.conrad.service.api.service.model.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.time.ZonedDateTime;
import java.util.UUID;

public class CreateTokenResponse extends ResourceSupport {

    @JsonProperty("uuid")
    private final UUID uuid;

    @JsonProperty("createdAt")
    private final ZonedDateTime createdAt;

    @JsonProperty("expiresAt")
    private final ZonedDateTime expiresAt;

    public CreateTokenResponse(UUID uuid, ZonedDateTime createdAt, ZonedDateTime expiresAt) {
        this.uuid = uuid;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }
}
