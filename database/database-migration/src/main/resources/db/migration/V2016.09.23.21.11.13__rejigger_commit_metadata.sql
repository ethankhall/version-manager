DROP TABLE commit_metadata;

CREATE TABLE commit_metadata (
    uuid         UUID PRIMARY KEY                                                               DEFAULT uuid_generate_v4(),
    commit_uuid  UUID REFERENCES commit_details (uuid) ON DELETE CASCADE               NOT NULL,
    project_uuid UUID REFERENCES project_details (uuid)                                NOT NULL,
    repo_uuid    UUID REFERENCES repo_details (uuid) ON DELETE CASCADE                 NOT NULL,
    name         VARCHAR(128)                                                          NOT NULL,
    uri          VARCHAR(512)                                                          NOT NULL,
    size         BIGINT                                                                NOT NULL,
    content_type VARCHAR(64)                                                           NOT NULL,
    created_at   TIMESTAMPTZ                                                           NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ,
    UNIQUE (commit_uuid, name)
);
