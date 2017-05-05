/*
 * This file is generated by jOOQ.
*/
package tech.crom.db;


import javax.annotation.Generated;

import tech.crom.db.tables.AclClassTable;
import tech.crom.db.tables.AclEntryTable;
import tech.crom.db.tables.AclObjectIdentityTable;
import tech.crom.db.tables.AclSidTable;
import tech.crom.db.tables.CommitDetailsTable;
import tech.crom.db.tables.CommitMetadataTable;
import tech.crom.db.tables.ProjectDetailTrackerTable;
import tech.crom.db.tables.ProjectDetailsTable;
import tech.crom.db.tables.RepoDetailsTable;
import tech.crom.db.tables.RepositoryTokensTable;
import tech.crom.db.tables.SecurityIdSeqTable;
import tech.crom.db.tables.SsUserconnectionTable;
import tech.crom.db.tables.UserDetailsTable;
import tech.crom.db.tables.UserTokensTable;
import tech.crom.db.tables.VersionBumpersTable;
import tech.crom.db.tables.VersionStateMachineConnectionsTable;
import tech.crom.db.tables.VersionStateMachineDefinitionsTable;
import tech.crom.db.tables.VersionStateMachineStatesTable;
import tech.crom.db.tables.WatcherTable;


/**
 * Convenience access to all tables in version_manager_test
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>version_manager_test.acl_class</code>.
     */
    public static final AclClassTable ACL_CLASS = tech.crom.db.tables.AclClassTable.ACL_CLASS;

    /**
     * The table <code>version_manager_test.acl_entry</code>.
     */
    public static final AclEntryTable ACL_ENTRY = tech.crom.db.tables.AclEntryTable.ACL_ENTRY;

    /**
     * The table <code>version_manager_test.acl_object_identity</code>.
     */
    public static final AclObjectIdentityTable ACL_OBJECT_IDENTITY = tech.crom.db.tables.AclObjectIdentityTable.ACL_OBJECT_IDENTITY;

    /**
     * The table <code>version_manager_test.acl_sid</code>.
     */
    public static final AclSidTable ACL_SID = tech.crom.db.tables.AclSidTable.ACL_SID;

    /**
     * The table <code>version_manager_test.commit_details</code>.
     */
    public static final CommitDetailsTable COMMIT_DETAILS = tech.crom.db.tables.CommitDetailsTable.COMMIT_DETAILS;

    /**
     * The table <code>version_manager_test.commit_metadata</code>.
     */
    public static final CommitMetadataTable COMMIT_METADATA = tech.crom.db.tables.CommitMetadataTable.COMMIT_METADATA;

    /**
     * The table <code>version_manager_test.project_details</code>.
     */
    public static final ProjectDetailsTable PROJECT_DETAILS = tech.crom.db.tables.ProjectDetailsTable.PROJECT_DETAILS;

    /**
     * The table <code>version_manager_test.project_detail_tracker</code>.
     */
    public static final ProjectDetailTrackerTable PROJECT_DETAIL_TRACKER = tech.crom.db.tables.ProjectDetailTrackerTable.PROJECT_DETAIL_TRACKER;

    /**
     * The table <code>version_manager_test.repository_tokens</code>.
     */
    public static final RepositoryTokensTable REPOSITORY_TOKENS = tech.crom.db.tables.RepositoryTokensTable.REPOSITORY_TOKENS;

    /**
     * The table <code>version_manager_test.repo_details</code>.
     */
    public static final RepoDetailsTable REPO_DETAILS = tech.crom.db.tables.RepoDetailsTable.REPO_DETAILS;

    /**
     * The table <code>version_manager_test.security_id_seq</code>.
     */
    public static final SecurityIdSeqTable SECURITY_ID_SEQ = tech.crom.db.tables.SecurityIdSeqTable.SECURITY_ID_SEQ;

    /**
     * The table <code>version_manager_test.ss_UserConnection</code>.
     */
    public static final SsUserconnectionTable SS_USERCONNECTION = tech.crom.db.tables.SsUserconnectionTable.SS_USERCONNECTION;

    /**
     * The table <code>version_manager_test.user_details</code>.
     */
    public static final UserDetailsTable USER_DETAILS = tech.crom.db.tables.UserDetailsTable.USER_DETAILS;

    /**
     * The table <code>version_manager_test.user_tokens</code>.
     */
    public static final UserTokensTable USER_TOKENS = tech.crom.db.tables.UserTokensTable.USER_TOKENS;

    /**
     * The table <code>version_manager_test.version_bumpers</code>.
     */
    public static final VersionBumpersTable VERSION_BUMPERS = tech.crom.db.tables.VersionBumpersTable.VERSION_BUMPERS;

    /**
     * The table <code>version_manager_test.version_state_machine_connections</code>.
     */
    public static final VersionStateMachineConnectionsTable VERSION_STATE_MACHINE_CONNECTIONS = tech.crom.db.tables.VersionStateMachineConnectionsTable.VERSION_STATE_MACHINE_CONNECTIONS;

    /**
     * The table <code>version_manager_test.version_state_machine_definitions</code>.
     */
    public static final VersionStateMachineDefinitionsTable VERSION_STATE_MACHINE_DEFINITIONS = tech.crom.db.tables.VersionStateMachineDefinitionsTable.VERSION_STATE_MACHINE_DEFINITIONS;

    /**
     * The table <code>version_manager_test.version_state_machine_states</code>.
     */
    public static final VersionStateMachineStatesTable VERSION_STATE_MACHINE_STATES = tech.crom.db.tables.VersionStateMachineStatesTable.VERSION_STATE_MACHINE_STATES;

    /**
     * The table <code>version_manager_test.watcher</code>.
     */
    public static final WatcherTable WATCHER = tech.crom.db.tables.WatcherTable.WATCHER;
}
