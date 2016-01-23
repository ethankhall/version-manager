package io.ehdev.conrad.database.internal.repo;

import io.ehdev.conrad.database.internal.project.ProjectModel;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "repo_model", uniqueConstraints = {@UniqueConstraint(columnNames = {"project_uuid", "repo_name"})})
public class RepoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    UUID id;

    @Column(name = "repo_name")
    String repoName;

    @Column(name = "url")
    String url;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_uuid")
    ProjectModel projectModel;

}
