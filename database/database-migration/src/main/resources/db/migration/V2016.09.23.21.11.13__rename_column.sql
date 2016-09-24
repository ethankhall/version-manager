ALTER TABLE commit_metadata RENAME COLUMN url TO uri;

ALTER TABLE commit_metadata ADD COLUMN size INT8 NOT NULL DEFAULT 0;
