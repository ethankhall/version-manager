INSERT INTO version_state_machine_definitions (initial_state, repo_detail_id)
    SELECT
        'DEFAULT',
        repo_detail_id
    FROM repo_details;

INSERT INTO version_state_machine_states (version_state_machine_id, state_name)
    SELECT
        version_state_machine_id,
        'DEFAULT'
    FROM version_state_machine_definitions;
