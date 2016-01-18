CREATE TABLE version_bumper (
    uuid        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bumper_name TEXT NOT NULL UNIQUE,
    class_name  TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL
);

CREATE TABLE vcs_repo_data (
    uuid                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    repo_name           TEXT                                  NOT NULL,
    repo_token          CHARACTER VARYING(60)                 NOT NULL,
    url                 TEXT,
    version_bumper_uuid UUID REFERENCES version_bumper (uuid) NOT NULL
);

CREATE TABLE repository_commit (
    uuid               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    commit_id          CHARACTER VARYING(40)                NOT NULL,
    version            CHARACTER VARYING(120)               NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE             NOT NULL,
    vcs_repo_uuid      UUID REFERENCES vcs_repo_data (uuid) NOT NULL,
    parent_commit_uuid UUID REFERENCES repository_commit (uuid),
    UNIQUE (commit_id, vcs_repo_uuid)
);

CREATE TABLE commit_metadata (
    uuid        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        CHARACTER VARYING(255)     NOT NULL,
    text        CHARACTER VARYING(1048576) NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE   NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE   NOT NULL,
    commit_uuid UUID REFERENCES repository_commit (uuid)
);
