/**
 * This class is generated by jOOQ
 */
package tech.crom.db.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.jooq.types.UInteger;
import org.jooq.types.ULong;


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
@Table(name = "acl_entry", schema = "version_manager_test", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"acl_object_identity", "ace_order"})
})
public class AclEntry implements Serializable {

    private static final long serialVersionUID = 1106417992;

    private ULong    id;
    private ULong    aclObjectIdentity;
    private Integer  aceOrder;
    private ULong    sid;
    private UInteger mask;
    private Boolean  granting;
    private Boolean  auditSuccess;
    private Boolean  auditFailure;

    public AclEntry() {}

    public AclEntry(AclEntry value) {
        this.id = value.id;
        this.aclObjectIdentity = value.aclObjectIdentity;
        this.aceOrder = value.aceOrder;
        this.sid = value.sid;
        this.mask = value.mask;
        this.granting = value.granting;
        this.auditSuccess = value.auditSuccess;
        this.auditFailure = value.auditFailure;
    }

    public AclEntry(
        ULong    id,
        ULong    aclObjectIdentity,
        Integer  aceOrder,
        ULong    sid,
        UInteger mask,
        Boolean  granting,
        Boolean  auditSuccess,
        Boolean  auditFailure
    ) {
        this.id = id;
        this.aclObjectIdentity = aclObjectIdentity;
        this.aceOrder = aceOrder;
        this.sid = sid;
        this.mask = mask;
        this.granting = granting;
        this.auditSuccess = auditSuccess;
        this.auditFailure = auditFailure;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, precision = 20)
    @NotNull
    public ULong getId() {
        return this.id;
    }

    public void setId(ULong id) {
        this.id = id;
    }

    @Column(name = "acl_object_identity", nullable = false, precision = 20)
    @NotNull
    public ULong getAclObjectIdentity() {
        return this.aclObjectIdentity;
    }

    public void setAclObjectIdentity(ULong aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }

    @Column(name = "ace_order", nullable = false, precision = 10)
    @NotNull
    public Integer getAceOrder() {
        return this.aceOrder;
    }

    public void setAceOrder(Integer aceOrder) {
        this.aceOrder = aceOrder;
    }

    @Column(name = "sid", nullable = false, precision = 20)
    @NotNull
    public ULong getSid() {
        return this.sid;
    }

    public void setSid(ULong sid) {
        this.sid = sid;
    }

    @Column(name = "mask", nullable = false, precision = 10)
    @NotNull
    public UInteger getMask() {
        return this.mask;
    }

    public void setMask(UInteger mask) {
        this.mask = mask;
    }

    @Column(name = "granting", nullable = false)
    @NotNull
    public Boolean getGranting() {
        return this.granting;
    }

    public void setGranting(Boolean granting) {
        this.granting = granting;
    }

    @Column(name = "audit_success", nullable = false)
    @NotNull
    public Boolean getAuditSuccess() {
        return this.auditSuccess;
    }

    public void setAuditSuccess(Boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }

    @Column(name = "audit_failure", nullable = false)
    @NotNull
    public Boolean getAuditFailure() {
        return this.auditFailure;
    }

    public void setAuditFailure(Boolean auditFailure) {
        this.auditFailure = auditFailure;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AclEntry (");

        sb.append(id);
        sb.append(", ").append(aclObjectIdentity);
        sb.append(", ").append(aceOrder);
        sb.append(", ").append(sid);
        sb.append(", ").append(mask);
        sb.append(", ").append(granting);
        sb.append(", ").append(auditSuccess);
        sb.append(", ").append(auditFailure);

        sb.append(")");
        return sb.toString();
    }
}
