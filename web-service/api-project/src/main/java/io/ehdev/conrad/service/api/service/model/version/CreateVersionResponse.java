package io.ehdev.conrad.service.api.service.model.version;

public class CreateVersionResponse extends AbstractVersionResponse {

    public CreateVersionResponse(String commitId, String version) {
        super(commitId, version);
    }
}
