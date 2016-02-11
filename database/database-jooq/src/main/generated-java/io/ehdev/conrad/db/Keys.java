/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db;


import io.ehdev.conrad.db.tables.CommitDetailsTable;
import io.ehdev.conrad.db.tables.CommitMetadataTable;
import io.ehdev.conrad.db.tables.ProjectDetailsTable;
import io.ehdev.conrad.db.tables.RepoDetailsTable;
import io.ehdev.conrad.db.tables.UserDetailsTable;
import io.ehdev.conrad.db.tables.UserPermissionsTable;
import io.ehdev.conrad.db.tables.UserSecurityClientProfileTable;
import io.ehdev.conrad.db.tables.UserTokensTable;
import io.ehdev.conrad.db.tables.VersionBumpersTable;
import io.ehdev.conrad.db.tables.records.CommitDetailsRecord;
import io.ehdev.conrad.db.tables.records.CommitMetadataRecord;
import io.ehdev.conrad.db.tables.records.ProjectDetailsRecord;
import io.ehdev.conrad.db.tables.records.RepoDetailsRecord;
import io.ehdev.conrad.db.tables.records.UserDetailsRecord;
import io.ehdev.conrad.db.tables.records.UserPermissionsRecord;
import io.ehdev.conrad.db.tables.records.UserSecurityClientProfileRecord;
import io.ehdev.conrad.db.tables.records.UserTokensRecord;
import io.ehdev.conrad.db.tables.records.VersionBumpersRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>public</code> 
 * schema
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_PKEY = UniqueKeys0.COMMIT_DETAILS_PKEY;
	public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_COMMIT_ID_REPO_DETAILS_UUID_KEY = UniqueKeys0.COMMIT_DETAILS_COMMIT_ID_REPO_DETAILS_UUID_KEY;
	public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_VERSION_REPO_DETAILS_UUID_KEY = UniqueKeys0.COMMIT_DETAILS_VERSION_REPO_DETAILS_UUID_KEY;
	public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_PKEY = UniqueKeys0.COMMIT_METADATA_PKEY;
	public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_NAME_COMMIT_UUID_KEY = UniqueKeys0.COMMIT_METADATA_NAME_COMMIT_UUID_KEY;
	public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PKEY = UniqueKeys0.PROJECT_DETAILS_PKEY;
	public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PROJECT_NAME_KEY = UniqueKeys0.PROJECT_DETAILS_PROJECT_NAME_KEY;
	public static final UniqueKey<RepoDetailsRecord> REPO_DETAILS_PKEY = UniqueKeys0.REPO_DETAILS_PKEY;
	public static final UniqueKey<RepoDetailsRecord> REPO_DETAILS_PROJECT_NAME_REPO_NAME_KEY = UniqueKeys0.REPO_DETAILS_PROJECT_NAME_REPO_NAME_KEY;
	public static final UniqueKey<UserDetailsRecord> USER_DETAILS_PKEY = UniqueKeys0.USER_DETAILS_PKEY;
	public static final UniqueKey<UserDetailsRecord> USER_DETAILS_USER_NAME_KEY = UniqueKeys0.USER_DETAILS_USER_NAME_KEY;
	public static final UniqueKey<UserPermissionsRecord> USER_PERMISSIONS_PKEY = UniqueKeys0.USER_PERMISSIONS_PKEY;
	public static final UniqueKey<UserPermissionsRecord> USER_PERMISSIONS_PROJECT_NAME_REPO_NAME_KEY = UniqueKeys0.USER_PERMISSIONS_PROJECT_NAME_REPO_NAME_KEY;
	public static final UniqueKey<UserPermissionsRecord> USER_PERMISSIONS_PROJECT_UUID_REPO_DETAILS_UUID_USER_UUID_KEY = UniqueKeys0.USER_PERMISSIONS_PROJECT_UUID_REPO_DETAILS_UUID_USER_UUID_KEY;
	public static final UniqueKey<UserSecurityClientProfileRecord> USER_SECURITY_CLIENT_PROFILE_PKEY = UniqueKeys0.USER_SECURITY_CLIENT_PROFILE_PKEY;
	public static final UniqueKey<UserSecurityClientProfileRecord> USER_SECURITY_CLIENT_PROFILE_PROVIDER_TYPE_PROVIDER_USER_ID_KEY = UniqueKeys0.USER_SECURITY_CLIENT_PROFILE_PROVIDER_TYPE_PROVIDER_USER_ID_KEY;
	public static final UniqueKey<UserTokensRecord> USER_TOKENS_PKEY = UniqueKeys0.USER_TOKENS_PKEY;
	public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_PKEY = UniqueKeys0.VERSION_BUMPERS_PKEY;
	public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_BUMPER_NAME_KEY = UniqueKeys0.VERSION_BUMPERS_BUMPER_NAME_KEY;
	public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_CLASS_NAME_KEY = UniqueKeys0.VERSION_BUMPERS_CLASS_NAME_KEY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final ForeignKey<CommitDetailsRecord, RepoDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_REPO_DETAILS_UUID_FKEY = ForeignKeys0.COMMIT_DETAILS__COMMIT_DETAILS_REPO_DETAILS_UUID_FKEY;
	public static final ForeignKey<CommitDetailsRecord, CommitDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_PARENT_COMMIT_UUID_FKEY = ForeignKeys0.COMMIT_DETAILS__COMMIT_DETAILS_PARENT_COMMIT_UUID_FKEY;
	public static final ForeignKey<CommitMetadataRecord, CommitDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_COMMIT_UUID_FKEY = ForeignKeys0.COMMIT_METADATA__COMMIT_METADATA_COMMIT_UUID_FKEY;
	public static final ForeignKey<CommitMetadataRecord, RepoDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_REPO_DETAILS_UUID_FKEY = ForeignKeys0.COMMIT_METADATA__COMMIT_METADATA_REPO_DETAILS_UUID_FKEY;
	public static final ForeignKey<RepoDetailsRecord, ProjectDetailsRecord> REPO_DETAILS__REPO_DETAILS_PROJECT_UUID_FKEY = ForeignKeys0.REPO_DETAILS__REPO_DETAILS_PROJECT_UUID_FKEY;
	public static final ForeignKey<RepoDetailsRecord, VersionBumpersRecord> REPO_DETAILS__REPO_DETAILS_VERSION_BUMPER_UUID_FKEY = ForeignKeys0.REPO_DETAILS__REPO_DETAILS_VERSION_BUMPER_UUID_FKEY;
	public static final ForeignKey<UserPermissionsRecord, ProjectDetailsRecord> USER_PERMISSIONS__USER_PERMISSIONS_PROJECT_UUID_FKEY = ForeignKeys0.USER_PERMISSIONS__USER_PERMISSIONS_PROJECT_UUID_FKEY;
	public static final ForeignKey<UserPermissionsRecord, RepoDetailsRecord> USER_PERMISSIONS__USER_PERMISSIONS_REPO_DETAILS_UUID_FKEY = ForeignKeys0.USER_PERMISSIONS__USER_PERMISSIONS_REPO_DETAILS_UUID_FKEY;
	public static final ForeignKey<UserPermissionsRecord, UserDetailsRecord> USER_PERMISSIONS__USER_PERMISSIONS_USER_UUID_FKEY = ForeignKeys0.USER_PERMISSIONS__USER_PERMISSIONS_USER_UUID_FKEY;
	public static final ForeignKey<UserSecurityClientProfileRecord, UserDetailsRecord> USER_SECURITY_CLIENT_PROFILE__USER_SECURITY_CLIENT_PROFILE_USER_UUID_FKEY = ForeignKeys0.USER_SECURITY_CLIENT_PROFILE__USER_SECURITY_CLIENT_PROFILE_USER_UUID_FKEY;
	public static final ForeignKey<UserTokensRecord, UserDetailsRecord> USER_TOKENS__USER_TOKENS_USER_UUID_FKEY = ForeignKeys0.USER_TOKENS__USER_TOKENS_USER_UUID_FKEY;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_PKEY = createUniqueKey(CommitDetailsTable.COMMIT_DETAILS, CommitDetailsTable.COMMIT_DETAILS.UUID);
		public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_COMMIT_ID_REPO_DETAILS_UUID_KEY = createUniqueKey(CommitDetailsTable.COMMIT_DETAILS, CommitDetailsTable.COMMIT_DETAILS.COMMIT_ID, CommitDetailsTable.COMMIT_DETAILS.REPO_DETAILS_UUID);
		public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_VERSION_REPO_DETAILS_UUID_KEY = createUniqueKey(CommitDetailsTable.COMMIT_DETAILS, CommitDetailsTable.COMMIT_DETAILS.VERSION, CommitDetailsTable.COMMIT_DETAILS.REPO_DETAILS_UUID);
		public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_PKEY = createUniqueKey(CommitMetadataTable.COMMIT_METADATA, CommitMetadataTable.COMMIT_METADATA.UUID);
		public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_NAME_COMMIT_UUID_KEY = createUniqueKey(CommitMetadataTable.COMMIT_METADATA, CommitMetadataTable.COMMIT_METADATA.NAME, CommitMetadataTable.COMMIT_METADATA.COMMIT_UUID);
		public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PKEY = createUniqueKey(ProjectDetailsTable.PROJECT_DETAILS, ProjectDetailsTable.PROJECT_DETAILS.UUID);
		public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PROJECT_NAME_KEY = createUniqueKey(ProjectDetailsTable.PROJECT_DETAILS, ProjectDetailsTable.PROJECT_DETAILS.PROJECT_NAME);
		public static final UniqueKey<RepoDetailsRecord> REPO_DETAILS_PKEY = createUniqueKey(RepoDetailsTable.REPO_DETAILS, RepoDetailsTable.REPO_DETAILS.UUID);
		public static final UniqueKey<RepoDetailsRecord> REPO_DETAILS_PROJECT_NAME_REPO_NAME_KEY = createUniqueKey(RepoDetailsTable.REPO_DETAILS, RepoDetailsTable.REPO_DETAILS.PROJECT_NAME, RepoDetailsTable.REPO_DETAILS.REPO_NAME);
		public static final UniqueKey<UserDetailsRecord> USER_DETAILS_PKEY = createUniqueKey(UserDetailsTable.USER_DETAILS, UserDetailsTable.USER_DETAILS.UUID);
		public static final UniqueKey<UserDetailsRecord> USER_DETAILS_USER_NAME_KEY = createUniqueKey(UserDetailsTable.USER_DETAILS, UserDetailsTable.USER_DETAILS.USER_NAME);
		public static final UniqueKey<UserPermissionsRecord> USER_PERMISSIONS_PKEY = createUniqueKey(UserPermissionsTable.USER_PERMISSIONS, UserPermissionsTable.USER_PERMISSIONS.UUID);
		public static final UniqueKey<UserPermissionsRecord> USER_PERMISSIONS_PROJECT_NAME_REPO_NAME_KEY = createUniqueKey(UserPermissionsTable.USER_PERMISSIONS, UserPermissionsTable.USER_PERMISSIONS.PROJECT_NAME, UserPermissionsTable.USER_PERMISSIONS.REPO_NAME);
		public static final UniqueKey<UserPermissionsRecord> USER_PERMISSIONS_PROJECT_UUID_REPO_DETAILS_UUID_USER_UUID_KEY = createUniqueKey(UserPermissionsTable.USER_PERMISSIONS, UserPermissionsTable.USER_PERMISSIONS.PROJECT_UUID, UserPermissionsTable.USER_PERMISSIONS.REPO_DETAILS_UUID, UserPermissionsTable.USER_PERMISSIONS.USER_UUID);
		public static final UniqueKey<UserSecurityClientProfileRecord> USER_SECURITY_CLIENT_PROFILE_PKEY = createUniqueKey(UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE, UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE.UUID);
		public static final UniqueKey<UserSecurityClientProfileRecord> USER_SECURITY_CLIENT_PROFILE_PROVIDER_TYPE_PROVIDER_USER_ID_KEY = createUniqueKey(UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE, UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE.PROVIDER_TYPE, UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE.PROVIDER_USER_ID);
		public static final UniqueKey<UserTokensRecord> USER_TOKENS_PKEY = createUniqueKey(UserTokensTable.USER_TOKENS, UserTokensTable.USER_TOKENS.UUID);
		public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_PKEY = createUniqueKey(VersionBumpersTable.VERSION_BUMPERS, VersionBumpersTable.VERSION_BUMPERS.UUID);
		public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_BUMPER_NAME_KEY = createUniqueKey(VersionBumpersTable.VERSION_BUMPERS, VersionBumpersTable.VERSION_BUMPERS.BUMPER_NAME);
		public static final UniqueKey<VersionBumpersRecord> VERSION_BUMPERS_CLASS_NAME_KEY = createUniqueKey(VersionBumpersTable.VERSION_BUMPERS, VersionBumpersTable.VERSION_BUMPERS.CLASS_NAME);
	}

	private static class ForeignKeys0 extends AbstractKeys {
		public static final ForeignKey<CommitDetailsRecord, RepoDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_REPO_DETAILS_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.REPO_DETAILS_PKEY, CommitDetailsTable.COMMIT_DETAILS, CommitDetailsTable.COMMIT_DETAILS.REPO_DETAILS_UUID);
		public static final ForeignKey<CommitDetailsRecord, CommitDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_PARENT_COMMIT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.COMMIT_DETAILS_PKEY, CommitDetailsTable.COMMIT_DETAILS, CommitDetailsTable.COMMIT_DETAILS.PARENT_COMMIT_UUID);
		public static final ForeignKey<CommitMetadataRecord, CommitDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_COMMIT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.COMMIT_DETAILS_PKEY, CommitMetadataTable.COMMIT_METADATA, CommitMetadataTable.COMMIT_METADATA.COMMIT_UUID);
		public static final ForeignKey<CommitMetadataRecord, RepoDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_REPO_DETAILS_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.REPO_DETAILS_PKEY, CommitMetadataTable.COMMIT_METADATA, CommitMetadataTable.COMMIT_METADATA.REPO_DETAILS_UUID);
		public static final ForeignKey<RepoDetailsRecord, ProjectDetailsRecord> REPO_DETAILS__REPO_DETAILS_PROJECT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.PROJECT_DETAILS_PKEY, RepoDetailsTable.REPO_DETAILS, RepoDetailsTable.REPO_DETAILS.PROJECT_UUID);
		public static final ForeignKey<RepoDetailsRecord, VersionBumpersRecord> REPO_DETAILS__REPO_DETAILS_VERSION_BUMPER_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.VERSION_BUMPERS_PKEY, RepoDetailsTable.REPO_DETAILS, RepoDetailsTable.REPO_DETAILS.VERSION_BUMPER_UUID);
		public static final ForeignKey<UserPermissionsRecord, ProjectDetailsRecord> USER_PERMISSIONS__USER_PERMISSIONS_PROJECT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.PROJECT_DETAILS_PKEY, UserPermissionsTable.USER_PERMISSIONS, UserPermissionsTable.USER_PERMISSIONS.PROJECT_UUID);
		public static final ForeignKey<UserPermissionsRecord, RepoDetailsRecord> USER_PERMISSIONS__USER_PERMISSIONS_REPO_DETAILS_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.REPO_DETAILS_PKEY, UserPermissionsTable.USER_PERMISSIONS, UserPermissionsTable.USER_PERMISSIONS.REPO_DETAILS_UUID);
		public static final ForeignKey<UserPermissionsRecord, UserDetailsRecord> USER_PERMISSIONS__USER_PERMISSIONS_USER_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.USER_DETAILS_PKEY, UserPermissionsTable.USER_PERMISSIONS, UserPermissionsTable.USER_PERMISSIONS.USER_UUID);
		public static final ForeignKey<UserSecurityClientProfileRecord, UserDetailsRecord> USER_SECURITY_CLIENT_PROFILE__USER_SECURITY_CLIENT_PROFILE_USER_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.USER_DETAILS_PKEY, UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE, UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE.USER_UUID);
		public static final ForeignKey<UserTokensRecord, UserDetailsRecord> USER_TOKENS__USER_TOKENS_USER_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.USER_DETAILS_PKEY, UserTokensTable.USER_TOKENS, UserTokensTable.USER_TOKENS.USER_UUID);
	}
}
