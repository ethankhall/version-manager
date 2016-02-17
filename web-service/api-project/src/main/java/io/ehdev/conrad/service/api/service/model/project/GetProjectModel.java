package io.ehdev.conrad.service.api.service.model.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class GetProjectModel extends ResourceSupport {

    @JsonPropertyDescription("Name of the project.")
    @JsonProperty("name")
    String name;

    @JsonPropertyDescription("List of repos attached to the project.")
    @JsonProperty("repos")
    List<RepoDefinitionsDetails> repos = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RepoDefinitionsDetails> getRepos() {
        return repos;
    }

    public void addRepo(RepoDefinitionsDetails repo) {
        this.repos.add(repo);
    }

}
