package io.ehdev.conrad.service.api.service.model.version;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractVersionResponse extends ResourceSupport {

    @JsonProperty("commitId")
    String commitId;

    @JsonProperty("version")
    String version;

    @JsonProperty("versionParts")
    List<Integer> versionParts;

    @JsonProperty("postfix")
    String postfix;

    public AbstractVersionResponse(String commitId, String version) {
        this.commitId = commitId;
        this.version = version;
        String subVersion = version;
        if (version.contains("-")) {
            int indexOfDash = version.indexOf('-');
            subVersion = version.substring(0, indexOfDash);
            postfix = version.substring(indexOfDash + 1);
        }

        versionParts = new ArrayList<>();
        String[] splitVersion = subVersion.split("\\.");
        for (int i = 0; i < splitVersion.length - 1; i++) {
            versionParts.add(Integer.parseInt(splitVersion[i]));
        }
    }

    public String getCommitId() {
        return commitId;
    }

    public String getVersion() {
        return version;
    }

    public List<Integer> getVersionParts() {
        return versionParts;
    }


    public String getPostfix() {
        return postfix;
    }
}
