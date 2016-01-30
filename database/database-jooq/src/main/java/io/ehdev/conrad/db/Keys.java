/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db;


import io.ehdev.conrad.db.tables.CommitDetails;
import io.ehdev.conrad.db.tables.CommitMetadata;
import io.ehdev.conrad.db.tables.ProjectDetails;
import io.ehdev.conrad.db.tables.RepoDetails;
import io.ehdev.conrad.db.tables.UserDetails;
import io.ehdev.conrad.db.tables.UserToken;
import io.ehdev.conrad.db.tables.VersionBumper;
import io.ehdev.conrad.db.tables.records.CommitDetailsRecord;
import io.ehdev.conrad.db.tables.records.CommitMetadataRecord;
import io.ehdev.conrad.db.tables.records.ProjectDetailsRecord;
import io.ehdev.conrad.db.tables.records.RepoDetailsRecord;
import io.ehdev.conrad.db.tables.records.UserDetailsRecord;
import io.ehdev.conrad.db.tables.records.UserTokenRecord;
import io.ehdev.conrad.db.tables.records.VersionBumperRecord;

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
	public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_COMMIT_ID_VCS_REPO_UUID_KEY = UniqueKeys0.COMMIT_DETAILS_COMMIT_ID_VCS_REPO_UUID_KEY;
	public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_VERSION_VCS_REPO_UUID_KEY = UniqueKeys0.COMMIT_DETAILS_VERSION_VCS_REPO_UUID_KEY;
	public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_PKEY = UniqueKeys0.COMMIT_METADATA_PKEY;
	public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_NAME_COMMIT_UUID_KEY = UniqueKeys0.COMMIT_METADATA_NAME_COMMIT_UUID_KEY;
	public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PKEY = UniqueKeys0.PROJECT_DETAILS_PKEY;
	public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PROJECT_NAME_KEY = UniqueKeys0.PROJECT_DETAILS_PROJECT_NAME_KEY;
	public static final UniqueKey<RepoDetailsRecord> REPO_DETAILS_PKEY = UniqueKeys0.REPO_DETAILS_PKEY;
	public static final UniqueKey<RepoDetailsRecord> REPO_DETAILS_PROJECT_NAME_REPO_NAME_KEY = UniqueKeys0.REPO_DETAILS_PROJECT_NAME_REPO_NAME_KEY;
	public static final UniqueKey<UserDetailsRecord> USER_DETAILS_PKEY = UniqueKeys0.USER_DETAILS_PKEY;
	public static final UniqueKey<UserTokenRecord> USER_TOKEN_PKEY = UniqueKeys0.USER_TOKEN_PKEY;
	public static final UniqueKey<VersionBumperRecord> VERSION_BUMPER_PKEY = UniqueKeys0.VERSION_BUMPER_PKEY;
	public static final UniqueKey<VersionBumperRecord> VERSION_BUMPER_BUMPER_NAME_KEY = UniqueKeys0.VERSION_BUMPER_BUMPER_NAME_KEY;
	public static final UniqueKey<VersionBumperRecord> VERSION_BUMPER_CLASS_NAME_KEY = UniqueKeys0.VERSION_BUMPER_CLASS_NAME_KEY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final ForeignKey<CommitDetailsRecord, RepoDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_VCS_REPO_UUID_FKEY = ForeignKeys0.COMMIT_DETAILS__COMMIT_DETAILS_VCS_REPO_UUID_FKEY;
	public static final ForeignKey<CommitDetailsRecord, CommitDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_PARENT_COMMIT_UUID_FKEY = ForeignKeys0.COMMIT_DETAILS__COMMIT_DETAILS_PARENT_COMMIT_UUID_FKEY;
	public static final ForeignKey<CommitMetadataRecord, CommitDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_COMMIT_UUID_FKEY = ForeignKeys0.COMMIT_METADATA__COMMIT_METADATA_COMMIT_UUID_FKEY;
	public static final ForeignKey<CommitMetadataRecord, RepoDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_REPO_DETAILS_UUID_FKEY = ForeignKeys0.COMMIT_METADATA__COMMIT_METADATA_REPO_DETAILS_UUID_FKEY;
	public static final ForeignKey<RepoDetailsRecord, ProjectDetailsRecord> REPO_DETAILS__REPO_DETAILS_PROJECT_UUID_FKEY = ForeignKeys0.REPO_DETAILS__REPO_DETAILS_PROJECT_UUID_FKEY;
	public static final ForeignKey<RepoDetailsRecord, VersionBumperRecord> REPO_DETAILS__REPO_DETAILS_VERSION_BUMPER_UUID_FKEY = ForeignKeys0.REPO_DETAILS__REPO_DETAILS_VERSION_BUMPER_UUID_FKEY;
	public static final ForeignKey<UserTokenRecord, UserDetailsRecord> USER_TOKEN__USER_TOKEN_USER_UUID_FKEY = ForeignKeys0.USER_TOKEN__USER_TOKEN_USER_UUID_FKEY;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_PKEY = createUniqueKey(CommitDetails.COMMIT_DETAILS, CommitDetails.COMMIT_DETAILS.UUID);
		public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_COMMIT_ID_VCS_REPO_UUID_KEY = createUniqueKey(CommitDetails.COMMIT_DETAILS, CommitDetails.COMMIT_DETAILS.COMMIT_ID, CommitDetails.COMMIT_DETAILS.VCS_REPO_UUID);
		public static final UniqueKey<CommitDetailsRecord> COMMIT_DETAILS_VERSION_VCS_REPO_UUID_KEY = createUniqueKey(CommitDetails.COMMIT_DETAILS, CommitDetails.COMMIT_DETAILS.VERSION, CommitDetails.COMMIT_DETAILS.VCS_REPO_UUID);
		public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_PKEY = createUniqueKey(CommitMetadata.COMMIT_METADATA, CommitMetadata.COMMIT_METADATA.UUID);
		public static final UniqueKey<CommitMetadataRecord> COMMIT_METADATA_NAME_COMMIT_UUID_KEY = createUniqueKey(CommitMetadata.COMMIT_METADATA, CommitMetadata.COMMIT_METADATA.NAME, CommitMetadata.COMMIT_METADATA.COMMIT_UUID);
		public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PKEY = createUniqueKey(ProjectDetails.PROJECT_DETAILS, ProjectDetails.PROJECT_DETAILS.UUID);
		public static final UniqueKey<ProjectDetailsRecord> PROJECT_DETAILS_PROJECT_NAME_KEY = createUniqueKey(ProjectDetails.PROJECT_DETAILS, ProjectDetails.PROJECT_DETAILS.PROJECT_NAME);
		public static final UniqueKey<RepoDetailsRecord> REPO_DETAILS_PKEY = createUniqueKey(RepoDetails.REPO_DETAILS, RepoDetails.REPO_DETAILS.UUID);
		public static final UniqueKey<RepoDetailsRecord> REPO_DETAILS_PROJECT_NAME_REPO_NAME_KEY = createUniqueKey(RepoDetails.REPO_DETAILS, RepoDetails.REPO_DETAILS.PROJECT_NAME, RepoDetails.REPO_DETAILS.REPO_NAME);
		public static final UniqueKey<UserDetailsRecord> USER_DETAILS_PKEY = createUniqueKey(UserDetails.USER_DETAILS, UserDetails.USER_DETAILS.UUID);
		public static final UniqueKey<UserTokenRecord> USER_TOKEN_PKEY = createUniqueKey(UserToken.USER_TOKEN, UserToken.USER_TOKEN.UUID);
		public static final UniqueKey<VersionBumperRecord> VERSION_BUMPER_PKEY = createUniqueKey(VersionBumper.VERSION_BUMPER, VersionBumper.VERSION_BUMPER.UUID);
		public static final UniqueKey<VersionBumperRecord> VERSION_BUMPER_BUMPER_NAME_KEY = createUniqueKey(VersionBumper.VERSION_BUMPER, VersionBumper.VERSION_BUMPER.BUMPER_NAME);
		public static final UniqueKey<VersionBumperRecord> VERSION_BUMPER_CLASS_NAME_KEY = createUniqueKey(VersionBumper.VERSION_BUMPER, VersionBumper.VERSION_BUMPER.CLASS_NAME);
	}

	private static class ForeignKeys0 extends AbstractKeys {
		public static final ForeignKey<CommitDetailsRecord, RepoDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_VCS_REPO_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.REPO_DETAILS_PKEY, CommitDetails.COMMIT_DETAILS, CommitDetails.COMMIT_DETAILS.VCS_REPO_UUID);
		public static final ForeignKey<CommitDetailsRecord, CommitDetailsRecord> COMMIT_DETAILS__COMMIT_DETAILS_PARENT_COMMIT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.COMMIT_DETAILS_PKEY, CommitDetails.COMMIT_DETAILS, CommitDetails.COMMIT_DETAILS.PARENT_COMMIT_UUID);
		public static final ForeignKey<CommitMetadataRecord, CommitDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_COMMIT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.COMMIT_DETAILS_PKEY, CommitMetadata.COMMIT_METADATA, CommitMetadata.COMMIT_METADATA.COMMIT_UUID);
		public static final ForeignKey<CommitMetadataRecord, RepoDetailsRecord> COMMIT_METADATA__COMMIT_METADATA_REPO_DETAILS_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.REPO_DETAILS_PKEY, CommitMetadata.COMMIT_METADATA, CommitMetadata.COMMIT_METADATA.REPO_DETAILS_UUID);
		public static final ForeignKey<RepoDetailsRecord, ProjectDetailsRecord> REPO_DETAILS__REPO_DETAILS_PROJECT_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.PROJECT_DETAILS_PKEY, RepoDetails.REPO_DETAILS, RepoDetails.REPO_DETAILS.PROJECT_UUID);
		public static final ForeignKey<RepoDetailsRecord, VersionBumperRecord> REPO_DETAILS__REPO_DETAILS_VERSION_BUMPER_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.VERSION_BUMPER_PKEY, RepoDetails.REPO_DETAILS, RepoDetails.REPO_DETAILS.VERSION_BUMPER_UUID);
		public static final ForeignKey<UserTokenRecord, UserDetailsRecord> USER_TOKEN__USER_TOKEN_USER_UUID_FKEY = createForeignKey(io.ehdev.conrad.db.Keys.USER_DETAILS_PKEY, UserToken.USER_TOKEN, UserToken.USER_TOKEN.USER_UUID);
	}
}
