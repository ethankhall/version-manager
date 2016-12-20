/**
 * This class is generated by jOOQ
 */
package tech.crom.db.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import tech.crom.db.tables.AclSidTable;


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
@Entity
@Table(name = "acl_sid", schema = "version_manager_test", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"sid", "principal"})
})
public class AclSidRecord extends UpdatableRecordImpl<AclSidRecord> implements Record3<ULong, Boolean, String> {

    private static final long serialVersionUID = -1180258747;

    /**
     * Setter for <code>version_manager_test.acl_sid.id</code>.
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>version_manager_test.acl_sid.id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, precision = 20)
    @NotNull
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>version_manager_test.acl_sid.principal</code>.
     */
    public void setPrincipal(Boolean value) {
        set(1, value);
    }

    /**
     * Getter for <code>version_manager_test.acl_sid.principal</code>.
     */
    @Column(name = "principal", nullable = false)
    @NotNull
    public Boolean getPrincipal() {
        return (Boolean) get(1);
    }

    /**
     * Setter for <code>version_manager_test.acl_sid.sid</code>.
     */
    public void setSid(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>version_manager_test.acl_sid.sid</code>.
     */
    @Column(name = "sid", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getSid() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<ULong> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<ULong, Boolean, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<ULong, Boolean, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return AclSidTable.ACL_SID.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field2() {
        return AclSidTable.ACL_SID.PRINCIPAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return AclSidTable.ACL_SID.SID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value2() {
        return getPrincipal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getSid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclSidRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclSidRecord value2(Boolean value) {
        setPrincipal(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclSidRecord value3(String value) {
        setSid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclSidRecord values(ULong value1, Boolean value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AclSidRecord
     */
    public AclSidRecord() {
        super(AclSidTable.ACL_SID);
    }

    /**
     * Create a detached, initialised AclSidRecord
     */
    public AclSidRecord(ULong id, Boolean principal, String sid) {
        super(AclSidTable.ACL_SID);

        set(0, id);
        set(1, principal);
        set(2, sid);
    }
}