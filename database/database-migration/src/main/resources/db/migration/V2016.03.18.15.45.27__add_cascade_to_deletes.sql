
ALTER TABLE token_join
    DROP CONSTRAINT token_join_repo_uuid_fkey,
    ADD CONSTRAINT token_join_repo_uuid_fkey
        FOREIGN KEY (repo_uuid) REFERENCES repo_details (uuid)
        ON DELETE CASCADE;

ALTER TABLE commit_details
    DROP CONSTRAINT commit_details_repo_details_uuid_fkey,
    ADD CONSTRAINT commit_details_repo_details_uuid_fkey
        FOREIGN KEY (repo_details_uuid) REFERENCES repo_details (uuid)
        ON DELETE CASCADE;

ALTER TABLE commit_metadata
    DROP CONSTRAINT commit_metadata_repo_details_uuid_fkey,
    ADD CONSTRAINT commit_metadata_repo_details_uuid_fkey
        FOREIGN KEY (repo_details_uuid) REFERENCES repo_details (uuid)
        ON DELETE CASCADE;

ALTER TABLE user_permissions
    DROP CONSTRAINT user_permissions_repo_details_uuid_fkey,
    ADD CONSTRAINT user_permissions_repo_details_uuid_fkey
        FOREIGN KEY (repo_details_uuid) REFERENCES repo_details (uuid)
        ON DELETE CASCADE;
