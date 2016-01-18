CREATE TABLE social_UserConnection (
    userId         VARCHAR(255) NOT NULL,
    providerId     VARCHAR(255) NOT NULL,
    providerUserId VARCHAR(255),
    rank           INT          NOT NULL,
    displayName    VARCHAR(255),
    profileUrl     VARCHAR(512),
    imageUrl       VARCHAR(512),
    accessToken    VARCHAR(512) NOT NULL,
    secret         VARCHAR(512),
    refreshToken   VARCHAR(512),
    expireTime     BIGINT,
    PRIMARY KEY (userId, providerId, providerUserId)
);

CREATE UNIQUE INDEX user_connection_rank ON social_UserConnection (userId, providerId, rank);

CREATE TABLE security_user (
    uuid          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name    VARCHAR(255) NOT NULL,
    email_address VARCHAR(256) NOT NULL
);

CREATE TABLE security_user_token (
    uuid       UUID PRIMARY KEY                              DEFAULT uuid_generate_v4(),
    user_uuid  UUID REFERENCES security_user (uuid) NOT NULL,
    created_at TIMESTAMP                            NOT NULL DEFAULT current_timestamp,
    expires_at TIMESTAMP                                     DEFAULT NULL,
    valid      BOOLEAN                                       DEFAULT TRUE,
    token_type VARCHAR(5)                           NOT NULL
);
