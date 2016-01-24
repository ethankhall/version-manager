package io.ehdev.conrad.database.impl.repo;

import io.ehdev.conrad.database.impl.bumper.VersionBumperModel;
import io.ehdev.conrad.database.impl.project.ProjectModel;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "repo_model", uniqueConstraints = {@UniqueConstraint(columnNames = {"project_uuid", "repo_name"})})
public class RepoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    private UUID id;

    @Column(name = "repo_name")
    private String repoName;

    @Column(name = "url")
    private String url;

    @ManyToOne(optional = false)
    @JoinColumn(name = "version_bumper_uuid")
    private VersionBumperModel versionBumperModel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_uuid")
    private ProjectModel projectModel;

    public RepoModel() {
    }

    public RepoModel(String repoName, String url, VersionBumperModel versionBumperModel, ProjectModel projectModel) {
        this.repoName = repoName;
        this.url = url;
        this.versionBumperModel = versionBumperModel;
        this.projectModel = projectModel;
    }

    public UUID getId() {
        return id;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getUrl() {
        return url;
    }

    public VersionBumperModel getVersionBumperModel() {
        return versionBumperModel;
    }

    public ProjectModel getProjectModel() {
        return projectModel;
    }
}
