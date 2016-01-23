CREATE TABLE security_user_token (
    uuid       UUID PRIMARY KEY                              DEFAULT uuid_generate_v4(),
    user_uuid  UUID REFERENCES user_model (uuid) NOT NULL,
    created_at TIMESTAMP                            NOT NULL DEFAULT current_timestamp,
    expires_at TIMESTAMP                                     DEFAULT NULL,
    valid      BOOLEAN                                       DEFAULT TRUE,
    token_type VARCHAR(5)                           NOT NULL
);

CREATE TABLE security_user_client_profile (
    uuid             UUID PRIMARY KEY                              DEFAULT uuid_generate_v4(),
    user_uuid        UUID REFERENCES user_model (uuid) NOT NULL,
    provider_type    VARCHAR(64)                          NOT NULL,
    provider_user_id VARCHAR(128)                         NOT NULL,
    UNIQUE (provider_type, provider_user_id)
);
