CREATE TABLE version_bumpers (
    version_bumper_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bumper_name text NOT NULL,
    class_name text NOT NULL,
    description text NOT NULL
) ENGINE=innodb;

CREATE TABLE security_id_seq (
    security_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(10)
) ENGINE=innodb;

CREATE TABLE project_details (
    product_details_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    project_name varchar(255) NOT NULL,
    security_id bigint NOT NULL,
    FOREIGN KEY (security_id) REFERENCES security_id_seq(security_id)
) ENGINE=innodb;

CREATE TABLE repo_details (
    repo_details_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    repo_name varchar(255) NOT NULL,
    project_id BIGINT NOT NULL,
    version_bumper_id BIGINT NOT NULL,
    url text,
    description text,
    public bool DEFAULT 1,
    security_id bigint NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project_details(product_details_id),
    FOREIGN KEY (version_bumper_id) REFERENCES version_bumpers(version_bumper_id),
    FOREIGN KEY (security_id) REFERENCES security_id_seq(security_id)
) ENGINE=innodb;

CREATE TABLE repository_tokens (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    expires_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    valid bool DEFAULT 1,
    repo_id BIGINT NOT NULL,
    FOREIGN KEY (repo_id) REFERENCES repo_details(repo_details_id)
) ENGINE=innodb;

CREATE TABLE user_details (
    user_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name varchar(128) NOT NULL,
    name varchar(255) NOT NULL
) ENGINE=innodb;

CREATE TABLE user_tokens (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    expires_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    valid bool DEFAULT 1,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_details(user_id)
) ENGINE=innodb;

CREATE TABLE watcher (
    watcher_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    project_details_id BIGINT NULL,
    repo_details_id BIGINT NULL,
    FOREIGN KEY (user_id) REFERENCES user_details(user_id),
    FOREIGN KEY (project_details_id) REFERENCES project_details(product_details_id),
    FOREIGN KEY (repo_details_id) REFERENCES repo_details(repo_details_id)
) ENGINE=innodb;

CREATE TABLE commit_details (
    commit_details_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    repo_details_id BIGINT NOT NULL REFERENCES repo_details(repo_details_id),
    parent_commit_id BIGINT NULL REFERENCES commit_details(commit_details_id),
    commit_id varchar(40) NOT NULL,
    version varchar(120) NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (repo_details_id) REFERENCES repo_details(repo_details_id),
    FOREIGN KEY (parent_commit_id) REFERENCES commit_details(commit_details_id)
) ENGINE=innodb;

CREATE TABLE commit_metadata (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    commit_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    repo_id BIGINT NOT NULL,
    name varchar(128) NOT NULL,
    uri text NOT NULL,
    size bigint NOT NULL,
    content_type varchar(64) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (commit_id) REFERENCES commit_details(commit_details_id),
    FOREIGN KEY (project_id) REFERENCES project_details(product_details_id),
    FOREIGN KEY (repo_id) REFERENCES repo_details(repo_details_id)
) ENGINE=innodb;

-- Spring ACL stuffs

CREATE TABLE acl_sid (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    principal BOOLEAN NOT NULL,
    sid VARCHAR(100) NOT NULL,
    UNIQUE KEY unique_acl_sid (sid, principal)
) ENGINE=InnoDB;

CREATE TABLE acl_class (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    class VARCHAR(100) NOT NULL,
    UNIQUE KEY uk_acl_class (class)
) ENGINE=InnoDB;

CREATE TABLE acl_object_identity (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    object_id_class BIGINT UNSIGNED NOT NULL,
    object_id_identity BIGINT NOT NULL,
    parent_object BIGINT UNSIGNED,
    owner_sid BIGINT UNSIGNED,
    entries_inheriting BOOLEAN NOT NULL,
    UNIQUE KEY uk_acl_object_identity (object_id_class, object_id_identity),
    CONSTRAINT fk_acl_object_identity_parent FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_object_identity_class FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
    CONSTRAINT fk_acl_object_identity_owner FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;

CREATE TABLE acl_entry (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    acl_object_identity BIGINT UNSIGNED NOT NULL,
    ace_order INTEGER NOT NULL,
    sid BIGINT UNSIGNED NOT NULL,
    mask INTEGER UNSIGNED NOT NULL,
    granting BOOLEAN NOT NULL,
    audit_success BOOLEAN NOT NULL,
    audit_failure BOOLEAN NOT NULL,
    UNIQUE KEY unique_acl_entry (acl_object_identity, ace_order),
    CONSTRAINT fk_acl_entry_object FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_entry_acl FOREIGN KEY (sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;


-- spring social stuff

create table ss_UserConnection (userId varchar(255) not null,
                                providerId varchar(255) not null,
                                providerUserId varchar(255),
                                rank int not null,
                                displayName varchar(255),
                                profileUrl varchar(512),
                                imageUrl varchar(512),
                                accessToken varchar(512) not null,
                                secret varchar(512),
                                refreshToken varchar(512),
                                expireTime bigint,
    primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on ss_UserConnection(userId, providerId, rank);
