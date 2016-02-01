CREATE TABLE user_details (
    uuid          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name          VARCHAR(255) NOT NULL,
    email_address VARCHAR(256) NOT NULL
);

CREATE TYPE token_type AS ENUM ('USER', 'API');

CREATE TABLE user_tokens (
    uuid       UUID PRIMARY KEY                                DEFAULT uuid_generate_v4(),
    user_uuid  UUID REFERENCES user_details (uuid) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE            NOT NULL    DEFAULT current_timestamp,
    expires_at TIMESTAMP WITH TIME ZONE                        DEFAULT NULL,
    valid      BOOLEAN                                         DEFAULT TRUE,
    token_type token_type                          NOT NULL
);

CREATE TABLE project_details (
    uuid         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    project_name CHARACTER VARYING(255) NOT NULL,
    UNIQUE (project_name)
);

CREATE TABLE version_bumpers (
    uuid        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bumper_name TEXT NOT NULL,
    class_name  TEXT NOT NULL,
    description TEXT NOT NULL,
    UNIQUE (bumper_name),
    UNIQUE (class_name)
);

CREATE TABLE repo_details (
    uuid                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    project_name        CHARACTER VARYING(255)  NOT NULL,
    repo_name           CHARACTER VARYING(255)  NOT NULL,
    project_uuid        UUID REFERENCES project_details (uuid),
    version_bumper_uuid UUID REFERENCES version_bumpers (uuid),
    url                 CHARACTER VARYING(1024) NOT NULL,
    description         TEXT                    NOT NULL,
    UNIQUE (project_name, repo_name)
);

CREATE TABLE commit_details (
    uuid               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    repo_details_uuid  UUID REFERENCES repo_details (uuid) NOT NULL,
    parent_commit_uuid UUID REFERENCES commit_details (uuid),
    commit_id          CHARACTER VARYING(40)               NOT NULL,
    version            CHARACTER VARYING(120)              NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE            NOT NULL,
    UNIQUE (commit_id, repo_details_uuid),
    UNIQUE (version, repo_details_uuid)
);

CREATE TABLE commit_metadata (
    uuid              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    commit_uuid       UUID REFERENCES commit_details (uuid),
    repo_details_uuid UUID REFERENCES repo_details (uuid),
    name              CHARACTER VARYING(255)     NOT NULL,
    text              CHARACTER VARYING(1048576) NOT NULL,
    created_at        TIMESTAMP WITH TIME ZONE   NOT NULL,
    updated_at        TIMESTAMP WITH TIME ZONE   NOT NULL,
    UNIQUE (name, commit_uuid)
);

CREATE TABLE user_security_client_profile (
    uuid             UUID PRIMARY KEY                              DEFAULT uuid_generate_v4(),
    user_uuid        UUID REFERENCES user_details (uuid) NOT NULL,
    provider_type    VARCHAR(64)                         NOT NULL,
    provider_user_id VARCHAR(128)                        NOT NULL,
    UNIQUE (provider_type, provider_user_id)
);
