package io.ehdev.conrad.database.impl.project;

import io.ehdev.conrad.database.impl.repo.RepoModel;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "project_model")
public class ProjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    private UUID id;

    @Column(name = "project_name")
    private String projectName;

    @OneToMany(mappedBy = "projectModel")
    private List<RepoModel> repoModels;

    ProjectModel() {
        //Ignored
    }

    public ProjectModel(String projectName) {
        this.projectName = projectName;
    }

    public UUID getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<RepoModel> getRepoModels() {
        return repoModels;
    }
}
