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
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "user_tokens", schema = "public")
public class UserTokens implements Serializable {

	private static final long serialVersionUID = 1463342750;

	private UUID      uuid;
	private UUID      userUuid;
	private Instant   createdAt;
	private Instant   expiresAt;
	private Boolean   valid;
	private TokenType tokenType;

	public UserTokens() {}

	public UserTokens(UserTokens value) {
		this.uuid = value.uuid;
		this.userUuid = value.userUuid;
		this.createdAt = value.createdAt;
		this.expiresAt = value.expiresAt;
		this.valid = value.valid;
		this.tokenType = value.tokenType;
	}

	public UserTokens(
		UUID      uuid,
		UUID      userUuid,
		Instant   createdAt,
		Instant   expiresAt,
		Boolean   valid,
		TokenType tokenType
	) {
		this.uuid = uuid;
		this.userUuid = userUuid;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.valid = valid;
		this.tokenType = tokenType;
	}

	@Id
	@Column(name = "uuid", unique = true, nullable = false)
	@NotNull
	public UUID getUuid() {
		return this.uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Column(name = "user_uuid", nullable = false)
	@NotNull
	public UUID getUserUuid() {
		return this.userUuid;
	}

	public void setUserUuid(UUID userUuid) {
		this.userUuid = userUuid;
	}

	@Column(name = "created_at", nullable = false)
	@NotNull
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
		StringBuilder sb = new StringBuilder("UserTokens (");

		sb.append(uuid);
		sb.append(", ").append(userUuid);
		sb.append(", ").append(createdAt);
		sb.append(", ").append(expiresAt);
		sb.append(", ").append(valid);
		sb.append(", ").append(tokenType);

		sb.append(")");
		return sb.toString();
	}
}