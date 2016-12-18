CREATE TABLE version_bumpers (
    version_bumper_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bumper_name       TEXT   NOT NULL,
    class_name        TEXT   NOT NULL,
    description       TEXT   NOT NULL
)
    ENGINE = innodb;

CREATE TABLE security_id_seq (
    security_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(10)
)
    ENGINE = innodb;

CREATE TABLE project_details (
    product_detail_id BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    project_name      VARCHAR(255) NOT NULL,
    security_id       BIGINT       NOT NULL,
    FOREIGN KEY (security_id) REFERENCES security_id_seq (security_id)
)
    ENGINE = innodb;

CREATE TABLE repo_details (
    repo_detail_id    BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    repo_name         VARCHAR(255) NOT NULL,
    project_id        BIGINT       NOT NULL,
    version_bumper_id BIGINT       NOT NULL,
    url               TEXT,
    description       TEXT,
    public            BOOL                  DEFAULT 1,
    security_id       BIGINT       NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project_details (product_detail_id)
        ON DELETE CASCADE,
    FOREIGN KEY (version_bumper_id) REFERENCES version_bumpers (version_bumper_id),
    FOREIGN KEY (security_id) REFERENCES security_id_seq (security_id)
)
    ENGINE = innodb;

CREATE TABLE repository_tokens (
    repository_token_id BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    public_repo_token   VARCHAR(64)  NOT NULL UNIQUE,
    created_at          TIMESTAMP(6) NOT NULL,
    expires_at          TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    valid               BOOLEAN               DEFAULT 1,
    repo_id             BIGINT       NOT NULL,
    FOREIGN KEY (repo_id) REFERENCES repo_details (repo_detail_id)
        ON DELETE CASCADE
)
    ENGINE = innodb;

CREATE TABLE user_details (
    user_id   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(128) NOT NULL,
    name      VARCHAR(255) NOT NULL
)
    ENGINE = innodb;

CREATE TABLE user_tokens (
    user_token_id     BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    public_user_token VARCHAR(64)  NOT NULL UNIQUE,
    created_at        TIMESTAMP(6) NOT NULL,
    expires_at        TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    valid             BOOL                  DEFAULT 1,
    user_id           BIGINT       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_details (user_id)
        ON DELETE CASCADE
)
    ENGINE = innodb;

CREATE TABLE watcher (
    watcher_id        BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id           BIGINT NOT NULL,
    project_detail_id BIGINT NULL,
    repo_detail_id    BIGINT NULL,
    FOREIGN KEY (user_id) REFERENCES user_details (user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (project_detail_id) REFERENCES project_details (product_detail_id)
        ON DELETE CASCADE,
    FOREIGN KEY (repo_detail_id) REFERENCES repo_details (repo_detail_id)
        ON DELETE CASCADE
)
    ENGINE = innodb;

CREATE TABLE commit_details (
    commit_detail_id BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    repo_detail_id   BIGINT       NOT NULL,
    parent_commit_id BIGINT       NULL,
    commit_id        VARCHAR(40)  NOT NULL,
    version          VARCHAR(120) NOT NULL,
    created_at       TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (repo_detail_id) REFERENCES repo_details (repo_detail_id)
        ON DELETE CASCADE,
    FOREIGN KEY (parent_commit_id) REFERENCES commit_details (commit_detail_id)
        ON DELETE SET NULL
)
    ENGINE = innodb;

CREATE TABLE commit_metadata (
    commit_metadata_id BIGINT       NOT NULL      AUTO_INCREMENT PRIMARY KEY,
    commit_id          BIGINT       NOT NULL,
    project_id         BIGINT       NOT NULL,
    repo_id            BIGINT       NOT NULL,
    name               VARCHAR(128) NOT NULL,
    uri                TEXT         NOT NULL,
    size               BIGINT       NOT NULL,
    content_type       VARCHAR(64)  NOT NULL,
    created_at         TIMESTAMP(6) NOT NULL      DEFAULT CURRENT_TIMESTAMP(6),
    updated_at         TIMESTAMP(6) NOT NULL      DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    FOREIGN KEY (commit_id) REFERENCES commit_details (commit_detail_id)
        ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES project_details (product_detail_id)
        ON DELETE CASCADE,
    FOREIGN KEY (repo_id) REFERENCES repo_details (repo_detail_id)
        ON DELETE CASCADE
)
    ENGINE = innodb;

-- Spring ACL stuffs

CREATE TABLE acl_sid (
    id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    principal BOOLEAN         NOT NULL,
    sid       VARCHAR(100)    NOT NULL,
    UNIQUE KEY unique_acl_sid (sid, principal)
)
    ENGINE = InnoDB;

CREATE TABLE acl_class (
    id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    class VARCHAR(100)    NOT NULL,
    UNIQUE KEY uk_acl_class (class)
)
    ENGINE = InnoDB;

CREATE TABLE acl_object_identity (
    id                 BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    object_id_class    BIGINT UNSIGNED NOT NULL,
    object_id_identity BIGINT          NOT NULL,
    parent_object      BIGINT UNSIGNED,
    owner_sid          BIGINT UNSIGNED,
    entries_inheriting BOOLEAN         NOT NULL,
    UNIQUE KEY uk_acl_object_identity (object_id_class, object_id_identity),
    CONSTRAINT fk_acl_object_identity_parent FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_object_identity_class FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
    CONSTRAINT fk_acl_object_identity_owner FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
)
    ENGINE = InnoDB;

CREATE TABLE acl_entry (
    id                  BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    acl_object_identity BIGINT UNSIGNED  NOT NULL,
    ace_order           INTEGER          NOT NULL,
    sid                 BIGINT UNSIGNED  NOT NULL,
    mask                INTEGER UNSIGNED NOT NULL,
    granting            BOOLEAN          NOT NULL,
    audit_success       BOOLEAN          NOT NULL,
    audit_failure       BOOLEAN          NOT NULL,
    UNIQUE KEY unique_acl_entry (acl_object_identity, ace_order),
    CONSTRAINT fk_acl_entry_object FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_entry_acl FOREIGN KEY (sid) REFERENCES acl_sid (id)
)
    ENGINE = InnoDB;

-- spring social stuff

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
