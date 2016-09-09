CREATE TABLE watcher (
    uuid                 UUID PRIMARY KEY                              DEFAULT uuid_generate_v4(),
    user_uuid            UUID REFERENCES user_details (uuid)           NOT NULL,
    project_details_uuid UUID REFERENCES project_details (uuid),
    repo_details_uuid    UUID REFERENCES repo_details (uuid)
);
