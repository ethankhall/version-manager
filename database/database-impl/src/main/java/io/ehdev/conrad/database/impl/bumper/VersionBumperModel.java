package io.ehdev.conrad.database.impl.bumper;

import io.ehdev.conrad.database.impl.project.ProjectModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "version_bumper")
public class VersionBumperModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    private UUID id;

    @NotBlank
    @NotEmpty
    @Column(nullable = false, unique = true, name = "class_name")
    private String className;

    @NotBlank
    @NotEmpty
    @Column(nullable = false, name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_uuid")
    private ProjectModel projectModel;

    @NotBlank
    @NotEmpty
    @Column(nullable = false, unique = true, name = "bumper_name")
    private String bumperName;

    public VersionBumperModel(String className, String description, String bumperName) {
        this.className = className;
        this.description = description;
        this.bumperName = bumperName;
    }

    public VersionBumperModel() {
    }

    public UUID getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getDescription() {
        return description;
    }

    public String getBumperName() {
        return bumperName;
    }

    public ProjectModel getProjectModel() {
        return projectModel;
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
