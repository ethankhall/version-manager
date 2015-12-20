DROP TABLE IF EXISTS temp_version_bumper;
DROP TABLE IF EXISTS temp_repository_commit;
DROP TABLE IF EXISTS temp_vcs_repo_data;

CREATE EXTENSION "uuid-ossp";

CREATE TEMPORARY TABLE temp_version_bumper (
    uuid        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id          BIGINT NOT NULL,
    bumper_name TEXT   NOT NULL,
    class_name  TEXT   NOT NULL,
    description TEXT   NOT NULL
);

INSERT INTO temp_version_bumper (id, bumper_name, class_name, description)
    SELECT *
    FROM version_bumper;


CREATE TEMPORARY TABLE temp_repository_commit (
    uuid          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id            BIGINT                   NOT NULL,
    commit_id     CHARACTER VARYING(40)    NOT NULL,
    version       CHARACTER VARYING(120)   NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL,
    vcs_repo      BIGINT                   NOT NULL,
    parent_commit BIGINT,
    parent_uuid   UUID,
    vcs_repo_uuid UUID
);

INSERT INTO temp_repository_commit (id, commit_id, version, created_at, vcs_repo, parent_commit)
    SELECT *
    FROM repository_commit;

CREATE TEMPORARY TABLE temp_vcs_repo_data (
    uuid                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id                  BIGINT,
    repo_name           TEXT                  NOT NULL,
    repo_token          CHARACTER VARYING(60) NOT NULL,
    url                 TEXT,
    version_bumper      BIGINT,
    version_bumper_uuid UUID
);

INSERT INTO temp_vcs_repo_data (id, uuid, repo_name, repo_token, url, version_bumper)
    SELECT id, uuid, repo_name, repo_token, url, version_bumper
    FROM vcs_repo_data;

UPDATE temp_repository_commit t1
SET parent_uuid = (SELECT t2.uuid
                   FROM temp_repository_commit t2
                   WHERE t2.id = t1.parent_commit);

UPDATE temp_repository_commit t1
SET vcs_repo_uuid = (SELECT t2.uuid
                     FROM vcs_repo_data t2
                     WHERE t1.vcs_repo = t2.id);

UPDATE temp_vcs_repo_data t1
SET version_bumper_uuid = (SELECT t2.uuid
                           FROM temp_version_bumper t2
                           WHERE t1.version_bumper = t2.id);

DROP TABLE repository_commit;
DROP TABLE vcs_repo_data;
DROP TABLE version_bumper;

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
    uuid          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    commit_id     CHARACTER VARYING(40)                NOT NULL,
    version       CHARACTER VARYING(120)               NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE             NOT NULL,
    vcs_repo_uuid UUID REFERENCES vcs_repo_data (uuid) NOT NULL,
    parent_uuid   UUID REFERENCES repository_commit (uuid),
    UNIQUE (commit_id, vcs_repo_uuid)
);

INSERT INTO version_bumper (uuid, bumper_name, class_name, description)
    SELECT
        uuid,
        bumper_name,
        class_name,
        description
    FROM temp_version_bumper;

INSERT INTO vcs_repo_data (uuid, repo_name, repo_token, url, version_bumper_uuid)
    SELECT
        uuid,
        repo_name,
        repo_token,
        url,
        version_bumper_uuid
    FROM temp_vcs_repo_data;

INSERT INTO repository_commit (uuid, commit_id, version, created_at, vcs_repo_uuid, parent_uuid)
    SELECT
        uuid,
        commit_id,
        version,
        created_at,
        vcs_repo_uuid,
        parent_uuid
    FROM temp_repository_commit;

DROP TABLE temp_repository_commit;
DROP TABLE temp_vcs_repo_data;
DROP TABLE temp_version_bumper;
