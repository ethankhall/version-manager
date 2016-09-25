/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db;


import io.ehdev.conrad.db.tables.AclClassTable;
import io.ehdev.conrad.db.tables.AclEntryTable;
import io.ehdev.conrad.db.tables.AclObjectIdentityTable;
import io.ehdev.conrad.db.tables.AclSidTable;
import io.ehdev.conrad.db.tables.CommitDetailsTable;
import io.ehdev.conrad.db.tables.CommitMetadataTable;
import io.ehdev.conrad.db.tables.ProjectDetailsTable;
import io.ehdev.conrad.db.tables.RepoDetailsTable;
import io.ehdev.conrad.db.tables.RepositoryTokensTable;
import io.ehdev.conrad.db.tables.SsUserconnectionTable;
import io.ehdev.conrad.db.tables.UserDetailsTable;
import io.ehdev.conrad.db.tables.UserTokensTable;
import io.ehdev.conrad.db.tables.VersionBumpersTable;
import io.ehdev.conrad.db.tables.WatcherTable;
import io.ehdev.conrad.db.tables.records.AclClassRecord;
import io.ehdev.conrad.db.tables.records.AclEntryRecord;
import io.ehdev.conrad.db.tables.records.AclObjectIdentityRecord;
import io.ehdev.conrad.db.tables.records.AclSidRecord;
import io.ehdev.conrad.db.tables.records.CommitDetailsRecord;
import io.ehdev.conrad.db.tables.records.CommitMetadataRecord;
import io.ehdev.conrad.db.tables.records.ProjectDetailsRecord;
import io.ehdev.conrad.db.tables.records.RepoDetailsRecord;
import io.ehdev.conrad.db.tables.records.RepositoryTokensRecord;
import io.ehdev.conrad.db.tables.records.SsUserconnectionRecord;
import io.ehdev.conrad.db.tables.records.UserDetailsRecord;
import io.ehdev.conrad.db.tables.records.UserTokensRecord;
import io.ehdev.conrad.db.tables.records.VersionBumpersRecord;
import io.ehdev.conrad.db.tables.records.WatcherRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>public</code> 
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

    public static final Identity<AclClassRecord, Long> IDENTITY_ACL_CLASS = Identities0.IDENTITY_ACL_CLASS;
    public static final Identity<AclEntryRecord, Long> IDENTITY_ACL_ENTRY = Identities0.IDENTITY_ACL_ENTRY;
    public static final Identity<AclObjectIdentityRecord, Long> IDENTITY_ACL_OBJECT_IDENTITY = Identities0.IDENTITY_ACL_OBJECT_IDENTITY;
    public static final Identity<AclSidRecord, Long> IDENTITY_ACL_SID = Identities0.IDENTITY_ACL_SID;
    public static final Identity<ProjectDetailsRecord, Long> IDENTITY_PROJECT_DETAILS = Identities0.IDENTITY_PROJECT_DETAILS;
    public static final Identity<RepoDetailsRecord, Long> IDENTITY_REPO_DETAILS = Identities0.IDENTITY_REPO_DETAILS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AclClassRecord> ACL_CLASS_PKEY = UniqueKeys0.ACL_CLASS_PKEY;
    public static final UniqueKey<AclClassRecord> UNIQUE_UK_2 = UniqueKeys0.UNIQUE_UK_2;
    public static final UniqueKey<AclEntryRecord> ACL_ENTRY_PKEY = UniqueKeys0.ACL_ENTRY_PKEY;
    public static final UniqueKey<AclEntryRecord> UNIQUE_UK_4 = UniqueKeys0.UNIQUE_UK_4;
    public static final UniqueKey<AclObjectIdentityRecord> ACL_OBJECT_IDENTITY_PKEY = UniqueKeys0.ACL_OBJECT_IDENTITY_PKEY;
    public static final UniqueKey<AclObjectIdentityRecord> UNIQUE_UK_3 = UniqueKeys0.UNIQUE_UK_3;
    public static final UniqueKey<AclSidRecord> ACL_SID_PKEY = UniqueKeys0.ACL_SID_PKEY;
    public static final UniqueKey<AclSidRecord> UNIQUE_UK_1 = UniqueKeys0.UNIQUE_UK_1;
    public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_PKEY = UniqueKeys0.COMMIT_DETAILS_PKEY;
    public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_COMMIT_ID_REPO_DETAILS_UUID_KEY = UniqueKeys0.COMMIT_DETAILS_COMMIT_ID_REPO_DETAILS_UUID_KEY;
    public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_VERSION_REPO_DETAILS_UUID_KEY = UniqueKeys0.COMMIT_DETAILS_VERSION_REPO_DETAILS_UUID_KEY;
    public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_PKEY = UniqueKeys0.COMMIT_METADATA_PKEY;
    public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_COMMIT_UUID_NAME_KEY = UniqueKeys0.COMMIT_METADATA_COMMIT_UUID_NAME_KEY;
    public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_COMMIT_UUID_PROJECT_UUID_REPO_UUID_KEY = UniqueKeys0.COMMIT_METADATA_COMMIT_UUID_PROJECT_UUID_REPO_UUID_KEY;
    public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PKEY = UniqueKeys0.PROJECT_DETAILS_PKEY;
    public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PROJECT_NAME_KEY = UniqueKeys0.PROJECT_DETAILS_PROJECT_NAME_KEY;
    public static final UniqueKey<RepoDetailsRecord> REPO_DETAILS_PKEY = UniqueKeys0.REPO_DETAILS_PKEY;
    public static final UniqueKey<RepositoryTokensRecord> REPOSITORY_TOKENS_PKEY = UniqueKeys0.REPOSITORY_TOKENS_PKEY;
    public static final UniqueKey<SsUserconnectionRecord> SS_USERCONNECTION_PKEY = UniqueKeys0.SS_USERCONNECTION_PKEY;
    public static final UniqueKey<UserDetailsRecord> USER_DETAILS_PKEY = UniqueKeys0.USER_DETAILS_PKEY;
    public static final UniqueKey<UserDetailsRecord> USER_DETAILS_USER_NAME_KEY = UniqueKeys0.USER_DETAILS_USER_NAME_KEY;
    public static final UniqueKey<UserTokensRecord> USER_TOKENS_PKEY = UniqueKeys0.USER_TOKENS_PKEY;
    public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_PKEY = UniqueKeys0.VERSION_BUMPERS_PKEY;
    public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_BUMPER_NAME_KEY = UniqueKeys0.VERSION_BUMPERS_BUMPER_NAME_KEY;
    public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_CLASS_NAME_KEY = UniqueKeys0.VERSION_BUMPERS_CLASS_NAME_KEY;
    public static final UniqueKey<WatcherRecord> WATCHER_PKEY = UniqueKeys0.WATCHER_PKEY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AclEntryRecord, AclObjectIdentityRecord> ACL_ENTRY__FOREIGN_FK_4 = ForeignKeys0.ACL_ENTRY__FOREIGN_FK_4;
    public static final ForeignKey<AclEntryRecord, AclSidRecord> ACL_ENTRY__FOREIGN_FK_5 = ForeignKeys0.ACL_ENTRY__FOREIGN_FK_5;
    public static final ForeignKey<AclObjectIdentityRecord, AclClassRecord> ACL_OBJECT_IDENTITY__FOREIGN_FK_2 = ForeignKeys0.ACL_OBJECT_IDENTITY__FOREIGN_FK_2;
    public static final ForeignKey<AclObjectIdentityRecord, AclObjectIdentityRecord> ACL_OBJECT_IDENTITY__FOREIGN_FK_1 = ForeignKeys0.ACL_OBJECT_IDENTITY__FOREIGN_FK_1;
    public static final ForeignKey<AclObjectIdentityRecord, AclSidRecord> ACL_OBJECT_IDENTITY__FOREIGN_FK_3 = ForeignKeys0.ACL_OBJECT_IDENTITY__FOREIGN_FK_3;
    public static final ForeignKey<CommitDetailsRecord, RepoDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_REPO_DETAILS_UUID_FKEY = ForeignKeys0.COMMIT_DETAILS__COMMIT_DETAILS_REPO_DETAILS_UUID_FKEY;
    public static final ForeignKey<CommitDetailsRecord, CommitDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_PARENT_COMMIT_UUID_FKEY = ForeignKeys0.COMMIT_DETAILS__COMMIT_DETAILS_PARENT_COMMIT_UUID_FKEY;
    public static final ForeignKey<CommitMetadataRecord, CommitDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_COMMIT_UUID_FKEY = ForeignKeys0.COMMIT_METADATA__COMMIT_METADATA_COMMIT_UUID_FKEY;
    public static final ForeignKey<CommitMetadataRecord, ProjectDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_PROJECT_UUID_FKEY = ForeignKeys0.COMMIT_METADATA__COMMIT_METADATA_PROJECT_UUID_FKEY;
    public static final ForeignKey<CommitMetadataRecord, RepoDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_REPO_UUID_FKEY = ForeignKeys0.COMMIT_METADATA__COMMIT_METADATA_REPO_UUID_FKEY;
    public static final ForeignKey<RepoDetailsRecord, ProjectDetailsRecord> REPO_DETAILS__REPO_DETAILS_PROJECT_UUID_FKEY = ForeignKeys0.REPO_DETAILS__REPO_DETAILS_PROJECT_UUID_FKEY;
    public static final ForeignKey<RepoDetailsRecord, VersionBumpersRecord> REPO_DETAILS__REPO_DETAILS_VERSION_BUMPER_UUID_FKEY = ForeignKeys0.REPO_DETAILS__REPO_DETAILS_VERSION_BUMPER_UUID_FKEY;
    public static final ForeignKey<RepositoryTokensRecord, RepoDetailsRecord> REPOSITORY_TOKENS__REPOSITORY_TOKENS_REPO_UUID_FKEY = ForeignKeys0.REPOSITORY_TOKENS__REPOSITORY_TOKENS_REPO_UUID_FKEY;
    public static final ForeignKey<UserTokensRecord, UserDetailsRecord> USER_TOKENS__USER_TOKENS_USER_UUID_FKEY = ForeignKeys0.USER_TOKENS__USER_TOKENS_USER_UUID_FKEY;
    public static final ForeignKey<WatcherRecord, UserDetailsRecord> WATCHER__WATCHER_USER_UUID_FKEY = ForeignKeys0.WATCHER__WATCHER_USER_UUID_FKEY;
    public static final ForeignKey<WatcherRecord, ProjectDetailsRecord> WATCHER__WATCHER_PROJECT_DETAILS_UUID_FKEY = ForeignKeys0.WATCHER__WATCHER_PROJECT_DETAILS_UUID_FKEY;
    public static final ForeignKey<WatcherRecord, RepoDetailsRecord> WATCHER__WATCHER_REPO_DETAILS_UUID_FKEY = ForeignKeys0.WATCHER__WATCHER_REPO_DETAILS_UUID_FKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<AclClassRecord, Long> IDENTITY_ACL_CLASS = createIdentity(AclClassTable.ACL_CLASS, AclClassTable.ACL_CLASS.ID);
        public static Identity<AclEntryRecord, Long> IDENTITY_ACL_ENTRY = createIdentity(AclEntryTable.ACL_ENTRY, AclEntryTable.ACL_ENTRY.ID);
        public static Identity<AclObjectIdentityRecord, Long> IDENTITY_ACL_OBJECT_IDENTITY = createIdentity(AclObjectIdentityTable.ACL_OBJECT_IDENTITY, AclObjectIdentityTable.ACL_OBJECT_IDENTITY.ID);
        public static Identity<AclSidRecord, Long> IDENTITY_ACL_SID = createIdentity(AclSidTable.ACL_SID, AclSidTable.ACL_SID.ID);
        public static Identity<ProjectDetailsRecord, Long> IDENTITY_PROJECT_DETAILS = createIdentity(ProjectDetailsTable.PROJECT_DETAILS, ProjectDetailsTable.PROJECT_DETAILS.SECURITY_ID);
        public static Identity<RepoDetailsRecord, Long> IDENTITY_REPO_DETAILS = createIdentity(RepoDetailsTable.REPO_DETAILS, RepoDetailsTable.REPO_DETAILS.SECURITY_ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<AclClassRecord> ACL_CLASS_PKEY = createUniqueKey(AclClassTable.ACL_CLASS, "acl_class_pkey", AclClassTable.ACL_CLASS.ID);
        public static final UniqueKey<AclClassRecord> UNIQUE_UK_2 = createUniqueKey(AclClassTable.ACL_CLASS, "unique_uk_2", AclClassTable.ACL_CLASS.CLASS);
        public static final UniqueKey<AclEntryRecord> ACL_ENTRY_PKEY = createUniqueKey(AclEntryTable.ACL_ENTRY, "acl_entry_pkey", AclEntryTable.ACL_ENTRY.ID);
        public static final UniqueKey<AclEntryRecord> UNIQUE_UK_4 = createUniqueKey(AclEntryTable.ACL_ENTRY, "unique_uk_4", AclEntryTable.ACL_ENTRY.ACL_OBJECT_IDENTITY, AclEntryTable.ACL_ENTRY.ACE_ORDER);
        public static final UniqueKey<AclObjectIdentityRecord> ACL_OBJECT_IDENTITY_PKEY = createUniqueKey(AclObjectIdentityTable.ACL_OBJECT_IDENTITY, "acl_object_identity_pkey", AclObjectIdentityTable.ACL_OBJECT_IDENTITY.ID);
        public static final UniqueKey<AclObjectIdentityRecord> UNIQUE_UK_3 = createUniqueKey(AclObjectIdentityTable.ACL_OBJECT_IDENTITY, "unique_uk_3", AclObjectIdentityTable.ACL_OBJECT_IDENTITY.OBJECT_ID_CLASS, AclObjectIdentityTable.ACL_OBJECT_IDENTITY.OBJECT_ID_IDENTITY);
        public static final UniqueKey<AclSidRecord> ACL_SID_PKEY = createUniqueKey(AclSidTable.ACL_SID, "acl_sid_pkey", AclSidTable.ACL_SID.ID);
        public static final UniqueKey<AclSidRecord> UNIQUE_UK_1 = createUniqueKey(AclSidTable.ACL_SID, "unique_uk_1", AclSidTable.ACL_SID.SID, AclSidTable.ACL_SID.PRINCIPAL);
        public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_PKEY = createUniqueKey(CommitDetailsTable.COMMIT_DETAILS, "commit_details_pkey", CommitDetailsTable.COMMIT_DETAILS.UUID);
        public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_COMMIT_ID_REPO_DETAILS_UUID_KEY = createUniqueKey(CommitDetailsTable.COMMIT_DETAILS, "commit_details_commit_id_repo_details_uuid_key", CommitDetailsTable.COMMIT_DETAILS.COMMIT_ID, CommitDetailsTable.COMMIT_DETAILS.REPO_DETAILS_UUID);
        public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_VERSION_REPO_DETAILS_UUID_KEY = createUniqueKey(CommitDetailsTable.COMMIT_DETAILS, "commit_details_version_repo_details_uuid_key", CommitDetailsTable.COMMIT_DETAILS.VERSION, CommitDetailsTable.COMMIT_DETAILS.REPO_DETAILS_UUID);
        public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_PKEY = createUniqueKey(CommitMetadataTable.COMMIT_METADATA, "commit_metadata_pkey", CommitMetadataTable.COMMIT_METADATA.UUID);
        public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_COMMIT_UUID_NAME_KEY = createUniqueKey(CommitMetadataTable.COMMIT_METADATA, "commit_metadata_commit_uuid_name_key", CommitMetadataTable.COMMIT_METADATA.COMMIT_UUID, CommitMetadataTable.COMMIT_METADATA.NAME);
        public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_COMMIT_UUID_PROJECT_UUID_REPO_UUID_KEY = createUniqueKey(CommitMetadataTable.COMMIT_METADATA, "commit_metadata_commit_uuid_project_uuid_repo_uuid_key", CommitMetadataTable.COMMIT_METADATA.COMMIT_UUID, CommitMetadataTable.COMMIT_METADATA.PROJECT_UUID, CommitMetadataTable.COMMIT_METADATA.REPO_UUID);
        public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PKEY = createUniqueKey(ProjectDetailsTable.PROJECT_DETAILS, "project_details_pkey", ProjectDetailsTable.PROJECT_DETAILS.UUID);
        public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PROJECT_NAME_KEY = createUniqueKey(ProjectDetailsTable.PROJECT_DETAILS, "project_details_project_name_key", ProjectDetailsTable.PROJECT_DETAILS.PROJECT_NAME);
        public static final UniqueKey<RepoDetailsRecord> REPO_DETAILS_PKEY = createUniqueKey(RepoDetailsTable.REPO_DETAILS, "repo_details_pkey", RepoDetailsTable.REPO_DETAILS.UUID);
        public static final UniqueKey<RepositoryTokensRecord> REPOSITORY_TOKENS_PKEY = createUniqueKey(RepositoryTokensTable.REPOSITORY_TOKENS, "repository_tokens_pkey", RepositoryTokensTable.REPOSITORY_TOKENS.UUID);
        public static final UniqueKey<SsUserconnectionRecord> SS_USERCONNECTION_PKEY = createUniqueKey(SsUserconnectionTable.SS_USERCONNECTION, "ss_userconnection_pkey", SsUserconnectionTable.SS_USERCONNECTION.USERID, SsUserconnectionTable.SS_USERCONNECTION.PROVIDERID, SsUserconnectionTable.SS_USERCONNECTION.PROVIDERUSERID);
        public static final UniqueKey<UserDetailsRecord> USER_DETAILS_PKEY = createUniqueKey(UserDetailsTable.USER_DETAILS, "user_details_pkey", UserDetailsTable.USER_DETAILS.UUID);
        public static final UniqueKey<UserDetailsRecord> USER_DETAILS_USER_NAME_KEY = createUniqueKey(UserDetailsTable.USER_DETAILS, "user_details_user_name_key", UserDetailsTable.USER_DETAILS.USER_NAME);
        public static final UniqueKey<UserTokensRecord> USER_TOKENS_PKEY = createUniqueKey(UserTokensTable.USER_TOKENS, "user_tokens_pkey", UserTokensTable.USER_TOKENS.UUID);
        public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_PKEY = createUniqueKey(VersionBumpersTable.VERSION_BUMPERS, "version_bumpers_pkey", VersionBumpersTable.VERSION_BUMPERS.UUID);
        public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_BUMPER_NAME_KEY = createUniqueKey(VersionBumpersTable.VERSION_BUMPERS, "version_bumpers_bumper_name_key", VersionBumpersTable.VERSION_BUMPERS.BUMPER_NAME);
        public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_CLASS_NAME_KEY = createUniqueKey(VersionBumpersTable.VERSION_BUMPERS, "version_bumpers_class_name_key", VersionBumpersTable.VERSION_BUMPERS.CLASS_NAME);
        public static final UniqueKey<WatcherRecord> WATCHER_PKEY = createUniqueKey(WatcherTable.WATCHER, "watcher_pkey", WatcherTable.WATCHER.UUID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<AclEntryRecord, AclObjectIdentityRecord> ACL_ENTRY__FOREIGN_FK_4 = createForeignKey(io.ehdev.conrad.db.Keys.ACL_OBJECT_IDENTITY_PKEY, AclEntryTable.ACL_ENTRY, "acl_entry__foreign_fk_4", AclEntryTable.ACL_ENTRY.ACL_OBJECT_IDENTITY);
        public static final ForeignKey<AclEntryRecord, AclSidRecord> ACL_ENTRY__FOREIGN_FK_5 = createForeignKey(io.ehdev.conrad.db.Keys.ACL_SID_PKEY, AclEntryTable.ACL_ENTRY, "acl_entry__foreign_fk_5", AclEntryTable.ACL_ENTRY.SID);
        public static final ForeignKey<AclObjectIdentityRecord, AclClassRecord> ACL_OBJECT_IDENTITY__FOREIGN_FK_2 = createForeignKey(io.ehdev.conrad.db.Keys.ACL_CLASS_PKEY, AclObjectIdentityTable.ACL_OBJECT_IDENTITY, "acl_object_identity__foreign_fk_2", AclObjectIdentityTable.ACL_OBJECT_IDENTITY.OBJECT_ID_CLASS);
        public static final ForeignKey<AclObjectIdentityRecord, AclObjectIdentityRecord> ACL_OBJECT_IDENTITY__FOREIGN_FK_1 = createForeignKey(io.ehdev.conrad.db.Keys.ACL_OBJECT_IDENTITY_PKEY, AclObjectIdentityTable.ACL_OBJECT_IDENTITY, "acl_object_identity__foreign_fk_1", AclObjectIdentityTable.ACL_OBJECT_IDENTITY.PARENT_OBJECT);
        public static final ForeignKey<AclObjectIdentityRecord, AclSidRecord> ACL_OBJECT_IDENTITY__FOREIGN_FK_3 = createForeignKey(io.ehdev.conrad.db.Keys.ACL_SID_PKEY, AclObjectIdentityTable.ACL_OBJECT_IDENTITY, "acl_object_identity__foreign_fk_3", AclObjectIdentityTable.ACL_OBJECT_IDENTITY.OWNER_SID);
        public static final ForeignKey<CommitDetailsRecord, RepoDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_REPO_DETAILS_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.REPO_DETAILS_PKEY, CommitDetailsTable.COMMIT_DETAILS, "commit_details__commit_details_repo_details_uuid_fkey", CommitDetailsTable.COMMIT_DETAILS.REPO_DETAILS_UUID);
        public static final ForeignKey<CommitDetailsRecord, CommitDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_PARENT_COMMIT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.COMMIT_DETAILS_PKEY, CommitDetailsTable.COMMIT_DETAILS, "commit_details__commit_details_parent_commit_uuid_fkey", CommitDetailsTable.COMMIT_DETAILS.PARENT_COMMIT_UUID);
        public static final ForeignKey<CommitMetadataRecord, CommitDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_COMMIT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.COMMIT_DETAILS_PKEY, CommitMetadataTable.COMMIT_METADATA, "commit_metadata__commit_metadata_commit_uuid_fkey", CommitMetadataTable.COMMIT_METADATA.COMMIT_UUID);
        public static final ForeignKey<CommitMetadataRecord, ProjectDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_PROJECT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.PROJECT_DETAILS_PKEY, CommitMetadataTable.COMMIT_METADATA, "commit_metadata__commit_metadata_project_uuid_fkey", CommitMetadataTable.COMMIT_METADATA.PROJECT_UUID);
        public static final ForeignKey<CommitMetadataRecord, RepoDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_REPO_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.REPO_DETAILS_PKEY, CommitMetadataTable.COMMIT_METADATA, "commit_metadata__commit_metadata_repo_uuid_fkey", CommitMetadataTable.COMMIT_METADATA.REPO_UUID);
        public static final ForeignKey<RepoDetailsRecord, ProjectDetailsRecord> REPO_DETAILS__REPO_DETAILS_PROJECT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.PROJECT_DETAILS_PKEY, RepoDetailsTable.REPO_DETAILS, "repo_details__repo_details_project_uuid_fkey", RepoDetailsTable.REPO_DETAILS.PROJECT_UUID);
        public static final ForeignKey<RepoDetailsRecord, VersionBumpersRecord> REPO_DETAILS__REPO_DETAILS_VERSION_BUMPER_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.VERSION_BUMPERS_PKEY, RepoDetailsTable.REPO_DETAILS, "repo_details__repo_details_version_bumper_uuid_fkey", RepoDetailsTable.REPO_DETAILS.VERSION_BUMPER_UUID);
        public static final ForeignKey<RepositoryTokensRecord, RepoDetailsRecord> REPOSITORY_TOKENS__REPOSITORY_TOKENS_REPO_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.REPO_DETAILS_PKEY, RepositoryTokensTable.REPOSITORY_TOKENS, "repository_tokens__repository_tokens_repo_uuid_fkey", RepositoryTokensTable.REPOSITORY_TOKENS.REPO_UUID);
        public static final ForeignKey<UserTokensRecord, UserDetailsRecord> USER_TOKENS__USER_TOKENS_USER_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.USER_DETAILS_PKEY, UserTokensTable.USER_TOKENS, "user_tokens__user_tokens_user_uuid_fkey", UserTokensTable.USER_TOKENS.USER_UUID);
        public static final ForeignKey<WatcherRecord, UserDetailsRecord> WATCHER__WATCHER_USER_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.USER_DETAILS_PKEY, WatcherTable.WATCHER, "watcher__watcher_user_uuid_fkey", WatcherTable.WATCHER.USER_UUID);
        public static final ForeignKey<WatcherRecord, ProjectDetailsRecord> WATCHER__WATCHER_PROJECT_DETAILS_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.PROJECT_DETAILS_PKEY, WatcherTable.WATCHER, "watcher__watcher_project_details_uuid_fkey", WatcherTable.WATCHER.PROJECT_DETAILS_UUID);
        public static final ForeignKey<WatcherRecord, RepoDetailsRecord> WATCHER__WATCHER_REPO_DETAILS_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.REPO_DETAILS_PKEY, WatcherTable.WATCHER, "watcher__watcher_repo_details_uuid_fkey", WatcherTable.WATCHER.REPO_DETAILS_UUID);
    }
}
