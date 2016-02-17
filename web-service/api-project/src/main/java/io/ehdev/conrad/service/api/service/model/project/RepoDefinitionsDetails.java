package io.ehdev.conrad.service.api.service.model.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.springframework.hateoas.ResourceSupport;

public class RepoDefinitionsDetails extends ResourceSupport {
    @JsonPropertyDescription("Name of the project.")
    @JsonProperty("name")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
