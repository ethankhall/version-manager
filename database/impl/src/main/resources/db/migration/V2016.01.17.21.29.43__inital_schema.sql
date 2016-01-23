CREATE TABLE user_model (
    uuid          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name          VARCHAR(255) NOT NULL,
    email_address VARCHAR(256) NOT NULL
);

CREATE TABLE user_token (
    uuid       UUID PRIMARY KEY                              DEFAULT uuid_generate_v4(),
    user_uuid  UUID REFERENCES user_model (uuid) NOT NULL,
    created_at TIMESTAMP                         NOT NULL    DEFAULT current_timestamp,
    expires_at TIMESTAMP                                     DEFAULT NULL,
    valid      BOOLEAN                                       DEFAULT TRUE,
    token_type VARCHAR(5)                        NOT NULL
);

CREATE TABLE project_model (
    uuid         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    project_name CHARACTER VARYING(255) NOT NULL
);

CREATE TABLE repo_model (
    uuid         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    project_uuid UUID REFERENCES project_model (uuid),
    repo_name    CHARACTER VARYING(255)  NOT NULL,
    url          CHARACTER VARYING(1024) NOT NULL,
    description  TEXT                    NOT NULL
);
