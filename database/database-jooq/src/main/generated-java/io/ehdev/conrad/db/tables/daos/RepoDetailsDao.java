/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.daos;


import io.ehdev.conrad.db.tables.RepoDetailsTable;
import io.ehdev.conrad.db.tables.pojos.RepoDetails;
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
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RepoDetailsDao extends DAOImpl<RepoDetailsRecord, RepoDetails, UUID> {

    /**
     * Create a new RepoDetailsDao without any configuration
     */
    public RepoDetailsDao() {
        super(RepoDetailsTable.REPO_DETAILS, RepoDetails.class);
    }

    /**
     * Create a new RepoDetailsDao with an attached configuration
     */
    public RepoDetailsDao(Configuration configuration) {
        super(RepoDetailsTable.REPO_DETAILS, RepoDetails.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UUID getId(RepoDetails object) {
        return object.getUuid();
    }

    /**
     * Fetch records that have <code>uuid IN (values)</code>
     */
    public List<RepoDetails> fetchByUuid(UUID... values) {
        return fetch(RepoDetailsTable.REPO_DETAILS.UUID, values);
    }

    /**
     * Fetch a unique record that has <code>uuid = value</code>
     */
    public RepoDetails fetchOneByUuid(UUID value) {
        return fetchOne(RepoDetailsTable.REPO_DETAILS.UUID, value);
    }

    /**
     * Fetch records that have <code>repo_name IN (values)</code>
     */
    public List<RepoDetails> fetchByRepoName(String... values) {
        return fetch(RepoDetailsTable.REPO_DETAILS.REPO_NAME, values);
    }

    /**
     * Fetch records that have <code>project_uuid IN (values)</code>
     */
    public List<RepoDetails> fetchByProjectUuid(UUID... values) {
        return fetch(RepoDetailsTable.REPO_DETAILS.PROJECT_UUID, values);
    }

    /**
     * Fetch records that have <code>version_bumper_uuid IN (values)</code>
     */
    public List<RepoDetails> fetchByVersionBumperUuid(UUID... values) {
        return fetch(RepoDetailsTable.REPO_DETAILS.VERSION_BUMPER_UUID, values);
    }

    /**
     * Fetch records that have <code>url IN (values)</code>
     */
    public List<RepoDetails> fetchByUrl(String... values) {
        return fetch(RepoDetailsTable.REPO_DETAILS.URL, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<RepoDetails> fetchByDescription(String... values) {
        return fetch(RepoDetailsTable.REPO_DETAILS.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>public IN (values)</code>
     */
    public List<RepoDetails> fetchByPublic(Boolean... values) {
        return fetch(RepoDetailsTable.REPO_DETAILS.PUBLIC, values);
    }

    /**
     * Fetch records that have <code>security_id IN (values)</code>
     */
    public List<RepoDetails> fetchBySecurityId(Long... values) {
        return fetch(RepoDetailsTable.REPO_DETAILS.SECURITY_ID, values);
    }
}
