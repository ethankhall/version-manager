/**
 * This class is generated by jOOQ
 */
package tech.crom.db;


import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.jooq.types.ULong;

import tech.crom.db.tables.AclClassTable;
import tech.crom.db.tables.AclEntryTable;
import tech.crom.db.tables.AclObjectIdentityTable;
import tech.crom.db.tables.AclSidTable;
import tech.crom.db.tables.CommitDetailsTable;
import tech.crom.db.tables.CommitMetadataTable;
import tech.crom.db.tables.ProjectDetailsTable;
import tech.crom.db.tables.RepoDetailsTable;
import tech.crom.db.tables.RepositoryTokensTable;
import tech.crom.db.tables.SecurityIdSeqTable;
import tech.crom.db.tables.SsUserconnectionTable;
import tech.crom.db.tables.UserDetailsTable;
import tech.crom.db.tables.UserTokensTable;
import tech.crom.db.tables.VersionBumpersTable;
import tech.crom.db.tables.WatcherTable;
import tech.crom.db.tables.records.AclClassRecord;
import tech.crom.db.tables.records.AclEntryRecord;
import tech.crom.db.tables.records.AclObjectIdentityRecord;
import tech.crom.db.tables.records.AclSidRecord;
import tech.crom.db.tables.records.CommitDetailsRecord;
import tech.crom.db.tables.records.CommitMetadataRecord;
import tech.crom.db.tables.records.ProjectDetailsRecord;
import tech.crom.db.tables.records.RepoDetailsRecord;
import tech.crom.db.tables.records.RepositoryTokensRecord;
import tech.crom.db.tables.records.SecurityIdSeqRecord;
import tech.crom.db.tables.records.SsUserconnectionRecord;
import tech.crom.db.tables.records.UserDetailsRecord;
import tech.crom.db.tables.records.UserTokensRecord;
import tech.crom.db.tables.records.VersionBumpersRecord;
import tech.crom.db.tables.records.WatcherRecord;


/**
 * A class modelling foreign key relationships between tables of the <code>version_manager_test</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<AclClassRecord, ULong> IDENTITY_ACL_CLASS = Identities0.IDENTITY_ACL_CLASS;
    public static final Identity<AclEntryRecord, ULong> IDENTITY_ACL_ENTRY = Identities0.IDENTITY_ACL_ENTRY;
    public static final Identity<AclObjectIdentityRecord, ULong> IDENTITY_ACL_OBJECT_IDENTITY = Identities0.IDENTITY_ACL_OBJECT_IDENTITY;
    public static final Identity<AclSidRecord, ULong> IDENTITY_ACL_SID = Identities0.IDENTITY_ACL_SID;
    public static final Identity<CommitDetailsRecord, Long> IDENTITY_COMMIT_DETAILS = Identities0.IDENTITY_COMMIT_DETAILS;
    public static final Identity<CommitMetadataRecord, Long> IDENTITY_COMMIT_METADATA = Identities0.IDENTITY_COMMIT_METADATA;
    public static final Identity<ProjectDetailsRecord, Long> IDENTITY_PROJECT_DETAILS = Identities0.IDENTITY_PROJECT_DETAILS;
    public static final Identity<RepositoryTokensRecord, Long> IDENTITY_REPOSITORY_TOKENS = Identities0.IDENTITY_REPOSITORY_TOKENS;
    public static final Identity<RepoDetailsRecord, Long> IDENTITY_REPO_DETAILS = Identities0.IDENTITY_REPO_DETAILS;
    public static final Identity<SecurityIdSeqRecord, Long> IDENTITY_SECURITY_ID_SEQ = Identities0.IDENTITY_SECURITY_ID_SEQ;
    public static final Identity<UserDetailsRecord, Long> IDENTITY_USER_DETAILS = Identities0.IDENTITY_USER_DETAILS;
    public static final Identity<UserTokensRecord, Long> IDENTITY_USER_TOKENS = Identities0.IDENTITY_USER_TOKENS;
    public static final Identity<VersionBumpersRecord, Long> IDENTITY_VERSION_BUMPERS = Identities0.IDENTITY_VERSION_BUMPERS;
    public static final Identity<WatcherRecord, Long> IDENTITY_WATCHER = Identities0.IDENTITY_WATCHER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AclClassRecord> KEY_ACL_CLASS_PRIMARY = UniqueKeys0.KEY_ACL_CLASS_PRIMARY;
    public static final UniqueKey<AclClassRecord> KEY_ACL_CLASS_UK_ACL_CLASS = UniqueKeys0.KEY_ACL_CLASS_UK_ACL_CLASS;
    public static final UniqueKey<AclEntryRecord> KEY_ACL_ENTRY_PRIMARY = UniqueKeys0.KEY_ACL_ENTRY_PRIMARY;
    public static final UniqueKey<AclEntryRecord> KEY_ACL_ENTRY_UNIQUE_ACL_ENTRY = UniqueKeys0.KEY_ACL_ENTRY_UNIQUE_ACL_ENTRY;
    public static final UniqueKey<AclObjectIdentityRecord> KEY_ACL_OBJECT_IDENTITY_PRIMARY = UniqueKeys0.KEY_ACL_OBJECT_IDENTITY_PRIMARY;
    public static final UniqueKey<AclObjectIdentityRecord> KEY_ACL_OBJECT_IDENTITY_UK_ACL_OBJECT_IDENTITY = UniqueKeys0.KEY_ACL_OBJECT_IDENTITY_UK_ACL_OBJECT_IDENTITY;
    public static final UniqueKey<AclSidRecord> KEY_ACL_SID_PRIMARY = UniqueKeys0.KEY_ACL_SID_PRIMARY;
    public static final UniqueKey<AclSidRecord> KEY_ACL_SID_UNIQUE_ACL_SID = UniqueKeys0.KEY_ACL_SID_UNIQUE_ACL_SID;
    public static final UniqueKey<CommitDetailsRecord> KEY_COMMIT_DETAILS_PRIMARY = UniqueKeys0.KEY_COMMIT_DETAILS_PRIMARY;
    public static final UniqueKey<CommitMetadataRecord> KEY_COMMIT_METADATA_PRIMARY = UniqueKeys0.KEY_COMMIT_METADATA_PRIMARY;
    public static final UniqueKey<ProjectDetailsRecord> KEY_PROJECT_DETAILS_PRIMARY = UniqueKeys0.KEY_PROJECT_DETAILS_PRIMARY;
    public static final UniqueKey<RepositoryTokensRecord> KEY_REPOSITORY_TOKENS_PRIMARY = UniqueKeys0.KEY_REPOSITORY_TOKENS_PRIMARY;
    public static final UniqueKey<RepoDetailsRecord> KEY_REPO_DETAILS_PRIMARY = UniqueKeys0.KEY_REPO_DETAILS_PRIMARY;
    public static final UniqueKey<SecurityIdSeqRecord> KEY_SECURITY_ID_SEQ_PRIMARY = UniqueKeys0.KEY_SECURITY_ID_SEQ_PRIMARY;
    public static final UniqueKey<SsUserconnectionRecord> KEY_SS_USERCONNECTION_PRIMARY = UniqueKeys0.KEY_SS_USERCONNECTION_PRIMARY;
    public static final UniqueKey<SsUserconnectionRecord> KEY_SS_USERCONNECTION_USERCONNECTIONRANK = UniqueKeys0.KEY_SS_USERCONNECTION_USERCONNECTIONRANK;
    public static final UniqueKey<UserDetailsRecord> KEY_USER_DETAILS_PRIMARY = UniqueKeys0.KEY_USER_DETAILS_PRIMARY;
    public static final UniqueKey<UserTokensRecord> KEY_USER_TOKENS_PRIMARY = UniqueKeys0.KEY_USER_TOKENS_PRIMARY;
    public static final UniqueKey<VersionBumpersRecord> KEY_VERSION_BUMPERS_PRIMARY = UniqueKeys0.KEY_VERSION_BUMPERS_PRIMARY;
    public static final UniqueKey<WatcherRecord> KEY_WATCHER_PRIMARY = UniqueKeys0.KEY_WATCHER_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AclEntryRecord, AclObjectIdentityRecord> FK_ACL_ENTRY_OBJECT = ForeignKeys0.FK_ACL_ENTRY_OBJECT;
    public static final ForeignKey<AclEntryRecord, AclSidRecord> FK_ACL_ENTRY_ACL = ForeignKeys0.FK_ACL_ENTRY_ACL;
    public static final ForeignKey<AclObjectIdentityRecord, AclClassRecord> FK_ACL_OBJECT_IDENTITY_CLASS = ForeignKeys0.FK_ACL_OBJECT_IDENTITY_CLASS;
    public static final ForeignKey<AclObjectIdentityRecord, AclObjectIdentityRecord> FK_ACL_OBJECT_IDENTITY_PARENT = ForeignKeys0.FK_ACL_OBJECT_IDENTITY_PARENT;
    public static final ForeignKey<AclObjectIdentityRecord, AclSidRecord> FK_ACL_OBJECT_IDENTITY_OWNER = ForeignKeys0.FK_ACL_OBJECT_IDENTITY_OWNER;
    public static final ForeignKey<CommitDetailsRecord, RepoDetailsRecord> COMMIT_DETAILS_IBFK_1 = ForeignKeys0.COMMIT_DETAILS_IBFK_1;
    public static final ForeignKey<CommitDetailsRecord, CommitDetailsRecord> COMMIT_DETAILS_IBFK_2 = ForeignKeys0.COMMIT_DETAILS_IBFK_2;
    public static final ForeignKey<CommitMetadataRecord, CommitDetailsRecord> COMMIT_METADATA_IBFK_1 = ForeignKeys0.COMMIT_METADATA_IBFK_1;
    public static final ForeignKey<CommitMetadataRecord, ProjectDetailsRecord> COMMIT_METADATA_IBFK_2 = ForeignKeys0.COMMIT_METADATA_IBFK_2;
    public static final ForeignKey<CommitMetadataRecord, RepoDetailsRecord> COMMIT_METADATA_IBFK_3 = ForeignKeys0.COMMIT_METADATA_IBFK_3;
    public static final ForeignKey<ProjectDetailsRecord, SecurityIdSeqRecord> PROJECT_DETAILS_IBFK_1 = ForeignKeys0.PROJECT_DETAILS_IBFK_1;
    public static final ForeignKey<RepositoryTokensRecord, RepoDetailsRecord> REPOSITORY_TOKENS_IBFK_1 = ForeignKeys0.REPOSITORY_TOKENS_IBFK_1;
    public static final ForeignKey<RepoDetailsRecord, ProjectDetailsRecord> REPO_DETAILS_IBFK_1 = ForeignKeys0.REPO_DETAILS_IBFK_1;
    public static final ForeignKey<RepoDetailsRecord, VersionBumpersRecord> REPO_DETAILS_IBFK_2 = ForeignKeys0.REPO_DETAILS_IBFK_2;
    public static final ForeignKey<RepoDetailsRecord, SecurityIdSeqRecord> REPO_DETAILS_IBFK_3 = ForeignKeys0.REPO_DETAILS_IBFK_3;
    public static final ForeignKey<UserTokensRecord, UserDetailsRecord> USER_TOKENS_IBFK_1 = ForeignKeys0.USER_TOKENS_IBFK_1;
    public static final ForeignKey<WatcherRecord, UserDetailsRecord> WATCHER_IBFK_1 = ForeignKeys0.WATCHER_IBFK_1;
    public static final ForeignKey<WatcherRecord, ProjectDetailsRecord> WATCHER_IBFK_2 = ForeignKeys0.WATCHER_IBFK_2;
    public static final ForeignKey<WatcherRecord, RepoDetailsRecord> WATCHER_IBFK_3 = ForeignKeys0.WATCHER_IBFK_3;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<AclClassRecord, ULong> IDENTITY_ACL_CLASS = createIdentity(AclClassTable.ACL_CLASS, AclClassTable.ACL_CLASS.ID);
        public static Identity<AclEntryRecord, ULong> IDENTITY_ACL_ENTRY = createIdentity(AclEntryTable.ACL_ENTRY, AclEntryTable.ACL_ENTRY.ID);
        public static Identity<AclObjectIdentityRecord, ULong> IDENTITY_ACL_OBJECT_IDENTITY = createIdentity(AclObjectIdentityTable.ACL_OBJECT_IDENTITY, AclObjectIdentityTable.ACL_OBJECT_IDENTITY.ID);
        public static Identity<AclSidRecord, ULong> IDENTITY_ACL_SID = createIdentity(AclSidTable.ACL_SID, AclSidTable.ACL_SID.ID);
        public static Identity<CommitDetailsRecord, Long> IDENTITY_COMMIT_DETAILS = createIdentity(CommitDetailsTable.COMMIT_DETAILS, CommitDetailsTable.COMMIT_DETAILS.COMMIT_DETAILS_ID);
        public static Identity<CommitMetadataRecord, Long> IDENTITY_COMMIT_METADATA = createIdentity(CommitMetadataTable.COMMIT_METADATA, CommitMetadataTable.COMMIT_METADATA.ID);
        public static Identity<ProjectDetailsRecord, Long> IDENTITY_PROJECT_DETAILS = createIdentity(ProjectDetailsTable.PROJECT_DETAILS, ProjectDetailsTable.PROJECT_DETAILS.PRODUCT_DETAILS_ID);
        public static Identity<RepositoryTokensRecord, Long> IDENTITY_REPOSITORY_TOKENS = createIdentity(RepositoryTokensTable.REPOSITORY_TOKENS, RepositoryTokensTable.REPOSITORY_TOKENS.ID);
        public static Identity<RepoDetailsRecord, Long> IDENTITY_REPO_DETAILS = createIdentity(RepoDetailsTable.REPO_DETAILS, RepoDetailsTable.REPO_DETAILS.REPO_DETAILS_ID);
        public static Identity<SecurityIdSeqRecord, Long> IDENTITY_SECURITY_ID_SEQ = createIdentity(SecurityIdSeqTable.SECURITY_ID_SEQ, SecurityIdSeqTable.SECURITY_ID_SEQ.SECURITY_ID);
        public static Identity<UserDetailsRecord, Long> IDENTITY_USER_DETAILS = createIdentity(UserDetailsTable.USER_DETAILS, UserDetailsTable.USER_DETAILS.USER_ID);
        public static Identity<UserTokensRecord, Long> IDENTITY_USER_TOKENS = createIdentity(UserTokensTable.USER_TOKENS, UserTokensTable.USER_TOKENS.ID);
        public static Identity<VersionBumpersRecord, Long> IDENTITY_VERSION_BUMPERS = createIdentity(VersionBumpersTable.VERSION_BUMPERS, VersionBumpersTable.VERSION_BUMPERS.VERSION_BUMPER_ID);
        public static Identity<WatcherRecord, Long> IDENTITY_WATCHER = createIdentity(WatcherTable.WATCHER, WatcherTable.WATCHER.WATCHER_ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<AclClassRecord> KEY_ACL_CLASS_PRIMARY = createUniqueKey(AclClassTable.ACL_CLASS, "KEY_acl_class_PRIMARY", AclClassTable.ACL_CLASS.ID);
        public static final UniqueKey<AclClassRecord> KEY_ACL_CLASS_UK_ACL_CLASS = createUniqueKey(AclClassTable.ACL_CLASS, "KEY_acl_class_uk_acl_class", AclClassTable.ACL_CLASS.CLASS);
        public static final UniqueKey<AclEntryRecord> KEY_ACL_ENTRY_PRIMARY = createUniqueKey(AclEntryTable.ACL_ENTRY, "KEY_acl_entry_PRIMARY", AclEntryTable.ACL_ENTRY.ID);
        public static final UniqueKey<AclEntryRecord> KEY_ACL_ENTRY_UNIQUE_ACL_ENTRY = createUniqueKey(AclEntryTable.ACL_ENTRY, "KEY_acl_entry_unique_acl_entry", AclEntryTable.ACL_ENTRY.ACL_OBJECT_IDENTITY, AclEntryTable.ACL_ENTRY.ACE_ORDER);
        public static final UniqueKey<AclObjectIdentityRecord> KEY_ACL_OBJECT_IDENTITY_PRIMARY = createUniqueKey(AclObjectIdentityTable.ACL_OBJECT_IDENTITY, "KEY_acl_object_identity_PRIMARY", AclObjectIdentityTable.ACL_OBJECT_IDENTITY.ID);
        public static final UniqueKey<AclObjectIdentityRecord> KEY_ACL_OBJECT_IDENTITY_UK_ACL_OBJECT_IDENTITY = createUniqueKey(AclObjectIdentityTable.ACL_OBJECT_IDENTITY, "KEY_acl_object_identity_uk_acl_object_identity", AclObjectIdentityTable.ACL_OBJECT_IDENTITY.OBJECT_ID_CLASS, AclObjectIdentityTable.ACL_OBJECT_IDENTITY.OBJECT_ID_IDENTITY);
        public static final UniqueKey<AclSidRecord> KEY_ACL_SID_PRIMARY = createUniqueKey(AclSidTable.ACL_SID, "KEY_acl_sid_PRIMARY", AclSidTable.ACL_SID.ID);
        public static final UniqueKey<AclSidRecord> KEY_ACL_SID_UNIQUE_ACL_SID = createUniqueKey(AclSidTable.ACL_SID, "KEY_acl_sid_unique_acl_sid", AclSidTable.ACL_SID.SID, AclSidTable.ACL_SID.PRINCIPAL);
        public static final UniqueKey<CommitDetailsRecord> KEY_COMMIT_DETAILS_PRIMARY = createUniqueKey(CommitDetailsTable.COMMIT_DETAILS, "KEY_commit_details_PRIMARY", CommitDetailsTable.COMMIT_DETAILS.COMMIT_DETAILS_ID);
        public static final UniqueKey<CommitMetadataRecord> KEY_COMMIT_METADATA_PRIMARY = createUniqueKey(CommitMetadataTable.COMMIT_METADATA, "KEY_commit_metadata_PRIMARY", CommitMetadataTable.COMMIT_METADATA.ID);
        public static final UniqueKey<ProjectDetailsRecord> KEY_PROJECT_DETAILS_PRIMARY = createUniqueKey(ProjectDetailsTable.PROJECT_DETAILS, "KEY_project_details_PRIMARY", ProjectDetailsTable.PROJECT_DETAILS.PRODUCT_DETAILS_ID);
        public static final UniqueKey<RepositoryTokensRecord> KEY_REPOSITORY_TOKENS_PRIMARY = createUniqueKey(RepositoryTokensTable.REPOSITORY_TOKENS, "KEY_repository_tokens_PRIMARY", RepositoryTokensTable.REPOSITORY_TOKENS.ID);
        public static final UniqueKey<RepoDetailsRecord> KEY_REPO_DETAILS_PRIMARY = createUniqueKey(RepoDetailsTable.REPO_DETAILS, "KEY_repo_details_PRIMARY", RepoDetailsTable.REPO_DETAILS.REPO_DETAILS_ID);
        public static final UniqueKey<SecurityIdSeqRecord> KEY_SECURITY_ID_SEQ_PRIMARY = createUniqueKey(SecurityIdSeqTable.SECURITY_ID_SEQ, "KEY_security_id_seq_PRIMARY", SecurityIdSeqTable.SECURITY_ID_SEQ.SECURITY_ID);
        public static final UniqueKey<SsUserconnectionRecord> KEY_SS_USERCONNECTION_PRIMARY = createUniqueKey(SsUserconnectionTable.SS_USERCONNECTION, "KEY_ss_UserConnection_PRIMARY", SsUserconnectionTable.SS_USERCONNECTION.USERID, SsUserconnectionTable.SS_USERCONNECTION.PROVIDERID, SsUserconnectionTable.SS_USERCONNECTION.PROVIDERUSERID);
        public static final UniqueKey<SsUserconnectionRecord> KEY_SS_USERCONNECTION_USERCONNECTIONRANK = createUniqueKey(SsUserconnectionTable.SS_USERCONNECTION, "KEY_ss_UserConnection_UserConnectionRank", SsUserconnectionTable.SS_USERCONNECTION.USERID, SsUserconnectionTable.SS_USERCONNECTION.PROVIDERID, SsUserconnectionTable.SS_USERCONNECTION.RANK);
        public static final UniqueKey<UserDetailsRecord> KEY_USER_DETAILS_PRIMARY = createUniqueKey(UserDetailsTable.USER_DETAILS, "KEY_user_details_PRIMARY", UserDetailsTable.USER_DETAILS.USER_ID);
        public static final UniqueKey<UserTokensRecord> KEY_USER_TOKENS_PRIMARY = createUniqueKey(UserTokensTable.USER_TOKENS, "KEY_user_tokens_PRIMARY", UserTokensTable.USER_TOKENS.ID);
        public static final UniqueKey<VersionBumpersRecord> KEY_VERSION_BUMPERS_PRIMARY = createUniqueKey(VersionBumpersTable.VERSION_BUMPERS, "KEY_version_bumpers_PRIMARY", VersionBumpersTable.VERSION_BUMPERS.VERSION_BUMPER_ID);
        public static final UniqueKey<WatcherRecord> KEY_WATCHER_PRIMARY = createUniqueKey(WatcherTable.WATCHER, "KEY_watcher_PRIMARY", WatcherTable.WATCHER.WATCHER_ID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<AclEntryRecord, AclObjectIdentityRecord> FK_ACL_ENTRY_OBJECT = createForeignKey(tech.crom.db.Keys.KEY_ACL_OBJECT_IDENTITY_PRIMARY, AclEntryTable.ACL_ENTRY, "fk_acl_entry_object", AclEntryTable.ACL_ENTRY.ACL_OBJECT_IDENTITY);
        public static final ForeignKey<AclEntryRecord, AclSidRecord> FK_ACL_ENTRY_ACL = createForeignKey(tech.crom.db.Keys.KEY_ACL_SID_PRIMARY, AclEntryTable.ACL_ENTRY, "fk_acl_entry_acl", AclEntryTable.ACL_ENTRY.SID);
        public static final ForeignKey<AclObjectIdentityRecord, AclClassRecord> FK_ACL_OBJECT_IDENTITY_CLASS = createForeignKey(tech.crom.db.Keys.KEY_ACL_CLASS_PRIMARY, AclObjectIdentityTable.ACL_OBJECT_IDENTITY, "fk_acl_object_identity_class", AclObjectIdentityTable.ACL_OBJECT_IDENTITY.OBJECT_ID_CLASS);
        public static final ForeignKey<AclObjectIdentityRecord, AclObjectIdentityRecord> FK_ACL_OBJECT_IDENTITY_PARENT = createForeignKey(tech.crom.db.Keys.KEY_ACL_OBJECT_IDENTITY_PRIMARY, AclObjectIdentityTable.ACL_OBJECT_IDENTITY, "fk_acl_object_identity_parent", AclObjectIdentityTable.ACL_OBJECT_IDENTITY.PARENT_OBJECT);
        public static final ForeignKey<AclObjectIdentityRecord, AclSidRecord> FK_ACL_OBJECT_IDENTITY_OWNER = createForeignKey(tech.crom.db.Keys.KEY_ACL_SID_PRIMARY, AclObjectIdentityTable.ACL_OBJECT_IDENTITY, "fk_acl_object_identity_owner", AclObjectIdentityTable.ACL_OBJECT_IDENTITY.OWNER_SID);
        public static final ForeignKey<CommitDetailsRecord, RepoDetailsRecord> COMMIT_DETAILS_IBFK_1 = createForeignKey(tech.crom.db.Keys.KEY_REPO_DETAILS_PRIMARY, CommitDetailsTable.COMMIT_DETAILS, "commit_details_ibfk_1", CommitDetailsTable.COMMIT_DETAILS.REPO_DETAILS_ID);
        public static final ForeignKey<CommitDetailsRecord, CommitDetailsRecord> COMMIT_DETAILS_IBFK_2 = createForeignKey(tech.crom.db.Keys.KEY_COMMIT_DETAILS_PRIMARY, CommitDetailsTable.COMMIT_DETAILS, "commit_details_ibfk_2", CommitDetailsTable.COMMIT_DETAILS.PARENT_COMMIT_ID);
        public static final ForeignKey<CommitMetadataRecord, CommitDetailsRecord> COMMIT_METADATA_IBFK_1 = createForeignKey(tech.crom.db.Keys.KEY_COMMIT_DETAILS_PRIMARY, CommitMetadataTable.COMMIT_METADATA, "commit_metadata_ibfk_1", CommitMetadataTable.COMMIT_METADATA.COMMIT_ID);
        public static final ForeignKey<CommitMetadataRecord, ProjectDetailsRecord> COMMIT_METADATA_IBFK_2 = createForeignKey(tech.crom.db.Keys.KEY_PROJECT_DETAILS_PRIMARY, CommitMetadataTable.COMMIT_METADATA, "commit_metadata_ibfk_2", CommitMetadataTable.COMMIT_METADATA.PROJECT_ID);
        public static final ForeignKey<CommitMetadataRecord, RepoDetailsRecord> COMMIT_METADATA_IBFK_3 = createForeignKey(tech.crom.db.Keys.KEY_REPO_DETAILS_PRIMARY, CommitMetadataTable.COMMIT_METADATA, "commit_metadata_ibfk_3", CommitMetadataTable.COMMIT_METADATA.REPO_ID);
        public static final ForeignKey<ProjectDetailsRecord, SecurityIdSeqRecord> PROJECT_DETAILS_IBFK_1 = createForeignKey(tech.crom.db.Keys.KEY_SECURITY_ID_SEQ_PRIMARY, ProjectDetailsTable.PROJECT_DETAILS, "project_details_ibfk_1", ProjectDetailsTable.PROJECT_DETAILS.SECURITY_ID);
        public static final ForeignKey<RepositoryTokensRecord, RepoDetailsRecord> REPOSITORY_TOKENS_IBFK_1 = createForeignKey(tech.crom.db.Keys.KEY_REPO_DETAILS_PRIMARY, RepositoryTokensTable.REPOSITORY_TOKENS, "repository_tokens_ibfk_1", RepositoryTokensTable.REPOSITORY_TOKENS.REPO_ID);
        public static final ForeignKey<RepoDetailsRecord, ProjectDetailsRecord> REPO_DETAILS_IBFK_1 = createForeignKey(tech.crom.db.Keys.KEY_PROJECT_DETAILS_PRIMARY, RepoDetailsTable.REPO_DETAILS, "repo_details_ibfk_1", RepoDetailsTable.REPO_DETAILS.PROJECT_ID);
        public static final ForeignKey<RepoDetailsRecord, VersionBumpersRecord> REPO_DETAILS_IBFK_2 = createForeignKey(tech.crom.db.Keys.KEY_VERSION_BUMPERS_PRIMARY, RepoDetailsTable.REPO_DETAILS, "repo_details_ibfk_2", RepoDetailsTable.REPO_DETAILS.VERSION_BUMPER_ID);
        public static final ForeignKey<RepoDetailsRecord, SecurityIdSeqRecord> REPO_DETAILS_IBFK_3 = createForeignKey(tech.crom.db.Keys.KEY_SECURITY_ID_SEQ_PRIMARY, RepoDetailsTable.REPO_DETAILS, "repo_details_ibfk_3", RepoDetailsTable.REPO_DETAILS.SECURITY_ID);
        public static final ForeignKey<UserTokensRecord, UserDetailsRecord> USER_TOKENS_IBFK_1 = createForeignKey(tech.crom.db.Keys.KEY_USER_DETAILS_PRIMARY, UserTokensTable.USER_TOKENS, "user_tokens_ibfk_1", UserTokensTable.USER_TOKENS.USER_ID);
        public static final ForeignKey<WatcherRecord, UserDetailsRecord> WATCHER_IBFK_1 = createForeignKey(tech.crom.db.Keys.KEY_USER_DETAILS_PRIMARY, WatcherTable.WATCHER, "watcher_ibfk_1", WatcherTable.WATCHER.USER_ID);
        public static final ForeignKey<WatcherRecord, ProjectDetailsRecord> WATCHER_IBFK_2 = createForeignKey(tech.crom.db.Keys.KEY_PROJECT_DETAILS_PRIMARY, WatcherTable.WATCHER, "watcher_ibfk_2", WatcherTable.WATCHER.PROJECT_DETAILS_ID);
        public static final ForeignKey<WatcherRecord, RepoDetailsRecord> WATCHER_IBFK_3 = createForeignKey(tech.crom.db.Keys.KEY_REPO_DETAILS_PRIMARY, WatcherTable.WATCHER, "watcher_ibfk_3", WatcherTable.WATCHER.REPO_DETAILS_ID);
    }
}
