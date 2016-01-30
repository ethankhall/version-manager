/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.daos;


import io.ehdev.conrad.db.tables.CommitMetadata;
import io.ehdev.conrad.db.tables.records.CommitMetadataRecord;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CommitMetadataDao extends DAOImpl<CommitMetadataRecord, io.ehdev.conrad.db.tables.pojos.CommitMetadata, UUID> {

	/**
	 * Create a new CommitMetadataDao without any configuration
	 */
	public CommitMetadataDao() {
		super(CommitMetadata.COMMIT_METADATA, io.ehdev.conrad.db.tables.pojos.CommitMetadata.class);
	}

	/**
	 * Create a new CommitMetadataDao with an attached configuration
	 */
	public CommitMetadataDao(Configuration configuration) {
		super(CommitMetadata.COMMIT_METADATA, io.ehdev.conrad.db.tables.pojos.CommitMetadata.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UUID getId(io.ehdev.conrad.db.tables.pojos.CommitMetadata object) {
		return object.getUuid();
	}

	/**
	 * Fetch records that have <code>uuid IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.CommitMetadata> fetchByUuid(UUID... values) {
		return fetch(CommitMetadata.COMMIT_METADATA.UUID, values);
	}

	/**
	 * Fetch a unique record that has <code>uuid = value</code>
	 */
	public io.ehdev.conrad.db.tables.pojos.CommitMetadata fetchOneByUuid(UUID value) {
		return fetchOne(CommitMetadata.COMMIT_METADATA.UUID, value);
	}

	/**
	 * Fetch records that have <code>commit_uuid IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.CommitMetadata> fetchByCommitUuid(UUID... values) {
		return fetch(CommitMetadata.COMMIT_METADATA.COMMIT_UUID, values);
	}

	/**
	 * Fetch records that have <code>repo_details_uuid IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.CommitMetadata> fetchByRepoDetailsUuid(UUID... values) {
		return fetch(CommitMetadata.COMMIT_METADATA.REPO_DETAILS_UUID, values);
	}

	/**
	 * Fetch records that have <code>name IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.CommitMetadata> fetchByName(String... values) {
		return fetch(CommitMetadata.COMMIT_METADATA.NAME, values);
	}

	/**
	 * Fetch records that have <code>text IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.CommitMetadata> fetchByText(String... values) {
		return fetch(CommitMetadata.COMMIT_METADATA.TEXT, values);
	}

	/**
	 * Fetch records that have <code>created_at IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.CommitMetadata> fetchByCreatedAt(Timestamp... values) {
		return fetch(CommitMetadata.COMMIT_METADATA.CREATED_AT, values);
	}

	/**
	 * Fetch records that have <code>updated_at IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.CommitMetadata> fetchByUpdatedAt(Timestamp... values) {
		return fetch(CommitMetadata.COMMIT_METADATA.UPDATED_AT, values);
	}
}
