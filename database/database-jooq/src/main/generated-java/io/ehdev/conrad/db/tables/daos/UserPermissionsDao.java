/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.daos;


import io.ehdev.conrad.db.tables.UserPermissionsTable;
import io.ehdev.conrad.db.tables.pojos.UserPermissions;
import io.ehdev.conrad.db.tables.records.UserPermissionsRecord;

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
public class UserPermissionsDao extends DAOImpl<UserPermissionsRecord, UserPermissions, UUID> {

    /**
     * Create a new UserPermissionsDao without any configuration
     */
    public UserPermissionsDao() {
        super(UserPermissionsTable.USER_PERMISSIONS, UserPermissions.class);
    }

    /**
     * Create a new UserPermissionsDao with an attached configuration
     */
    public UserPermissionsDao(Configuration configuration) {
        super(UserPermissionsTable.USER_PERMISSIONS, UserPermissions.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UUID getId(UserPermissions object) {
        return object.getUuid();
    }

    /**
     * Fetch records that have <code>uuid IN (values)</code>
     */
    public List<UserPermissions> fetchByUuid(UUID... values) {
        return fetch(UserPermissionsTable.USER_PERMISSIONS.UUID, values);
    }

    /**
     * Fetch a unique record that has <code>uuid = value</code>
     */
    public UserPermissions fetchOneByUuid(UUID value) {
        return fetchOne(UserPermissionsTable.USER_PERMISSIONS.UUID, value);
    }

    /**
     * Fetch records that have <code>project_uuid IN (values)</code>
     */
    public List<UserPermissions> fetchByProjectUuid(UUID... values) {
        return fetch(UserPermissionsTable.USER_PERMISSIONS.PROJECT_UUID, values);
    }

    /**
     * Fetch records that have <code>repo_details_uuid IN (values)</code>
     */
    public List<UserPermissions> fetchByRepoDetailsUuid(UUID... values) {
        return fetch(UserPermissionsTable.USER_PERMISSIONS.REPO_DETAILS_UUID, values);
    }

    /**
     * Fetch records that have <code>user_uuid IN (values)</code>
     */
    public List<UserPermissions> fetchByUserUuid(UUID... values) {
        return fetch(UserPermissionsTable.USER_PERMISSIONS.USER_UUID, values);
    }

    /**
     * Fetch records that have <code>permissions IN (values)</code>
     */
    public List<UserPermissions> fetchByPermissions(Integer... values) {
        return fetch(UserPermissionsTable.USER_PERMISSIONS.PERMISSIONS, values);
    }
}
