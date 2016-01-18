package io.ehdev.conrad.security.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Table(name = "security_user_token")
public class UserTokenModel {

    private static final ZoneId ZONE_UTC = ZoneId.of("UTC");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    private UUID id;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreatedDate
    @Column(name = "expires_at", updatable = false)
    private LocalDateTime expiresAt;

    @Column(name = "valid")
    private boolean valid = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false)
    private TokenType tokenType;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_uuid")
    private UserModel userModel;

    UserTokenModel() {
        //Empty
    }

    public UserTokenModel(UserModel userModel, TokenType type, LocalDateTime expiresAt) {
        this.tokenType = type;
        this.userModel = userModel;
        this.createdAt = LocalDateTime.now(ZONE_UTC);
        this.expiresAt = expiresAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
