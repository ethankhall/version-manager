/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.daos;


import io.ehdev.conrad.db.tables.RepoDetails;
import io.ehdev.conrad.db.tables.records.RepoDetailsRecord;

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
public class RepoDetailsDao extends DAOImpl<RepoDetailsRecord, io.ehdev.conrad.db.tables.pojos.RepoDetails, UUID> {

	/**
	 * Create a new RepoDetailsDao without any configuration
	 */
	public RepoDetailsDao() {
		super(RepoDetails.REPO_DETAILS, io.ehdev.conrad.db.tables.pojos.RepoDetails.class);
	}

	/**
	 * Create a new RepoDetailsDao with an attached configuration
	 */
	public RepoDetailsDao(Configuration configuration) {
		super(RepoDetails.REPO_DETAILS, io.ehdev.conrad.db.tables.pojos.RepoDetails.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UUID getId(io.ehdev.conrad.db.tables.pojos.RepoDetails object) {
		return object.getUuid();
	}

	/**
	 * Fetch records that have <code>uuid IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.RepoDetails> fetchByUuid(UUID... values) {
		return fetch(RepoDetails.REPO_DETAILS.UUID, values);
	}

	/**
	 * Fetch a unique record that has <code>uuid = value</code>
	 */
	public io.ehdev.conrad.db.tables.pojos.RepoDetails fetchOneByUuid(UUID value) {
		return fetchOne(RepoDetails.REPO_DETAILS.UUID, value);
	}

	/**
	 * Fetch records that have <code>repo_name IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.RepoDetails> fetchByRepoName(String... values) {
		return fetch(RepoDetails.REPO_DETAILS.REPO_NAME, values);
	}

	/**
	 * Fetch records that have <code>project_name IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.RepoDetails> fetchByProjectName(String... values) {
		return fetch(RepoDetails.REPO_DETAILS.PROJECT_NAME, values);
	}

	/**
	 * Fetch records that have <code>project_uuid IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.RepoDetails> fetchByProjectUuid(UUID... values) {
		return fetch(RepoDetails.REPO_DETAILS.PROJECT_UUID, values);
	}

	/**
	 * Fetch records that have <code>version_bumper_uuid IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.RepoDetails> fetchByVersionBumperUuid(UUID... values) {
		return fetch(RepoDetails.REPO_DETAILS.VERSION_BUMPER_UUID, values);
	}

	/**
	 * Fetch records that have <code>url IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.RepoDetails> fetchByUrl(String... values) {
		return fetch(RepoDetails.REPO_DETAILS.URL, values);
	}

	/**
	 * Fetch records that have <code>description IN (values)</code>
	 */
	public List<io.ehdev.conrad.db.tables.pojos.RepoDetails> fetchByDescription(String... values) {
		return fetch(RepoDetails.REPO_DETAILS.DESCRIPTION, values);
	}
}
