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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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
@Table(name = "version_bumpers", schema = "version_manager_test")
public class VersionBumpers implements Serializable {

    private static final long serialVersionUID = -2038899511;

    private Long   versionBumperId;
    private String bumperName;
    private String className;
    private String description;

    public VersionBumpers() {}

    public VersionBumpers(VersionBumpers value) {
        this.versionBumperId = value.versionBumperId;
        this.bumperName = value.bumperName;
        this.className = value.className;
        this.description = value.description;
    }

    public VersionBumpers(
        Long   versionBumperId,
        String bumperName,
        String className,
        String description
    ) {
        this.versionBumperId = versionBumperId;
        this.bumperName = bumperName;
        this.className = className;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_bumper_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getVersionBumperId() {
        return this.versionBumperId;
    }

    public void setVersionBumperId(Long versionBumperId) {
        this.versionBumperId = versionBumperId;
    }

    @Column(name = "bumper_name", nullable = false, length = 65535)
    @NotNull
    @Size(max = 65535)
    public String getBumperName() {
        return this.bumperName;
    }

    public void setBumperName(String bumperName) {
        this.bumperName = bumperName;
    }

    @Column(name = "class_name", nullable = false, length = 65535)
    @NotNull
    @Size(max = 65535)
    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Column(name = "description", nullable = false, length = 65535)
    @NotNull
    @Size(max = 65535)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("VersionBumpers (");

        sb.append(versionBumperId);
        sb.append(", ").append(bumperName);
        sb.append(", ").append(className);
        sb.append(", ").append(description);

        sb.append(")");
        return sb.toString();
    }
}
