/*
 * This file is generated by jOOQ.
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
import javax.validation.constraints.Size;

import org.jooq.types.ULong;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "acl_sid", schema = "version_manager_test", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"sid", "principal"})
})
public class AclSid implements Serializable {

    private static final long serialVersionUID = -513533505;

    private ULong   id;
    private Boolean principal;
    private String  sid;

    public AclSid() {}

    public AclSid(AclSid value) {
        this.id = value.id;
        this.principal = value.principal;
        this.sid = value.sid;
    }

    public AclSid(
        ULong   id,
        Boolean principal,
        String  sid
    ) {
        this.id = id;
        this.principal = principal;
        this.sid = sid;
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

    @Column(name = "principal", nullable = false)
    @NotNull
    public Boolean getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    @Column(name = "sid", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AclSid (");

        sb.append(id);
        sb.append(", ").append(principal);
        sb.append(", ").append(sid);

        sb.append(")");
        return sb.toString();
    }
}
