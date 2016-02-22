package io.ehdev.conrad.service.api.service.model.permissions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class PermissionCreateResponse extends ResourceSupport {

    @JsonProperty("accepted")
    boolean accepted;

    public PermissionCreateResponse(boolean accepted) {
        this.accepted = accepted;
    }

    public PermissionCreateResponse() {
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
