CREATE TABLE user_model (
    uuid          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name          VARCHAR(255) NOT NULL,
    email_address VARCHAR(256) NOT NULL
);

CREATE TABLE user_token (
    uuid       UUID PRIMARY KEY                              DEFAULT uuid_generate_v4(),
    user_uuid  UUID REFERENCES user_model (uuid) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE          NOT NULL    DEFAULT current_timestamp,
    expires_at TIMESTAMP WITH TIME ZONE                      DEFAULT NULL,
    valid      BOOLEAN                                       DEFAULT TRUE,
    token_type VARCHAR(5)                        NOT NULL
);

CREATE TABLE project_model (
    uuid         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    project_name CHARACTER VARYING(255) NOT NULL
);

CREATE TABLE version_bumper (
    uuid        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bumper_name TEXT NOT NULL UNIQUE,
    class_name  TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL
);

CREATE TABLE repo_model (
    uuid                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    repo_name           CHARACTER VARYING(255)  NOT NULL,
    project_uuid        UUID REFERENCES project_model (uuid),
    version_bumper_uuid UUID REFERENCES version_bumper (uuid),
    url                 CHARACTER VARYING(1024) NOT NULL,
    description         TEXT                    NOT NULL
);

CREATE TABLE commit_model (
    uuid               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    vcs_repo_uuid      UUID REFERENCES repo_model (uuid) NOT NULL,
    parent_commit_uuid UUID REFERENCES commit_model (uuid),
    commit_id          CHARACTER VARYING(40)             NOT NULL,
    version            CHARACTER VARYING(120)            NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE          NOT NULL,
    UNIQUE (commit_id, vcs_repo_uuid)
);

CREATE TABLE commit_metadata (
    uuid        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    commit_uuid UUID REFERENCES commit_model (uuid),
    name        CHARACTER VARYING(255)     NOT NULL,
    text        CHARACTER VARYING(1048576) NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE   NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE   NOT NULL
);
