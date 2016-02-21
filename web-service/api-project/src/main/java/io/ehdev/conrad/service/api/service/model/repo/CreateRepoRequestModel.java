package io.ehdev.conrad.service.api.service.model.repo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.hateoas.ResourceSupport;

public class CreateRepoRequestModel extends ResourceSupport {

    @JsonPropertyDescription("The name of the bumper to use for this project")
    @JsonProperty("bumper")
    @NotBlank
    String bumperName;

    @JsonPropertyDescription("The link to the scm for this project")
    @JsonProperty("scmUrl")
    @URL
    String repoUrl;

    public CreateRepoRequestModel(String bumperName, String repoUrl) {
        this.bumperName = bumperName;
        this.repoUrl = repoUrl;
    }

    public CreateRepoRequestModel() {
    }

    public String getBumperName() {
        return bumperName;
    }

    public void setBumperName(String bumperName) {
        this.bumperName = bumperName;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }
}
