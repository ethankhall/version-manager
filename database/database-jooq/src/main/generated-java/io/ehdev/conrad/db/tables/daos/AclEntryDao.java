/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.daos;


import io.ehdev.conrad.db.tables.AclEntryTable;
import io.ehdev.conrad.db.tables.pojos.AclEntry;
import io.ehdev.conrad.db.tables.records.AclEntryRecord;

import java.util.List;

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
public class AclEntryDao extends DAOImpl<AclEntryRecord, AclEntry, Long> {

    /**
     * Create a new AclEntryDao without any configuration
     */
    public AclEntryDao() {
        super(AclEntryTable.ACL_ENTRY, AclEntry.class);
    }

    /**
     * Create a new AclEntryDao with an attached configuration
     */
    public AclEntryDao(Configuration configuration) {
        super(AclEntryTable.ACL_ENTRY, AclEntry.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(AclEntry object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<AclEntry> fetchById(Long... values) {
        return fetch(AclEntryTable.ACL_ENTRY.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public AclEntry fetchOneById(Long value) {
        return fetchOne(AclEntryTable.ACL_ENTRY.ID, value);
    }

    /**
     * Fetch records that have <code>acl_object_identity IN (values)</code>
     */
    public List<AclEntry> fetchByAclObjectIdentity(Long... values) {
        return fetch(AclEntryTable.ACL_ENTRY.ACL_OBJECT_IDENTITY, values);
    }

    /**
     * Fetch records that have <code>ace_order IN (values)</code>
     */
    public List<AclEntry> fetchByAceOrder(Integer... values) {
        return fetch(AclEntryTable.ACL_ENTRY.ACE_ORDER, values);
    }

    /**
     * Fetch records that have <code>sid IN (values)</code>
     */
    public List<AclEntry> fetchBySid(Long... values) {
        return fetch(AclEntryTable.ACL_ENTRY.SID, values);
    }

    /**
     * Fetch records that have <code>mask IN (values)</code>
     */
    public List<AclEntry> fetchByMask(Integer... values) {
        return fetch(AclEntryTable.ACL_ENTRY.MASK, values);
    }

    /**
     * Fetch records that have <code>granting IN (values)</code>
     */
    public List<AclEntry> fetchByGranting(Boolean... values) {
        return fetch(AclEntryTable.ACL_ENTRY.GRANTING, values);
    }

    /**
     * Fetch records that have <code>audit_success IN (values)</code>
     */
    public List<AclEntry> fetchByAuditSuccess(Boolean... values) {
        return fetch(AclEntryTable.ACL_ENTRY.AUDIT_SUCCESS, values);
    }

    /**
     * Fetch records that have <code>audit_failure IN (values)</code>
     */
    public List<AclEntry> fetchByAuditFailure(Boolean... values) {
        return fetch(AclEntryTable.ACL_ENTRY.AUDIT_FAILURE, values);
    }
}