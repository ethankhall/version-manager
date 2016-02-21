package io.ehdev.conrad.service.api.service.model.version;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class GetAllVersionsResponse extends ResourceSupport {

    @JsonPropertyDescription("All of the commits for a repository")
    @JsonProperty("commits")
    List<GetAllVersionsCommitResponse> commits = new ArrayList<>();

    @JsonProperty("latest")
    GetAllVersionsCommitResponse latest;

    public List<GetAllVersionsCommitResponse> getCommits() {
        return commits;
    }

    public void addCommits(GetAllVersionsCommitResponse commit) {
        this.commits.add(commit);
    }

    public GetAllVersionsCommitResponse getLatest() {
        return latest;
    }

    public void setLatest(GetAllVersionsCommitResponse latest) {
        this.latest = latest;
    }
}
