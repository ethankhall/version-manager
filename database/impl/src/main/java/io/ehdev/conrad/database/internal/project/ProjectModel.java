package io.ehdev.conrad.database.internal.project;

import io.ehdev.conrad.database.internal.repo.RepoModel;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "project_model")
public class ProjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    UUID id;

    @Column(name = "project_name")
    String projectName;

    @OneToMany(mappedBy = "projectModel")
    List<RepoModel> repoModels;
}
