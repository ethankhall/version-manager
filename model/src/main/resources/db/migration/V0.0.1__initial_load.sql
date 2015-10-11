CREATE TABLE version_bumper (
  id          BIGSERIAL PRIMARY KEY,
  bumper_name TEXT UNIQUE NOT NULL,
  class_name  TEXT UNIQUE NOT NULL,
  description TEXT        NOT NULL
);

CREATE TABLE scm_meta_data (
  id             BIGSERIAL PRIMARY KEY,
  uuid           UUID                                  NOT NULL,
  repo_name      TEXT UNIQUE                           NOT NULL,
  version_bumper BIGINT REFERENCES version_bumper (id) NOT NULL
);

CREATE TABLE repository_commit (
  id            BIGSERIAL PRIMARY KEY,
  commit_id     VARCHAR(40)                              NOT NULL,
  version       VARCHAR(120)                             NOT NULL,
  created_at    TIMESTAMP WITH TIME ZONE                 NOT NULL,
  scm_meta_data BIGINT REFERENCES scm_meta_data (id)     NOT NULL,
  next_commit   BIGINT REFERENCES repository_commit (id) NULL,
  bugfix_commit BIGINT REFERENCES repository_commit (id) NULL,
  UNIQUE (commit_id, scm_meta_data)
);

INSERT INTO version_bumper (bumper_name, class_name, description)
VALUES ('semver', 'SemverVersionBumper', 'SemVer bumper');
