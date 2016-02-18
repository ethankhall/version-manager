package io.ehdev.conrad.service.api.service.model.version;

public class GetVersionResponse extends AbstractVersionResponse {

    public GetVersionResponse(String commitId, String version) {
        super(commitId, version);
    }
}
