CREATE TABLE resource_detail_lookup (
    uuid                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    project_name VARCHAR(255)                           NOT NULL,
    repo_name    VARCHAR(255),
    project_uuid UUID REFERENCES project_details (uuid) NOT NULL,
    repo_uuid    UUID REFERENCES repo_details (uuid) UNIQUE,
    UNIQUE (project_name, repo_name),
    UNIQUE (project_uuid, repo_uuid)
);

INSERT INTO resource_detail_lookup (project_name, repo_name, project_uuid, repo_uuid)
    SELECT
        project_name,
        repo_name,
        project_uuid,
        uuid
    FROM repo_details;


ALTER TABLE repo_details
    DROP COLUMN project_name;

ALTER TABLE user_permissions
    DROP COLUMN project_name;
ALTER TABLE user_permissions
    DROP COLUMN repo_name;
