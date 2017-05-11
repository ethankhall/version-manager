CREATE TABLE version_state_machine_definitions (
    version_state_machine_id BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    initial_state            VARCHAR(64) NOT NULL,
    repo_detail_id           BIGINT      NOT NULL UNIQUE,
    FOREIGN KEY (repo_detail_id) REFERENCES repo_details (repo_detail_id)
        ON DELETE CASCADE
)
    ENGINE = innodb;

CREATE TABLE version_state_machine_states (
    version_state_machine_state_id BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    version_state_machine_id       BIGINT      NOT NULL,
    state_name                     VARCHAR(64) NOT NULL,
    auto_transition                BOOLEAN              DEFAULT FALSE,
    next_state                     VARCHAR(64) NULL,
    FOREIGN KEY (version_state_machine_id) REFERENCES version_state_machine_definitions (version_state_machine_id)
        ON DELETE CASCADE,
    UNIQUE KEY (version_state_machine_id, state_name)
)
    ENGINE = innodb;

CREATE TABLE version_state_machine_connections (
    state_machine_detail_id  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    version_state_machine_id BIGINT NOT NULL,
    source                   BIGINT NOT NULL,
    destination              BIGINT NOT NULL,
    FOREIGN KEY (version_state_machine_id) REFERENCES version_state_machine_definitions (version_state_machine_id)
        ON DELETE CASCADE,
    FOREIGN KEY (source) REFERENCES version_state_machine_states (version_state_machine_state_id)
        ON DELETE CASCADE,
    FOREIGN KEY (destination) REFERENCES version_state_machine_states (version_state_machine_state_id)
        ON DELETE CASCADE,
    UNIQUE KEY (source, destination)
)
    ENGINE = innodb;
