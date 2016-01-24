package io.ehdev.conrad.database.impl.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ehdev.conrad.database.impl.user.BaseUserModel;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_token")
public class UserTokenModel {

    private static final ZoneId ZONE_UTC = ZoneOffset.UTC;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    UUID id;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    ZonedDateTime createdAt;

    @CreatedDate
    @Column(name = "expires_at", updatable = false)
    ZonedDateTime expiresAt;

    @Column(name = "valid")
    boolean valid = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false)
    TokenType tokenType;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_uuid")
    BaseUserModel userModel;

    UserTokenModel() {
        //Empty
    }

    public UserTokenModel(BaseUserModel userModel, TokenType type, ZonedDateTime expiresAt) {
        this.tokenType = type;
        this.userModel = userModel;
        this.createdAt = ZonedDateTime.now(ZONE_UTC);
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

    public boolean isValid() {
        return valid;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public BaseUserModel getUserModel() {
        return userModel;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
