package io.ehdev.conrad.client.java;

import feign.RequestLine;

public interface ConradClient {

    @RequestLine("POST /api/v1/project/{projectName}/repo/{repoName}")
    CreateProjectModel
}
