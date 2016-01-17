CREATE TABLE commit_metadata (
    uuid        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        CHARACTER VARYING(255)     NOT NULL,
    text        CHARACTER VARYING(1048576) NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE   NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE   NOT NULL,
    commit_uuid UUID REFERENCES repository_commit (uuid)
);
