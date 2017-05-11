ALTER TABLE commit_details
    ADD COLUMN state VARCHAR(64) NOT NULL DEFAULT 'RELEASED';

UPDATE commit_details
SET state = 'RELEASED';

CREATE INDEX commit_id_state_idx
    ON commit_details (commit_detail_id, state);

CREATE INDEX repo_id_state_idx
    ON commit_details (repo_detail_id, state);
