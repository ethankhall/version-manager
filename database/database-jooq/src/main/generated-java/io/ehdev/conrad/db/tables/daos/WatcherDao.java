/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.daos;


import io.ehdev.conrad.db.tables.WatcherTable;
import io.ehdev.conrad.db.tables.pojos.Watcher;
import io.ehdev.conrad.db.tables.records.WatcherRecord;

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
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WatcherDao extends DAOImpl<WatcherRecord, Watcher, UUID> {

    /**
     * Create a new WatcherDao without any configuration
     */
    public WatcherDao() {
        super(WatcherTable.WATCHER, Watcher.class);
    }

    /**
     * Create a new WatcherDao with an attached configuration
     */
    public WatcherDao(Configuration configuration) {
        super(WatcherTable.WATCHER, Watcher.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UUID getId(Watcher object) {
        return object.getUuid();
    }

    /**
     * Fetch records that have <code>uuid IN (values)</code>
     */
    public List<Watcher> fetchByUuid(UUID... values) {
        return fetch(WatcherTable.WATCHER.UUID, values);
    }

    /**
     * Fetch a unique record that has <code>uuid = value</code>
     */
    public Watcher fetchOneByUuid(UUID value) {
        return fetchOne(WatcherTable.WATCHER.UUID, value);
    }

    /**
     * Fetch records that have <code>user_uuid IN (values)</code>
     */
    public List<Watcher> fetchByUserUuid(UUID... values) {
        return fetch(WatcherTable.WATCHER.USER_UUID, values);
    }

    /**
     * Fetch records that have <code>project_details_uuid IN (values)</code>
     */
    public List<Watcher> fetchByProjectDetailsUuid(UUID... values) {
        return fetch(WatcherTable.WATCHER.PROJECT_DETAILS_UUID, values);
    }

    /**
     * Fetch records that have <code>repo_details_uuid IN (values)</code>
     */
    public List<Watcher> fetchByRepoDetailsUuid(UUID... values) {
        return fetch(WatcherTable.WATCHER.REPO_DETAILS_UUID, values);
    }
}