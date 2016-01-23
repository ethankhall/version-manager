package io.ehdev.conrad.database.internal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.UUID;

@Table(
    name = "version_bumper",
    uniqueConstraints = {@UniqueConstraint(columnNames = "bumper_name")}
)
@Entity
public class VersionBumperModel implements UniqueModel {

    @Id
    @Type(type="pg-uuid")
    @Column(name = "uuid", unique = true)
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    private UUID id;

    @NotBlank
    @NotEmpty
    @Column(nullable = false, unique = true, name = "class_name")
    private String className;

    @NotBlank
    @NotEmpty
    @Column(nullable = false, name = "description")
    private String description;

    @NotBlank
    @NotEmpty
    @Column(nullable = false, name = "bumper_name")
    private String bumperName;

    public VersionBumperModel(String className, String description, String bumperName) {
        this.className = className;
        this.description = description;
        this.bumperName = bumperName;
    }

    public VersionBumperModel() {
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBumperName() {
        return bumperName;
    }

    public void setBumperName(String bumperName) {
        this.bumperName = bumperName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VersionBumperModel that = (VersionBumperModel) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(className, that.className)
            .append(description, that.description)
            .append(bumperName, that.bumperName)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(className)
            .append(description)
            .append(bumperName)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("className", className)
            .append("description", description)
            .append("bumperName", bumperName)
            .toString();
    }
}
