/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.daos;


import io.ehdev.conrad.db.tables.AclSidTable;
import io.ehdev.conrad.db.tables.pojos.AclSid;
import io.ehdev.conrad.db.tables.records.AclSidRecord;

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
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AclSidDao extends DAOImpl<AclSidRecord, AclSid, Long> {

    /**
     * Create a new AclSidDao without any configuration
     */
    public AclSidDao() {
        super(AclSidTable.ACL_SID, AclSid.class);
    }

    /**
     * Create a new AclSidDao with an attached configuration
     */
    public AclSidDao(Configuration configuration) {
        super(AclSidTable.ACL_SID, AclSid.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(AclSid object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<AclSid> fetchById(Long... values) {
        return fetch(AclSidTable.ACL_SID.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public AclSid fetchOneById(Long value) {
        return fetchOne(AclSidTable.ACL_SID.ID, value);
    }

    /**
     * Fetch records that have <code>principal IN (values)</code>
     */
    public List<AclSid> fetchByPrincipal(Boolean... values) {
        return fetch(AclSidTable.ACL_SID.PRINCIPAL, values);
    }

    /**
     * Fetch records that have <code>sid IN (values)</code>
     */
    public List<AclSid> fetchBySid(String... values) {
        return fetch(AclSidTable.ACL_SID.SID, values);
    }
}
