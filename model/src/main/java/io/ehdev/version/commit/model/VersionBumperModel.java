package io.ehdev.version.commit.model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @NotEmpty
    @Column(nullable = false, unique = true, name = "bumper_name")
    private String bumperName;

    public String getBumperName() {
        return bumperName;
    }

    public void setBumperName(String bumperName) {
        this.bumperName = bumperName;
    }
}
