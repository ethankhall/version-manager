package io.ehdev.version.model.commit.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Table(
    name = "version_bumper",
    uniqueConstraints = {@UniqueConstraint(columnNames = "bumper_name")}
)
@Entity
public class VersionBumperModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
}
