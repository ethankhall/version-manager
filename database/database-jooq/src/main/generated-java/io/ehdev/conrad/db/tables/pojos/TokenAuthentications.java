/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.pojos;


import io.ehdev.conrad.db.enums.TokenType;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "token_authentications", schema = "public")
public class TokenAuthentications implements Serializable {

    private static final long serialVersionUID = -1001823685;

    private UUID      uuid;
    private Instant   createdAt;
    private Instant   expiresAt;
    private Boolean   valid;
    private TokenType tokenType;

    public TokenAuthentications() {}

    public TokenAuthentications(TokenAuthentications value) {
        this.uuid = value.uuid;
        this.createdAt = value.createdAt;
        this.expiresAt = value.expiresAt;
        this.valid = value.valid;
        this.tokenType = value.tokenType;
    }

    public TokenAuthentications(
        UUID      uuid,
        Instant   createdAt,
        Instant   expiresAt,
        Boolean   valid,
        TokenType tokenType
    ) {
        this.uuid = uuid;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.valid = valid;
        this.tokenType = tokenType;
    }

    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Column(name = "created_at", nullable = false)
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "expires_at")
    public Instant getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Column(name = "valid")
    public Boolean getValid() {
        return this.valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Column(name = "token_type", nullable = false)
    @NotNull
    public TokenType getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TokenAuthentications (");

        sb.append(uuid);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(expiresAt);
        sb.append(", ").append(valid);
        sb.append(", ").append(tokenType);

        sb.append(")");
        return sb.toString();
    }
}
