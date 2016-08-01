ALTER TABLE commit_metadata
    RENAME COLUMN text TO URL;

DROP TABLE user_security_client_profile;

ALTER TABLE user_details
    DROP COLUMN email_address;

CREATE TABLE ss_UserConnection (
    userId         VARCHAR(255) NOT NULL,
    providerId     VARCHAR(255) NOT NULL,
    providerUserId VARCHAR(255),
    rank           INT          NOT NULL,
    displayName    VARCHAR(255),
    profileUrl     VARCHAR(512),
    imageUrl       VARCHAR(512),
    accessToken    VARCHAR(512) NOT NULL,
    secret         VARCHAR(512),
    refreshToken   VARCHAR(512),
    expireTime     BIGINT,
    PRIMARY KEY (userId, providerId, providerUserId)
);

CREATE UNIQUE INDEX UserConnectionRank
    ON ss_UserConnection (userId, providerId, rank);

CREATE SEQUENCE token_auth_security_id_seq;

ALTER TABLE repo_details
    ADD COLUMN security_id BIGINT NOT NULL DEFAULT nextval('token_auth_security_id_seq');

ALTER TABLE project_details
    ADD COLUMN security_id BIGINT NOT NULL DEFAULT nextval('token_auth_security_id_seq');

DROP TABLE user_permissions;
DROP TABLE token_join;
DROP TABLE token_authentications;
DROP TABLE resource_detail_lookup;

DROP TYPE token_type;

CREATE TABLE user_tokens (
    uuid        UUID PRIMARY KEY                                                            DEFAULT uuid_generate_v4(),
    created_at  TIMESTAMP WITH TIME ZONE                             NOT NULL               DEFAULT current_timestamp,
    expires_at  TIMESTAMP WITH TIME ZONE                                                    DEFAULT NULL,
    valid       BOOLEAN                                                                     DEFAULT TRUE,
    user_uuid   UUID REFERENCES user_details (uuid)                  NOT NULL
);

CREATE TABLE repository_tokens (
    uuid        UUID PRIMARY KEY                                                            DEFAULT uuid_generate_v4(),
    created_at  TIMESTAMP WITH TIME ZONE                             NOT NULL               DEFAULT current_timestamp,
    expires_at  TIMESTAMP WITH TIME ZONE                                                    DEFAULT NULL,
    valid       BOOLEAN                                                                     DEFAULT TRUE,
    repo_uuid   UUID REFERENCES repo_details (UUID)                  NOT NULL
);

-- spring acl stuff below

CREATE TABLE acl_sid (
    id        BIGSERIAL    NOT NULL PRIMARY KEY,
    principal BOOLEAN      NOT NULL,
    sid       VARCHAR(100) NOT NULL,
    CONSTRAINT unique_uk_1 UNIQUE (sid, principal)
);

CREATE TABLE acl_class (
    id    BIGSERIAL    NOT NULL PRIMARY KEY,
    class VARCHAR(100) NOT NULL,
    CONSTRAINT unique_uk_2 UNIQUE (class)
);

CREATE TABLE acl_object_identity (
    id                 BIGSERIAL PRIMARY KEY,
    object_id_class    BIGINT  NOT NULL,
    object_id_identity BIGINT  NOT NULL,
    parent_object      BIGINT,
    owner_sid          BIGINT,
    entries_inheriting BOOLEAN NOT NULL,
    CONSTRAINT unique_uk_3 UNIQUE (object_id_class, object_id_identity),
    CONSTRAINT foreign_fk_1 FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
    CONSTRAINT foreign_fk_2 FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
    CONSTRAINT foreign_fk_3 FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
);

CREATE TABLE acl_entry (
    id                  BIGSERIAL PRIMARY KEY,
    acl_object_identity BIGINT  NOT NULL,
    ace_order           INT     NOT NULL,
    sid                 BIGINT  NOT NULL,
    mask                INTEGER NOT NULL,
    granting            BOOLEAN NOT NULL,
    audit_success       BOOLEAN NOT NULL,
    audit_failure       BOOLEAN NOT NULL,
    CONSTRAINT unique_uk_4 UNIQUE (acl_object_identity, ace_order),
    CONSTRAINT foreign_fk_4 FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
    CONSTRAINT foreign_fk_5 FOREIGN KEY (sid) REFERENCES acl_sid (id)
);
